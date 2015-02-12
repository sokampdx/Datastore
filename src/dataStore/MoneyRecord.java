package dataStore;

/**
 * Created by sokam on 2/7/15.
 */
public class MoneyRecord extends Record {

  public static final String DATA_NOT_IN_CORRECT_FORMAT = "Data must be in Dollar and Cents. e.g. 3.25";
  public static final String COMPARED_TO_DIFFERENT_OBJECT = "Must compare to same data Type";
  public static final String REGEX = "\\d+\\.\\d{2}";

  // TODO: MoneyRecord extends DoubleRecord extends Record. Create string dollar format method

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


  @Override
  public boolean isSummable() {
    return true;
  }

  public boolean isValid () {
    return MoneyRecord.isValid(getData());
  }

  public static boolean isValid (String data) {
    return data.matches(REGEX);
  }

  @Override
  public int compareTo(Record record) throws IllegalArgumentException {
    if (record instanceof MoneyRecord) {
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
