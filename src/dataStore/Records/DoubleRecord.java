package dataStore.Records;

/**
 * Created by sokam on 2/7/15.
 */
public class DoubleRecord extends Record {

  public static final String DATA_NOT_IN_CORRECT_FORMAT = "Data must be in number format. e.g. 3.25";
  public static final String COMPARED_TO_DIFFERENT_OBJECT = "Must compare to same data Type";
  public static final String REGEX = "(0|([1-9]\\d*))(\\.[0-9]*)?";
  public static final String FORMAT = "%f";

  public DoubleRecord() {
    super();
  }

  public DoubleRecord(String data) {
    super();
    setData(FORMAT, data);
  }

  public DoubleRecord(String data, String format) {
    super();
    setData(String.format(format, data));
  }

  public void setData(String data) {
    setData(FORMAT, data);
  }

  public void setData(String format, String data) {
    if (data.matches(REGEX)) {
      String formatData = String.format(format, Double.parseDouble(data));
      super.setData(formatData);
    } else {
      System.out.println(data);
      throw new IllegalArgumentException(DATA_NOT_IN_CORRECT_FORMAT);
    }
  }

  @Override
  public void add(Record record) {
    if (this.isSummable() && record.isSummable()) {
      super.add(record);
      super.setData(String.format(FORMAT, Double.parseDouble(getData())));
    }
  }

  @Override
  public boolean isSummable() {
    return true;
  }

  public boolean isValid () {
    return DoubleRecord.isValid(getData());
  }

  public static boolean isValid (String data) {
    return data.matches(REGEX);
  }

  @Override
  public int compareTo(Record record) throws IllegalArgumentException {
    if (record instanceof DoubleRecord) {
      if (this.isValid() && record.isValid()) {
        Double fst = Double.parseDouble(this.getData());
        Double snd = Double.parseDouble(record.getData());
        return fst.compareTo(snd);
      } else {
        throw new IllegalArgumentException(DATA_NOT_IN_CORRECT_FORMAT);
      }

    } else {
      throw new IllegalArgumentException(COMPARED_TO_DIFFERENT_OBJECT);
    }
  }
}
