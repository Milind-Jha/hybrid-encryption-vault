package com.milind.hybridencryptionvault.bean;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.security.KeyPair;
import java.util.Map;

@Entity
@Table(name = "encrypted_data_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EncryptedDataDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "key_pair")
    private KeyPair keyPair;

    @ElementCollection
    @MapKeyColumn(name = "data_key")
    @Column(name = "data_value")
    @CollectionTable(name = "encrypted_data", joinColumns = {
            @JoinColumn(name = "app_id", referencedColumnName = "app_id"),
            @JoinColumn(name = "entry_id", referencedColumnName = "entry_id")
    })
    private Map<String, String> encryptedData;

    // Getters and setters
}
