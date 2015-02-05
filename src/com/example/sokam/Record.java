package com.example.sokam;


/**
 * Created by sokam on 2/5/15.
 */
public class Record implements Comparable<Record>{
  protected String data;

  public Record () {
    this.data = "";
  }

  public Record(String text) {
    this.data = text;
  }

  public Record(String text, int max) {
    int len = text.length();
    if (len > max)
      len = max;
    this.data = text.substring(0,len);
  }

  public String getData() {
    return data;
  }

  public int compareTo(Record o) {
    Record r = (Record) o;
    return this.data.compareTo(r.data);
  }

}

