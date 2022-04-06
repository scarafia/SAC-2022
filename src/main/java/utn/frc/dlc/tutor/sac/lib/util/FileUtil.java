/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author scarafia
 */
public abstract class FileUtil {
  
  public static String txtFileReadContents(Path path, Charset charset) throws IOException {
    return Files.readString(path, charset);
  }
  
  public static String txtFileReadContents(Path path, String charsetName) throws IOException {
    return txtFileReadContents(path, Charset.forName(charsetName));
  }

  public static String txtFileReadContents(Path path) throws IOException {
    return txtFileReadContents(path, Charset.defaultCharset());
  }
  
  public static String txtFileReadContents(String path, Charset charset) throws IOException {
    return txtFileReadContents(Paths.get(path), charset);
  }
  
  public static String txtFileReadContents(String path, String charsetName) throws IOException {
    return txtFileReadContents(Paths.get(path), Charset.forName(charsetName));
  }
  
  public static String txtFileReadContents(String path) throws IOException {
    return txtFileReadContents(Paths.get(path), Charset.defaultCharset());
  }
  
  public static void txtFileWriteContents(Path path, String contents, Charset charset) throws IOException {
    Files.writeString(path, contents, charset);
  }
  
  public static void txtFileWriteContents(Path path, String contents, String charsetName) throws IOException {
    txtFileWriteContents(path, contents, Charset.forName(charsetName));
  }
  
  public static void txtFileWriteContents(Path path, String contents) throws IOException {
    txtFileWriteContents(path, contents, Charset.defaultCharset());
  }
  
  public static void txtFileWriteContents(String path, String contents, Charset charset) throws IOException {
    txtFileWriteContents(Paths.get(path), contents, charset);
  }
  
  public static void txtFileWriteContents(String path, String contents, String charsetName) throws IOException {
    txtFileWriteContents(Paths.get(path), contents, Charset.forName(charsetName));
  }
  
  public static void txtFileWriteContents(String path, String contents) throws IOException {
    txtFileWriteContents(Paths.get(path), contents, Charset.defaultCharset());
  }
  

  
  
}
