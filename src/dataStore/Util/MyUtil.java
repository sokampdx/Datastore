package dataStore.Util;

import dataStore.Records.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sokam on 2/7/15.
 */
public abstract class MyUtil {
  public static final String DIVIDER = "---------------";
  public static final String COMMA = ",";
  public static final String EOL = "\n";

  public static String ListOfListOfRecordToString(List<List<Record>> collection) {
    String str = "";

    for (List<Record> row : collection) {
      int len = row.size();

      str += row.get(0);
      for (int j = 1; j < len; ++j) {
        str += COMMA + row.get(j).toString();
      }
      str += '\n';
    }

    return str;
  }

  public static String ListOfListOfStringToString(List<List<String>> collection, String delimiter) {
    String str = "";

    for (List<String> row : collection) {
      int len = row.size();

      str += row.get(0);
      for (int j = 1; j < len; ++j) {
        str += delimiter + row.get(j);
      }
      str += '\n';
    }

    return str;
  }

  public static List<Record> cloneList(List<Record> recordList) {
    List<Record> newList = new ArrayList<Record>();

    for (Record record : recordList) {
      newList.add(record);
    }
    return newList;
  }

  public static List<List<Record>> cloneRecords(List<List<Record>> records) {
    List<List<Record>> newRecords = new ArrayList<List<Record>>();

    for (List<Record> list : records) {
      newRecords.add(cloneList(list));
    }
    return newRecords;
  }

  public static void print(String... msgs) {
    for (String msg : msgs)
      System.out.print(" | " + msg);
    System.out.println();
  }

  public static void printResultAt(String method, List<List<Record>> result) {
    System.out.println(MyUtil.DIVIDER + method + EOL +
        MyUtil.ListOfListOfRecordToString(result) + EOL +
        MyUtil.DIVIDER + EOL);
  }

  public static String ArrayListToString (List<String> strings) {
    String finalString = "";
    for (String string : strings) {
      finalString += string + " ";
    }
    return finalString;
  }


}
