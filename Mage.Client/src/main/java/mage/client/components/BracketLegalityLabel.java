package mage.client.components;

import mage.MageObject;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.client.util.GUISizeHelper;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

/**
 * Inject bracket level inside validation panel
 * See more details at <a href="https://mtg.wiki/page/Commander_Brackets">wiki</a>
 * <p>
 * Support:
 * - [x] game changers
 * - [x] infinite combos
 * - [x] mass land destruction
 * - [x] extra turns
 * - [x] tutors
 * Features:
 * - [x] find possible bracket level of the deck
 * - [x] find affected cards by checking group
 * - [x] can auto-generate infinite combos list, see verify test downloadAndPrepareCommanderBracketsData
 * - [ ] TODO: tests
 * - [ ] TODO: table - players brackets level disclose settings
 * - [ ] TODO: generate - convert card name to xmage format and assert on bad names (ascii only)
 *
 * @author JayDi85
 */
public class BracketLegalityLabel extends LegalityLabel {

    private static final Logger logger = Logger.getLogger(BracketLegalityLabel.class);

    private static final String GROUP_GAME_CHANGES = "Game Changers";
    private static final String GROUP_INFINITE_COMBOS = "Early-game 2-Card Combos";
    private static final String GROUP_MASS_LAND_DESTRUCTION = "Mass Land Destruction";
    private static final String GROUP_EXTRA_TURN = "Extra Turns";

    private static final Map<String, List<Integer>> MAX_GROUP_LIMITS = new LinkedHashMap<>();

    static {
        // 1
        // No cards from the Game Changer list.
        // No intentional two-card infinite combos.
        // No mass land destruction.
        // No extra turn cards.
        // Tutors should be sparse.
        // 2
        // No cards from the Game Changer list.
        // No intentional two-card infinite combos.
        // No mass land destruction.
        // Extra turn cards should only appear in low quantities and should not be chained in succession or looped.
        // Tutors should be sparse.
        // 3
        // Up to three (3) cards from the Game Changer list.
        // No intentional early game two-card infinite combos.
        // No mass land destruction.
        // Extra turn cards should only appear in low quantities and should not be chained in succession or looped.
        // 4
        // 5
        // allow any cards

        // cards limits per brackets level, it's ok to use 99 as max
        // group - levels 0, 1, 2, 3, 4, 5
        MAX_GROUP_LIMITS.put(GROUP_GAME_CHANGES,
                Arrays.asList(0, 0, 0, 3, 99, 99));
        MAX_GROUP_LIMITS.put(GROUP_INFINITE_COMBOS,
                Arrays.asList(0, 0, 0, 0, 99, 99));
        MAX_GROUP_LIMITS.put(GROUP_MASS_LAND_DESTRUCTION,
                Arrays.asList(0, 0, 0, 0, 99, 99));
        MAX_GROUP_LIMITS.put(GROUP_EXTRA_TURN,
                Arrays.asList(0, 0, 0, 3, 99, 99));
    }

    private static final String RESOURCE_INFINITE_COMBOS = "brackets/infinite-combos.txt";

    private final String fullName;
    private final String shortName;
    private final int maxLevel;

    private final List<String> foundGameChangers = new ArrayList<>();
    private final List<String> foundInfiniteCombos = new ArrayList<>();
    private final List<String> foundMassLandDestruction = new ArrayList<>();
    private final List<String> foundExtraTurn = new ArrayList<>();

    private final List<String> badCards = new ArrayList<>();
    private final List<String> fullGameChanges = new ArrayList<>();
    private final Set<String> fullInfiniteCombos = new HashSet<>(); // card1@card2, sorted by names, name must be xmage compatible

    public BracketLegalityLabel(String fullName, String shortName, int maxLevel) {
        super(shortName, null);
        this.fullName = fullName;
        this.shortName = shortName;
        this.maxLevel = maxLevel;
        setPreferredSize(DIM_PREFERRED_1_OF_5);
    }

