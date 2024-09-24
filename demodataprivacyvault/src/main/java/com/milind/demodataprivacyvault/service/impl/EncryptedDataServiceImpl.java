package com.milind.demodataprivacyvault.service.impl;

import com.milind.demodataprivacyvault.bean.EncryptedDataDetails;
import com.milind.demodataprivacyvault.dao.EncryptedDataDAO;
import com.milind.demodataprivacyvault.response.EncryptedDetailsResponse;
import com.milind.demodataprivacyvault.service.EncryptedDataService;
import com.milind.demodataprivacyvault.util.DataEncryption;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EncryptedDataServiceImpl implements EncryptedDataService {

    private EncryptedDataDAO encryptedDataDAO;

    private ModelMapper modelMapper;
    private DataEncryption dataEncryption;

    public EncryptedDataServiceImpl(EncryptedDataDAO encryptedDataDAO, DataEncryption dataEncryption,
                                    ModelMapper modelMapper) {
        this.encryptedDataDAO = encryptedDataDAO;
        this.dataEncryption = dataEncryption;
        this.modelMapper = modelMapper;
    }

    @Override
    public EncryptedDetailsResponse createEncryptedDataDetails(EncryptedDataDetails detailsToEncrypt) throws
            Exception {
        List<EncryptedDataDetails> detailsByUrl = encryptedDataDAO.findByAppUrl(detailsToEncrypt.getAppUrl());
        System.out.println("details to encrypt list : "+detailsByUrl);
        if(!detailsByUrl.isEmpty()){
            throw new Exception("Url already registered");
        }
        detailsToEncrypt.setAppId(UUID.randomUUID().toString());
        detailsToEncrypt.setEntryId(UUID.randomUUID().toString());
        Map<String, String> normalData = detailsToEncrypt.getEncryptedData();
        KeyPair keyPair = dataEncryption.generateKeyPair();
        detailsToEncrypt.setKeyPair(keyPair);
        Map<String, String> encryptedValues = normalData.entrySet().stream().collect(Collectors.toMap(
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
        if(!detailsToEncrypt.getPassword().equals(detailsByUrl.get(0).getPassword()))
            throw new Exception("Wrong Password");
        detailsToEncrypt.setAppId(detailsByUrl.get(0).getAppId());
        detailsToEncrypt.setEntryId(UUID.randomUUID().toString());
        Map<String, String> normalData = detailsToEncrypt.getEncryptedData();
        KeyPair keyPair = dataEncryption.generateKeyPair();
        detailsToEncrypt.setKeyPair(keyPair);
        Map<String, String> encryptedValues = normalData.entrySet().stream().collect(Collectors.toMap(
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
    public EncryptedDetailsResponse retriveDecryptedDataDetails(String entryId,String password) throws Exception {
        EncryptedDataDetails encryptedDataDetails = encryptedDataDAO.findById(entryId)
                .orElseThrow(() -> new Exception("Data not found Invalid entryId"));
        String[] split = password.split("\"");
        String passwordFromJson = split[split.length-2];
        if(!encryptedDataDetails.getPassword().equals(password) &&
                !encryptedDataDetails.getPassword().equals(passwordFromJson)){
            System.out.println(encryptedDataDetails.getPassword()+ " : "+password);
            throw new Exception("Wrong Password");
        }
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

        if(!encryptedDataDetails.getPassword().equals(detailsToEncrypt.getPassword()))
            throw new Exception("invalid password provided.");

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
        detailsToEncrypt.setEncryptedData(encryptedValues);
        encryptedDataDAO.save(detailsToEncrypt);
        return modelMapper.map(detailsToEncrypt,EncryptedDetailsResponse.class);
    }

    @Override
    public void deleteEncryptedDataDetails(String entryId, String password) throws Exception {
        EncryptedDataDetails encryptedDataDetails = encryptedDataDAO.findById(entryId)
                .orElseThrow(() -> new Exception("Data not found Invalid entryId"));
        String[] split = password.split("\"");
        String passwordFromJson = split[split.length-2];
        if(!encryptedDataDetails.getPassword().equals(password) &&
                !encryptedDataDetails.getPassword().equals(passwordFromJson)){
            System.out.println(encryptedDataDetails.getPassword()+ " : "+password);
            throw new Exception("Wrong Password");
        }
        encryptedDataDAO.deleteById(entryId);
    }
}
