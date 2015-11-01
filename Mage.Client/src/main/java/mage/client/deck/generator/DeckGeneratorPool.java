/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.client.deck.generator;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.repository.CardInfo;
import mage.constants.ColoredManaSymbol;

import java.util.*;

/**
 *
 * @author Simown
 */
public class DeckGeneratorPool
{
    // Constants for a 40 card deck
    private static final int CREATURE_COUNT_40 = 15;
    private static final int LAND_COUNT_40 = 17;
    private static final int NONCREATURE_COUNT_40 = 8;
    // Constants for a 60 card deck
    private static final int CREATURE_COUNT_60 = 23;
    private static final int LAND_COUNT_60 = 24;
    private static final int NONCREATURE_COUNT_60 = 13;

    private final List<ColoredManaSymbol> allowedColors;
    private final List<DeckGeneratorCMC> poolCMCs;
    private final int creatureCount;
    private final int nonCreatureCount;
    private final int landCount;
    private final boolean isSingleton;
    private final int deckSize;

    // Count how many copies of the card exists in the deck to check we don't go over 4 copies (or 1 for singleton)
    private Map<String, Integer> cardCounts = new HashMap<>();
    // If there is only a single color selected to generate a deck
    private boolean monoColored = false;
    // List of cards so far in the deck
    private List<Card> deckCards = new ArrayList<>();
    // List of reserve cards found to fix up undersized decks
    private List<Card> reserveSpells = new ArrayList<>();
    private Deck deck;

    public DeckGeneratorPool(final int deckSize, final List<ColoredManaSymbol> allowedColors, boolean isSingleton)
    {
        this.deckSize = deckSize;
        this.allowedColors = allowedColors;
        this.isSingleton = isSingleton;

        this.deck = new Deck();

        if(this.deckSize > 40) {
            this.creatureCount = CREATURE_COUNT_60;
            this.nonCreatureCount = NONCREATURE_COUNT_60;
            this.landCount = LAND_COUNT_60;
            poolCMCs = new ArrayList<DeckGeneratorCMC>() {{
                add(new DeckGeneratorCMC(0, 2, 0.20f));
                add(new DeckGeneratorCMC(3, 5, 0.50f));
                add(new DeckGeneratorCMC(6, 7, 0.25f));
                add(new DeckGeneratorCMC(8, 100, 0.05f));
            }};

        }
        else {
            this.creatureCount = CREATURE_COUNT_40;
            this.nonCreatureCount = NONCREATURE_COUNT_40;
            this.landCount = LAND_COUNT_40;
            poolCMCs = new ArrayList<DeckGeneratorCMC>() {{
                add(new DeckGeneratorCMC(0, 2, 0.30f));
                add(new DeckGeneratorCMC(3, 4, 0.45f));
                add(new DeckGeneratorCMC(5, 6, 0.20f));
                add(new DeckGeneratorCMC(7, 100, 0.05f));
            }};
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
    public List<DeckGeneratorCMC> getCMCsForSpellCount(int cardsCount) {
        List<DeckGeneratorCMC> adjustedCMCs = new ArrayList<>(this.poolCMCs);
        // For each CMC calculate how many spell cards are needed, given the total amount of cards
        for(DeckGeneratorCMC deckCMC : adjustedCMCs) {
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
        return (cardCount < 4);
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
        for (String symbol : card.getManaCost().getSymbols()) {
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
            for (String symbol : spell.getManaCost().getSymbols()) {
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
        for(Card card : deckCards)
            actualDeck.add(card);
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
        Random random = new Random();
        int spellSize = deckCards.size();
        int nonLandSize = (deckSize - landCount);

        // Less spells than needed
        if(spellSize < nonLandSize) {

            int spellsNeeded = nonLandSize-spellSize;

            // If we haven't got enough spells in reserve to fulfil the amount we need, we can't continue.
            if(reserveSpells.size() < spellsNeeded) {
                throw new IllegalStateException("Not enough cards found to generate deck. Please try again");
            }

            List<Card> spellsToAdd = new ArrayList<>(spellsNeeded);

            // Initial reservoir
            for(int i = 0; i < spellsNeeded; i++)
                spellsToAdd.add(reserveSpells.get(i));

            for(int i = spellsNeeded+1; i < reserveSpells.size()-1; i++) {
                int j = random.nextInt(i);
                Card randomCard = reserveSpells.get(j);
                if (isValidSpellCard(randomCard) && j < spellsToAdd.size()) {
                    spellsToAdd.set(j, randomCard);
                }
            }
            // Add randomly selected spells needed
            deckCards.addAll(spellsToAdd);
        }
        // More spells than needed
        else if(spellSize > (deckSize - landCount)) {

            int spellsRemoved = (spellSize)-(deckSize-landCount);
            for(int i = 0; i < spellsRemoved; ++i) {
                deckCards.remove(random.nextInt(deckCards.size()));
            }
        }

        // Not strictly necessary as we check when adding cards, but worth double checking anyway.
        if(deckCards.size() != nonLandSize) {
            throw new IllegalStateException("Not enough cards found to generate deck. Please try again");
        }

        // Return the fixed amount
        return deckCards;
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
     * Returns if this land taps for the given color.
     * Basic string matching to check the ability adds one of the chosen mana when tapped.
     * @param ability MockAbility of the land card
     * @param symbol colored mana symbol.
     * @return if the ability is tapping to produce the mana the symbol represents.
     */
    private boolean landTapsForAllowedColor(String ability, String symbol)  {
        return ability.matches(".*Add \\{" + symbol + "\\} to your mana pool.");
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
}
