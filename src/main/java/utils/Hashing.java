package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bouncycastle.util.encoders.Hex;

public final class Hashing {

  public static String md5(String rawString, String salt) {
    try {

      // We load the hashing algoritm we wish to use.
      MessageDigest md = MessageDigest.getInstance("MD5");

      // Add salt to the rawString
      rawString += salt;

      // We convert to byte array
      byte[] byteArray = md.digest(rawString.getBytes());

      // Initialize a string buffer
      StringBuffer sb = new StringBuffer();

      // Run through byteArray one element at a time and append the value to our stringBuffer
      for (int i = 0; i < byteArray.length; ++i) {
        sb.append(Integer.toHexString((byteArray[i] & 0xFF) | 0x100).substring(1, 3));
      }

      //Convert back to a single string and return
      return sb.toString();

    } catch (java.security.NoSuchAlgorithmException e) {

      //If somethings breaks
      System.out.println("Could not hash string");
    }

    return null;
  }

  public static String sha(String rawString, String salt) {
    try {
      // We load the hashing algoritm we wish to use.
      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      // Add salt to the rawString
      rawString += salt;

      // We convert to byte array
      byte[] hash = digest.digest(rawString.getBytes(StandardCharsets.UTF_8));

      // We create the hashed string
      String sha256hex = new String(Hex.encode(hash));

      // And return the string
      return sha256hex;

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return rawString;
  }

  /**
   * Failled attempts
   */

  //TODO: DELETE OR MAKE JWT
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