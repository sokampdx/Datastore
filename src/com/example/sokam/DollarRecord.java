package com.example.sokam;

public class DollarRecord extends Record {
  public DollarRecord (String value) {
    if (value.matches("\\d+\\.\\d{2}")) {
      this.data = value;
    } else {
      throw new RuntimeException();
    }
  }

  public double getValue () {
    return Double.parseDouble(this.data);
  }
}


