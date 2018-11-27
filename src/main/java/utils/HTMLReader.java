package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTMLReader {
  public static String readFromHTML () throws java.io.IOException{
    // Read File and store input
    InputStream input = Config.class.getResourceAsStream("/Frontpage.html");
    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

    // Go through the lines one by one
    StringBuffer stringBuffer = new StringBuffer();
    String str;

    // Read file one line at a time
    while ((str = reader.readLine()) != null) {
      stringBuffer.append(str);
    }

    return stringBuffer.toString();
  }
}
