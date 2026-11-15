package mage.utils;

import mage.cards.Card;
import mage.cards.RateCard;
import mage.cards.SplitCard;
import mage.cards.decks.Deck;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.util.DeckBuildUtils;
import mage.util.RandomUtil;
import mage.util.TournamentUtil;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Limited deck (40 cards) from a card pool by card ratings.
 * Used by bots and by the construct timeout of human players (same logic for all)
 *
 * @author JayDi85
 */
public final class LimitedDeckBuilder {

    private LimitedDeckBuilder() {
        // utility class
    }

    public static Deck buildDeck(int deckMinSize, List<Card> cardPool, final List<ColoredManaSymbol> colors) {
        // top 23 cards plus basic lands until 40 deck size
        Deck deck = new Deck();
        final int DECK_SIZE = deckMinSize != 0 ? deckMinSize : 40;
        final int DECK_CARDS_COUNT = Math.floorDiv(DECK_SIZE * 23, 40); // 23 from 40
        final int DECK_LANDS_COUNT = DECK_SIZE - DECK_CARDS_COUNT;

        // sort card pool by top score
        Map<UUID, Integer> scores = new HashMap<>();
        cardPool.forEach(card -> scores.put(card.getId(), RateCard.rateCard(card, colors)));
        List<Card> sortedCards = new ArrayList<>(cardPool);
        sortedCards.sort((o1, o2) -> Integer.compare(scores.get(o2.getId()), scores.get(o1.getId())));

        // get top cards
        // nonbasic lands from the pool use lands slots, not spells slots
        List<Card> deckSpells = new ArrayList<>();
        int nonBasicLandsCount = 0;
        int cardNum = 0;
        while (deckSpells.size() < DECK_CARDS_COUNT && sortedCards.size() > cardNum) {
            Card card = sortedCards.get(cardNum);
            if (card.isLand()) {
                if (!card.isBasic() && nonBasicLandsCount < DECK_LANDS_COUNT) {
                    deck.getCards().add(card);
                    nonBasicLandsCount++;
                }
            } else {
                deckSpells.add(card);
            }
            cardNum++;
        }
        final int BASIC_LANDS_COUNT = DECK_LANDS_COUNT - nonBasicLandsCount;

        // improve curve and creatures amount by other spells from the pool
        List<Card> otherSpells = sortedCards.subList(cardNum, sortedCards.size())
                .stream()
                .filter(card -> !card.isLand())
                .filter(card -> !RateCard.isOffColorCard(card, colors))
                .collect(Collectors.toList());
        improveDeckSpells(deckSpells, otherSpells, scores, DECK_CARDS_COUNT);
        deck.getCards().addAll(deckSpells);

        // add basic lands by colored mana symbols of the spells (nonbasic lands of the deck are color sources too)
        Map<ColoredManaSymbol, Integer> landsByColors = DeckBuildUtils.splitBasicLandsByColors(BASIC_LANDS_COUNT, DeckBuildUtils.countColoredManaSymbols(deck.getCards(), colors), deck.getCards());
        int mostLand = 0;
        String mostLandName = "Forest"; // default for colorless decks
        for (Entry<ColoredManaSymbol, Integer> entry : landsByColors.entrySet()) {
            String landName = getBasicLandName(entry.getKey());
            addBasicLands(deck, landName, entry.getValue());
            if (entry.getValue() > mostLand) {
                mostLand = entry.getValue();
                mostLandName = landName;
            }
        }

        // adds remaining lands (most popular name), e.g. for a small pool without enough spells
        addBasicLands(deck, mostLandName, DECK_SIZE - deck.getMaindeckCards().size());

        return deck;
    }

