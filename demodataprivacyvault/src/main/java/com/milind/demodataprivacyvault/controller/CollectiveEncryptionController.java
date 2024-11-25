package com.milind.demodataprivacyvault.controller;

import com.milind.demodataprivacyvault.bean.EncryptedDataDetails;
import com.milind.demodataprivacyvault.dao.EncryptedDataDAO;
import com.milind.demodataprivacyvault.service.EncryptedDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
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
