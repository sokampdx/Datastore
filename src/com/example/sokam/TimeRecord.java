package com.example.sokam;

public class TimeRecord extends Record {
  public TimeRecord (String time) {
    if (time.matches("[0-2][0-3]:[0-5][0-9]")) {
      this.data = time;
    } else {
      throw new RuntimeException();
    }
  }
}
