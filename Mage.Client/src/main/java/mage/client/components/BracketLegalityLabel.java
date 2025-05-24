package mage.client.components;

import mage.MageObject;
import mage.cards.decks.Deck;
import mage.client.util.GUISizeHelper;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

/**
 * Inject bracket level inside validation panel
 * See more details at <a href="https://mtg.wiki/page/Commander_Brackets">wiki</a>
 * <p>
 * Support:
 * - [x] game changers
 * - [ ] infinite combos
 * - [ ] mass land destruction
 * - [ ] extra turns
 * - [ ] tutors
 *
 * @author JayDi85
 */
public class BracketLegalityLabel extends LegalityLabel {

    private static final String GROUP_GAME_CHANGES = "Game Changers";
    private static final String GROUP_INFINITE_COMBOS = "Infinite Combos (unsupported)";
    private static final String GROUP_MASS_LAND_DESTRUCTION = "Mass Land Destruction (unsupported)";
    private static final String GROUP_EXTRA_TURN = "Extra Turns (unsupported)";
    private static final String GROUP_TUTORS = "Tutors (unsupported)";

    private final BracketLevel level;

    private final List<String> foundGameChangers = new ArrayList<>();
    private final List<String> foundInfiniteCombos = new ArrayList<>();
    private final List<String> foundMassLandDestruction = new ArrayList<>();
    private final List<String> foundExtraTurn = new ArrayList<>();
    private final List<String> foundTutors = new ArrayList<>();

    private final List<String> badCards = new ArrayList<>();
    private final List<String> fullGameChanges = new ArrayList<>();

    public enum BracketLevel {
        BRACKET_1("Bracket 1"),
        BRACKET_2_3("Bracket 2-3"),
        BRACKET_4_5("Bracket 4-5");

        private final String name;

        BracketLevel(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public BracketLegalityLabel(BracketLevel level) {
        super(level.toString(), null);
        this.level = level;
        setPreferredSize(DIM_PREFERRED);
    }

    @Override
    public List<String> selectCards() {
        return new ArrayList<>(this.badCards);
    }

    private void validateBracketLevel() {
        this.badCards.clear();
        switch (this.level) {
            case BRACKET_1:
                // No cards from the Game Changer list.
                // No intentional two-card infinite combos.
                // No mass land destruction.
                // No extra turn cards.
                // Tutors should be sparse.
                this.badCards.addAll(this.foundGameChangers);
                this.badCards.addAll(this.foundInfiniteCombos);
                this.badCards.addAll(this.foundMassLandDestruction);
                this.badCards.addAll(this.foundExtraTurn);
                if (this.foundTutors.size() > 3) {
                    this.badCards.addAll(this.foundTutors);
                }
                break;
            case BRACKET_2_3:
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
                if (this.foundGameChangers.size() > 3) {
                    this.badCards.addAll(this.foundGameChangers);
                }
                this.badCards.addAll(this.foundInfiniteCombos);
                this.badCards.addAll(this.foundMassLandDestruction);
                if (this.foundExtraTurn.size() > 3) {
                    this.badCards.addAll(this.foundExtraTurn);
                }
                // this.badCards.addAll(this.foundTutors); // allow any amount
                break;
            case BRACKET_4_5:
                // any allow
                break;
            default:
                throw new IllegalArgumentException("Unsupported level: " + this.level);
        }
    }

    @Override
    public void validateDeck(Deck deck) {
        collectAll(deck);
        validateBracketLevel();

        int infoFontSize = Math.round(GUISizeHelper.cardTooltipFont.getSize() * 0.6f);

        // show all found cards in any use cases
        Color showColor = this.badCards.isEmpty() ? COLOR_LEGAL : COLOR_NOT_LEGAL;
        List<String> showInfo = new ArrayList<>();

        Map<String, List<String>> groups = new LinkedHashMap<>();
        groups.put(GROUP_GAME_CHANGES, this.foundGameChangers);
        groups.put(GROUP_INFINITE_COMBOS, this.foundInfiniteCombos);
        groups.put(GROUP_MASS_LAND_DESTRUCTION, this.foundMassLandDestruction);
        groups.put(GROUP_EXTRA_TURN, this.foundExtraTurn);
        groups.put(GROUP_TUTORS, this.foundTutors);
        groups.forEach((group, cards) -> {
            showInfo.add("<br>");
            showInfo.add("<u><span style='font-weight:bold;'>" + group + ": " + cards.size() + "</span></u>");
            if (!cards.isEmpty()) {
                showInfo.add("<ul style=\"font-size: " + infoFontSize + "px; width: " + TOOLTIP_TABLE_WIDTH + "px; padding-left: 10px; margin: 0;\">");
                cards.forEach(s -> showInfo.add(String.format("<li style=\"margin-bottom: 2px;\">%s</li>", s)));
                showInfo.add("</ul>");
            }
        });

        String showText = "<html><body>" + String.join("\n", showInfo) + "</body></html>";
        showState(showColor, showText, false);
    }

    private void collectAll(Deck deck) {
        collectGameChangers(deck);
        collectInfiniteCombos(deck);
        collectMassLandDestruction(deck);
        collectExtraTurn(deck);
        collectTutors(deck);
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
                    "Deflecting Swat",
                    "Enlightened Tutor",
                    "Expropriate",
                    "Field of the Dead",
                    "Fierce Guardianship",
                    "Food Chain",
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
                    "Jin-Gitaxias, Core Augur",
                    "Kinnan, Bonder Prodigy",
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
                    "Sway of the Stars",
                    "Teferi's Protection",
                    "Tergrid, God of Fright",
                    "Thassa's Oracle",
                    "The One Ring",
                    "The Tabernacle at Pendrell Vale",
                    "Underworld Breach",
                    "Urza, Lord High Artificer",
                    "Vampiric Tutor",
                    "Vorinclex, Voice of Hunger",
                    "Yuriko, the Tiger's Shadow",
                    "Winota, Joiner of Forces",
                    "Worldly Tutor"
            ));
        }

        Stream.concat(deck.getCards().stream(), deck.getSideboard().stream())
                .map(MageObject::getName)
                .filter(fullGameChanges::contains)
                .distinct()
                .sorted()
                .forEach(this.foundGameChangers::add);
    }

    private void collectInfiniteCombos(Deck deck) {
        // TODO: implement
        this.foundInfiniteCombos.clear();
    }

    private void collectMassLandDestruction(Deck deck) {
        // TODO: implement
        this.foundMassLandDestruction.clear();
    }

    private void collectExtraTurn(Deck deck) {
        // TODO: implement
        this.foundExtraTurn.clear();
    }

    private void collectTutors(Deck deck) {
        // TODO: implement
        this.foundTutors.clear();
    }
}