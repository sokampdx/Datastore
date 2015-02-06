package com.example.sokam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sokam on 2/5/15.
 */
public class DataStore {
  private ArrayList<String> header;
  private String name;
  private ArrayList<String> keys;

  public DataStore() {
    this.header = new ArrayList<String>();
    this.keys = new ArrayList<String>();
    this.name = "";

  }

  public DataStore(String name, String[] header, String[] keys) {
    this.name = new String(name);
    this.header = new ArrayList<String>(Arrays.asList(header));
    this.keys = new ArrayList<String>(Arrays.asList(keys));
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

}
