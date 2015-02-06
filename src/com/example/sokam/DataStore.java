package com.example.sokam;

import java.util.*;

/**
 * Created by sokam on 2/5/15.
 */
public class DataStore {
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
    this.name = new String(name);
    this.header = new ArrayList<String>(Arrays.asList(header));
    this.keys = new ArrayList<String>(Arrays.asList(keys));
    this.record = new HashMap<String, ArrayList<String>>();
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

  public int getRows() {
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
}
