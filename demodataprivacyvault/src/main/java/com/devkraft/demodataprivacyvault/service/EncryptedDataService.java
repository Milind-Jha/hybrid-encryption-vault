package com.devkraft.demodataprivacyvault.service;

import com.devkraft.demodataprivacyvault.bean.EncryptedDataDetails;
import com.devkraft.demodataprivacyvault.response.EncryptedDetailsResponse;

import javax.validation.Valid;

public interface EncryptedDataService {

    public EncryptedDetailsResponse createEncryptedDataDetails(EncryptedDataDetails detailsToEncrypt) throws Exception;
    public EncryptedDetailsResponse retriveDecryptedDataDetails(Long entryId,String password) throws Exception;

    public EncryptedDetailsResponse addEncryptedDataDetails(EncryptedDataDetails detailsToEncrypt) throws Exception;

    public EncryptedDetailsResponse updateEncryptedDataDetails(Long entryId,EncryptedDataDetails detailsToEncrypt) throws Exception;

    public void deleteEncryptedDataDetails(Long entryId,String password) throws Exception;


}
