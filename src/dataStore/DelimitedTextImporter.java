package dataStore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by sokam on 2/7/15.
 */
public class DelimitedTextImporter extends Importer {
  private String delimiter;

  public DelimitedTextImporter(String filename, String delimiter) {
    super(filename);
    this.delimiter = delimiter;
    readFromFile();
  }

  private void readFromFile() {
    String [] strList;
    Scanner in = null;
    try {
      in = new Scanner(new File(getSource()));
      while (in.hasNextLine()) {
        String line = in.nextLine();
        strList = line.split(delimiter);
        super.addData(new ArrayList<String>(Arrays.asList(strList)));
      }

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
