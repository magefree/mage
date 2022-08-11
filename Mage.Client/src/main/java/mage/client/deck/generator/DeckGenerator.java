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

    private static final int MAX_TRIES = 8196;
    private static DeckGeneratorDialog genDialog;
    private static DeckGeneratorPool genPool;

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
        Map<String, List<CardInfo>> basicLands = generateBasicLands(setsToUse);

        generateSpells(creatureCriteria, genPool.getCreatureCount());
        generateSpells(nonCreatureCriteria, genPool.getNonCreatureCount());
        generateLands(nonBasicLandCriteria, genPool.getLandCount(), basicLands);

        // Reconstructs the final deck and adjusts for Math rounding and/or missing cards
        return genPool.getDeck();
    }

    /**
     * Generates all spells for the deck. Each card is retrieved from the
     * database and checked against the converted mana cost (CMC) needed for the
     * current card pool. If a card's CMC matches the CMC range required by the
     * pool, it is added to the deck. This ensures that the majority of cards
     * fit a fixed mana curve for the deck, and it is playable. Creatures and
     * non-creatures are retrieved separately to ensure the deck contains a
     * reasonable mix of both.
     *
     * @param criteria   the criteria to search for in the database.
     * @param spellCount the number of spells that match the criteria needed in
     *                   the deck.
     */
    private static void generateSpells(CardCriteria criteria, int spellCount) {
        List<CardInfo> cardPool = CardRepository.instance.findCards(criteria);
        int retrievedCount = cardPool.size();
        List<DeckGeneratorCMC.CMC> deckCMCs = genPool.getCMCsForSpellCount(spellCount);
        int count = 0;
        int reservesAdded = 0;
        boolean added;
        if (retrievedCount > 0 && retrievedCount >= spellCount) {
            int tries = 0;
            while (count < spellCount) {
                Card card = cardPool.get(RandomUtil.nextInt(retrievedCount)).getMockCard();
                if (genPool.isValidSpellCard(card)) {
                    int cardCMC = card.getManaValue();
                    for (DeckGeneratorCMC.CMC deckCMC : deckCMCs) {
                        if (cardCMC >= deckCMC.min && cardCMC <= deckCMC.max) {
                            int currentAmount = deckCMC.getAmount();
                            if (currentAmount > 0) {
                                deckCMC.setAmount(currentAmount - 1);
                                genPool.addCard(card.copy());
                                count++;
                            }
                        } else if (reservesAdded < (genPool.getDeckSize() / 2)) {
                            added = genPool.tryAddReserve(card, cardCMC);
                            if (added) {
                                reservesAdded++;
                            }
                        }
                    }
                }
                tries++;
                if (tries > MAX_TRIES) {
                    // Break here, we'll fill in random missing ones later
                    break;
                }
            }
        } else {
            throw new IllegalStateException("Not enough cards to generate deck.");
        }

    }

    /**
     * Generates all the lands for the deck. Generates non-basic if selected by
     * the user and if the deck isn't monocolored. Will fetch non-basic lands if
     * required and then fill up the remaining space with basic lands. Basic
     * lands are adjusted according to the mana symbols seen in the cards used
     * in this deck. Usually the lands will be well balanced relative to the
     * color of cards.
     *
     * @param criteria   the criteria of the lands to search for in the database.
     * @param landsCount the amount of lands required for this deck.
     * @param basicLands information about the basic lands from the sets used.
     */
    private static void generateLands(CardCriteria criteria, int landsCount, Map<String, List<CardInfo>> basicLands) {

        int tries = 0;
        int countNonBasic = 0;
        // Store the nonbasic lands (if any) we'll add
        List<Card> deckLands = new ArrayList<>();

        // Calculates the percentage of colored mana symbols over all spells in the deck
        Map<String, Double> percentage = genPool.calculateSpellColorPercentages();

        // Only dual/tri color lands are generated for now, and not non-basic lands that only produce colorless mana.
        if (!genPool.isMonoColoredDeck() && genDialog.useNonBasicLand()) {
            List<Card> landCards = genPool.filterLands(CardRepository.instance.findCards(criteria));
            int allCount = landCards.size();
            if (allCount > 0) {
                while (countNonBasic < landsCount / 2) {
                    Card card = landCards.get(RandomUtil.nextInt(allCount));
                    if (genPool.isValidLandCard(card)) {
                        Card addedCard = card.copy();
                        deckLands.add(addedCard);
                        genPool.addCard(addedCard);
                        countNonBasic++;
                    }
                    tries++;
                    // to avoid infinite loop
                    if (tries > MAX_TRIES) {
                        // Not a problem, just use what we have
                        break;
                    }
                }
            }
        }
        // Calculate the amount of colored mana already can be produced by the non-basic lands
        Map<String, Integer> count = genPool.countManaProduced(deckLands);
        // Fill up the rest of the land quota with basic lands adjusted to fit the deck's mana costs
        addBasicLands(landsCount - countNonBasic, percentage, count, basicLands);
    }

    /**
     * Returns a map of colored mana symbol to basic land cards of that color.
     *
     * @param setsToUse which sets to retrieve basic lands from.
     * @return a map of color to basic lands.
     */
    private static Map<String, List<CardInfo>> generateBasicLands(List<String> setsToUse) {

        Set<String> landSets = TournamentUtil.getLandSetCodeForDeckSets(setsToUse);

        CardCriteria criteria = new CardCriteria();
        if (!landSets.isEmpty()) {
            criteria.setCodes(landSets.toArray(new String[landSets.size()]));
        }
        criteria.ignoreSetsWithSnowLands();

        Map<String, List<CardInfo>> basicLandMap = new HashMap<>();

        for (ColoredManaSymbol c : ColoredManaSymbol.values()) {
            String landName = DeckGeneratorPool.getBasicLandName(c.toString());
            criteria.rarities(Rarity.LAND).nameExact(landName);
            List<CardInfo> cards = CardRepository.instance.findCards(criteria);
            if (cards.isEmpty()) { // Workaround to get basic lands if lands are not available for the given sets
                criteria.setCodes("M15");
                cards = CardRepository.instance.findCards(criteria);
            }
            basicLandMap.put(landName, cards);
        }
        return basicLandMap;
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
    private static void addBasicLands(int landsNeeded, Map<String, Double> percentage, Map<String, Integer> count, Map<String, List<CardInfo>> basicLands) {

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
