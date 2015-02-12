package dataStore.Records;

/**
 * Created by sokam on 2/7/15.
 */
public class TextRecord extends Record {
  private final int DEFAULT_MAX_CHAR_ALLOW = 64;
  private int max_Char_Allow;

/*
  public TextRecord() {
    super();
  }
*/

  public TextRecord(String data) {
    this.max_Char_Allow = this.DEFAULT_MAX_CHAR_ALLOW;
    this.setData(data, this.max_Char_Allow);
  }

  public TextRecord(String data, int max_Char_Allow) {
    this.setData(data, max_Char_Allow);
  }

  public void setData(String data) {
    this.setData(data, DEFAULT_MAX_CHAR_ALLOW);
  }

  public void setData(String data, int max_Char_Allow) {
    int len = data.length();
    if (len > max_Char_Allow)
      len = max_Char_Allow;

    super.setData(data.substring(0, len));
    this.max_Char_Allow = max_Char_Allow;
  }

  public boolean isValid() {
    return getData().length() <= this.max_Char_Allow;
  }

}
