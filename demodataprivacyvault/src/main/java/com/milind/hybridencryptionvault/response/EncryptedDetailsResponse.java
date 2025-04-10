package com.milind.hybridencryptionvault.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EncryptedDetailsResponse {

    private String entryId;

    private String appId;

    private String appUrl;

    private String appOwner;
    private Map<String, String> encryptedData;

}
