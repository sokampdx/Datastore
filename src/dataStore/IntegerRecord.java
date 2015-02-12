package dataStore;

/**
 * Created by sokam on 2/11/15.
 */
public class IntegerRecord extends Record {
  private final static String REGEX = ("0|([1-9]\\d*)");
  private final static String DATA_IS_NOT_INTEGER = "Data is not an Integer.";

  public IntegerRecord (int data) {
    super();
    String str = ((Integer) data).toString();
    setData(str);
  }

  public void setData(String data) {
    if (isValid(data)) {
      super.setData(data);
    } else {
      throw new IllegalArgumentException(DATA_IS_NOT_INTEGER);
    }
  }

  public void increment() {
    Integer i = Integer.parseInt(super.getData());
    setData((++i).toString());
  }

  @Override
  public boolean isSummable() {
    return true;
  }

  @Override
  public boolean isValid () {
    return IntegerRecord.isValid(getData());
  }

  public static boolean isValid (String data) {
    return data.matches(REGEX);
  }
}
