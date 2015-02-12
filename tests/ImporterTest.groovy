import dataStore.Importer.DelimitedTextImporter
import dataStore.Importer.DataImporter
import dataStore.Util.MyUtil

/**
 * Created by sokam on 2/7/15.
 */
class ImporterTest extends GroovyTestCase {
    public static final String SAMPLE_TXT_FILE = "sample.txt";
    public static final String ORIGINAL_TXT_FILE = "original.txt";
    public static final String DELIMITER = "\\|";

    public static final String ORIGINAL = "STB|TITLE|PROVIDER|DATE|REV|VIEW_TIME\n" +
            "stb1|the matrix|warner bros|2014-04-01|4.00|1:30\n" +
            "stb1|unbreakable|buena vista|2014-04-03|6.00|2:05\n" +
            "stb2|the hobbit|warner bros|2014-04-02|8.00|2:45\n" +
            "stb3|the matrix|warner bros|2014-04-02|4.00|1:05\n"

    public void testImporterReadCorrectFilename() {
        DataImporter importer = new DelimitedTextImporter(SAMPLE_TXT_FILE, DELIMITER);
        assertEquals(SAMPLE_TXT_FILE, importer.getSource());
    }

    public void testImporterReadIncorrectFilename() {
        DataImporter importer = new DelimitedTextImporter(ORIGINAL_TXT_FILE, DELIMITER);
        assertEquals(ORIGINAL, MyUtil.ListOfListOfStringToString(importer.getData(),"|"));
    }


}
