package dataStore.Records;

/**
 * Created by sokam on 2/7/15.
 */
public abstract class Record implements Comparable<Record>, Cloneable {
  private String REGEX = "\\d+(\\.\\d+)?";
  private String data;

  public Record() {
    this.data = "";
  }

  public void setData(String data) {
    this.data = data;
  }

  public void setData(String format, String data) {
    if (data.matches(REGEX)) {
      this.data = String.format(format, Double.parseDouble(data));
    } else {
      this.data = data;
    }
  }
  public String getData() {
    return this.data;
  }

  public abstract boolean isValid();

  public boolean isSummable() {
    return false;
  }

  public void add(Record record) {
    if (this.isSummable() && record.isSummable()) {
      Double sum = Double.parseDouble(this.data) + Double.parseDouble(record.data);
      this.data = sum.toString();
    }
  }

  public abstract Record clone();

  public boolean equals(Record record) {
    return this.data.equals(record.data);
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
