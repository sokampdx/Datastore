package dataStore;

import java.util.ArrayList;

/**
 * Created by sokam on 2/7/15.
 */
public class DateRecord extends Record {
  public static final String DATA_NOT_IN_CORRECT_FORMAT = "Data must be Date Format YYYY-MM-DD. e.g. 2014-12-31";
  public static final String COMPARED_TO_DIFFERENT_OBJECT = "Must compare to same data Type";
  public static final String REGEX = "\\d{4}-\\d{2}-\\d{2}";

  public DateRecord(String data) {
    super();
    this.setData(data);
  }

  public boolean isValid() {
    return DateRecord.isValid(getData());
  }

  @Override
  public void setData(String data) {
    if (DateRecord.isValid(data)) {
      super.setData(data);
    } else {
      throw new IllegalArgumentException(DATA_NOT_IN_CORRECT_FORMAT);
    }
  }

  @Override
  public int compareTo(Record record) throws IllegalArgumentException {
    if (record instanceof DateRecord) {
      if (this.isValid() && record.isValid()) {
        return super.compareTo(record);
      } else {
        throw new IllegalArgumentException(DATA_NOT_IN_CORRECT_FORMAT);
      }

    } else {
      throw new IllegalArgumentException(COMPARED_TO_DIFFERENT_OBJECT);
    }
  }

  public static boolean isValid (String date) {
    String[] str = date.split("-");
    int year = Integer.parseInt(str[0]);
    int month = Integer.parseInt(str[1]);
    int day = Integer.parseInt(str[2]);

    return date.matches(REGEX) && DateChecker.isValid(year, month, day);
  }

}