    @Override
    public List<String> selectCards() {
        return new ArrayList<>(this.badCards);
    }

    private void validateBracketLevel() {
        this.badCards.clear();

        if (this.foundGameChangers.size() > getMaxCardsLimit(GROUP_GAME_CHANGES)) {
            this.badCards.addAll(this.foundGameChangers);
        }
        if (this.foundInfiniteCombos.size() > getMaxCardsLimit(GROUP_INFINITE_COMBOS)) {
            this.badCards.addAll(this.foundInfiniteCombos);
        }
        if (this.foundMassLandDestruction.size() > getMaxCardsLimit(GROUP_MASS_LAND_DESTRUCTION)) {
            this.badCards.addAll(this.foundMassLandDestruction);
        }
        if (this.foundExtraTurn.size() > getMaxCardsLimit(GROUP_EXTRA_TURN)) {
            this.badCards.addAll(this.foundExtraTurn);
        }
    }

    private Integer getMaxCardsLimit(String groupName) {
        return MAX_GROUP_LIMITS.get(groupName).get(this.maxLevel);
    }

    @Override
    public void validateDeck(Deck deck) {
        collectAll(deck);
        validateBracketLevel();

        int infoFontHeaderSize = Math.round(GUISizeHelper.cardTooltipFont.getSize() * 1.0f);
        int infoFontTextSize = Math.round(GUISizeHelper.cardTooltipFont.getSize() * 0.6f);

        // show all found cards in any use cases
        Color showColor = this.badCards.isEmpty() ? COLOR_LEGAL : COLOR_NOT_LEGAL;

        List<String> showInfo = new ArrayList<>();
        if (this.badCards.isEmpty()) {
            showInfo.add(String.format("<span style='font-weight:bold;font-size:%dpx;'><p>Deck is <span style='color:green;'>GOOD</span> for %s</p></span>",
                    infoFontHeaderSize,
                    this.fullName
            ));
        } else {
            showInfo.add(String.format("<span style='font-weight:bold;font-size:%dpx;'><p>Deck is <span style='color:#BF544A;'>BAD</span> for %s</p></span>",
                    infoFontHeaderSize,
                    this.fullName
            ));
            showInfo.add("<p>(click here to select all bad cards)</p>");
        }

        Map<String, List<String>> groups = new LinkedHashMap<>();
        groups.put(GROUP_GAME_CHANGES + getStats(GROUP_GAME_CHANGES), this.foundGameChangers);
        groups.put(GROUP_INFINITE_COMBOS + getStats(GROUP_INFINITE_COMBOS), this.foundInfiniteCombos);
        groups.put(GROUP_MASS_LAND_DESTRUCTION + getStats(GROUP_MASS_LAND_DESTRUCTION), this.foundMassLandDestruction);
        groups.put(GROUP_EXTRA_TURN + getStats(GROUP_EXTRA_TURN), this.foundExtraTurn);
        groups.forEach((group, cards) -> {
            showInfo.add("<br>");
            showInfo.add("<span style='font-weight:bold;font-size: " + infoFontTextSize + "px;'>" + group + "</span>");
            if (cards.isEmpty()) {
                showInfo.add("<ul style=\"font-size: " + infoFontTextSize + "px; width: " + TOOLTIP_TABLE_WIDTH + "px; padding-left: 10px; margin: 0;\">");
                showInfo.add("<li style=\"margin-bottom: 2px;\">no cards</li>");
                showInfo.add("</ul>");
            } else {
                showInfo.add("<ul style=\"font-size: " + infoFontTextSize + "px; width: " + TOOLTIP_TABLE_WIDTH + "px; padding-left: 10px; margin: 0;\">");
                cards.forEach(s -> showInfo.add(String.format("<li style=\"margin-bottom: 2px;\">%s</li>", s)));
                showInfo.add("</ul>");
            }
        });

        String showText = "<html><body>" + String.join("\n", showInfo) + "</body></html>";
        showState(showColor, showText, false);
    }

