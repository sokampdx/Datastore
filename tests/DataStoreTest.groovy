

import com.example.sokam.DataStore

/**
 * Created by sokam on 2/4/15.
 */
class DataStoreTest extends GroovyTestCase {

    private final String FILENAME = "sample.txt";
    private final String[] HEADER = "STB|TITLE|PROVIDER|DATE|REV|VIEW_TIME".split(DataStore.DELIMITER);
    private String[] line;
    private DataStore dataStore = new DataStore(DataStore.FILENAME);

    public void testDatastoreReadCorrectFilename() {
        assertEquals(DataStore.FILENAME, dataStore.getFilename());
    }

    public void testHeaderDataIsCorrect() {
        String expected = DataStoreTest.convertArrayToString(HEADER);
        assertEquals(expected, dataStore.getHeader());
    }

    private static String convertArrayToString(String[] inputStr) {
        String expected = "";
        for (String str : inputStr) {
            expected += str;
        }
        return expected;
    }

    public void testLine11OfTheRecord() {
        line = "stb10|the matrix|warner bros|2014-04-02|3.00|1:05".split(DataStore.DELIMITER);
        String expected = DataStoreTest.convertArrayToString(line);
        assertEquals(expected, dataStore.getRow(11));
    }

    public void testOutOfBoundIndexForData() {
        assertEquals("", dataStore.getRow(-1));
    }


}
