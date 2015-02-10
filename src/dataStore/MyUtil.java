package dataStore;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sokam on 2/7/15.
 */
public abstract class MyUtil {
  public static final String DIVIDER = "---------------";

  public static String toStringForArrayListOfList(List<List<Object>> collection) {
    String str = "";

    for (List<Object> row : collection) {
      int len = row.size();

      str += row.get(0);
      for (int j = 1; j < len; ++j) {
        str += "|" + row.get(j).toString();
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