    private String getStats(String groupName) {
        int currentAmount = 0;
        switch (groupName) {
            case GROUP_GAME_CHANGES:
                currentAmount = this.foundGameChangers.size();
                break;
            case GROUP_INFINITE_COMBOS:
                currentAmount = this.foundInfiniteCombos.size();
                break;
            case GROUP_MASS_LAND_DESTRUCTION:
                currentAmount = this.foundMassLandDestruction.size();
                break;
            case GROUP_EXTRA_TURN:
                currentAmount = this.foundExtraTurn.size();
                break;
            default:
                throw new IllegalArgumentException("Unknown group " + groupName);
        }
        int maxAmount = MAX_GROUP_LIMITS.get(groupName).get(this.maxLevel);

        String info;
        if (currentAmount > maxAmount) {
            info = " (<span style='color:#BF544A;'>%s of %s</span>)";
        } else {
            info = " (<span>%s of %s</span>)";
        }

        return String.format(info, currentAmount, maxAmount == 99 ? "any" : maxAmount);
    }

    private void collectAll(Deck deck) {
        collectGameChangers(deck);
        collectInfiniteCombos(deck);
        collectMassLandDestruction(deck);
        collectExtraTurn(deck);
    }

    private void collectGameChangers(Deck deck) {
        this.foundGameChangers.clear();

        if (fullGameChanges.isEmpty()) {
            // https://mtg.wiki/page/Game_Changers
            // TODO: share list with AbstractCommander and edh power level
            fullGameChanges.addAll(Arrays.asList(
                    "Ad Nauseam",
                    "Ancient Tomb",
                    "Aura Shards",
                    "Bolas's Citadel",
                    "Braids, Cabal Minion",
                    "Demonic Tutor",
                    "Drannith Magistrate",
                    "Chrome Mox",
                    "Coalition Victory",
                    "Consecrated Sphinx",
                    "Crop Rotation",
                    "Cyclonic Rift",
                    "Enlightened Tutor",
                    "Field of the Dead",
                    "Fierce Guardianship",
                    "Force of Will",
                    "Gaea's Cradle",
                    "Gamble",
                    "Gifts Ungiven",
                    "Glacial Chasm",
                    "Grand Arbiter Augustin IV",
                    "Grim Monolith",
                    "Humility",
                    "Imperial Seal",
                    "Intuition",
                    "Jeska's Will",
                    "Lion's Eye Diamond",
                    "Mana Vault",
                    "Mishra's Workshop",
                    "Mox Diamond",
                    "Mystical Tutor",
                    "Narset, Parter of Veils",
                    "Natural Order",
                    "Necropotence",
                    "Notion Thief",
                    "Rhystic Study",
                    "Opposition Agent",
                    "Orcish Bowmasters",
                    "Panoptic Mirror",
                    "Seedborn Muse",
                    "Serra's Sanctum",
                    "Smothering Tithe",
                    "Survival of the Fittest",
                    "Teferi's Protection",
                    "Tergrid, God of Fright",
                    "Thassa's Oracle",
                    "The One Ring",
                    "The Tabernacle at Pendrell Vale",
                    "Underworld Breach",
                    "Vampiric Tutor",
                    "Worldly Tutor"
            ));
        }

        Stream.concat(deck.getCards().stream(), deck.getSideboard().stream())
                .map(MageObject::getName)
                .filter(fullGameChanges::contains)
                .sorted()
                .forEach(this.foundGameChangers::add);
    }

