package com.milind.hybridencryptionvault.factory.impl;

import com.milind.hybridencryptionvault.factory.DataEncryptionFactory;
import com.milind.hybridencryptionvault.util.DataEncryption;
import com.milind.hybridencryptionvault.util.impl.*;
import org.springframework.stereotype.Component;

@Component
public class BasicDataEncryptionFactoryImpl extends DataEncryptionFactory {


    @Override
    public DataEncryption getEncryptionFactory(String encryptionType) throws Exception {
        switch (encryptionType){
            case "fastest":
                 return FastestDataEncryptionImpl.getInstance();
            case "fast":
                return FastDataEncryptionImpl.getInstance();
            case "strong" :
                return StrongDataEncryptionImpl.getInstance();
            case "stronger":
                return StrongerDataEncryptionImpl.getInstance();
            default:
                return MediumDataEncryptionImpl.getInstance();
        }
    }
}
