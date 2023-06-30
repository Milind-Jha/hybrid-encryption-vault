package com.devkraft.demodataprivacyvault.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKey;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.security.KeyPair;
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
