package mage.client.deck.generator;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.cards.repository.ExpansionRepository;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.sets.ConstructedFormats;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.constants.SuperType;
import mage.util.RandomUtil;
import mage.util.TournamentUtil;

import java.util.*;

/**
 * Generates random card pool and builds a deck.
 *
 * @author nantuko
 * @author Simown
 */
public final class DeckGenerator {

    public static class DeckGeneratorException extends RuntimeException {

        public DeckGeneratorException(String message) {
            super(message);
        }

    }

    protected static final int MAX_TRIES = 8196;
    private static DeckGeneratorDialog genDialog;
    protected static DeckGeneratorPool genPool;

    /**
     * Builds a deck out of the selected block/set/format.
     *
     * @return a path to the generated deck.
     */
    public static String generateDeck() {

        genDialog = new DeckGeneratorDialog();
        if (genDialog.getSelectedColors() != null) {
            Deck deck = buildDeck();
            return genDialog.saveDeck(deck);
        }
        // If the deck couldn't be generated or the user cancelled, repopulate the deck selection with its cached value
        return PreferencesDialog.getCachedValue(PreferencesDialog.KEY_NEW_TABLE_DECK_FILE, null);
    }

    protected static Deck buildDeck() {

        String selectedColors = genDialog.getSelectedColors();
        List<ColoredManaSymbol> allowedColors = new ArrayList<>();
        selectedColors = selectedColors != null ? selectedColors.toUpperCase(Locale.ENGLISH) : getRandomColors("X");
        String format = genDialog.getSelectedFormat();

        List<String> setsToUse = ConstructedFormats.getSetsByFormat(format);
        if (setsToUse == null) {
            throw new DeckGeneratorException("Deck sets aren't initialized; please connect to a server to update the database.");
        }
        if (setsToUse.isEmpty()) {
            // Default to using all sets
            setsToUse = ExpansionRepository.instance.getSetCodes();
        }

        int deckSize = genDialog.getDeckSize();

        if (selectedColors.contains("X")) {
            selectedColors = getRandomColors(selectedColors);
        }

        for (int i = 0; i < selectedColors.length(); i++) {
            char c = selectedColors.charAt(i);
            allowedColors.add(ColoredManaSymbol.lookup(c));
        }

        return generateDeck(deckSize, allowedColors, setsToUse);
    }

    /**
     * If the user has selected random colors, pick them randomly for the user.
     *
     * @param selectedColors a string of the colors selected.
     * @return a String representation of the new colors chosen.
     */
    private static String getRandomColors(String selectedColors) {

        List<Character> availableColors = new ArrayList<>();
        for (ColoredManaSymbol cms : ColoredManaSymbol.values()) {
            availableColors.add(cms.toString().charAt(0));
        }

        StringBuilder generatedColors = new StringBuilder();
        int randomColors = 0;
        for (int i = 0; i < selectedColors.length(); i++) {
            char currentColor = selectedColors.charAt(i);
            if (currentColor != 'X') {
                generatedColors.append(currentColor);
                availableColors.remove(new Character(currentColor));
            } else {
                randomColors++;
            }
        }
        for (int i = 0; i < randomColors && !availableColors.isEmpty(); i++) {
            int index = RandomUtil.nextInt(availableColors.size());
            generatedColors.append(availableColors.remove(index));
        }
        return generatedColors.toString();
    }

