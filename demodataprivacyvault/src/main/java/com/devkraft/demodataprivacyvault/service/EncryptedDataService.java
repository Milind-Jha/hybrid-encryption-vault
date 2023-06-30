package com.devkraft.demodataprivacyvault.service;

import com.devkraft.demodataprivacyvault.bean.EncryptedDataDetails;
import com.devkraft.demodataprivacyvault.response.EncryptedDetailsResponse;

import javax.validation.Valid;

public interface EncryptedDataService {

    public EncryptedDetailsResponse createEncryptedDataDetails(EncryptedDataDetails detailsToEncrypt) throws Exception;
    public EncryptedDetailsResponse retriveDecryptedDataDetails(String entryId,String password) throws Exception;

    public EncryptedDetailsResponse addEncryptedDataDetails(EncryptedDataDetails detailsToEncrypt) throws Exception;

    public EncryptedDetailsResponse updateEncryptedDataDetails(String entryId,EncryptedDataDetails detailsToEncrypt) throws Exception;

    public void deleteEncryptedDataDetails(String entryId,String password) throws Exception;


}
