package dataStore;

/**
 * Created by sokam on 2/11/15.
 */
public interface QueryKeywords {
  public static final String EOL = "\n";
  public static final String BLANK = " ";
  public static final String COMMA = ",";
  public static final String COLON = ":";
  public static final String EQUAL = "=";
  public static final String DASH = "-";
  public static final String SELECT = "s";
  public static final String ORDER = "o";
  public static final String FILTER = "f";
  public static final String GROUP = "g";
  public static final String OPEN = "(";
  public static final String CLOSE = ")";
  public static final String AND = "AND";
  public static final String OR = "OR";
  public static final String DOUBLE_QUOTE = "\"";
  public static final String SINGLE_QUOTE = "'";

  public final String MAX = "max";
  public final String MIN = "min";
  public final String SUM = "sum";
  public final String COUNT = "count";
  public final String COLLECT = "collect";

}