    /**
     * Generates all the cards to use in the deck. Adds creatures,
     * non-creatures, lands (including non-basic). Fixes the deck, adjusting for
     * size and color of the cards retrieved.
     *
     * @param deckSize      how big the deck is to generate.
     * @param allowedColors which colors are allowed in the deck.
     * @param setsToUse     which sets to use to retrieve cards for this deck.
     * @return the final deck to use.
     */
    private static Deck generateDeck(int deckSize, List<ColoredManaSymbol> allowedColors, List<String> setsToUse) {

        genPool = new DeckGeneratorPool(deckSize, genDialog.getCreaturePercentage(), genDialog.getNonCreaturePercentage(),
                genDialog.getLandPercentage(), allowedColors, genDialog.isSingleton(), genDialog.isColorless(),
                genDialog.isAdvanced(), genDialog.getDeckGeneratorCMC());

        final String[] sets = setsToUse.toArray(new String[setsToUse.size()]);

        // Creatures
        final CardCriteria creatureCriteria = new CardCriteria();
        creatureCriteria.setCodes(sets);
        creatureCriteria.notTypes(CardType.LAND);
        creatureCriteria.types(CardType.CREATURE);
        if (!(genDialog.useArtifacts())) {
            creatureCriteria.notTypes(CardType.ARTIFACT);
        }

        // Non-creatures (sorcery, instant, enchantment, artifact etc.)
        final CardCriteria nonCreatureCriteria = new CardCriteria();
        nonCreatureCriteria.setCodes(sets);
        nonCreatureCriteria.notTypes(CardType.LAND);
        nonCreatureCriteria.notTypes(CardType.CREATURE);
        if (!(genDialog.useArtifacts())) {
            nonCreatureCriteria.notTypes(CardType.ARTIFACT);
        }

        // Non-basic land
        final CardCriteria nonBasicLandCriteria = new CardCriteria();
        nonBasicLandCriteria.setCodes(sets);
        nonBasicLandCriteria.types(CardType.LAND);
        nonBasicLandCriteria.notSupertypes(SuperType.BASIC);

        // Generate basic land cards
        Map<String, List<CardInfo>> basicLands = DeckGeneratorPool.generateBasicLands(setsToUse);

        DeckGeneratorPool.generateSpells(creatureCriteria, genPool.getCreatureCount());
        DeckGeneratorPool.generateSpells(nonCreatureCriteria, genPool.getNonCreatureCount());
        DeckGeneratorPool.generateLands(genDialog.useNonBasicLand(), nonBasicLandCriteria, basicLands);

        // Reconstructs the final deck and adjusts for Math rounding and/or missing cards
        return genPool.getDeck();
    }

    /**
     * Once any non-basic lands are added, add basic lands until the deck is
     * filled.
     *
     * @param landsNeeded how many remaining lands are needed.
     * @param percentage  the percentage needed for each color in the final deck.
     * @param count       how many of each color can be produced by non-basic lands.
     * @param basicLands  list of information about basic lands from the
     *                    database.
     */
    static void addBasicLands(int landsNeeded, Map<String, Double> percentage, Map<String, Integer> count, Map<String, List<CardInfo>> basicLands) {

        int colorTotal = 0;
        ColoredManaSymbol colorToAdd = ColoredManaSymbol.U;

        // Add up the totals for all colors, to keep track of the percentage a color is.
        for (Map.Entry<String, Integer> c : count.entrySet()) {
            colorTotal += c.getValue();
        }

        // Keep adding basic lands until we fill the deck
        while (landsNeeded > 0) {

            double minPercentage = Integer.MIN_VALUE;

            for (ColoredManaSymbol color : ColoredManaSymbol.values()) {
                // What percentage of this color is requested
                double neededPercentage = percentage.get(color.toString());
                // If there is a 0% need for basic lands of this color, skip it
                if (neededPercentage <= 0) {
                    continue;
                }
                int currentCount = count.get(color.toString());
                double thisPercentage = 0.0;
                // Calculate the percentage of lands so far that produce this color
                if (currentCount > 0) {
                    thisPercentage = (currentCount / (double) colorTotal) * 100.0;
                }
                // Check if the color is the most "needed" (highest percentage) we have seen so far
                if (neededPercentage - thisPercentage > minPercentage) {
                    // Put this color land forward to be added
                    colorToAdd = color;
                    minPercentage = (neededPercentage - thisPercentage);
                }
            }
            genPool.addCard(getBasicLand(colorToAdd, basicLands));
            count.put(colorToAdd.toString(), count.get(colorToAdd.toString()) + 1);
            colorTotal++;
            landsNeeded--;
        }
    }

    /**
     * Return a random basic land of the chosen color.
     *
     * @param color      the color the basic land should produce.
     * @param basicLands list of information about basic lands from the
     *                   database.
     * @return a single basic land that produces the color needed.
     */
    private static Card getBasicLand(ColoredManaSymbol color, Map<String, List<CardInfo>> basicLands) {
        String landName = DeckGeneratorPool.getBasicLandName(color.toString());
        List<CardInfo> basicLandsInfo = basicLands.get(landName);
        return basicLandsInfo.get(RandomUtil.nextInt(basicLandsInfo.size())).getMockCard().copy();
    }
}
