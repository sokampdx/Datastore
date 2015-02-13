package dataStore.Records;

/**
 * Created by sokam on 2/7/15.
 */
public class TimeRecord extends Record {
  public static final String DATA_NOT_IN_CORRECT_FORMAT = "Data must be Time Format hh:mm. e.g. 20:59";
  public static final String COMPARED_TO_DIFFERENT_OBJECT = "Must compare to same data Type";
  public static final String TIME_DELIMITER = ":";
  public static final int HOUR = 0;
  public static final int MINUTE = 1;
  public static final String REGEX = "((2[0-3])|((1|0?)[0-9])):[0-5][0-9]";

  public TimeRecord(String data) {
    super();
    this.setData(data);
  }

  public void setData(String data) {
    if (TimeRecord.isValid(data)) {
      super.setData(data);
    } else {
      throw new IllegalArgumentException(DATA_NOT_IN_CORRECT_FORMAT);
    }
  }

  public boolean isValid() {
    return TimeRecord.isValid(this.getData());
  }

  public static boolean isValid(String data) {
    return data.matches(REGEX);
  }

  @Override
  public int compareTo(Record record) throws IllegalArgumentException {
    if (record instanceof TimeRecord) {
      if (this.isValid() && record.isValid()) {
        String[] fstString = this.getData().split(TIME_DELIMITER);
        String[] sndString = record.getData().split(TIME_DELIMITER);

        Integer [] fstInt = convertStringToInt(fstString);
        Integer [] sndInt = convertStringToInt(sndString);

        return arrayCompare(fstInt, sndInt);

      } else {
        throw new IllegalArgumentException(DATA_NOT_IN_CORRECT_FORMAT);
      }

    } else {
      throw new IllegalArgumentException(COMPARED_TO_DIFFERENT_OBJECT);
    }
  }

  private Integer[] convertStringToInt(String[] stringsArray) {
    int len = stringsArray.length;
    Integer [] integerArray = new Integer[len];

    for (int i = 0; i < len; ++i) {
      integerArray[i] = Integer.parseInt(stringsArray[i]);
    }

    return integerArray;
  }

  private int arrayCompare(Integer[] array1, Integer[] array2) {
    int compareHour = array1[HOUR].compareTo(array2[HOUR]);

    if (compareHour == 0) {
      return array1[MINUTE].compareTo(array2[MINUTE]);
    } else {
      return compareHour;
    }
  }

  @Override
  public Record clone() {
    return new TimeRecord(getData());
  }
}
