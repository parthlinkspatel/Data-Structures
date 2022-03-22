package app;

import java.util.Scanner;
import java.io.*;

public class RLEconverter {
   private char[] fileChars; // stores unique chars as they appear in the file.
   private final static int DEFAULT_LEN = 100; // used to create arrays.
   private int numLines; // number of lines in the file that is read.
   
   /*
    *  Initialize the char array to length 2.
    *  as the number of characters is 2 for any file.
    */
   public RLEconverter(){
      fileChars = new char[2];
      numLines=0;
   }

   /*
    *  This method reads in an uncompressed ascii image file that contains 
    *  2 characters. It stores each line of the file in an array.
    *  It then calls compressLines to get an array that stores the compressed
    *  version of each uncompressed line from the file. The compressed array
    *  is then passed to the getCompressedFileStr method which returns a String
    *  of all compressed lines (the two charcaters are written in the first line)
    *  in CSV format. This String is written to a text file with the prefix "RLE_"
    *  added to the original, uncompressed file name.
    */   
   public void compressFile(String fileName) throws IOException{
    Scanner scan = new Scanner(new FileReader(fileName));
    String line = null;
    String[] decompressed = new String [DEFAULT_LEN];
    numLines=0;
    while(scan.hasNext()){
      line = scan.next();
      if(line != null && line.length()>0)
        decompressed[numLines]=line;
        numLines++;
    }
    scan.close();
    String[] compressed = compressLines(decompressed);
    writeFile(getCompressedFileStr(compressed, fileChars), "RLE_"+fileName);
   }
  
   
/*
 * This method implements the RLE compression algorithm. It takes a line of uncompressed data
 * from an ascii file and returns the RLE encoding of that line in CSV format.
 * The two characters that make up the image file are passed in as a char array, where
 * the first cell contains the first character that occurred in the file.
*/
public String compressLine(String line, char[] fileChars){
   String res = "";
   //TODO1: Implement this method
   int freq = 0;
   int lineLength = line.length();
   if(fileChars[1]==line.charAt(0)){
      res = "0,";
   }
   for(int i = 0; i < line.length(); i++){
      if(line.charAt(i) == fileChars[0]){
         freq++;
         if( (lineLength - 1) == i){
            res += freq + ",";
            freq = 0;
         }
         else if(line.charAt(i + 1) == fileChars[1]){
            res += freq + ",";
            freq = 0;
         }
      }
      else if(line.charAt(i) == fileChars[1]){
         freq++;
         if( (lineLength - 1) == i){
            res += freq + ",";
            freq = 0;
         }
         else if(line.charAt(i + 1) == fileChars[0]){
            res += freq + ",";
            freq = 0;
         }
      }
   }
   if(res.charAt(res.length()-1) == ','){
      res = res.substring(0, res.length()-1);
   }
   return res;
}

  /*
   *  This method discovers the two ascii characters that make up the image. 
   *  It iterates through all of the lines and writes each compressed line
   *  to a String array which is returned. The method compressLine is called on 
   *  each line.
   */
public String[] compressLines(String[] lines){
   String[] compressed = null;
   //TODO2: Implement this method
   fileChars[0] = lines[0].charAt(0);
   for(int i = 0; i < lines[0].length(); i++){
      if(lines[0].charAt(i) != fileChars[0]){
         fileChars[1] = lines[0].charAt(i);
      }
   }
   for(int i = 0; i < lines.length; i++){
      compressed[i] = compressLine(lines[i], fileChars);
   }
   return compressed;
}

/*
 *  This method assembles the lines of compressed data for
 *  writing to a file. The first line must be the 2 ascii characters
 *  in comma-separated format. 
 */
public String getCompressedFileStr(String[] compressed, char[] fileChars) {
   String data = "";
   //TODO3: Implement this method
   data = fileChars[0] + "," + fileChars[1];
   for(int i = 0; i < compressed.length; i++){
      data += "\n" + compressed[i];
   }
   return data;
}
   /*
    *  This method reads in an RLE compressed ascii image file that contains 
    *  2 characters. It stores each line of the file in an array.
    *  It then calls decompressLines to get an array that stores the decompressed
    *  version of each compressed line from the file. The first row contains the two 
    *  ascii charcaters used in the original image file. The decompressed array
    *  is then passed to the getDecompressedFileStr method which returns a String
    *  of all decompressed lines, thus restoring the original, uncompressed image.
    *  This String is written to a text file with the prefix "DECOMP_"
    *  added to the original, compressed file name.
    */   
public void decompressFile(String fileName) throws IOException{
    Scanner scan = new Scanner(new FileReader(fileName));
    String line = null;
    String[] compressed = new String [DEFAULT_LEN];
    numLines=0;
    while(scan.hasNext()){
      line = scan.next();
      if(line != null && line.length()>0)
        compressed[numLines]=line;
        numLines++;
    }
    scan.close();
    String[] decompressed = decompressLines(compressed);
    writeFile(getDecompressedFileStr(decompressed), "DECOMP_"+fileName);
  }
 
   /*
   * This method decodes lines that were encoded by the RLE compression algorithm. 
   * It takes a line of compressed data and returns the decompressed, or original version
   * of that line. The two characters that make up the image file are passed in as a char array, 
   * where the first cell contains the first character that occurred in the file.
   */
   public String decompressLine(String line, char[] fileChars){
      String res = "";
      //TODO4: Implement this method
      int whichChar = 0;
      int num;
      for(int i=0; i < line.length(); i++){
         if(Character.isDigit(line.charAt(i))){
            num = Character.getNumericValue(line.charAt(i));
            if(line.length() - 1 == i){
               num = Character.getNumericValue(line.charAt(i));
            }
            else if(Character.isDigit(line.charAt(i+1))){
               num = (num * 10);
            }
            for(int j=0; j < num; j++){
               if(whichChar % 2 == 0){
                  res += fileChars[0];
               }
               else if(whichChar % 2 == 1){
                  res += fileChars[1];
               }
            }
         } else if(line.charAt(i) == ','){
            whichChar = whichChar + 1;
         }
      }
      return res;
   }
    /*
   *  This method iterates through all of the compressed lines and writes 
   *  each decompressed line to a String array which is returned. 
   *  The method decompressLine is called on each line. The first line in
   *  the compressed array passed in are the 2 ascii characters used to make
   *  up the image. 
   */
   public String[] decompressLines(String[] lines){
      String[] decompressed = null;
     //TODO5: Implement this method
      fileChars[0] = lines[0].charAt(0);
      fileChars[1] = lines[0].charAt(3);
      for(int i=0; i < lines.length; i++){
         decompressed[i] = decompressLine(lines[i+1], fileChars);
      }
      return decompressed;
   }
  
  /*
   *  This method assembles the lines of decompressed data for
   *  writing to a file. 
   */
   public String getDecompressedFileStr(String[] decompressed){
      String data = "";
      //TODO6: Implement this method
      data = decompressed[0];
      for(int i=1; i < decompressed.length; i++){
         data += "\n" + decompressed[i];
      }
      return data;
   }


   
   public void writeFile(String data, String fileName) throws IOException{
		PrintWriter pw = new PrintWriter(fileName);
      pw.print(data);
      pw.close();
   }
}