

import com.example.sokam.Importer

/**
 * Created by sokam on 2/4/15.
 */
class ImporterTest extends GroovyTestCase {

    private final String FILENAME = "sample.txt";
    private final String[] HEADER = "STB|TITLE|PROVIDER|DATE|REV|VIEW_TIME".split(Importer.DELIMITER);
    private String[] line;
    private Importer importer = new Importer(Importer.FILENAME);

    public void testImporterReadCorrectFilename() {
        assertEquals(Importer.FILENAME, importer.getFilename());
    }

    public void testHeaderDataIsCorrect() {
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

    public void testSpecificLineOfTheRecord() {
        line = "stb10|the matrix|warner bros|2014-04-02|3.00|1:05".split(Importer.DELIMITER);
        String expected = ImporterTest.convertArrayToString(line);
        assertEquals(expected, importer.getRow(10));
    }

    public void testOutOfBoundIndexForData() {
        assertEquals("", importer.getRow(-1));
    }

    public void testInvalidDataDoesNotAdd() {
        assertEquals(11, importer.getRow());
    }

    public void testGetAllImportData() {
        //System.out.println(importer.toString());
        assertEquals(importer.toString(), Importer.convertToText(importer.getData()));
    }

}
