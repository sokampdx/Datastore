package dataStore.Records;

/**
 * Created by sokam on 2/7/15.
 */
public class MoneyRecord extends DoubleRecord {

  public static final String DATA_NOT_IN_CORRECT_FORMAT = "Data must be in Dollar and Cents. e.g. 3.25";
  public static final String COMPARED_TO_DIFFERENT_OBJECT = "Must compare to same data Type";
  public static final String REGEX = "\\d+\\.\\d{2}";

  public MoneyRecord(String data) {
    super();
    setData(data);
  }

  public void setData(String data) {
    if (data.matches(REGEX)) {
      super.setData(data);
    } else {
      throw new IllegalArgumentException(DATA_NOT_IN_CORRECT_FORMAT);
    }
  }

  public boolean isValid () {
    return MoneyRecord.isValid(getData());
  }

  public static boolean isValid (String data) {
    return data.matches(REGEX);
  }

}
