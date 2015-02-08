import dataStore.DataStore
import dataStore.ImportFromFileWithRules;

/**
 * Created by sokam on 2/5/15.
 */
class DataStoreTest extends GroovyTestCase {
    private final String[] header = "STB|TITLE|PROVIDER|DATE|REV|VIEW_TIME".split("\\|");
    private final String name = "sample";
    private final String[] keys = "STB|TITLE|DATE".split("\\|");
    private final String[] data1 = "stb1|the matrix|warner bros|2014-04-01|4.00|1:30".split("\\|");
    private final String[] data2 = "stb1|unbreakable|buena vista|2014-04-03|6.00|2:05".split("\\|");
    private final String[] data3 = "stb1|the matrix|buena vista|2014-04-01|6.00|2:05".split("\\|");

    public void testNewDataStore() {
        DataStore dataStore = new DataStore();
    }

    public void testCreateTable() {
        DataStore dataStore = new DataStore();
        dataStore.create(name, header, keys);
    }

    public void testCreateTableVerifyName() {
        DataStore dataStore = new DataStore();
        dataStore.create(name, header, keys);
        assertEquals(name, dataStore.getName());
    }

    public void testNewDataStoreWithArgConstructor() {
        DataStore dataStore = new DataStore(name, header, keys);
        assertEquals(name, dataStore.getName());
    }


    public void testVerifyHeader() {
        DataStore dataStore = new DataStore(name, header, keys);
        assertEquals(header.toString(), dataStore.getHeader().toString());
    }

    public void testNoRecordReturnZero() {
        DataStore dataStore = new DataStore(name, header, keys);
        assertEquals(0, dataStore.getNumRows());
    }

    public void testAddOneRowDataReturnOneRow() {
        DataStore dataStore = new DataStore(name, header, keys);
        dataStore.insert(data1);
        assertEquals(1, dataStore.getNumRows());
    }

    public void testAddTwoRowDataReturnTwoRow() {
        DataStore dataStore = new DataStore(name, header, keys);
        dataStore.insert(data1);
        dataStore.insert(data2);
        assertEquals(2, dataStore.getNumRows());
    }

    public void testAddSameKeyDataReturnUniqueRow() {
        DataStore dataStore = new DataStore(name, header, keys);
        dataStore.insert(data3);
        dataStore.insert(data1);
        dataStore.insert(data2);
        assertEquals(2, dataStore.getNumRows());
    }

    public void testCreateDataStoreFromImporter() {
        ImportFromFileWithRules importer = new ImportFromFileWithRules(name + ".txt");
        DataStore dataStore = new DataStore(name, importer.getHeader(), importer.getData(), keys);
        assertEquals(10, dataStore.getNumRows());
    }

    public void testCloseDataStoreFile() {
        ImportFromFileWithRules importer = new ImportFromFileWithRules(name + ".txt");
        DataStore dataStore = new DataStore(name, importer.getHeader(), importer.getData(), keys);
        dataStore.close();
    }

    public void testOpenDataStoreFile() {
        ImportFromFileWithRules importer = new ImportFromFileWithRules(name + ".txt");
        DataStore dataStore = new DataStore(name, importer.getHeader(), importer.getData(), keys);
        dataStore.open();
        //dataStore.printAll();
    }
}
