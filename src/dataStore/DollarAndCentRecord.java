package dataStore;

import java.text.ParseException;

/**
 * Created by sokam on 2/7/15.
 */
public class DollarAndCentRecord extends Record {

  public static final String DATA_NOT_IN_CORRECT_FORMAT = "Data must be in Dollar and Cents. e.g. 3.25";
  public static final String COMPARED_TO_DIFFERENT_OBJECT = "Must compare to same data Type";

/*  public DollarAndCentRecord() {
    super();
  }*/

  public DollarAndCentRecord(String data) {
    super();
    setData(data);
  }

  public void setData(String data) {
    if (data.matches("\\d+\\.\\d{2}")) {
      super.setData(data);
    } else {
      throw new IllegalArgumentException(DATA_NOT_IN_CORRECT_FORMAT);
    }
  }


  public boolean isValid () {
    return DollarAndCentRecord.isValid(getData());
  }

  public static boolean isValid (String data) {
    return data.matches("\\d+\\.\\d{2}");
  }

  @Override
  public int compareTo(Record record) throws IllegalArgumentException {
    if (record instanceof DollarAndCentRecord) {
      if (this.isValid() && record.isValid()) {
        Double fst = Double.parseDouble(this.getData());
        Double snd = Double.parseDouble(record.getData());
        return fst.compareTo(snd);
      } else {
        throw new IllegalArgumentException(DATA_NOT_IN_CORRECT_FORMAT);
      }

    } else {
      throw new IllegalArgumentException(COMPARED_TO_DIFFERENT_OBJECT);
    }
  }
}
