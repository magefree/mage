
package mage.client.deck.generator;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.util.RandomUtil;
import mage.util.TournamentUtil;

import java.util.*;

/**
 *
 * @author Simown
 */
public class DeckGeneratorPool
{


    public static final int DEFAULT_CREATURE_PERCENTAGE = 38;
    public static final int DEFAULT_NON_CREATURE_PERCENTAGE = 21;
    public static final int DEFAULT_LAND_PERCENTAGE = 41;

    private final List<ColoredManaSymbol> allowedColors;
    private final boolean colorlessAllowed;
    private final List<DeckGeneratorCMC.CMC> poolCMCs;
    private final int creatureCount;
    private final int nonCreatureCount;
    private final int landCount;
    private final boolean isSingleton;
    private final int deckSize;

    // Count how many copies of the card exists in the deck to check we don't go over 4 copies (or 1 for singleton)
    private final Map<String, Integer> cardCounts = new HashMap<>();
    // If there is only a single color selected to generate a deck
    private boolean monoColored = false;
    // List of cards so far in the deck
    private final List<Card> deckCards = new ArrayList<>();
    // List of reserve cards found to fix up undersized decks
    private final List<Card> reserveSpells = new ArrayList<>();
    private final Deck deck;

    /**
     * Creates a card pool with specified criterea used when generating a deck.
     *
     * @param deckSize the size of the complete deck
     * @param creaturePercentage what percentage of creatures to use when generating the deck.
     * @param nonCreaturePercentage percentage of non-creatures to use when generating the deck.
     * @param landPercentage percentage of lands to use when generating the deck.
     * @param allowedColors which card colors are allowed in the generated deck.
     * @param isSingleton if the deck only has 1 copy of each non-land card.
     * @param colorlessAllowed if colourless mana symbols are allowed in costs in the deck.
     * @param isAdvanced if the user has provided advanced options to generate the deck.
     * @param deckGeneratorCMC the CMC curve to use for this deck
     */
    public DeckGeneratorPool(final int deckSize, final int creaturePercentage, final int nonCreaturePercentage, final int landPercentage,
                             final List<ColoredManaSymbol> allowedColors, boolean isSingleton, boolean colorlessAllowed, boolean isAdvanced, DeckGeneratorCMC deckGeneratorCMC)
    {
        this.deckSize = deckSize;
        this.allowedColors = allowedColors;
        this.isSingleton = isSingleton;
        this.colorlessAllowed = colorlessAllowed;

        this.deck = new Deck();

        // Advanced (CMC Slider panel and curve drop-down in the dialog)
        if(isAdvanced) {
            this.creatureCount = (int)Math.ceil((deckSize / 100.0) * creaturePercentage);
            this.nonCreatureCount = (int)Math.ceil((deckSize / 100.0)* nonCreaturePercentage);
            this.landCount = (int)Math.ceil((deckSize / 100.0)* landPercentage);
            if(this.deckSize == 60) {
                this.poolCMCs = deckGeneratorCMC.get60CardPoolCMC();
            } else {
                this.poolCMCs = deckGeneratorCMC.get40CardPoolCMC();
            }
        } else {
            // Ignore the advanced group, just use defaults
            this.creatureCount = (int)Math.ceil((deckSize / 100.0) * DEFAULT_CREATURE_PERCENTAGE);
            this.nonCreatureCount = (int)Math.ceil((deckSize / 100.0) * DEFAULT_NON_CREATURE_PERCENTAGE);
            this.landCount = (int)Math.ceil((deckSize / 100.0) * DEFAULT_LAND_PERCENTAGE);
            if(this.deckSize == 60) {
                this.poolCMCs = DeckGeneratorCMC.Default.get60CardPoolCMC();
            } else {
                this.poolCMCs = DeckGeneratorCMC.Default.get40CardPoolCMC();
            }
        }

        if(allowedColors.size() == 1) {
            monoColored = true;
        }

    }


    /**
     * Adjusts the number of spell cards that should be in a converted mana cost (CMC) range, given the amount of cards total.
     * @param cardsCount the number of total cards.
     * @return a list of CMC ranges, with the amount of cards for each CMC range
     */
    public List<DeckGeneratorCMC.CMC> getCMCsForSpellCount(int cardsCount) {
        List<DeckGeneratorCMC.CMC> adjustedCMCs = new ArrayList<>(this.poolCMCs);
        // For each CMC calculate how many spell cards are needed, given the total amount of cards
        for(DeckGeneratorCMC.CMC deckCMC : adjustedCMCs) {
            deckCMC.setAmount((int)Math.ceil(deckCMC.percentage * cardsCount));
        }
        return adjustedCMCs;
    }

