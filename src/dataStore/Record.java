package dataStore;

/**
 * Created by sokam on 2/7/15.
 */
public abstract class Record implements Comparable<Record> {
  private String data;

  public Record() {
    this.data = "";
  }
/*
  public Record(String data) {
    this.data = data;
  }*/

  public void setData(String data) {
    this.data = data;
  }

  public String getData() {
    return this.data;
  }

  public abstract boolean isValid();

  public boolean isSummable() {
    return false;
  }

  public int compareTo(Record record) {
    if (record == null) {
      return 1;
    }
    return this.data.compareTo(record.data);
  }

  public String toString() {
    return this.data;
  }
}
