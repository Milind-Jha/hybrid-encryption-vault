package com.milind.demodataprivacyvault.factory;

import com.milind.demodataprivacyvault.util.DataEncryption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class DataEncryptionFactory {

    private String RSA_ALGORITHM;
    private String AES_ALGORITHM;
    private int AES_KEY_SIZE;

    public abstract DataEncryption getEncryptionFactory(String encryptionType) throws Exception;


    public void getDynamicEncryptionFactory(String encryptionType, String RSA_ALGORITHM,
                                                      String AES_ALGORITHM, int AES_KEY_SIZE) throws Exception {
        this.AES_ALGORITHM = AES_ALGORITHM;
        this.RSA_ALGORITHM = AES_ALGORITHM;
        this.AES_KEY_SIZE = AES_KEY_SIZE;

    }
}
