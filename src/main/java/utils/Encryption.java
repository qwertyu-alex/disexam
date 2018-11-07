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

      // The key is predefined and hidden in code
      // TODO: Create a more complex code and store it somewhere better DONE
      char[] key = Config.getEncrKey().toCharArray();

      // Stringbuilder enables you to play around with strings and make useful stuff
      StringBuilder thisIsEncrypted = new StringBuilder();

      // TODO: This is where the magic of XOR is happening. Are you able to explain what is going on?
      for (int i = 0; i < rawString.length(); i++) {
        thisIsEncrypted.append((char) (rawString.charAt(i) ^ key[i % key.length]));
      }

      // We return the encrypted string
      return thisIsEncrypted.toString();

    } else {
      // We return without having done anything
      return rawString;
    }
  }

  public static String encryptJSON(String key){

    try {
      Algorithm algorithm = Algorithm.HMAC256(key);
      String token = JWT.create()
              .withIssuer("auth0")
              .withSubject("email")
              .withClaim("THISCLAIM", "CLAIM")
              .sign(algorithm);


      return token;
    } catch (JWTCreationException err){
      err.printStackTrace();
    }

    return "token";
  }

  public static DecodedJWT verifier(String token, String key){
    try {
      Algorithm algorithm = Algorithm.HMAC256(key);

      JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();

      DecodedJWT jwt = verifier.verify(token);


      return jwt;

      //https://github.com/auth0/java-jwt
      //https://github.com/jwtk/jjwt#overview
      //https://en.wikipedia.org/wiki/JSON_Web_Token
      //https://github.com/auth0-samples/auth0-servlet-sample/tree/master/01-Login
      //https://manage.auth0.com/#/applications/DAM2Q8c2y1b1W1m5B87DV9JgvCGBu6ti/quickstart

    } catch (JWTVerificationException err){
      err.printStackTrace();
    }
    return null;

  }


}
