package dataStore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by sokam on 2/5/15.
 */
public class DataStore {
  private static final String EXTENTION = ".datastore";
  private static final String WRITEDELIMITER = "|";
  private static final String READDELIMITER = "\\|";
  private static final String LINEFEED = "\n";
  private ArrayList<String> header;
  private String name;
  private ArrayList<String> keys;
  private HashMap<String, ArrayList<String>> record;

  public DataStore() {
    this.header = new ArrayList<String>();
    this.keys = new ArrayList<String>();
    this.record = new HashMap<String, ArrayList<String>>();
    this.name = "";

  }

  public DataStore(String name, String[] header, String[] keys) {
    this.name = name;
    this.header = new ArrayList<String>(Arrays.asList(header));
    this.keys = new ArrayList<String>(Arrays.asList(keys));
    this.record = new HashMap<String, ArrayList<String>>();
  }

  public DataStore(String name, ArrayList<String> header, List<List<String>> data, String[] keys) {
    this.name = name;
    this.header = new ArrayList<String>(header);
    this.keys = new ArrayList<String>(Arrays.asList(keys));
    this.record = new HashMap<String, ArrayList<String>>();

    String [] array = new String[this.header.size()];
    for (List<String> list : data) {
      insert(list.toArray(array));
    }
  }

  public void create(String name, ArrayList<String> header, List<List<String>> data, String[] keys) {
    this.name = name;
    this.header = new ArrayList<String>(header);
    this.keys = new ArrayList<String>(Arrays.asList(keys));
    this.record = new HashMap<String, ArrayList<String>>();

    String [] array = new String[this.header.size()];
    for (List<String> list : data) {
      insert(list.toArray(array));
    }
  }

  public void create(String name, String[] header, String[] keys) {
    this.name = name;
    for (String column : header) {
      this.header.add(column);
    }
    for (String key : keys){
      this.keys.add(key);
    }
  }

  public String getName() {
    return this.name;
  }

  public String[] getHeader() {
    String [] list = new String[this.header.size()];
    for (int i = 0; i < this.header.size(); ++i) {
      list[i] = this.header.get(i);
    }
    return list;
  }

  public int getNumRows() {
    return record.size();
  }

  public void insert(String[] data) {
    if (data.length == this.header.size()) {
      int [] index = new int [this.keys.size()];
      String key = "";

      for (int i = 0; i < this.keys.size(); ++i) {
        index[i] = this.header.indexOf(this.keys.get(i));
        key += data[index[i]];
      }

      this.record.put(key, new ArrayList<String>(Arrays.asList(data)));

    }
  }

  public void printAll() {
    Set set = record.entrySet();
    Iterator iterator = set.iterator();
    while (iterator.hasNext()) {
      Map.Entry entry = (Map.Entry) iterator.next();
      System.out.println("Key: " + entry.getKey() + "; Value: " + entry.getValue());
    }
  }


  public void close() {
    try {
      File file = new File(name + EXTENTION);

      if (!file.exists()) {
        file.createNewFile();
      }

      FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      bufferedWriter.write(getArrayDelimitedString(this.header));

      Set set = record.entrySet();
      Iterator iterator = set.iterator();
      while (iterator.hasNext()) {
        Map.Entry entry = (Map.Entry) iterator.next();
        bufferedWriter.write(getArrayDelimitedString((ArrayList<String>) entry.getValue()));
      }

      bufferedWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getArrayDelimitedString(ArrayList<String> strings) {
    String line = strings.get(0);
    for (int i = 1; i < strings.size(); ++i) {
      line += WRITEDELIMITER + strings.get(i);
    }
    return line + LINEFEED;
  }

  public void open() {
    String line = "";
    String [] strList;
    Scanner in = null;
    boolean isHeader = true;

    try {
      in = new Scanner(new File(name + EXTENTION));
      while (in.hasNextLine()) {
        line = in.nextLine();
        strList = line.split(READDELIMITER);
        if (isHeader) {
          this.header = new ArrayList<String>(Arrays.asList(strList));
          isHeader = false;
        } else {
          insert(strList);
        }
      }

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

}
