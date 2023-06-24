package mage.cards.decks;

import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Constructed extends DeckValidator {

    private static final Logger logger = Logger.getLogger(Constructed.class);

    protected List<String> banned = new ArrayList<>();
    protected List<String> restricted = new ArrayList<>();
    protected List<String> setCodes = new ArrayList<>();
    protected List<Rarity> rarities = new ArrayList<>();
    protected Set<String> singleCards = new HashSet<>();

    protected Constructed(String name) {
        this(name, null);
    }

    protected Constructed(String name, String shortName) {
        super(name, shortName);
        // Conspiracy cards are banned
        banned.add("Adriana's Valor");
        banned.add("Advantageous Proclamation");
        banned.add("Assemble the Rank and Vile");
        banned.add("Backup Plan");
        banned.add("Brago's Favor");
        banned.add("Double Stroke");
        banned.add("Echoing Boon");
        banned.add("Emissary's Ploy");
        banned.add("Hired Heist");
        banned.add("Hold the Perimeter");
        banned.add("Hymn of the Wilds");
        banned.add("Immediate Action");
        banned.add("Incendiary Dissent");
        banned.add("Iterative Analysis");
        banned.add("Muzzio's Preparations");
        banned.add("Natural Unity");
        banned.add("Power Play");
        banned.add("Secrets of Paradise");
        banned.add("Secret Summoning");
        banned.add("Sentinel Dispatch");
        banned.add("Sovereign's Realm");
        banned.add("Summoner's Bond");
        banned.add("Unexpected Potential");
        banned.add("Weight Advantage");
        banned.add("Worldknit");

        // Dexterity cards are banned
        banned.add("Chaos Orb");
        banned.add("Falling Star");

        // Sub-game cards are banned
        banned.add("Shahrazad");

        // Ante cards are banned
        banned.add("Amulet of Quoz");
        banned.add("Bronze Tablet");
        banned.add("Contract from Below");
        banned.add("Darkpact");
        banned.add("Demonic Attorney");
        banned.add("Jeweled Bird");
        banned.add("Rebirth");
        banned.add("Tempest Efreet");
        banned.add("Timmerian Fiends");

        // Potentially offensive cards are banned
        banned.add("Cleanse");
        banned.add("Crusade");
        banned.add("Imprison");
        banned.add("Invoke Prejudice");
        banned.add("Jihad");
        banned.add("Pradesh Gypsies");
        banned.add("Stone-Throwing Devils");

        // Acorn stamp cards are banned
        banned.add("\"Brims\" Barone, Midway Mobster");
        banned.add("A Real Handful");
        banned.add("Aardwolf's Advantage");
        banned.add("Alpha Guard");
        banned.add("Amped Up");
        banned.add("An Incident Has Occurred");
        banned.add("Angelic Harold");
        banned.add("Animate Graveyard");
        banned.add("Animate Object");
        banned.add("Art Appreciation");
        banned.add("Assembled Ensemble");
        banned.add("Astroquarium");
        banned.add("Autograph Book");
        banned.add("Bag Check");
        banned.add("Bar Entry");
        banned.add("Blue Ribbon");
        banned.add("Blufferfish");
        banned.add("Busted!");
        banned.add("Carnival Barker");
        banned.add("Centrifuge");
        banned.add("Claire D'Loon, Joy Sculptor");
        banned.add("Cover the Spot");
        banned.add("D00-DL, Caricaturist");
        banned.add("Dart Throw");
        banned.add("Decisions, Decisions");
        banned.add("Devil K. Nevil");
        banned.add("Disemvowel");
        banned.add("Don't Try This at Home");
        banned.add("Exit Through the Grift Shop");
        banned.add("Far Out");
        banned.add("Fluros of Myra's Marvels");
        banned.add("Focused Funambulist");
        banned.add("Form of the Approach of the Second Sun");
        banned.add("Gallery of Legends");
        banned.add("Get Your Head in the Game");
        banned.add("Gift Shop");
        banned.add("Goblin Blastronauts");
        banned.add("Goblin Cruciverbalist");
        banned.add("Goblin Girder Gang");
        banned.add("Gobsmacked");
        banned.add("Grand Marshal Macie");
        banned.add("Gray Merchant of Alphabet");
        banned.add("Greatest Show in the Multiverse");
        banned.add("Guess Your Fate");
        banned.add("Haberthrasher");
        banned.add("Hardy of Myra's Marvels");
        banned.add("Hat Trick");
        banned.add("How Is This a Par Three?!");
        banned.add("Icing Manipulator");
        banned.add("Ignacio of Myra's Marvels");
        banned.add("Impounding Lot-Bot");
        banned.add("It Came from Planet Glurg");
        banned.add("Jermane, Pride of the Circus");
        banned.add("Jetpack Janitor");
        banned.add("Juggletron");
        banned.add("Katerina of Myra's Marvels");
        banned.add("Killer Cosplay");
        banned.add("Knife and Death");
        banned.add("Knight in _____ Armor");
        banned.add("Leading Performance");
        banned.add("Lila, Hospitality Hostess");
        banned.add("Log Flume");
        banned.add("Main Event Horizon");
        banned.add("Meet and Greet \"Sisay\"");
        banned.add("Memory Test");
        banned.add("Mistakes Were Made");
        banned.add("Mobile Clone");
        banned.add("Nearby Planet");
        banned.add("Nocturno of Myra's Marvels");
        banned.add("Now You See Me . . .");
        banned.add("Octo Opus");
        banned.add("Omniclown Colossus");
        banned.add("Opening Ceremony");
        banned.add("Park Map");
        banned.add("Park Re-Entry");
        banned.add("Phone a Friend");
        banned.add("Photo Op");
        banned.add("Pie-Eating Contest");
        banned.add("Pietra, Crafter of Clowns");
        banned.add("Plate Spinning");
        banned.add("Plot Armor");
        banned.add("Push Your Luck");
        banned.add("Questionable Cuisine");
        banned.add("Rat in the Hat");
        banned.add("Rock Star");
        banned.add("Scavenger Hunt");
        banned.add("Scooch");
        banned.add("Solaflora, Intergalactic Icon");
        banned.add("Sole Performer");
        banned.add("Souvenir T-Shirt");
        banned.add("Spelling Bee");
        banned.add("Squirrel Stack");
        banned.add("Standard Procedure");
        banned.add("Super-Duper Lost");
        banned.add("Surprise Party");
        banned.add("T.A.P.P.E.R.");
        banned.add("Tchotchke Elemental");
        banned.add("The Big Top");
        banned.add("The Superlatorium");
        banned.add("Ticking Mime Bomb");
        banned.add("Trapeze Artist");
        banned.add("Treacherous Trapezist");
        banned.add("Trigger Happy");
        banned.add("Trivia Contest");
        banned.add("Truss, Chief Engineer");
        banned.add("Tug of War");
        banned.add("Urza's Fun House");
        banned.add("Vorthos, Steward of Myth");
        banned.add("Water Gun Balloon Game");
        banned.add("Well Done");
    }

    public List<String> getSetCodes() {
        return setCodes;
    }

    @Override
    public int getDeckMinSize() {
        return 60;
    }

    @Override
    public int getSideboardMinSize() {
        return 0;
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        errorsList.clear();
        //20091005 - 100.2a
        if (deck.getCards().size() < getDeckMinSize()) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain at least " + getDeckMinSize() + " cards: has only " + deck.getCards().size() + " cards");
            valid = false;
        }
        //20130713 - 100.4a
        if (deck.getSideboard().size() > 15) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Sideboard", "Must contain no more than 15 cards : has " + deck.getSideboard().size() + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        valid = checkCounts(4, counts) && valid;

        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                addError(DeckValidatorErrorType.BANNED, bannedCard, "Banned", true);
                valid = false;
            }
        }

        for (String restrictedCard : restricted) {
            if (counts.containsKey(restrictedCard)) {
                int count = counts.get(restrictedCard);
                if (count > 1) {
                    addError(DeckValidatorErrorType.OTHER, restrictedCard, "Restricted amount: " + count, true);
                    valid = false;
                }
            }
        }

        if (!rarities.isEmpty()) {
            for (Card card : deck.getCards()) {
                if (!rarities.contains(card.getRarity())) {
                    if (!legalRarity(card)) {
                        valid = false;
                    }
                }
            }
            for (Card card : deck.getSideboard()) {
                if (!rarities.contains(card.getRarity())) {
                    if (!legalRarity(card)) {
                        valid = false;
                    }
                }
            }
        }

        for (Card card : deck.getCards()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    valid = false;
                }
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    /**
     * Checks if the given card is legal in any of the given rarities
     *
     * @param card - the card to check
     * @return Whether the card was printed at any of the given rarities.
     */
    protected boolean legalRarity(Card card) {
        // check if card is legal if taken from other set
        boolean legal = false;
        List<CardInfo> cardInfos = CardRepository.instance.findCards(card.getName());
        for (CardInfo cardInfo : cardInfos) {
            if (rarities.contains(cardInfo.getRarity())) {
                legal = true;
                break;
            }
        }
        if (!legal && !errorsListContainsGroup(card.getName())) {
            addError(DeckValidatorErrorType.OTHER, card.getName(), "Invalid rarity: " + card.getRarity(), true);
        }
        return legal;
    }

    /**
     * Checks if a given set is legal in this format.
     *
     * @param code - the set code to check
     * @return Whether the set is legal in this format.
     */
    protected boolean isSetAllowed(String code) {
        return setCodes.isEmpty() || setCodes.contains(code);
    }

    /**
     * Checks if the given card is legal in any of the given sets or as single card
     *
     * @param card - the card to check
     * @return Whether the card was printed in any of this format's sets.
     */
    protected boolean legalSets(Card card) {
        // check if card is legal if taken from other set
        boolean legal = false;
        List<CardInfo> cardInfos = CardRepository.instance.findCards(card.getName());
        for (CardInfo cardInfo : cardInfos) {
            if (isSetAllowed(cardInfo.getSetCode())) {
                legal = true;
                break;
            }
        }

        // check if single card allows
        if (singleCards.contains(card.getName())) {
            legal = true;
        }

        if (!legal && !errorsListContainsGroup(card.getName())) {
            addError(DeckValidatorErrorType.WRONG_SET, card.getName(), "Invalid set: " + card.getExpansionSetCode(), true);
        }
        return legal;
    }

    protected boolean checkCounts(int maxCopies, Map<String, Integer> counts) {
        boolean valid = true;
        for (Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > getMaxCopies(entry.getKey(), maxCopies)) {
                addError(DeckValidatorErrorType.OTHER, entry.getKey(), "Too many: " + entry.getValue(), true);
                valid = false;
            }
        }
        return valid;
    }
}