    private void collectInfiniteCombos(Deck deck) {
        this.foundInfiniteCombos.clear();

        if (this.fullInfiniteCombos.isEmpty()) {
            InputStream in = BracketLegalityLabel.class.getClassLoader().getResourceAsStream(RESOURCE_INFINITE_COMBOS);
            if (in == null) {
                throw new RuntimeException("Commander brackets: can't load infinite combos list");
            }
            try (InputStreamReader input = new InputStreamReader(in);
                 BufferedReader reader = new BufferedReader(input)) {
                String line = reader.readLine();
                while (line != null) {
                    try {
                        line = line.trim();
                        if (line.startsWith("#")) {
                            continue;
                        }
                        List<String> cards = Arrays.asList(line.split("@"));
                        if (cards.size() != 2) {
                            logger.warn("wrong line format in commander brackets file: " + line);
                            continue;
                        }

                        Collections.sort(cards);
                        this.fullInfiniteCombos.add(String.join("@", cards));
                    } finally {
                        line = reader.readLine();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Tokens brackets: can't load infinite combos list - " + e);
            }
        }

        // search and check all x2 combinations
        List<Card> deckCards = new ArrayList<>();
        Set<Card> foundCards = new HashSet<>();
        deckCards.addAll(deck.getCards());
        deckCards.addAll(deck.getSideboard());
        for (Card card1 : deckCards) {
            for (Card card2 : deckCards) {
                if (card1 == card2) {
                    continue;
                }
                List<String> names = Arrays.asList(card1.getName(), card2.getName());
                Collections.sort(names);
                String deckCombo = String.join("@", names);
                if (this.fullInfiniteCombos.contains(deckCombo)) {
                    foundCards.add(card1);
                    foundCards.add(card2);
                    break;
                }
            }
        }

        foundCards.stream()
                .map(MageObject::getName)
                .sorted()
                .forEach(this.foundInfiniteCombos::add);
    }

    private void collectMassLandDestruction(Deck deck) {
        // https://mtg.wiki/page/Land_destruction
        // https://draftsim.com/mtg-mass-land-destruction/
        this.foundMassLandDestruction.clear();
        Stream.concat(deck.getCards().stream(), deck.getSideboard().stream())
                .filter(card -> card.getRules().stream()
                        .map(s -> s.toLowerCase(Locale.ENGLISH))
                        .anyMatch(s -> (s.contains("destroy") || s.contains("sacrifice"))
                                && (s.contains("all") || s.contains("x target") || s.contains("{x} target"))
                                && isTextContainsLandName(s)
                        )
                )
                .map(Card::getName)
                .sorted()
                .forEach(this.foundMassLandDestruction::add);
    }

    private void collectExtraTurn(Deck deck) {
        this.foundExtraTurn.clear();
        Stream.concat(deck.getCards().stream(), deck.getSideboard().stream())
                .filter(card -> card.getRules().stream()
                        .map(s -> s.toLowerCase(Locale.ENGLISH))
                        .anyMatch(s -> s.contains("extra turn"))
                )
                .map(Card::getName)
                .sorted()
                .forEach(this.foundExtraTurn::add);
    }

    private boolean isTextContainsLandCard(String lowerText) {
        // TODO: share code with AbstractCommander and edh power level
        // TODO: add tests
        return lowerText.contains("basic ")
                || lowerText.contains("plains card")
                || lowerText.contains("island card")
                || lowerText.contains("swamp card")
                || lowerText.contains("mountain card")
                || lowerText.contains("forest card");
    }

    private boolean isTextContainsLandName(String lowerText) {
        // TODO: add tests to find all cards from https://mtg.wiki/page/Land_destruction
        // TODO: add tests
        /*
// mass land destruction
Ajani Vengeant
Armageddon
Avalanche
Bend or Break
Boil
Boiling Seas
Boom // Bust
Burning of Xinye
Catastrophe
Decree of Annihilation
Desolation Angel
Devastation
Fall of the Thran
From the Ashes
Impending Disaster
Jokulhaups
Myojin of Infinite Rage
Numot, the Devastator
Obliterate
Orcish Settlers
Ravages of War
Ruination
Rumbling Crescendo
Scorched Earth
Tsunami
Wake of Destruction
Wildfire
         */
        return lowerText.contains("lands")
                || lowerText.contains("plains")
                || lowerText.contains("island")
                || lowerText.contains("swamp")
                || lowerText.contains("mountain")
                || lowerText.contains("forest");
    }
}