    /**
     * Verifies if the spell card supplied is valid for this pool of cards.
     * Checks that there isn't too many copies of this card in the deck.
     * Checks that the card fits the chosen colors for this pool.
     * @param card the spell card
     * @return if the spell card is valid for this pool.
     */
    public boolean isValidSpellCard(Card card)
    {
        int cardCount = getCardCount((card.getName()));
        // Check it hasn't already got the maximum number of copies in a deck
        if(cardCount < (isSingleton ? 1 : 4)) {
            if(cardFitsChosenColors(card)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies if the non-basic land card supplied is valid for this pool of cards.
     * @param card the non-basic land card
     * @return if the land card generates the allowed colors for this pool.
     */
    public boolean isValidLandCard(Card card)
    {
        int cardCount = getCardCount((card.getName()));
        // No need to check if the land is valid for the colors chosen
        // They are all filtered before searching for lands to include in the deck.
        return (cardCount < (isSingleton ? 1 : 4));
    }


    /**
     * Adds a card to the pool and updates the count of this card.
     * @param card the card to add.
     */
    public void addCard(Card card)
    {
        Object cnt = cardCounts.get((card.getName()));
        if(cnt == null)
            cardCounts.put(card.getName(), 0);
        int existingCount = cardCounts.get((card.getName()));
        cardCounts.put(card.getName(), existingCount+1);
        deckCards.add(card);
    }

    /**
     * Adds a card to the reserve pool.
     * Reserve pool is used when the deck generation fails to build a complete deck, or
     * a partially complete deck (e.g. if there are no cards found that match a CMC)
     * @param card the card to add
     * @param cardCMC the converted mana cost of the card
     */
    public boolean tryAddReserve(Card card, int cardCMC) {
        // Only cards with CMC < 7 and don't already exist in the deck
        // can be added to our reserve pool as not to overwhelm the curve
        // with high CMC cards and duplicates.
        if(cardCMC < 7 && getCardCount(card.getName()) == 0) {
            this.reserveSpells.add(card);
            return true;
        }
        return false;
    }

    /**
     * Checks if the mana symbols in the card all match the allowed colors for this pool.
     * @param card the spell card to check.
     * @return if all the mana symbols fit the chosen colors.
     */
    private boolean cardFitsChosenColors(Card card) {
        for (String symbol : card.getManaCostSymbols()) {
            boolean found = false;
            symbol = symbol.replace("{", "").replace("}", "");
            if (isColoredManaSymbol(symbol)) {
                for (ColoredManaSymbol allowed : allowedColors) {
                    if (symbol.contains(allowed.toString())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            if (symbol.equals("C") && !colorlessAllowed) {
                return false;
            }
        }
        return true;
    }


    /**
     * Calculates the percentage of colored mana symbols over all spell cards in the deck.
     * Used to balance the generation of basic lands so the amount of lands matches the
     * cards mana costs.
     * @return a list of colored mana symbols and the percentage of symbols seen in cards mana costs.
     */
    public Map<String, Double> calculateSpellColorPercentages() {

        final Map<String, Integer> colorCount = new HashMap<>();
        for (final ColoredManaSymbol color : ColoredManaSymbol.values()) {
            colorCount.put(color.toString(), 0);
        }

        // Counts how many colored mana symbols we've seen in total so we can get the percentage of each color
        int totalCount = 0;

        List<Card> fixedSpells = getFixedSpells();
        for(Card spell: fixedSpells) {
            for (String symbol : spell.getManaCostSymbols()) {
                symbol = symbol.replace("{", "").replace("}", "");
                if (isColoredManaSymbol(symbol)) {
                    for (ColoredManaSymbol allowed : allowedColors) {
                        if (symbol.contains(allowed.toString())) {
                            int cnt = colorCount.get(allowed.toString());
                            colorCount.put(allowed.toString(), cnt+1);
                            totalCount++;
                        }
                    }
                }
            }
        }
        final Map<String, Double> percentages = new HashMap<>();
        for(Map.Entry<String, Integer> singleCount: colorCount.entrySet()) {
            String color = singleCount.getKey();
            int count = singleCount.getValue();
            // Calculate the percentage this color has out of the total color counts
            double percentage = (count / (double) totalCount) * 100;
            percentages.put(color, percentage);
        }
        return percentages;
    }

    /**
     * Calculates how many of each mana the non-basic lands produce.
     * @param deckLands the non-basic lands which will be used in the deck.
     * @return a mapping of colored mana symbol to the amount that can be produced.
     */
    public Map<String,Integer> countManaProduced(List<Card> deckLands)
    {
        Map<String, Integer> manaCounts = new HashMap<>();
        for (final ColoredManaSymbol color : ColoredManaSymbol.values()) {
            manaCounts.put(color.toString(), 0);
        }
        for(Card land: deckLands)  {
            for(Ability landAbility: land.getAbilities()) {
                for (ColoredManaSymbol symbol : allowedColors) {
                    String abilityString = landAbility.getRule();
                    if(landTapsForAllowedColor(abilityString, symbol.toString())) {
                        Integer count = manaCounts.get(symbol.toString());
                        manaCounts.put(symbol.toString(), count + 1);
                    }
                }
            }
        }
        return manaCounts;
    }

    /** Filter all the non-basic lands retrieved from the database.
     * @param landCardsInfo information about all the cards.
     * @return a list of cards that produce the allowed colors for this pool.
     */
    public List<Card> filterLands(List<CardInfo> landCardsInfo) {
        List<Card> matchingLandList = new ArrayList<>();
        for(CardInfo landCardInfo: landCardsInfo) {
            Card landCard = landCardInfo.getMockCard();
            if(landProducesChosenColors(landCard)) {
                matchingLandList.add(landCard);
            }
        }
        return matchingLandList;
    }

    /**
     * Returns the card name that represents the basic land for this color.
     * @param symbolString the colored mana symbol.
     * @return the name of a basic land card.
     */
    public static String getBasicLandName(String symbolString) {
        switch(symbolString) {
            case "B":
                return "Swamp";
            case "G":
                return "Forest";
            case "R":
                return "Mountain";
            case "U":
                return "Island";
            case "W":
                return "Plains";
            default:
                return "";
        }
    }


    /**
     * Returns a complete deck.
     * @return the deck.
     */
    public Deck getDeck() {
        Set<Card> actualDeck = deck.getCards();
        actualDeck.addAll(deckCards);
        return deck;
    }


    /**
     * Returns the number of creatures needed in this pool.
     * @return the number of creatures.
     */
    public int getCreatureCount() {
        return creatureCount;
    }

    /**
     * Returns the number of non-creatures needed in this pool.
     * @return the number of non-creatures.
     */
    public int getNonCreatureCount() {
        return nonCreatureCount;
    }

    /**
     * Returns the number of lands (basic + non-basic) needed in this pool.
     * @return the number of lands.
     */
    public int getLandCount() {
        return landCount;
    }

    /**
     * Returns if this pool only uses one color.
     * @return if this pool is monocolored.
     */
    public boolean isMonoColoredDeck() {
        return monoColored;
    }

    /**
     * Returns the size of the deck to generate from this pool.
     * @return the deck size.
     */
    public int getDeckSize() {
        return deckSize;
    }

    /**
     * Fixes undersized or oversized decks that have been generated.
     * Removes random cards from an oversized deck until it is the correct size.
     * Uses the reserve pool to fill up and undersized deck with cards.
     * @return a fixed list of cards for this deck.
     */
    private List<Card> getFixedSpells()
    {
        int spellSize = deckCards.size();
        int nonLandSize = (deckSize - landCount);

        // Less spells than needed
        if(spellSize < nonLandSize) {

            int spellsNeeded = nonLandSize-spellSize;

            // If we haven't got enough spells in reserve to fulfil the amount we need, skip adding any.
            if(reserveSpells.size() >= spellsNeeded) {

                List<Card> spellsToAdd = new ArrayList<>(spellsNeeded);

                // Initial reservoir
                for (int i = 0; i < spellsNeeded; i++)
                    spellsToAdd.add(reserveSpells.get(i));

                for (int i = spellsNeeded + 1; i < reserveSpells.size() - 1; i++) {
                    int j = RandomUtil.nextInt(i);
                    Card randomCard = reserveSpells.get(j);
                    if (isValidSpellCard(randomCard) && j < spellsToAdd.size()) {
                        spellsToAdd.set(j, randomCard);
                    }
                }
                // Add randomly selected spells needed
                deckCards.addAll(spellsToAdd);
            }
        }

        // More spells than needed
        else if(spellSize > (deckSize - landCount)) {
            int spellsRemoved = (spellSize)-(deckSize-landCount);
            for(int i = 0; i < spellsRemoved; ++i) {
                deckCards.remove(RandomUtil.nextInt(deckCards.size()));
            }
        }

        // Check we have exactly the right amount of cards for a deck.
        if(deckCards.size() != nonLandSize) {
            throw new IllegalStateException("Not enough cards found to generate deck.");
        }
        // Return the fixed amount
        return deckCards;
    }

    /**
     * Returns if this land taps for the given color.
     * Basic string matching to check the ability adds one of the chosen mana when tapped.
     * @param ability MockAbility of the land card
     * @param symbol colored mana symbol.
     * @return if the ability is tapping to produce the mana the symbol represents.
     */
    private boolean landTapsForAllowedColor(String ability, String symbol)  {
        return ability.matches(".*Add \\{" + symbol + "\\}.");
    }

    /**
     * Returns if this land will produce the chosen colors for this pool.
     * @param card a non-basic land card.
     * @return if this land card taps to produces the colors chosen.
     */
    private boolean landProducesChosenColors(Card card) {
        // All mock card abilities will be MockAbilities so we can't differentiate between ManaAbilities
        // and other Abilities so we have to do some basic string matching on land cards for now.
        List<Ability> landAbilities = card.getAbilities();
        int count = 0;
        for(Ability ability : landAbilities) {
            String abilityString = ability.getRule();
            // Lands that tap to produce mana of the chosen colors
            for(ColoredManaSymbol symbol : allowedColors) {
                if(landTapsForAllowedColor(abilityString, symbol.toString())) {
                    count++;
                }
            }
            if(count > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns if the symbol is a colored mana symbol.
     * @param symbol the symbol to check.
     * @return If it is a basic mana symbol or a hybrid mana symbol.
     */
    private static boolean isColoredManaSymbol(String symbol) {
        // Hybrid mana
        if(symbol.contains("/")) {
            return true;
        }
        for(ColoredManaSymbol c: ColoredManaSymbol.values()) {
            if (symbol.charAt(0) == (c.toString().charAt(0))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns how many of this card is in the pool.
     * If there are none in the pool it will initalise the card count.
     * @param cardName the name of the card to check.
     * @return the number of cards in the pool of this name.
     */
    private int getCardCount(String cardName) {
        Object cC = cardCounts.get((cardName));
        if(cC == null)
            cardCounts.put(cardName, 0);
        return  cardCounts.get((cardName));
    }


    /**
     * Generates all the lands for the deck. Generates non-basic if selected by
     * the user and if the deck isn't monocolored. Will fetch non-basic lands if
     * required and then fill up the remaining space with basic lands. Basic
     * lands are adjusted according to the mana symbols seen in the cards used
     * in this deck. Usually the lands will be well balanced relative to the
     * color of cards.
     *
     * @param useNonBasicLand
     * @param criteria   the criteria of the lands to search for in the database.
     * @param basicLands information about the basic lands from the sets used.
     */
    protected static void generateLands(boolean useNonBasicLand, CardCriteria criteria, Map<String, List<CardInfo>> basicLands) {
        DeckGeneratorPool genPool = DeckGenerator.genPool;
        int tries = 0;
        int countNonBasic = 0;
        int landsCount = genPool.getLandCount();
        // Store the nonbasic lands (if any) we'll add
        List<Card> deckLands = new ArrayList<>();

        // Calculates the percentage of colored mana symbols over all spells in the deck
        Map<String, Double> percentage = genPool.calculateSpellColorPercentages();

        // Only dual/tri color lands are generated for now, and not non-basic lands that only produce colorless mana.
        if (!genPool.isMonoColoredDeck() && useNonBasicLand) {
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
                    if (tries > DeckGenerator.MAX_TRIES) {
                        // Not a problem, just use what we have
                        break;
                    }
                }
            }
        }
        // Calculate the amount of colored mana already can be produced by the non-basic lands
        Map<String, Integer> count = genPool.countManaProduced(deckLands);
        // Fill up the rest of the land quota with basic lands adjusted to fit the deck's mana costs
        DeckGenerator.addBasicLands(landsCount - countNonBasic, percentage, count, basicLands);
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
    protected static void generateSpells(CardCriteria criteria, int spellCount) {
        DeckGeneratorPool genPool = DeckGenerator.genPool;
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
                if (tries > DeckGenerator.MAX_TRIES) {
                    // Break here, we'll fill in random missing ones later
                    break;
                }
            }
        } else {
            throw new IllegalStateException("Not enough cards to generate deck.");
        }
    }

    /**
     * Returns a map of colored mana symbol to basic land cards of that color.
     *
     * @param setsToUse which sets to retrieve basic lands from.
     * @return a map of color to basic lands.
     */
    protected static Map<String, List<CardInfo>> generateBasicLands(List<String> setsToUse) {

        Set<String> landSets = TournamentUtil.getLandSetCodeForDeckSets(setsToUse);

        CardCriteria criteria = new CardCriteria();
        if (!landSets.isEmpty()) {
            criteria.setCodes(landSets.toArray(new String[landSets.size()]));
        }
        criteria.ignoreSetsWithSnowLands();

        Map<String, List<CardInfo>> basicLandMap = new HashMap<>();

        for (ColoredManaSymbol c : ColoredManaSymbol.values()) {
            String landName = DeckGeneratorPool.getBasicLandName(c.toString());
            criteria.rarities(Rarity.LAND).name(landName);
            List<CardInfo> cards = CardRepository.instance.findCards(criteria);
            if (cards.isEmpty()) { // Workaround to get basic lands if lands are not available for the given sets
                criteria.setCodes("M15");
                cards = CardRepository.instance.findCards(criteria);
            }
            basicLandMap.put(landName, cards);
        }
        return basicLandMap;
    }
}
