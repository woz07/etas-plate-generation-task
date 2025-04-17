package github.woz07;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private static final Set<String> PLATES = new HashSet<>();  // Data structure to stores plates
    private static final String FILE        = "plates.txt";     // The file which stores the plates that get loaded and saved to
    private static final char[] LETTERS     = {                 // The alphabet in char array that gets accessed when generating plate
                'A','B','C','D','E','F','G','H','I','J','K',
                'L','M','N','O','P','Q','R','S','T','U','V',
                'W','X','Y','Z'
        };
    private static final Random RANDOM = new Random();          // The randomizer, used in generating plates
    private static boolean debug       = false;                 // Should the app output any errors/messages? (default is false)

    // Entry point;
    // Loads plates, generates plates and saves them
    public static void main(String[] args) {

        /*
         * Assumptions:
         * - A folder is created where "epgt.exe" is put and then ran from terminal
         * - Java 11 (Amazon Corretto 11.0.24) is installed and used to run the jar, if running the jar
         * - The program is being run on Windows 11
         * - For first time run just keep the program/exe/jar within its own directory and run, it will then create "plates.txt", if it has access
         * - Program/Exe/Jar is executed in directory where "plates.txt" resides and doesn't move the program at all
         * - Content within "plates.txt" is in plaintext format, with one plate per line
         * - No duplicate memory tags are passed in during runtime
         * - All input date strings follow "dd/MM/yyyy" format
         * - The sizes of memorytags and datetags should be equal ((memorytags.size() == datetags.size()) -> true)
         * - No memory tags contain invalid characters (e.g., numbers or special symbols)
         * - Program has full permissions to read/write/create "plates.txt" (which gets created in the directory where the program is executed first)
         * - Program runs in a single threaded context (no parallel execution)
         * - It is assumed that memorytag can contain I and Q (if you don't want this then you can set line 80 parameter to false)
         * - The program assumes that the correct format is followed for date tags where it goes dd/mm/yyyy
        */

        // Debug mode for whether output should be displayed or not
        debug = Arrays.asList(args).contains("--debug");

        // Load previously stored plates from file to set
        LoadGeneratedPlates();

        // Hard coded test data from PDF
        String[] memorytags = new String[]{"YC", "LT", "FF"};
        String[] datetags   = new String[]{"04/07/2019", "23/01/2003", "30/05/2032"};

        // Generate plates
        // ASSUME memorytags.size() == datetags.size()
        for (int i = 0; i < memorytags.length; i++) {
            // If debug is enabled then output the result
            if (debug) {
                String plate = GeneratePlate(memorytags[i], datetags[i]);
                System.out.println("[INFO] " + (i + 1) + ") Generated Plate: " + plate);
            // Otherwise just add to the set and continue without output
            } else {
                GeneratePlate(memorytags[i], datetags[i]);
            }
        }

        // Save plates from set to file
        SaveGeneratedPlates();
    }

    /**
     * Generate unique plate using provided parameters (memory tag and date tag)
     * @param memorytag The 2 letters that are used for memory identification
     * @param datetag   The date used for the plate to generate age identifier
     * @return          The unique generated plate
     */
    public static String GeneratePlate(String memorytag, String datetag) {
        LocalDate date = LocalDate.parse(datetag, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        int month = date.getMonthValue();
        int year  = date.getYear();

        // March = 3rd month, August = 9th month
        // If `month` not between 3rd-8th then add 50 but also minus 1 from year
        // else don't add 50
        int age;
        if (month >= 3 && month <= 8)
            age = year % 100;
        else
            age = ((year - 1) % 100) + 50;

        String letters;
        String plate;
        do {
            letters = GetRandomLetters(3, true);
            plate   = memorytag + String.format("%02d", age) + letters;
        } while (PLATES.contains(plate));

        PLATES.add(plate);

        return plate;
    }

    /**
     * Returns a string of count `length` random letters (including both 'I' and 'Q')
     * @param length    How many random letters
     * @param exclude   Should the generation exclude 'I' and 'Q'? True == yes, False == no
     * @return          Returns a string of random generated letters of length `n`
     */
    public static String GetRandomLetters(int length, boolean exclude) {
        StringBuilder letters = new StringBuilder(length);

        for (int i = 0; i < length; ++i) {
            char letter = LETTERS[RANDOM.nextInt(LETTERS.length)];
            if (exclude && (letter == 'I' || letter == 'Q')) {
                i--;
            } else {
                letters.append(letter);
            }
        }

        return letters.toString();
    }

    /**
     * Loads previously generated plates from file into set `PLATES`
     */
    private static void LoadGeneratedPlates() {
        File file = new File(FILE);

        if (!file.exists()) {
            // No saved files exists to read from yet
            if (debug) {
                System.out.println("[WARNING] No existing \"plates.txt\" file found. Starting fresh");
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                PLATES.add(line.trim());
                count++;
            }
            if (debug) {
                System.out.println("[INFO] Loaded " + count + " plates from file");
            }
        } catch (IOException err) {
            if (debug) {
                System.err.println("[ERROR] Plates could not be loaded: " + err.getMessage());
            }
        }
    }

    /**
     * Writes current plates within set `PLATES` to file of path `FILE`
     */
    private static void SaveGeneratedPlates() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE, false))) {
            for (String plate : PLATES) {
                writer.println(plate);
            }
            if (debug) {
                System.out.println("[INFO] Saved " + PLATES.size() + " plates to file");
            }
        } catch (IOException err) {
            if (debug) {
                System.err.println("[SEVERE] Plates could not be saved: " + err.getMessage());
            }
        }
    }
}
