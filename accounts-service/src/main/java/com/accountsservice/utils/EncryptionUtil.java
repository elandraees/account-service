package com.accountsservice.utils;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionUtil {

    public static String encyptString(String value) {
        return BCrypt.hashpw(value, BCrypt.gensalt());
    }

    public static boolean checkValue(String value, String encryptedValue) {
        return BCrypt.checkpw(value, encryptedValue);
    }

}
