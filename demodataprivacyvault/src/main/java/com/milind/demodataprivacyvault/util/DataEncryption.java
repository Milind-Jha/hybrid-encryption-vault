package com.milind.demodataprivacyvault.util;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface DataEncryption {

    String RSA_ALGORITHM = "RSA";
    String AES_ALGORITHM = "AES";
    int AES_KEY_SIZE = 128;

    KeyPair generateKeyPair() throws Exception;
    SecretKey generateSecretKey() throws Exception;

    String encryptData(String data, PublicKey publicKey, PrivateKey privateKey) throws Exception;
    String decryptData(String encryptedData, PrivateKey privateKey, PublicKey publicKey) throws Exception;
    int getPrivateKeyModulusLength(PrivateKey privateKey);
}
