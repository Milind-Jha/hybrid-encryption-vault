package com.milind.hybridencryptionvault.controller;

import com.milind.hybridencryptionvault.dao.EncryptedDataDAO;
import com.milind.hybridencryptionvault.service.EncryptedDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("encryption")
public class CollectiveEncryptionController {

    private final EncryptedDataService encryptedDataService;
    private final EncryptedDataDAO encryptedDataDAO;

    public CollectiveEncryptionController(EncryptedDataService encryptedDataService, EncryptedDataDAO encryptedDataDAO) {
        this.encryptedDataService = encryptedDataService;
        this.encryptedDataDAO=encryptedDataDAO;
    }

    @GetMapping("/getAllDecryptedDataForApplication/{appId}")
    public ResponseEntity<List<Map<String,String>>> getDecryptedDataForAppId(@PathVariable String appId){
        System.out.println(appId);
        List<Map<String, String>> mapList = encryptedDataService.getDecryptedDataForAppId(appId);
        return ResponseEntity.status(HttpStatus.OK).body(mapList);
    }
}
