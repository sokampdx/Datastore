package com.example.sokam;

import java.util.ArrayList;

public class DateRecord extends Record {

  public DateRecord(String text) {

    String[] str = text.split("-");
    int year = Integer.parseInt(str[0]);
    int month = Integer.parseInt(str[1]);
    int day = Integer.parseInt(str[2]);

    if (text.matches("\\d{4}-\\d{2}-\\d{2}") && isValidDate(year, month, day)) {
      this.data = text;
    } else {
      throw new RuntimeException();
    }
  }

  public String getDate() {
    return super.getData();
  }

  private static boolean isValidDate(int year, int month, int day) {
    boolean isValid = true;

    ArrayList<Integer> largeMonth = getListOfLargeMonth();

    if (isInvalidMonth(month))
      isValid = false;

    if (year < 1900)
      isValid = false;

    if (day < 1)
      isValid = false;

    if (largeMonth.contains(month) && day > 31)
      isValid = false;

    if (!largeMonth.contains(month) && month != 2 && day > 30)
      isValid = false;

    if (month == 2 && isLeap(year) && day > 29)
      isValid = false;

    if (month == 2 && !isLeap(year) && day > 28)
      isValid = false;

    return isValid;
  }

  private static boolean isInvalidMonth(int month) {
    return month < 1 || month > 12;
  }

  private static ArrayList<Integer> getListOfLargeMonth() {
    ArrayList<Integer> largeMonth = new ArrayList<Integer>();

    largeMonth.add(1);
    largeMonth.add(3);
    largeMonth.add(5);
    largeMonth.add(7);
    largeMonth.add(8);
    largeMonth.add(10);
    largeMonth.add(12);
    return largeMonth;
  }

  private static boolean isLeap(int year) {
    return (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
  }

}
