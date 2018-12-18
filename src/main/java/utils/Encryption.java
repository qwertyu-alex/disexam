package utils;

public final class Encryption {

  public static String encryptDecryptXOR(String rawString) {

    // If encryption is enabled in Config.
    if (Config.getEncryption()) {

      //Change the keys depending on the length of the rawString
      int keyMod = (rawString.length() % 11);
      int keyMod2 = (rawString.length() % 3);

      char[] key = caesarCipher(Config.getEncrKey(), keyMod);
      char[] key2 = caesarCipher (Config.getEncrKey2(),keyMod2);

      // Stringbuilder enables you to play around with strings and make useful stuff
      StringBuilder thisIsEncrypted = new StringBuilder();
      StringBuilder thisIsEncryptedTwice = new StringBuilder();

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


  /**
   *
   * @param key Nøglen som skal bruges til at enkryptere
   * @param shifts Hvor meget hver char skal rykkes
   * @return En ny nøgle som kan bruges
   */
  private static char[] caesarCipher(String key, int shifts){
    StringBuilder keyBuild = new StringBuilder();
    for (int i = 0; i < key.length(); i++){
      char c =  (char)(Config.getEncrKey().charAt(i) + shifts);
      if (c > 'z'){
        keyBuild.append((char)(Config.getEncrKey().charAt(i) - (26-shifts)));
      } else {
        keyBuild.append((char)(Config.getEncrKey().charAt(i) + (shifts)));
      }
    }

    return keyBuild.toString().toCharArray();

  }

}
