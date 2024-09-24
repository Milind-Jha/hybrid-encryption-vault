package com.milind.demodataprivacyvault.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.crypto.SecretKey;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.*;
import java.security.KeyPair;
import java.util.Map;
@Entity
@Table(name = "encrypted_data_details",  schema = "public",
        indexes = {@Index(columnList = "app_id, appOwner")})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EncryptedDataDetails implements Serializable {


    @Id
    @Column(name = "entry_id")
    private String entryId;

    @Column(name = "app_id")
    private String appId;

    @NotEmpty
    private String appUrl;

    @NotEmpty
    private String appOwner;

    @NotEmpty
    private String password;

    @ElementCollection
    @MapKeyColumn(name = "data_key")
    @Column(name = "data_value")
    @CollectionTable(name = "encrypted_data", joinColumns = {
            @JoinColumn(name = "app_id", referencedColumnName = "app_id"),
            @JoinColumn(name = "entry_id", referencedColumnName = "entry_id")
    })
    private Map<String, String> encryptedData;

    @JsonIgnore
    private KeyPair keyPair;

}
