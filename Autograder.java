import java.io.File;
import java.io.FilenameFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Autograder {
    public static void main(String[] args) {
        File afdsDir = new File("tests/afds/");
        File[] afdFiles = afdsDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".afd");
            }
        });

        if (afdFiles == null || afdFiles.length == 0) {
            System.out.println("No tests found.");
            return;
        }

        Arrays.sort(afdFiles);

        int totalTests = afdFiles.length;
        double scorePerTest = 100.0 / totalTests;
        double finalScore = 0;
        ArrayList<String> results = new ArrayList<String>();

        for (int i = 0; i < afdFiles.length; i++) {
            File afdFile = afdFiles[i];
            String fileName = afdFile.getName();
            String testName = fileName.substring(0, fileName.lastIndexOf(".afd"));

            String stringsPath = "tests/strings/" + testName + ".txt";
            String expectedEvalPath = "tests/expected/evaluation/" + testName + ".txt";
            String expectedSerialPath = "tests/expected/serialization/" + testName + ".txt";

            double testScore = 0;

            try {
                AFD afd = new AFD(afdFile.getPath());

                // --- Evaluation: 60% ---
                boolean evalPassed = evaluateStrings(afd, stringsPath, expectedEvalPath);
                if (evalPassed) {
                    testScore += scorePerTest * 0.6;
                }

                // --- Serialization: 40% ---
                boolean serialPassed = checkSerialization(afd, expectedSerialPath);
                if (serialPassed) {
                    testScore += scorePerTest * 0.4;
                }

                finalScore += testScore;

                if (evalPassed && serialPassed) {
                    results.add(testName + ": PASSED (" + formatScore(testScore) + "/" + formatScore(scorePerTest) + ")");
                } else {
                    String detail = "";
                    if (!evalPassed) detail += " [evaluation failed]";
                    if (!serialPassed) detail += " [serialization failed]";
                    results.add(testName + ": PARTIAL" + detail + " (" + formatScore(testScore) + "/" + formatScore(scorePerTest) + ")");
                }

            } catch (Exception e) {
                results.add(testName + ": FAILED [exception: " + e.getMessage() + "] (0.0/" + formatScore(scorePerTest) + ")");
            }
        }

        System.out.println("Results:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println("  " + results.get(i));
        }
        System.out.println("Final Score: " + formatScore(finalScore) + "/100.0");
    }

    private static boolean evaluateStrings(AFD afd, String stringsPath, String expectedPath) {
        BufferedReader stringsReader = null;
        BufferedReader expectedReader = null;
        try {
            stringsReader = new BufferedReader(new FileReader(stringsPath));
            expectedReader = new BufferedReader(new FileReader(expectedPath));
            String inputLine, expectedLine;

            while (true) {
                inputLine = stringsReader.readLine();
                expectedLine = expectedReader.readLine();
                if (inputLine == null && expectedLine == null) break;
                if (inputLine == null || expectedLine == null) return false;

                boolean result = afd.accept(inputLine.trim());
                boolean expected = Boolean.parseBoolean(expectedLine.trim());
                if (result != expected) return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try { if (stringsReader != null) stringsReader.close(); } catch (IOException e) {}
            try { if (expectedReader != null) expectedReader.close(); } catch (IOException e) {}
        }
    }

    private static boolean checkSerialization(AFD afd, String expectedPath) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(expectedPath));
            String expectedLine = reader.readLine();
            if (expectedLine == null) return false;
            return afd.toString().trim().equals(expectedLine.trim());
        } catch (IOException e) {
            return false;
        } finally {
            try { if (reader != null) reader.close(); } catch (IOException e) {}
        }
    }

    private static String formatScore(double score) {
        return String.valueOf(Math.round(score * 100.0) / 100.0);
    }
}
