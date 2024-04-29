package com.devkraft.demodataprivacyvault.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.crypto.SecretKey;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.*;
import java.security.KeyPair;
import java.util.Map;

@Entity
@Table(name = "encrypted_data_details")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EncryptedDataDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "entry_id")
    private Long entryId;

    @Column(name = "app_id")
    private String appId;

    @NotEmpty
    @Column(name = "app_url")
    private String appUrl;

    @NotEmpty
    @Column(name = "app_owner")
    private String appOwner;

    @NotEmpty
    private String password;

    @ElementCollection
    @MapKeyColumn(name = "data_key")
    @Column(name = "data_value", length = 1000)
    @CollectionTable(name = "encrypted_data", joinColumns = {
            @JoinColumn(name = "entry_id", referencedColumnName = "entry_id")
    })
    private Map<String, String> encryptedData;

    @JsonIgnore
    private KeyPair keyPair;

}
