

import dataStore.ImportFromFileWithRules

/**
 * Created by sokam on 2/4/15.
 */
class ImportFromFileWithRulesTest extends GroovyTestCase {

    public static final String FILENAME = "sample.txt";
    public static final String DELIMITER = "\\|";
    private final String[] HEADER = "STB|TITLE|PROVIDER|DATE|REV|VIEW_TIME".split(ImportFromFileWithRules.DELIMITER);
    private String[] line;
    private ImportFromFileWithRules importer = new ImportFromFileWithRules(ImportFromFileWithRules.FILENAME);

    public void testImporterReadCorrectFilename() {
        assertEquals(ImportFromFileWithRules.FILENAME, importer.getFilename());
    }

    public void testHeaderDataIsCorrect() {
        String expected = ImportFromFileWithRulesTest.convertArrayToString(HEADER);
        assertEquals(expected, importer.getHeaderString());
    }

    private static String convertArrayToString(String[] inputStr) {
        String expected = "";
        for (String str : inputStr) {
            expected += str;
        }
        return expected;
    }

    public void testSpecificLineOfTheRecord() {
        line = "stb10|the matrix|warner bros|2014-04-02|3.00|1:05".split(ImportFromFileWithRules.DELIMITER);
        String expected = ImportFromFileWithRulesTest.convertArrayToString(line);
        assertEquals(expected, importer.getRowString(9));
    }

    public void testOutOfBoundIndexForData() {
        assertEquals("", importer.getRowString(-1));
    }

    public void testInvalidDataDoesNotAdd() {
        assertEquals(11, importer.getRow());
    }

    public void testGetAllImportData() {
        //System.out.println(importer.toString());
        assertEquals(importer.toString(), ImportFromFileWithRules.convertToText(importer.getData()));
    }

}
