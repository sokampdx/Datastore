package dataStore;

import java.util.ArrayList;

/**
 * Created by sokam on 2/7/15.
 */
public class DateRecord extends Record {
  public static final String DATA_NOT_IN_CORRECT_FORMAT = "Data must be Date Format YYYY-MM-DD. e.g. 2014-12-31";
  public static final String COMPARED_TO_DIFFERENT_OBJECT = "Must compare to same data Type";
  public static final int FIRST_YEAR = 1900;
  public static final int FIRST_DAY_OF_MONTH = 1;
  public static final int LAST_DAY_OF_LONG_MONTH = 31;
  public static final int FEBRUARY = 2;
  public static final int LAST_DAY_OF_SHORT_MONTH = 30;
  public static final int LAST_DAY_OF_LEAP_FEB = 29;
  public static final int LAST_DAY_OF_NON_LEAP_FEB = 28;
  public static final int FIRST_MONTH = 1;
  public static final int LAST_MONTH = 12;
  public static final int[] LARGE_MONTH = {1,3,5,7,8,10,12};
/*

  public DateRecord () {
    super();
  }
*/

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

    return date.matches("\\d{4}-\\d{2}-\\d{2}") && DateRecord.isValid(year, month, day);
  }

  private static boolean isValid (int year, int month, int day) {
    boolean isValid = true;

    ArrayList<Integer> largeMonth = getListOfLargeMonth();


    if (DateRecord.isInvalidMonth(month))
      isValid = false;

    if (year < FIRST_YEAR)
      isValid = false;

    if (day < FIRST_DAY_OF_MONTH)
      isValid = false;

    if (largeMonth.contains(month) && day > LAST_DAY_OF_LONG_MONTH)
      isValid = false;

    if (!largeMonth.contains(month) && month != FEBRUARY && day > LAST_DAY_OF_SHORT_MONTH)
      isValid = false;

    if (month == FEBRUARY && DateRecord.isLeap(year) && day > LAST_DAY_OF_LEAP_FEB)
      isValid = false;

    if (month == FEBRUARY && !DateRecord.isLeap(year) && day > LAST_DAY_OF_NON_LEAP_FEB)
      isValid = false;

    return isValid;
  }

  private static boolean isInvalidMonth(int month) {
    return month < FIRST_MONTH || month > LAST_MONTH;
  }

  private static ArrayList<Integer> getListOfLargeMonth() {
    ArrayList<Integer> largeMonth = new ArrayList<Integer>();

    for (int i : LARGE_MONTH) {
      largeMonth.add(i);
    }
    return largeMonth;
  }

  private static boolean isLeap(int year) {
    return (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
  }

}


