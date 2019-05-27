package mage.deck;

import mage.abilities.Ability;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.filter.FilterMana;

import java.util.*;

/**
 * @author JayDi85
 */
public class Oathbreaker extends Vintage {

    protected List<String> bannedCommander = new ArrayList<>();
    private static final Map<String, Integer> pdAllowed = new HashMap<>();

    public Oathbreaker() {
        super();
        this.name = "Oathbreaker";

        // banned = vintage + oathbreaker's list: https://weirdcards.org/oathbreaker-ban-list
        // last updated 4/4/19 - High Tide banned
        banned.add("Ad Nauseam");
        banned.add("Ancestral Recall");
        banned.add("Balance");
        banned.add("Biorhythm");
        banned.add("Black Lotus");
        banned.add("Channel");
        banned.add("Doomsday");
        banned.add("Emrakul, the Aeons Torn");
        banned.add("Expropriate");
        banned.add("Fastbond");
        banned.add("Gifts Ungiven");
        banned.add("Griselbrand");
        banned.add("High Tide");
        banned.add("Library of Alexandria");
        banned.add("Limited Resources");
        banned.add("Lion's Eye Diamond");
        banned.add("Mana Crypt");
        banned.add("Mana Geyser");
        banned.add("Mana Vault");
        banned.add("Mox Emerald");
        banned.add("Mox Jet");
        banned.add("Mox Pearl");
        banned.add("Mox Ruby");
        banned.add("Mox Sapphire");
        banned.add("Natural Order");
        banned.add("Painter's Servant");
        banned.add("Panoptic Mirror");
        banned.add("Primal Surge");
        banned.add("Primeval Titan");
        banned.add("Recurring Nightmare");
        banned.add("Saheeli, the Gifted");
        banned.add("Sol Ring");
        banned.add("Sundering Titan");
        banned.add("Sway of the Stars");
        banned.add("Sylvan Primordial");
        banned.add("Time Vault");
        banned.add("Time Walk");
        banned.add("Tinker");
        banned.add("Tolarian Academy");
        banned.add("Tooth and Nail");
        banned.add("Trade Secrets");
        banned.add("Upheaval");
        banned.add("Worldfire");
        banned.add("Yawgmoth's Bargain");
    }

    @Override
    public int getDeckMinSize() {
        return 60 - (1 + 2); // spell + 2 x partner oathbreakers
    }

    @Override
    public int getSideboardMinSize() {
        return 2; // spell + oathbreaker
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        FilterMana colorIdentity = new FilterMana();

        if (deck.getCards().size() + deck.getSideboard().size() != 60) {
            invalid.put("Deck", "Must contain " + 60 + " cards: has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                if (!basicLandNames.contains(entry.getKey()) && !anyNumberCardsAllowed.contains(entry.getKey())) {
                    invalid.put(entry.getKey(), "Too many: " + entry.getValue());
                    valid = false;
                }
            }
        }

        Set<String> commanderNames = new HashSet<>();
        String signatureSpell = null;
        if (deck.getSideboard().size() < 2 || deck.getSideboard().size() > 3) {
            invalid.put("Oathbreaker", "Sideboard must contain only the oathbreaker(s) with signature spell");
            valid = false;
        } else {
            for (Card commander : deck.getSideboard()) {
                if (commander.isInstantOrSorcery()) {
                    if (signatureSpell == null) {
                        signatureSpell = commander.getName();
                    } else {
                        invalid.put("Signature spell", "Only one signature spell allows, but found: " + signatureSpell + " and " + commander.getName());
                        valid = false;
                    }
                } else {
                    if (commander.isPlaneswalker()) {
                        commanderNames.add(commander.getName());
                    } else {
                        invalid.put("Oathbreaker", "Only planeswalker can be Oathbreaker, not " + commander.getName());
                        valid = false;
                    }
                }
            }

            for (Card commander : deck.getSideboard()) {
                if (commanderNames.contains(commander.getName())) {
                    // partner checks
                    if (commanderNames.size() == 2 && !commander.getAbilities().contains(PartnerAbility.getInstance())) {
                        boolean partnersWith = false;
                        for (Ability ability : commander.getAbilities()) {
                            if (ability instanceof PartnerWithAbility && commanderNames.contains(((PartnerWithAbility) ability).getPartnerName())) {
                                partnersWith = true;
                                break;
                            }
                        }
                        if (!partnersWith) {
                            invalid.put("Oathbreaker", "Oathbreaker without Partner (" + commander.getName() + ')');
                            valid = false;
                        }
                    }

                    // color identity from commanders only, not spell
                    FilterMana commanderColor = commander.getColorIdentity();
                    if (commanderColor.isWhite()) {
                        colorIdentity.setWhite(true);
                    }
                    if (commanderColor.isBlue()) {
                        colorIdentity.setBlue(true);
                    }
                    if (commanderColor.isBlack()) {
                        colorIdentity.setBlack(true);
                    }
                    if (commanderColor.isRed()) {
                        colorIdentity.setRed(true);
                    }
                    if (commanderColor.isGreen()) {
                        colorIdentity.setGreen(true);
                    }
                }
            }

            if (commanderNames.size() == 0) {
                invalid.put("Sideboard", "Can't find any oathbreaker");
                valid = false;
            }
            if (signatureSpell == null) {
                invalid.put("Sideboard", "Can't find signature spell");
                valid = false;
            }
        }

        // signature spell color
        for (Card card : deck.getSideboard()) {
            if (card.getName().equals(signatureSpell) && !cardHasValidColor(colorIdentity, card)) {
                invalid.put(card.getName(), "Invalid color for signature spell (" + colorIdentity.toString() + ')');
                valid = false;
            }
        }

        // no needs in cards check on wrong commanders
        if (!valid) {
            return false;
        }

        for (Card card : deck.getCards()) {
            if (!cardHasValidColor(colorIdentity, card)) {
                invalid.put(card.getName(), "Invalid color (" + colorIdentity.toString() + ')');
                valid = false;
            }
        }

        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    invalid.put(card.getName(), "Not allowed Set: " + card.getExpansionSetCode());
                    valid = false;
                }
            }
        }
        return valid;
    }

    public boolean cardHasValidColor(FilterMana commander, Card card) {
        FilterMana cardColor = card.getColorIdentity();
        return !(cardColor.isBlack() && !commander.isBlack()
                || cardColor.isBlue() && !commander.isBlue()
                || cardColor.isGreen() && !commander.isGreen()
                || cardColor.isRed() && !commander.isRed()
                || cardColor.isWhite() && !commander.isWhite());
    }
}
