package com.example.sokam;


import java.util.*;
import java.io.IOException;
import java.io.File;

public class Importer {

  public final int STB = 0;
  public final int TITLE = 1;
  public final int PROVIDER = 2;
  public final int DATE = 3;
  public final int REV = 4;
  public final int VIEW_TIME = 5;
  public final int MAX_CHAR = 64;

  public static final String DELIMITER = "\\|";
  public static final String FILENAME = "sample.txt";
  private String filename;
  private List<List<String>> data;


  Importer(String filename) {
      this.filename = filename;
      this.data = new ArrayList<List<String>>();
      readFromFile();
  }

  public String getFilename() {
      return this.filename;
  }

  public String getHeader() {
      return getRow(0);
  }

  public String getRow(int index) {
      String result = "";

      if (index < 0 || index >= data.size())
          return result;

      for (String str : data.get(index)) {
          result += str;
      }
      return result;
  }

  public int getRow() {
    return this.data.size();
  }

  private void readFromFile() {
      String line = "";
      String [] strList;
      Scanner in = null;
      int numCol = 0;
      try {
          in = new Scanner(new File(filename));
          while (in.hasNextLine()) {
              line = in.nextLine();
              strList = line.split(DELIMITER);
              if (isHeader()) {
                numCol = strList.length;
                this.data.add(new ArrayList<String>(Arrays.asList(strList)));
              } else {
                addRecord(strList, numCol);
              }
          }

      } catch (IOException ioe) {
          ioe.printStackTrace();
      }
  }

  private void addRecord(String[] strList, int numCol) {
    if (strList.length != numCol)
      return;

    if (isValidData(strList)) {
      ArrayList<String> record = new ArrayList<String>();
      for (String str : strList) {
        int len = str.length();
        if (len > MAX_CHAR)
          len = MAX_CHAR;
        record.add(str.substring(0, len));
      }
      this.data.add(record);
    }
  }

  private boolean isHeader() {
    return data.size() == 0;
  }

  private boolean isValidData(String [] values) {
    return isValidDate(values[DATE]) && isValidTime(values[VIEW_TIME]) && isValidDollar(values[REV]);
  }

  private boolean isValidDollar(String value) {
    return value.matches("\\d+\\.\\d{2}");
  }

  private boolean isValidTime(String time) {
    return time.matches("((2[0-3])|((1|0?)[0-9])):[0-5][0-9]");
  }

  private boolean isValidDate(String date) {
    String[] str = date.split("-");
    int year = Integer.parseInt(str[0]);
    int month = Integer.parseInt(str[1]);
    int day = Integer.parseInt(str[2]);

    return date.matches("\\d{4}-\\d{2}-\\d{2}") && isValidDate(year, month, day);
  }

  private static boolean isValidDate(int year, int month, int day) {
    boolean isValid = true;

    ArrayList<Integer> largeMonth = getListOfLargeMonth();

    if (isInvalidMonth(month))
      isValid = false;

    if (year < 1900)
      isValid = false;

    if (day < 1)
      isValid = false;

    if (largeMonth.contains(month) && day > 31)
      isValid = false;

    if (!largeMonth.contains(month) && month != 2 && day > 30)
      isValid = false;

    if (month == 2 && isLeap(year) && day > 29)
      isValid = false;

    if (month == 2 && !isLeap(year) && day > 28)
      isValid = false;

    return isValid;
  }

  private static boolean isInvalidMonth(int month) {
    return month < 1 || month > 12;
  }

  private static ArrayList<Integer> getListOfLargeMonth() {
    ArrayList<Integer> largeMonth = new ArrayList<Integer>();

    largeMonth.add(1);
    largeMonth.add(3);
    largeMonth.add(5);
    largeMonth.add(7);
    largeMonth.add(8);
    largeMonth.add(10);
    largeMonth.add(12);
    return largeMonth;
  }

  private static boolean isLeap(int year) {
    return (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
  }

  public String toString () {
    return convertToText(this.data);
  }

  public List<List<String>> getData () {
    return this.data;
  }

  public static String convertToText (List<List<String>> data) {
    String result = "";
    for (List<String> list : data) {

      for (String str : list) {
        result += str;
      }
      result += '\n';
    }

    return result;
  }

  public static void main(String[] args) {
  // write your code here


  }
}
