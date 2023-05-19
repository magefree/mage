package mage.game.command;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.cards.FrameStyle;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.util.RandomUtil;
import mage.util.SubTypes;

import java.util.*;
import java.util.stream.Collectors;

// CR 100.2d. Some formats and casual play variants allow players to use a supplementary deck of nontraditional
// Magic cards (see rule 108.2a). These supplementary decks have their own deck construction rules. See rule 717,
// "Attraction Cards;" rule 901, "Planechase;" and rule 904, "Archenemy."
public abstract class ExtraDeck
    // Supplementary decks always seem to go in the command zone. The closest thing I can find to the rules
    // explicitly acknowledging this is the subgame rules:
    // CR 724.2a. As a subgame begins, if one or more supplementary decks of nontraditional cards are being used, each
    // player moves each of their supplementary decks from the main-game command zone to the subgame command zone and
    // shuffles it. (Face-up nontraditional cards remain in the main-game command zone.)
    // See also the Attraction rules, as far as cards legal in tournament play:
    // CR 717.2. Attraction cards do not begin the game in a player's deck and do not count toward maximum or minimum
    // deck sizes. Rather, a player who chooses to play with Attraction cards begins the game with a supplementary
    // Attraction deck that exists in the command zone. [...]
        extends CommandObjectImpl {
    protected UUID ownerID;
    protected Deque<UUID> contents = new ArrayDeque<>();

    public ExtraDeck(UUID ownerID, String name) {
        super(name);
        this.ownerID = ownerID;
    }
    public ExtraDeck(ExtraDeck extraDeck) {
        super(extraDeck);
        this.ownerID = extraDeck.ownerID;
        for (UUID id : extraDeck.contents) {
            this.contents.addLast(id);
        }
    }

    // begin methods adapted from Library
    public void shuffle() {
        UUID[] shuffled = contents.toArray(new UUID[0]);
        for (int n = shuffled.length - 1; n > 0; n--) {
            int r = RandomUtil.nextInt(n + 1);
            UUID temp = shuffled[n];
            shuffled[n] = shuffled[r];
            shuffled[r] = temp;
        }
        contents.clear();
        contents.addAll(Arrays.asList(shuffled));
    }
    public void clear() {
        contents.clear();
    }

    public int size() {
        return contents.size();
    }

    public void set(ExtraDeck newExtraDeck) {
        contents.clear();
        contents.addAll(newExtraDeck.getCardList());
    }

    public List<UUID> getCardList() {
        return new ArrayList<>(contents);
    }

    /**
     * Returns the cards of the deck in a list ordered from top to bottom
     *
     * @param game
     * @return
     */
    public List<Card> getCards(Game game) {
        return contents.stream().map(game::getCard).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Set<Card> getTopCards(Game game, int amount) {
        Set<Card> cards = new LinkedHashSet<>();
        Iterator<UUID> it = this.contents.iterator();
        int count = 0;
        while (it.hasNext() && count < amount) {
            UUID cardId = it.next();
            Card card = game.getCard(cardId);
            if (card != null) {
                cards.add(card);
                ++count;
            }
        }
        return cards;
    }

    public Collection<Card> getUniqueCards(Game game) {
        Map<String, Card> cards = new HashMap<>();
        for (UUID cardId : this.contents) {
            Card card = game.getCard(cardId);
            if (card != null) {
                cards.putIfAbsent(card.getName(), card);
            }
        }
        return cards.values();
    }

    public int count(FilterCard filter, Game game) {
        return (int) contents.stream().filter(cardId -> filter.match(game.getCard(cardId), game)).count();
    }
    public void addAll(Set<Card> cards, Game game) {
        for (Card card : cards) {
            // as discussed earlier, extra decks and their cards live in the command zone
            card.setZone(Zone.COMMAND, game);
            card.setFaceDown(true, game);
            this.contents.add(card.getId());
        }
    }
    public Card getCard(UUID cardId, Game game) {
        for (UUID card : contents) {
            if (card.equals(cardId)) {
                return game.getCard(card);
            }
        }
        return null;
    }

    public Card remove(UUID cardId, Game game) {
        Iterator<UUID> it = contents.iterator();
        while (it.hasNext()) {
            UUID card = it.next();
            if (card.equals(cardId)) {
                it.remove();
                return game.getCard(card);
            }
        }
        return null;
    }

    public boolean hasCards() {
        return size() > 0;
    }

    @Override
    public String toString() {
        return getName() + " (" + contents.size() + " cards)";
    }

    // end methods adapted from Library

    @Override
    public List<CardType> getCardType(Game game) {
        return Collections.emptyList();
    }

    @Override
    public SubTypes getSubtype() {
        return new SubTypes();
    }

    @Override
    public SubTypes getSubtype(Game game) {
        return new SubTypes();
    }

    @Override
    public boolean hasSubtype(SubType subtype, Game game) {
        return false;
    }

    @Override
    public List<SuperType> getSuperType(Game game) {
        return Collections.emptyList();
    }


    @Override
    public Abilities<Ability> getAbilities() {
        return new AbilitiesImpl<>();
    }

    @Override
    public boolean hasAbility(Ability ability, Game game) {
        return false;
    }

    @Override
    public ObjectColor getColor() {
        return ObjectColor.COLORLESS;
    }

    @Override
    public ObjectColor getColor(Game game) {
        return ObjectColor.COLORLESS;
    }

    @Override
    public ObjectColor getFrameColor(Game game) {
        return ObjectColor.COLORLESS;
    }

    @Override
    public FrameStyle getFrameStyle() {
        // should be no frame
        return FrameStyle.UST_FULL_ART_BASIC;
    }

    private static final ManaCosts emptyCost = new ManaCostsImpl<>();
    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return emptyCost;
    }

    @Override
    public int getManaValue() {
        return 0;
    }

    @Override
    public MageInt getPower() {
        return MageInt.EmptyMageInt;
    }

    @Override
    public MageInt getToughness() {
        return MageInt.EmptyMageInt;
    }

    @Override
    public int getStartingLoyalty() {
        return 0;
    }

    @Override
    public void setStartingLoyalty(int startingLoyalty) {
    }

    @Override
    public int getStartingDefense() {
        return 0;
    }

    @Override
    public void setStartingDefense(int startingDefense) {
    }

    @Override
    public int getZoneChangeCounter(Game game) {
        return 1; // extra decks don't change zones
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void setZoneChangeCounter(int value, Game game) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public boolean isAllCreatureTypes(Game game) {
        return false;
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {
    }

    @Override
    public void setIsAllCreatureTypes(Game game, boolean value) {
    }

    @Override
    public void removePTCDA() {
    }

    @Override
    public UUID getControllerId() {
        return ownerID;
    }

    // if null for these is good enough for Dungeons, it's good enough for decks that never do things
    @Override
    public UUID getSourceId() {
        return null;
    }
    @Override
    public MageObject getSourceObject() {
        return null;
    }
    // copying decks doesn't seem like a thing that happens
    @Override
    public void setCopy(boolean isCopy, MageObject copiedFrom) {
    }

    @Override
    public MageObject getCopyFrom() {
        return null;
    }

    @Override
    public boolean isCopy() {
        return false;
    }

}
