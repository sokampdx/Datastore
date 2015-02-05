

import com.example.sokam.Importer
import jdk.nashorn.internal.ir.annotations.Ignore

/**
 * Created by sokam on 2/4/15.
 */
class ImporterTest extends GroovyTestCase {

    private final String FILENAME = "sample.txt";
    private final String[] HEADER = "STB|TITLE|PROVIDER|DATE|REV|VIEW_TIME".split(Importer.DELIMITER);
    private String[] line;

    public void testFileNameIsReadCorrectly() {
        Importer importer = new Importer(FILENAME);
        assertEquals(FILENAME, importer.getFilename());
    }

    public void testHeaderFromData() {
        Importer importer = new Importer(FILENAME);
        String expected = ImporterTest.convertArrayToString(HEADER);
        assertEquals(expected, importer.getHeader());
    }

    private static String convertArrayToString(String[] inputStr) {
        String expected = "";
        for (String str : inputStr) {
            expected += str;
        }
        return expected;
    }

    public void testLastLineFromData() {
        Importer importer = new Importer(FILENAME);
        line = "stb10|the matrix|warner bros|2014-04-02|3.00|1:05".split(Importer.DELIMITER);
        String expected = ImporterTest.convertArrayToString(line);
        assertEquals(expected, importer.getRow(11));
    }

    public void testOutOfBoundIndexForData() {
        Importer importer = new Importer(FILENAME);
        assertEquals("", importer.getRow(-1));
    }

}
