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

    DataStore(String filename) {
        this.filename = filename;
        this.data = new ArrayList<List<String>>();
        readFromFile();
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