    /**
     * Improve deck's curve and creatures amount: replace the worst deck's spells by the best other spells from the pool
     * (deck with 23 spells: max 3 cards with mana value 6+, min 15 creatures, min 4 cards with mana value 2 or less)
     */
    private static void improveDeckSpells(List<Card> deckSpells, List<Card> otherSpells, Map<UUID, Integer> scores, int deckSpellsCount) {
        final int MAX_TOP_DROPS = Math.floorDiv(deckSpellsCount * 3, 23);
        final int MIN_CREATURES = Math.floorDiv(deckSpellsCount * 15, 23);
        final int MIN_EARLY_DROPS = Math.floorDiv(deckSpellsCount * 4, 23);

        // too many expensive cards
        while (countCards(deckSpells, LimitedDeckBuilder::isTopDrop) > MAX_TOP_DROPS) {
            Card oldCard = findWorstCard(deckSpells, scores, LimitedDeckBuilder::isTopDrop);
            Card newCard = findBestCard(otherSpells, card -> !isTopDrop(card) && card.isCreature() == oldCard.isCreature());
            if (newCard == null) {
                newCard = findBestCard(otherSpells, card -> !isTopDrop(card));
            }
            if (newCard == null) {
                break;
            }
            replaceCard(deckSpells, otherSpells, oldCard, newCard);
        }

        // not enough creatures
        while (countCards(deckSpells, Card::isCreature) < MIN_CREATURES) {
            Card oldCard = findWorstCard(deckSpells, scores, card -> !card.isCreature());
            Card newCard = findBestCard(otherSpells, card -> card.isCreature() && !isTopDrop(card));
            if (oldCard == null || newCard == null) {
                break;
            }
            replaceCard(deckSpells, otherSpells, oldCard, newCard);
        }

        // not enough cheap cards
        while (countCards(deckSpells, LimitedDeckBuilder::isEarlyDrop) < MIN_EARLY_DROPS) {
            boolean keepCreatures = countCards(deckSpells, Card::isCreature) <= MIN_CREATURES;
            Card newCard = findBestCard(otherSpells, card -> isEarlyDrop(card) && (!keepCreatures || card.isCreature()));
            if (newCard == null) {
                newCard = findBestCard(otherSpells, LimitedDeckBuilder::isEarlyDrop);
            }
            if (newCard == null) {
                break;
            }
            boolean newCreature = newCard.isCreature();
            Card oldCard = findWorstCard(deckSpells, scores, card -> getCurveManaValue(card) >= 4
                    && (!keepCreatures || newCreature || !card.isCreature()));
            if (oldCard == null) {
                break;
            }
            replaceCard(deckSpells, otherSpells, oldCard, newCard);
        }
    }

    private static int countCards(List<Card> cards, Predicate<Card> filter) {
        return (int) cards.stream().filter(filter).count();
    }

    private static Card findWorstCard(List<Card> cards, Map<UUID, Integer> scores, Predicate<Card> filter) {
        return cards.stream()
                .filter(filter)
                .min(Comparator.comparingInt(card -> scores.get(card.getId())))
                .orElse(null);
    }

    private static Card findBestCard(List<Card> sortedCards, Predicate<Card> filter) {
        return sortedCards.stream()
                .filter(filter)
                .findFirst()
                .orElse(null);
    }

    private static void replaceCard(List<Card> deckSpells, List<Card> otherSpells, Card oldCard, Card newCard) {
        deckSpells.remove(oldCard);
        otherSpells.remove(newCard);
        deckSpells.add(newCard);
    }

    private static boolean isTopDrop(Card card) {
        return getCurveManaValue(card) >= 6;
    }

    private static boolean isEarlyDrop(Card card) {
        return getCurveManaValue(card) <= 2;
    }

    /**
     * Mana value for a deck's curve. Split and room cards: each half can be cast alone, so it uses the cheapest half
     * (mana value of the whole card is a sum of halves)
     */
    private static int getCurveManaValue(Card card) {
        if (card instanceof SplitCard) {
            SplitCard splitCard = (SplitCard) card;
            return Math.min(splitCard.getLeftHalfCard().getManaValue(), splitCard.getRightHalfCard().getManaValue());
        }
        return card.getManaValue();
    }

    private static String getBasicLandName(ColoredManaSymbol color) {
        switch (color) {
            case W:
                return "Plains";
            case U:
                return "Island";
            case B:
                return "Swamp";
            case R:
                return "Mountain";
            case G:
                return "Forest";
            default:
                throw new IllegalArgumentException("Unknown basic land color: " + color);
        }
    }

    public static void addBasicLands(Deck deck, String landName, int number) {
        Set<String> landSets = TournamentUtil.getLandSetCodeForDeckSets(deck.getExpansionSetCodes());

        CardCriteria criteria = new CardCriteria();
        if (!landSets.isEmpty()) {
            criteria.setCodes(landSets.toArray(new String[0]));
        }
        criteria.rarities(Rarity.LAND).name(landName);
        List<CardInfo> cards = CardRepository.instance.findCards(criteria);

        if (cards.isEmpty()) {
            criteria = new CardCriteria();
            criteria.rarities(Rarity.LAND).name(landName);
            criteria.setCodes("M15");
            cards = CardRepository.instance.findCards(criteria);
        }

        for (int i = 0; i < number; i++) {
            Card land = cards.get(RandomUtil.nextInt(cards.size())).createCard();
            deck.getCards().add(land);
        }
    }
}
