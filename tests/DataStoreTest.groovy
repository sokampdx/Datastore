import dataStore.DataStore
import dataStore.DelimitedTextImporter
import dataStore.Importer
import dataStore.Record
import dataStore.TextFileDataStore

/**
 * Created by sokam on 2/8/15.
 */
class DataStoreTest extends GroovyTestCase {
    private final String DELIMITER = "\\|";
    private final String[] columns = "STB|TITLE|PROVIDER|DATE|REV|VIEW_TIME".split(DELIMITER);
    private final String sample = "sample";
    private final String test = "test";
    private final String original = "original";
    private final String[] keys = "STB|TITLE|DATE".split(DELIMITER);
    private final String[] types = "TEXT|TEXT|TEXT|DATE|MONEY|TIME".split(DELIMITER);
    private final String[] data1 = "stb1|the matrix|warner bros|2014-04-01|4.00|1:30".split(DELIMITER);
    private final String[] data2 = "stb1|unbreakable|buena vista|2014-04-03|6.00|2:05".split(DELIMITER);
    private final String[] data3 = "stb1|the matrix|buena vista|2014-04-01|6.00|2:05".split(DELIMITER);

    public void testNewDataStore() {
        DataStore dataStore = new TextFileDataStore();
    }

    public void testNewDataStoreWithAllArg() {
        List<List<Record>> records = new ArrayList<List<Record>>();

        records.add(TextFileDataStore.createRecord(data1, Arrays.asList(types)));
        records.add(TextFileDataStore.createRecord(data2, Arrays.asList(types)));
        records.add(TextFileDataStore.createRecord(data3, Arrays.asList(types)));

        DataStore dataStore = new TextFileDataStore(test,
                Arrays.asList(keys),
                Arrays.asList(columns),
                Arrays.asList(types),
                records);

        assertEquals(test, dataStore.getName());
        assertEquals(keys.toString(), dataStore.getListOfKey());
        assertEquals(columns.toString(), dataStore.getListOfColumn());
        assertEquals(types.toString(), dataStore.getListOfType());
        dataStore.close();
    }

    public void testNewDataStoreWithFileArg() {
        DataStore dataStore = new TextFileDataStore(test);
        assertEquals(test, dataStore.getName());
        assertEquals(keys.toString(), dataStore.getListOfKey());
        assertEquals(columns.toString(), dataStore.getListOfColumn());
        assertEquals(types.toString(), dataStore.getListOfType());
    }

    public void testNewDataStoreUsingImporterWithOutInvalidRow() {
        Importer importer = new DelimitedTextImporter(original + ".txt", DELIMITER);
        List<List<String>> data = importer.getData();
        DataStore dataStore = new TextFileDataStore(original,
                Arrays.asList(keys),
                data.remove(0),
                Arrays.asList(types),
                TextFileDataStore.createAllRecords(data, Arrays.asList(types)));
        dataStore.close();
    }

    public void testNewDataStoreUsingImporterWithInvalidRow() {
        Importer importer = new DelimitedTextImporter(sample + ".txt", DELIMITER);
        List<List<String>> data = importer.getData();
        DataStore dataStore = new TextFileDataStore(sample,
                Arrays.asList(keys),
                data.remove(0),
                Arrays.asList(types),
                TextFileDataStore.createAllRecords(data, Arrays.asList(types)));
        dataStore.close();
    }

    public void testReadFromDataStoreFile() {
        DataStore dataStore = new TextFileDataStore(sample);
        assertEquals(sample, dataStore.getName());
        assertEquals(keys.toString(), dataStore.getListOfKey());
        assertEquals(columns.toString(), dataStore.getListOfColumn());
        assertEquals(types.toString(), dataStore.getListOfType());
    }
}
