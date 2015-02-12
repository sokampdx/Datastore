package dataStore;

import java.util.ArrayList;

/**
 * Created by sokam on 2/11/15.
 */
public class DateChecker {
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

  public static boolean isValid (int year, int month, int day) {
    boolean isValid = true;
    ArrayList<Integer> largeMonth = getListOfLargeMonth();

    if (isInvalidMonth(month))
      isValid = false;

    if (year < FIRST_YEAR)
      isValid = false;

    if (day < FIRST_DAY_OF_MONTH)
      isValid = false;

    if (largeMonth.contains(month) && day > LAST_DAY_OF_LONG_MONTH)
      isValid = false;

    if (!largeMonth.contains(month) && month != FEBRUARY && day > LAST_DAY_OF_SHORT_MONTH)
      isValid = false;

    if (month == FEBRUARY && isLeap(year) && day > LAST_DAY_OF_LEAP_FEB)
      isValid = false;

    if (month == FEBRUARY && !isLeap(year) && day > LAST_DAY_OF_NON_LEAP_FEB)
      isValid = false;

    return isValid;
  }

  public static boolean isInvalidMonth(int month) {
    return month < FIRST_MONTH || month > LAST_MONTH;
  }

  public static ArrayList<Integer> getListOfLargeMonth() {
    ArrayList<Integer> largeMonth = new ArrayList<Integer>();

    for (int i : LARGE_MONTH) {
      largeMonth.add(i);
    }
    return largeMonth;
  }

  public static boolean isLeap(int year) {
    return (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
  }

}
