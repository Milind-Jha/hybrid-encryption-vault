package com.milind.demodataprivacyvault.util.impl;

import com.milind.demodataprivacyvault.util.DataEncryption;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;

@Component
@Getter
@Slf4j
public class DataEncryptionImpl implements DataEncryption {

    private static DataEncryptionImpl dataEncryption;
    private KeyPair keyPair;
    private SecretKey secretKey = generateSecretKey();

    private DataEncryptionImpl() throws Exception {
    }

    public static DataEncryptionImpl getInstance() throws Exception {
        synchronized (DataEncryptionImpl.class){
            if(dataEncryption==null)
                dataEncryption = new DataEncryptionImpl();
        }
        return dataEncryption;
    }

    @Override
    public KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(128);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        log.info("Generating RSA key pair "+keyPair);
        return keyPair;
    }

    @Override
    public SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE);
        SecretKey secretKey = keyGenerator.generateKey();
        log.info("Generating AES key pair "+secretKey);
        return secretKey;
    }
    @Override
    public String encryptData(String data, PublicKey publicKey, PrivateKey privateKey) throws Exception {
        // Generate AES secret key

        // Encrypt the data with AES symmetric encryption
        Cipher aesCipher = Cipher.getInstance(AES_ALGORITHM);
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = aesCipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Encrypt the AES secret key with RSA public key
        Cipher rsaCipher = Cipher.getInstance(RSA_ALGORITHM);
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKeyBytes = rsaCipher.doFinal(secretKey.getEncoded());

        // Combine the encrypted key and encrypted data
        byte[] combinedBytes = new byte[encryptedKeyBytes.length + encryptedBytes.length];
        System.arraycopy(encryptedKeyBytes, 0, combinedBytes, 0, encryptedKeyBytes.length);
        System.arraycopy(encryptedBytes, 0, combinedBytes, encryptedKeyBytes.length, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(combinedBytes);
    }

    @Override
    public String decryptData(String encryptedData, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        byte[] combinedBytes = Base64.getDecoder().decode(encryptedData);

        // Separate the encrypted key and encrypted data
        int keyLength = getPrivateKeyModulusLength(privateKey);
        byte[] encryptedKeyBytes = new byte[keyLength];
        byte[] encryptedBytes = new byte[combinedBytes.length - keyLength];
        System.arraycopy(combinedBytes, 0, encryptedKeyBytes, 0, keyLength);
        System.arraycopy(combinedBytes, keyLength, encryptedBytes, 0, combinedBytes.length - keyLength);

        // Decrypt the AES secret key using RSA private key
        Cipher rsaCipher = Cipher.getInstance(RSA_ALGORITHM);
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedKeyBytes = rsaCipher.doFinal(encryptedKeyBytes);

        // Reconstruct the AES secret key
        SecretKey secretKey = new SecretKeySpec(decryptedKeyBytes, AES_ALGORITHM);

        // Decrypt the data with AES symmetric decryption
        Cipher aesCipher = Cipher.getInstance(AES_ALGORITHM);
        aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = aesCipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    @Override
    public int getPrivateKeyModulusLength(PrivateKey privateKey) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        return rsaPrivateKey.getModulus().bitLength() / 8;
    }
}
