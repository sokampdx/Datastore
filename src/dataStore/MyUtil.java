package dataStore;
import java.util.List;

/**
 * Created by sokam on 2/7/15.
 */
public abstract class MyUtil {
  public static final String DIVIDER = "---------------";

  public static String ListOfListOfRecordToString(List<List<Record>> collection) {
    String str = "";

    for (List<Record> row : collection) {
      int len = row.size();

      str += row.get(0);
      for (int j = 1; j < len; ++j) {
        str += Main.COMMA + row.get(j).toString();
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

  public static void print(String... msgs) {
    for (String msg : msgs)
      System.out.print(msg + " | ");
    System.out.println();
  }

  public static String ArrayListToString (List<String> strings) {
    String finalString = "";
    for (String string : strings) {
      finalString += string + " ";
    }
    return finalString;
  }


}
