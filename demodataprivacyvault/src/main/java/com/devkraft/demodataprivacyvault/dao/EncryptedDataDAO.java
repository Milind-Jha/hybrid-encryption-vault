package com.devkraft.demodataprivacyvault.dao;

import com.devkraft.demodataprivacyvault.bean.EncryptedDataDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncryptedDataDAO extends JpaRepository<EncryptedDataDetails,Long> {

    List<EncryptedDataDetails> findByAppUrl(String appUrl);
    List<String> findEntryIdByAppId(String appId);
}
