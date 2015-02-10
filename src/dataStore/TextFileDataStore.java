package dataStore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by sokam on 2/8/15.
 */
public class TextFileDataStore extends DataStore {
  public static String TEXTS = "TEXT";
  public static String DATES = "DATE";
  public static String TIMES = "TIME";
  public static String MONEY = "MONEY";

  public static String[] TYPES_RECORD = {TEXTS, DATES, TIMES, MONEY};
  public static ArrayList<String> TYPES = new ArrayList<String>(Arrays.asList(TYPES_RECORD));

  public static final String EXTENSION = ".datastore";
  public static final String WRITE_DELIMITER = "|";
  public static final String READ_DELIMITER = "\\|";
  public static final String LINEFEED = "\n";

  public TextFileDataStore() {
    super();
  }


  public TextFileDataStore(String name) {
    super();
    this.name = name;
    open(name);
  }


  public TextFileDataStore(String name,
                           List<String> keys,
                           List<String> columns,
                           List<String> types,
                           List<List<Record>> records) {
    this.create(name, keys, columns, types, records);
  }


  public void open(String name) {
    this.name = name;
    Scanner in = null;

    try {
      in = new Scanner(new File(name + EXTENSION));
      importExistingData(in);

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }


  private void importExistingData(Scanner in) {
    boolean hasNotSetColumns = true;
    boolean hasNotSetTypes = true;
    boolean hasNotSetKeys = true;

    String line = "";
    String [] listOfData;

    while (in.hasNextLine()) {
      line = in.nextLine();
      listOfData = line.split(READ_DELIMITER);

      if (hasNotSetKeys) {
        this.keys = new ArrayList<String>(Arrays.asList(listOfData));
        hasNotSetKeys = false;
      } else if (hasNotSetTypes) {
        this.types = new ArrayList<String>(Arrays.asList(listOfData));
        hasNotSetTypes = false;
      } else if (hasNotSetColumns) {
        this.columns = new ArrayList<String>(Arrays.asList(listOfData));
        hasNotSetColumns = false;
      } else {
        insert(listOfData);
      }
    }
  }


  public void insert(String[] data) {
    int len = this.columns.size();

    if (data.length == len) {
      ArrayList<Record> record = createRecord(data);

      if (record.size() == len) {
        this.records.put(createHashKey(record), record);
      }
    }
  }


  public ArrayList<Record> createRecord(String[] data) {
    return createRecord(Arrays.asList(data), this.types);
  }

  public ArrayList<Record> createRecord(List<String> data) {
    return createRecord(data, this.types);
  }



  public static ArrayList<Record> createRecord(List<String> data, List<String> types) {
    int len = types.size();
    ArrayList<Record> record = new ArrayList<Record>();

    for (int i = 0; i < len; ++i) {
      Record newRecord = matchRecordTypeOf(data.get(i), types.get(i));

      if (newRecord == null) {
        break;
      }
      record.add(newRecord);
    }
    return record;
  }


  public static ArrayList<Record> createRecord(String[] data, List<String> types) {
    return createRecord(Arrays.asList(data), types);
  }


  private static Record matchRecordTypeOf(String data, String type) {
    Record newRecord = null;


    if (type.equals(TEXTS)) {
      newRecord = new TextRecord(data);
    } else if (type.equals(DATES) && DateRecord.isValid(data)) {
      newRecord = new DateRecord(data);
    } else if (type.equals(TIMES) && TimeRecord.isValid(data)) {
      newRecord = new TimeRecord(data);
    } else if (type.equals(MONEY) && MoneyRecord.isValid(data)) {
      newRecord = new MoneyRecord(data);
    }
    return newRecord;
  }

  public static ArrayList<List<Record>> createAllRecords (List<List<String>> data,
                                                          List<String> types) {
    ArrayList<List<Record>> records = new ArrayList<List<Record>>();

    for (List<String> line : data) {
      List<Record> record = createRecord(line, types);
      if (record.size() == types.size()) {
        records.add(record);
      }
    }

    return records;
  }


  public void close() {
    try {
      File file = new File(name + EXTENSION);

      FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      writeDataTo(bufferedWriter);
      bufferedWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private void writeDataTo(BufferedWriter bufferedWriter) throws IOException {
    writeHeadersTo(bufferedWriter);
    writeRecordTo(bufferedWriter);
  }


  private void writeRecordTo(BufferedWriter bufferedWriter) throws IOException {
    for (Map.Entry<String, List<Record>> pair : this.records.entrySet()) {
      bufferedWriter.write(getDelimitedStringFromArrayListOfRecord(pair.getValue()));
    }
  }


  private void writeHeadersTo(BufferedWriter bufferedWriter) throws IOException {
    bufferedWriter.write(getDelimitedStringFromArrayListOfString(this.keys));
    bufferedWriter.write(getDelimitedStringFromArrayListOfString(this.types));
    bufferedWriter.write(getDelimitedStringFromArrayListOfString(this.columns));
  }


  protected String getDelimitedStringFromArrayListOfString(List<String> strings) {
    String line = strings.get(0);
    for (int i = 1; i < strings.size(); ++i) {
      line += WRITE_DELIMITER + strings.get(i);
    }
    return line + LINEFEED;
  }


  protected String getDelimitedStringFromArrayListOfRecord(List<Record> records) {
    String line = records.get(0).getData();
    for (int i = 1; i < records.size(); ++i) {
      line += WRITE_DELIMITER + records.get(i).getData();
    }
    return line + LINEFEED;
  }

}
