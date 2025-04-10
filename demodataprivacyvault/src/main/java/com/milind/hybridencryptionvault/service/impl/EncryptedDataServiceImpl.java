package com.milind.hybridencryptionvault.service.impl;

import com.milind.hybridencryptionvault.bean.EncryptedDataDetails;
import com.milind.hybridencryptionvault.dao.EncryptedDataDAO;
import com.milind.hybridencryptionvault.response.EncryptedDetailsResponse;
import com.milind.hybridencryptionvault.service.EncryptedDataService;
import com.milind.hybridencryptionvault.util.DataEncryption;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EncryptedDataServiceImpl implements EncryptedDataService {

    private EncryptedDataDAO encryptedDataDAO;
    private ModelMapper modelMapper;
    private DataEncryption dataEncryption;

    public EncryptedDataServiceImpl(EncryptedDataDAO encryptedDataDAO, DataEncryption dataEncryption,
                                    ModelMapper modelMapper) {
        synchronized (this) {
            this.encryptedDataDAO = encryptedDataDAO;
            this.dataEncryption = dataEncryption;
            this.modelMapper = modelMapper;
        }
    }

    @Override
    public EncryptedDetailsResponse createEncryptedDataDetails(EncryptedDataDetails detailsToEncrypt) throws
            Exception {
        List<EncryptedDataDetails> detailsByUrl = encryptedDataDAO.findByAppUrl(detailsToEncrypt.getAppUrl());
        System.out.println("AppUrl : "+detailsToEncrypt.getAppUrl());
        System.out.println("details to encrypt list : "+detailsByUrl);
        if(!detailsByUrl.isEmpty()){
            throw new Exception("Url already registered");
        }
        detailsToEncrypt.setAppId(UUID.randomUUID().toString());
        detailsToEncrypt.setEntryId(UUID.randomUUID().toString());
        Map<String, String> normalData = detailsToEncrypt.getEncryptedData();
        KeyPair keyPair = dataEncryption.generateKeyPair();
        System.out.println(dataEncryption);
        detailsToEncrypt.setKeyPair(keyPair);
        Map<String, String> encryptedValues = normalData.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    try {
                        System.out.println(dataEncryption);
                        return dataEncryption.encryptData(entry.getValue(), keyPair.getPublic(), keyPair.getPrivate());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ));
        detailsToEncrypt.setEncryptedData(encryptedValues);
        encryptedDataDAO.save(detailsToEncrypt);
        EncryptedDetailsResponse map = modelMapper.map(detailsToEncrypt, EncryptedDetailsResponse.class);
        System.out.println(map);
        return map;
    }

    @Override
    public EncryptedDetailsResponse addEncryptedDataDetails(EncryptedDataDetails detailsToEncrypt) throws
            Exception {
        List<EncryptedDataDetails> detailsByUrl = encryptedDataDAO.findByAppUrl(detailsToEncrypt.getAppUrl());
        if(detailsByUrl.isEmpty()){
            throw new Exception("Url not registered");
        }
        detailsToEncrypt.setAppId(detailsByUrl.get(0).getAppId());
        detailsToEncrypt.setEntryId(UUID.randomUUID().toString());
        Map<String, String> previousData = detailsToEncrypt.getEncryptedData();
        KeyPair keyPair = dataEncryption.generateKeyPair();
        System.out.println(dataEncryption);
        detailsToEncrypt.setKeyPair(keyPair);
        Map<String, String> encryptedValues = previousData.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    try {
                        return dataEncryption.encryptData(entry.getValue(), keyPair.getPublic(), keyPair.getPrivate());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ));
        detailsToEncrypt.setEncryptedData(encryptedValues);
        encryptedDataDAO.save(detailsToEncrypt);
        return  modelMapper.map(detailsToEncrypt,EncryptedDetailsResponse.class);
    }

    @Override
    public EncryptedDetailsResponse retriveDecryptedDataDetails(String entryId) throws Exception {
        EncryptedDataDetails encryptedDataDetails = encryptedDataDAO.findById(entryId)
                .orElseThrow(() -> new Exception("Data not found Invalid entryId"));
        Map<String, String> encryptedData = encryptedDataDetails.getEncryptedData();
        KeyPair keyPair = encryptedDataDetails.getKeyPair();
        Map<String, String> decryptedValues = encryptedData.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    try {
                        return dataEncryption.decryptData(entry.getValue(), keyPair.getPrivate(), keyPair.getPublic());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ));
        System.out.println(dataEncryption);
        encryptedDataDetails.setEncryptedData(decryptedValues);
        return modelMapper.map(encryptedDataDetails,EncryptedDetailsResponse.class);
    }

    @Override
    public EncryptedDetailsResponse updateEncryptedDataDetails(String entryId, EncryptedDataDetails detailsToEncrypt) throws Exception {
        EncryptedDataDetails encryptedDataDetails = encryptedDataDAO.findById(entryId)
                .orElseThrow(() -> new Exception("Data not found Invalid entryId"));
        detailsToEncrypt.setEntryId(entryId);
        detailsToEncrypt.setAppUrl(encryptedDataDetails.getAppUrl());
        detailsToEncrypt.setAppOwner(encryptedDataDetails.getAppOwner());
        detailsToEncrypt.setAppId(encryptedDataDetails.getAppId());
        Map<String, String> normalData = detailsToEncrypt.getEncryptedData();
        KeyPair keyPair = dataEncryption.generateKeyPair();
        detailsToEncrypt.setKeyPair(keyPair);
        Map<String, String> encryptedValues = normalData.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    try {
                        return dataEncryption.encryptData(entry.getValue(), keyPair.getPublic(),
                                keyPair.getPrivate());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ));
        System.out.println(dataEncryption);
        detailsToEncrypt.setEncryptedData(encryptedValues);
        encryptedDataDAO.save(detailsToEncrypt);
        return modelMapper.map(detailsToEncrypt,EncryptedDetailsResponse.class);
    }

    @Override
    public void deleteEncryptedDataDetails(String entryId) throws Exception {
        EncryptedDataDetails encryptedDataDetails = encryptedDataDAO.findById(entryId)
                .orElseThrow(() -> new Exception("Data not found Invalid entryId"));
        encryptedDataDAO.deleteById(entryId);
    }

    @Override
    public List<Map<String, String>> getDecryptedDataForAppId(String appId) {
        List<Map<String,String>> mapList = new ArrayList<>();
        List<EncryptedDataDetails> encryptedDataDetails = encryptedDataDAO.findEntryIdsByAppId(appId);
        List<Map<String,String>> data = new ArrayList<>();
        encryptedDataDetails.forEach(encryptedDataDetail -> {
            mapList.add(encryptedDataDetail.getEncryptedData());
        });
        return mapList;
    }
}
