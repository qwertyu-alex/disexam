package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public final class Encryption {

  public static String encryptDecryptXOR(String rawString) {

    // If encryption is enabled in Config.
    if (Config.getEncryption()) {

      //Change the keys depending on the length of the rawString
      String keyMod = Integer.toString(rawString.length() % 11);
      String keyMod2 = Integer.toString(rawString.length() % 3);
      System.out.println(keyMod);

      char[] key = (keyMod + Config.getEncrKey()).toCharArray();
      char[] key2 = (keyMod2 + Config.getEncrKey2()).toCharArray();

      // Stringbuilder enables you to play around with strings and make useful stuff
      StringBuilder thisIsEncrypted = new StringBuilder();
      StringBuilder thisIsEncryptedTwice = new StringBuilder();

      // TODO: This is where the magic of XOR is happening. Are you able to explain what is going on?
      for (int i = 0; i < rawString.length(); i++) {
        thisIsEncrypted.append((char) (rawString.charAt(i) ^ key[i % key.length]));
      }

      // We return the encrypted string
      String encryptedString = thisIsEncrypted.toString();

      for (int i = 0; i < encryptedString.length(); i++) {
        thisIsEncryptedTwice.append((char) (encryptedString.charAt(i) ^ key2[i % key2.length]));
      }

      return thisIsEncryptedTwice.toString();


    } else {
      // We return without having done anything
      return rawString;
    }
  }

}
