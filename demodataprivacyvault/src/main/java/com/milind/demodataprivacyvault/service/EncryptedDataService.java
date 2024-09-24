package com.milind.demodataprivacyvault.service;

import com.milind.demodataprivacyvault.bean.EncryptedDataDetails;
import com.milind.demodataprivacyvault.response.EncryptedDetailsResponse;

public interface EncryptedDataService {

    public EncryptedDetailsResponse createEncryptedDataDetails(EncryptedDataDetails detailsToEncrypt) throws Exception;
    public EncryptedDetailsResponse retriveDecryptedDataDetails(String entryId,String password) throws Exception;

    public EncryptedDetailsResponse addEncryptedDataDetails(EncryptedDataDetails detailsToEncrypt) throws Exception;

    public EncryptedDetailsResponse updateEncryptedDataDetails(String entryId,EncryptedDataDetails detailsToEncrypt) throws Exception;

    public void deleteEncryptedDataDetails(String entryId,String password) throws Exception;


}
