import dataStore.Main
import dataStore.Util.MyUtil

/**
 * Created by sokam on 2/10/15.
 */
class MainTest extends GroovyTestCase  {
    public final String COMMA = ",";
    public final String EOL = "\n";

    public final String query1 = "-s TITLE,REV,DATE";
    public final String query1Answer =
            "the matrix,4.00,2014-04-01\n" +
            "unbreakable,6.00,2014-04-03\n" +
            "the hobbit,8.00,2014-04-02\n" +
            "the matrix,4.00,2014-04-02\n";
    public final String query2 = "-s TITLE,REV,DATE -o DATE,TITLE";
    public final String query2Answer =
            "the matrix,4.00,2014-04-01\n" +
            "the hobbit,8.00,2014-04-02\n" +
            "the matrix,4.00,2014-04-02\n" +
            "unbreakable,6.00,2014-04-03\n";
    public final String query3 = "-f DATE=2014-04-02";
    public final String query3Answer =
            "stb2,the hobbit,warner bros,2014-04-02,8.00,2:45\n" +
            "stb3,the matrix,warner bros,2014-04-02,4.00,1:05";

    public final String query4 = "-s TITLE,REV,DATE -f DATE=2014-04-01";
    public final String query4Answer = "the matrix,4.00,2014-04-01";

    public final String query5 = "-o DATE, TITLE";
    public final String query5Answer =
            "stb1,the matrix,warner bros,2014-04-01,4.00,1:30\n" +
            "stb2,the hobbit,warner bros,2014-04-02,8.00,2:45\n" +
            "stb3,the matrix,warner bros,2014-04-02,4.00,1:05\n" +
            "stb1,unbreakable,buena vista,2014-04-03,6.00,2:05\n";

    public final String query6 = "-s TITLE,REV,DATE -o DATE,REV -f DATE=2014-04-02";
    public final String query6Answer =
            "the matrix,4.00,2014-04-02\n" +
            "the hobbit,8.00,2014-04-02\n";

    public final String query7 = "-s REV:min";
    public final String query7Answer = "4.00\n"

    public final String query8 = "-s VIEW_TIME:min";
    public final String query8Answer = "1:05\n"

    public final String query9 = "-s DATE:max";
    public final String query9Answer = "2014-04-03\n"

    public final String query10 = "-s VIEW_TIME:max";
    public final String query10Answer = "2:45\n"

    public final String query11 = "-s TITLE,PROVIDER:max,VIEW_TIME:max";
    public final String query11Answer = "the matrix,warner bros,2:45\n"

    public final String query12 = "-s DATE:count -f DATE=2014-04-02";
    public final String query12Answer = "1\n";

    public final String query13 = "-s PROVIDER:count";
    public final String query13Answer = "2\n";

    public final String query14 = "-s TITLE:collect";
    public final String query14Answer = "[the hobbit, the matrix, unbreakable]\n";

    public final String query15 = "-s TITLE,DATE:collect";
    public final String query15Answer = "the matrix,[2014-04-01, 2014-04-02, 2014-04-03]\n"

    public final String query16 = "-s REV:sum";
    public final String query16Answer = "22.00\n";

    public final String query17 = "-s TITLE,DATE:collect -f TITLE=\"the matrix\"";
    public final String query17Answer = "the matrix,[2014-04-01, 2014-04-02]\n"

    public final String query18 = "-s TITLE,REV:sum,STB:collect -g TITLE";
    public final String query18Answer =
            "the matrix,8.00,[stb1, stb3]\n" +
            "the hobbit,8.00,[stb2]\n" +
            "unbreakable,6.00,[stb1]\n";

    public final String query19 = "-s STB:collect,TITLE,PROVIDER,VIEW_TIME:max -g TITLE,PROVIDER";
    public final String query19Answer =
            "[stb2],the hobbit,warner bros,2:45\n" +
            "[stb1],unbreakable,buena vista,2:05\n" +
            "[stb1, stb3],the matrix,warner bros,1:30\n";

    public final String query20 = "-s TITLE,REV -f STB=\"stb1\" AND STB=\"stb2\"";
    public final String query20Answer =
            "the hobbit,8.00\n" +
            "the matrix,4.00\n";

