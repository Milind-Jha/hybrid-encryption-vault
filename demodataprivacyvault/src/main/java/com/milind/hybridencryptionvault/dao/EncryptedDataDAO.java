package com.milind.hybridencryptionvault.dao;

import com.milind.hybridencryptionvault.bean.EncryptedDataDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncryptedDataDAO extends JpaRepository<EncryptedDataDetails,String> {

    List<EncryptedDataDetails> findByAppUrl(String appUrl);
    List<EncryptedDataDetails> findEntryIdsByAppId(String appId);
}
