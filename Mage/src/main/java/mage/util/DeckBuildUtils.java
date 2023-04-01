package mage.util;

import mage.cards.Card;

import java.util.Set;

public class DeckBuildUtils {

    public static int[] landCountSuggestion(int deckSize, Set<Card> deckList) {
        /*
        Returns the number of basic lands suggested to complete a deck
         */
        int plains = 0, islands = 0, swamps = 0, mountains = 0, forests = 0;
        int landsNeeded = deckSize - deckList.size();
        if (landsNeeded > 0) {
            int white = 0, blue = 0, black = 0, red = 0, green = 0;
            for (Card cd : deckList) {
                for (String s : cd.getManaCostSymbols()) {
                    if (s.contains("W")) white++;
                    if (s.contains("U")) blue++;
                    if (s.contains("B")) black++;
                    if (s.contains("R")) red++;
                    if (s.contains("G")) green++;
                }
            }
            int total = white + blue + black + red + green;
            if (total > 0) {
                plains = Math.round(landsNeeded * ((float) white / (float) total));
                total -= white;
                landsNeeded -= plains;

                islands = Math.round(landsNeeded * ((float) blue / (float) total));
                total -= blue;
                landsNeeded -= islands;

                swamps = Math.round(landsNeeded * ((float) black / (float) total));
                total -= black;
                landsNeeded -= swamps;

                mountains = Math.round(landsNeeded * ((float) red / (float) total));
                landsNeeded -= mountains;

                forests = landsNeeded;
            }
        }
        return new int[] {plains, islands, swamps, mountains, forests};
    }

    // Hide constructor - not to be instantiated
    private DeckBuildUtils() {
    }

}
