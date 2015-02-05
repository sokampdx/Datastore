package com.example.sokam;


import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataStore {

    public static final String DELIMITER = "\\|";
    public static final String FILENAME = "sample.txt";
    private String filename;
    private List<List<String>> data;
    private ArrayList<String> header;
    private List<List<Record>> record;


    DataStore(String filename) {
        this.filename = filename;
        this.data = new ArrayList<List<String>>();
        readFromFile();

        this.header = new ArrayList<String>(this.data.get(0));

        ArrayList<Record> current = new ArrayList<Record>();

 /*       int len = data.size();
        for (int i = 1; i < len; ++i) {
            List<String> list = data.get(i);
            current.add(new TextRecord(list.get(0)));
            current.add(new TextRecord(list.get(1)));
            current.add(new TextRecord(list.get(2)));
            current.add(new DateRecord(list.get(3)));
            current.add(new DollarRecord(list.get(4)));
            current.add(new TimeRecord(list.get(5)));
        }*/
    }

    public String getFilename() {
        return this.filename;
    }

    public String getHeader() {
        return getRow(0);
    }

    public String getRow(int index) {
        String result = "";

        if (index < 0 || index >= data.size())
            return result;

        for (String str : data.get(index)) {
            result += str;
        }
        return result;
    }

    private void readFromFile() {
        String line = "";
        List<String> strList;
        Scanner in = null;
        try {
            in = new Scanner(new File(filename));
            while (in.hasNextLine()) {
                line = in.nextLine();
                strList = Arrays.asList(line.split(DELIMITER));
                this.data.add(new ArrayList<String>(strList));
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }



    public static void main(String[] args) {
	  // write your code here


    }
}
