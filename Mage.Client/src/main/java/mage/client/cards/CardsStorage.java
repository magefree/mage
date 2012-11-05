package mage.client.cards;

import mage.cards.Card;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Stores all implemented cards on client side.
 * Used by deck editor, deck generator, collection viewer, etc.
 *
 * @author nantuko
 */
public class CardsStorage {

    private static Map<String, Integer> ratings;
    private static Integer min = Integer.MAX_VALUE, max = 0;

    /**
     * Rating that is given for new cards.
     * Ratings are in [1,10] range, so setting it high will make new cards appear more often.
     */
    private static final int DEFAULT_NOT_RATED_CARD_RATING = 6;

    /**
     * Return rating of a card: 1-10.
     *
     * @param card
     * @return
     */
    public static int rateCard(Card card) {
        if (ratings == null) {
            readRatings();
        }
        if (ratings.containsKey(card.getName())) {
            int r = ratings.get(card.getName());
            float f = 10.0f * (r - min) / (max - min);
            // normalize to [1..10]
            return (int) Math.round(f);
        }
        return DEFAULT_NOT_RATED_CARD_RATING;
    }

    private synchronized static void readRatings() {
        if (ratings == null) {
            ratings = new HashMap<String, Integer>();
            String filename = "/ratings.txt";
            try {
                InputStream is = CardsStorage.class.getResourceAsStream(filename);
                Scanner scanner = new Scanner(is);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] s = line.split(":");
                    if (s.length == 2) {
                        Integer rating = Integer.parseInt(s[0].trim());
                        String name = s[1].trim();
                        if (rating > max) {
                            max = rating;
                        }
                        if (rating < min) {
                            min = rating;
                        }
                        ratings.put(name, rating);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                ratings.clear(); // no rating available on exception
            }
        }
    }
}
