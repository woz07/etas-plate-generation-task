package github.woz07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    // Note: `max` in this context is the same as expected

    @org.junit.jupiter.api.Test
    void testPattern() {
        // Passes
        String[] arr = {"YC19ZCT"};

        for (String pass : arr) {
            System.out.println(pass.matches("^[A-Z]{2}\\d{2,3}[A-HJ-NPR-Z]{3}$") ? "Pass passed [GOOD]" : "Pass failed [BAD]");
        }

        // Fails
        arr = new String[]{"YC19ZCI"};

        for (String fail : arr) {
            System.out.println(fail.matches("^[A-Z]{2}\\d{2,3}[A-HJ-NPR-Z]{3}$") ? "Fail passed [BAD]" : "Fail failed [GOOD]");
        }
    }

    /**
     * General testing to make sure that GeneratePlate generates correct plates
     */
    @org.junit.jupiter.api.Test
    void testGeneratePlates() {
        // If you would like to test an even greater amount just alter 'max' to a higher value
        // Note: Doing so may take a longer time and slow down computer, depending on value set for max
        // Warning: If you set max to Integer.MAX_VALUE in this context then you will most likely run into an OutOfMemoryError exception "Java heap space"

        int passed = 0;
        int failed = 0;
        int max    = 1000;

        // Generate a bunch of memory tags
        List<String> memorytags = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            memorytags.add(Main.GetRandomLetters(2, false));
        }

        // Generate a bunch of date tags
        List<String> datetags       = new ArrayList<>();
        Random random               = new Random();

        for (int i = 0; i < max; i++) {
            int year = random.nextInt(2100 - 1900) + 1900; // Testing from 1900 to 2099
            int month = random.nextInt(12) + 1; // 1-12 (Jan - Dec)
            int day;

            // Handle varying month lengths
            switch (month) {
                case 2: // 28 days
                    day = random.nextInt(28) + 1;
                    break;
                case 4: case 6: case 9: case 11: // 30 days
                    day = random.nextInt(30) + 1;
                    break;
                default: // 31 days
                    day = random.nextInt(31) + 1;
            }

            String datestring = String.format("%02d/%02d/%04d", day, month, year);
            datetags.add(datestring);
        }

        for (int i = 0; i < max; i++) {
            try {
                String plate = Main.GeneratePlate(memorytags.get(i), datetags.get(i));
                // NOTE: This allows for plates of length 8 (where the age identifier has gone above 100)
                if (plate.matches("^[A-Z]{2}\\d{2,3}[A-HJ-PR-Z]{3}$"))
                    passed++;
                else {
                    failed++;
                    System.out.println("Failed="+plate);
                }
            } catch (Exception e) {
                failed++;
            }
        }

        System.out.println("GeneratePlates() test result:");
        System.out.println("Max: " + max);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        assertEquals(max, passed);
    }

    /**
     * General testing to make sure that `GetRandomLetters` for memory tag puts any letter,
     * regardless if 'I' or 'Q' and is exactly length of 2
     */
    @org.junit.jupiter.api.Test
    void testGetRandomLettersForMemoryTag() {
        // If you would like to test an even greater amount just alter 'max' to a higher value
        // Note: Doing so may take a longer time and slow down computer, depending on value set for max

        int passed = 0;
        int failed = 0;
        int max    = 100000;

        String value;
        for (int i = 0; i < max; i++) {
            value = Main.GetRandomLetters(2, false);

            // Since it's hard to test this we are just going to test for capital A-Z and exactly 2 letters
            if (value.length() == 2)
                passed++;
            else
                failed++;
        }

        System.out.println("GetRandomLettersForMemoryTag() Result:");
        System.out.println("Max: " + max);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        assertEquals(max, passed);
    }

    /**
     * General testing to make sure that `GetRandomLetters` for random letter
     * doesn't return any 'I' or 'Q' and is exactly length of 3
     */
    @org.junit.jupiter.api.Test
    void testGetRandomLettersForRandomLetters() {
        // If you would like to test an even greater amount just alter 'max' to a higher value
        // Note: Doing so may take a longer time and slow down computer, depending on value set for max

        int passed = 0;
        int failed = 0;
        int max    = 100000;

        String value;
        for (int i = 0; i < max; i++) {
            value = Main.GetRandomLetters(3, true);

            if (!value.contains("I") && !value.contains("Q") && value.length() == 3)
                passed++;
            else
                failed++;
        }

        System.out.println("GetRandomLettersForRandomLetters() Result:");
        System.out.println("Max: " + max);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        assertEquals(max, passed);
    }
}