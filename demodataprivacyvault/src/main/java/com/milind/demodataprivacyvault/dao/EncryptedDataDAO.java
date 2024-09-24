package com.milind.demodataprivacyvault.dao;

import com.milind.demodataprivacyvault.bean.EncryptedDataDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncryptedDataDAO extends JpaRepository<EncryptedDataDetails,String> {

    List<EncryptedDataDetails> findByAppUrl(String appUrl);
    List<String> findEntryIdByAppId(String appId);
}