    public final String query21 = "-s TITLE,REV -o TITLE -f STB=\"stb1\" OR STB=\"stb2\"";
    public final String query21Answer =
            "the hobbit,8.00\n" +
            "the matrix,4.00\n" +
            "unbreakable,6.00\n";

    public final String query22 = "-s TITLE,REV -f (STB=\"stb2\" OR STB=\"stb2\") AND (TITLE=\"the hobbit\" OR TITLE=\"unbreakable\") AND DATE=2014-04-01";
    public final String query22Answer =
            "the hobbit,8.00\n" +
            "unbreakable,6.00\n";

    public void testSimpleSelect () {
        String result = Main.run(query1);
        String[] expected = query1Answer.split(EOL);
        String[] actual = result.split(EOL);
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected.toString(), actual.toString());
    }

    public void testSimpleFilter () {
        String result = Main.run(query3);
        String[] expected = query3Answer.split(EOL);
        String[] actual = result.split(EOL);
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected.toString(), actual.toString());
    }

    public void testSimpleSelectWithFilter () {
        String result = Main.run(query4);
        String[] expected = query4Answer.split(EOL);
        String[] actual = result.split(EOL);
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected.toString(), actual.toString());
    }

    public void testOrder () {
        String result = Main.run(query5);
        assertEquals(query5Answer.toString(), result.toString());
    }

    public void testSimpleSelectWithOrder () {
        String result = Main.run(query2);
        assertEquals(query2Answer.toString(), result.toString());
    }

    public void testSimpleSelectWithOrderWithSimpleFilter () {
        String result = Main.run(query6);
        assertEquals(query6Answer.toString(), result.toString());
    }

    public void testMinRev () {
        String result = Main.run(query7);
        assertEquals(query7Answer.toString(), result.toString());
    }

    public void testMinTIME () {
        String result = Main.run(query8);
        assertEquals(query8Answer.toString(), result.toString());
    }

    public void testMaxDATE () {
        String result = Main.run(query9);
        assertEquals(query9Answer.toString(), result.toString());
    }

    public void testMaxTIME () {
        String result = Main.run(query10);
        assertEquals(query10Answer.toString(), result.toString());
    }

    public void testMaxPROVIDERSelectTITLE () {
        String result = Main.run(query11);
        assertEquals(query11Answer.toString(), result.toString());
    }

    public void testCountDATEWithFilter () {
        String result = Main.run(query12);
        assertEquals(query12Answer.toString(), result.toString());
    }

    public void testCountPROVIDER () {
        String result = Main.run(query13);
        assertEquals(query13Answer.toString(), result.toString());
    }

    public void testCollectTitle () {
        String result = Main.run(query14);
        assertEquals(query14Answer.toString(), result.toString());
    }

    public void testCollectDATEWithTITLE () {
        String result = Main.run(query15);
        assertEquals(query15Answer.toString(), result.toString());
    }

    public void testSumREV () {
        String result = Main.run(query16);
        assertEquals(query16Answer.toString(), result.toString());
    }

    public void testFilterTitle () {
        String result = Main.run(query17);
        assertEquals(query17Answer.toString(), result.toString());
    }

    public void testGroupWithAggregate () {
        String result = Main.run(query18);
        assertEquals(query18Answer.toString(), result.toString());
    }

    public void testTwoGroupWithTwoAggregate () {
        String result = Main.run(query19);
        assertEquals(query19Answer.toString(), result.toString());
    }

/*    public void testAdvanceFilterAND() {
        String result = Main.run(query20);
        assertEquals(query20Answer.toString(), result.toString());
        MyUtil.print(query20Answer.toString());
        MyUtil.print(result.toString());
    }*/

    public void testAdvanceFilterOR () {
        String result = Main.run(query21);
        assertEquals(query21Answer.toString(), result.toString());
        MyUtil.print(query21Answer.toString());
        MyUtil.print(result.toString());
    }

    /*    public void testAdvanceFilterBOTH () {
    String result = Main.run(query22);
    assertEquals(query22Answer.toString(), result.toString());
    MyUtil.print(query22Answer.toString());
    MyUtil.print(result.toString());
    }*/
}
