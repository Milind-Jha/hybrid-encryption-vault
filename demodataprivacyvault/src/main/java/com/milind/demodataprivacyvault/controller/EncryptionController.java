package com.milind.demodataprivacyvault.controller;

import com.milind.demodataprivacyvault.bean.EncryptedDataDetails;
import com.milind.demodataprivacyvault.response.EncryptedDetailsResponse;
import com.milind.demodataprivacyvault.service.EncryptedDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController("/encryption")
public class EncryptionController {
    private EncryptedDataService encryptedDataService;
    public EncryptionController(EncryptedDataService encryptedDataService) {
        this.encryptedDataService = encryptedDataService;
    }
    @PostMapping(value = "/createEncryptedData" , produces = "application/json")
    public ResponseEntity<EncryptedDetailsResponse> createEncryptedData(
            @Valid@RequestBody EncryptedDataDetails dataToEncrypt) throws Exception {
        return  ResponseEntity.status(HttpStatus.CREATED).body(
                encryptedDataService.createEncryptedDataDetails(dataToEncrypt));
    }
    @GetMapping(value = "/getDecryptedData/{entryId}", produces = "application/json")
    public ResponseEntity<EncryptedDetailsResponse> getDecryptedData(@PathVariable String entryId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(
                encryptedDataService.retriveDecryptedDataDetails(entryId));
    }
    @PostMapping(value = "addEncryptedData")
    public ResponseEntity<EncryptedDetailsResponse> addEncryptedData(
            @Valid @RequestBody EncryptedDataDetails dataToEncrypt) throws Exception {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    encryptedDataService.addEncryptedDataDetails(dataToEncrypt));
    }
    @PutMapping(value = "/updateEncryptedData/{entryId}")
    public ResponseEntity<EncryptedDetailsResponse> updateEncryptedData(@PathVariable String entryId,
                                 @RequestBody EncryptedDataDetails newDetailstoEncrypt) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(
                encryptedDataService.updateEncryptedDataDetails(entryId,newDetailstoEncrypt));
    }
    @DeleteMapping(value = "/deleteEncryptedData/{entryId}")
    public ResponseEntity<EncryptedDetailsResponse> deleteEncryptedData(@PathVariable String entryId) throws Exception {
        encryptedDataService.deleteEncryptedDataDetails(entryId);
        return ResponseEntity.noContent().build();
    }
}
