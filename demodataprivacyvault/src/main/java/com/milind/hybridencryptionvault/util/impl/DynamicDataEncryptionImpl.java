package com.milind.hybridencryptionvault.util.impl;

import com.milind.hybridencryptionvault.factory.impl.BasicDataEncryptionFactoryImpl;
import com.milind.hybridencryptionvault.util.DataEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
public class DynamicDataEncryptionImpl implements DataEncryption {

    @Autowired
    private BasicDataEncryptionFactoryImpl basicDataEncryptionFactory;




    @Override
    public KeyPair generateKeyPair() throws Exception {
        return null;
    }

    @Override
    public SecretKey generateSecretKey() throws Exception {
        return null;
    }

    @Override
    public String encryptData(String data, PublicKey publicKey, PrivateKey privateKey) throws Exception {
        return "";
    }

    @Override
    public String decryptData(String encryptedData, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        return "";
    }

    @Override
    public int getPrivateKeyModulusLength(PrivateKey privateKey) {
        return 0;
    }
}
