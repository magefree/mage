package mage.deck;

import mage.abilities.Ability;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidatorErrorType;
import mage.constants.CardType;
import mage.filter.FilterMana;
import mage.util.ManaUtil;

import java.util.*;

/**
 * @author JayDi85
 */
public class Oathbreaker extends Vintage {

    protected List<String> bannedCommander = new ArrayList<>();
    private static final Map<String, Integer> pdAllowed = new HashMap<>();

    public Oathbreaker() {
        super();
        setName("Oathbreaker");

        // banned = vintage + oathbreaker's list: https://oathbreakermtg.org/banned-list/
        banned.add("Ad Nauseam");
        banned.add("Ancestral Recall");
        banned.add("Balance");
        banned.add("Biorhythm");
        banned.add("Black Lotus");
        banned.add("Channel");
        banned.add("Chaos Orb");
        banned.add("Cleanse");
        banned.add("Crusade");
        banned.add("Dark Ritual");
        banned.add("Doomsday");
        banned.add("Emrakul, the Aeons Torn");
        banned.add("Expropriate");
        banned.add("Falling Star");
        banned.add("Fastbond");
        banned.add("Gifts Ungiven");
        banned.add("Griselbrand");
        banned.add("High Tide");
        banned.add("Imprison");
        banned.add("Invoke Prejudice");
        banned.add("Jihad");
        banned.add("Jeweled Lotus");
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
        banned.add("Pradesh Gypsies");
        banned.add("Primal Surge");
        banned.add("Saheeli, the Gifted");
        banned.add("Shahrazad");
        banned.add("Sol Ring");
        banned.add("Stone-Throwing Devils");
        banned.add("Sundering Titan");
        banned.add("Sylvan Primordial");
        banned.add("Time Vault");
        banned.add("Time Walk");
        banned.add("Tinker");
        banned.add("Tolarian Academy");
        banned.add("Tooth and Nail");
        banned.add("Trade Secrets");
        banned.add("Upheaval");
        banned.add("Yawgmoth's Bargain");
    }

    @Override
    public int getDeckMinSize() {
        return 60 - (2 + 2); // 2 x spells + 2 x partner oathbreakers
    }

    @Override
    public int getSideboardMinSize() {
        return 2; // spell + oathbreaker
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        errorsList.clear();

        if (deck.getCards().size() + deck.getSideboard().size() != 60) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain " + 60 + " cards: has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());

        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                addError(DeckValidatorErrorType.BANNED, bannedCard, "Banned", true);
                valid = false;
            }
        }

        valid = checkCounts(1, counts) && valid;

        Set<String> commanderNames = new HashSet<>();
        Set<String> signatureSpells = new HashSet<>();
        FilterMana allCommandersColor = new FilterMana();
        if (deck.getSideboard().size() < 2 || deck.getSideboard().size() > 4) {
            addError(DeckValidatorErrorType.PRIMARY, "Oathbreaker", "Sideboard must contain only 2 or 4 cards (oathbreaker + signature spell)");
            valid = false;
        } else {
            // collect data
            for (Card commander : deck.getSideboard()) {
                if (commander.isInstantOrSorcery()) {
                    signatureSpells.add(commander.getName());
                } else {
                    if (commander.hasCardTypeForDeckbuilding(CardType.PLANESWALKER)) {
                        commanderNames.add(commander.getName());

                        // color identity from commanders only, not spell
                        ManaUtil.collectColorIdentity(allCommandersColor, commander.getColorIdentity());
                    } else {
                        addError(DeckValidatorErrorType.PRIMARY, commander.getName(), "Oathbreaker (only planeswalker can be Oathbreaker, not " + commander.getName(), true);
                        valid = false;
                    }
                }
            }

            // check size (1+1 or 2+2 allows)
            if (commanderNames.isEmpty() || commanderNames.size() > 2) {
                addError(DeckValidatorErrorType.PRIMARY, "Oathbreaker", "Sideboard must contains 1 or 2 oathbreakers, but found: " + commanderNames.size());
                valid = false;
            }
            if (signatureSpells.isEmpty() || signatureSpells.size() > 2) {
                addError(DeckValidatorErrorType.PRIMARY, "Signature Spell", "Sideboard must contains 1 or 2 signature spells, but found: " + signatureSpells.size());
                valid = false;
            }
            if (signatureSpells.size() != commanderNames.size()) {
                addError(DeckValidatorErrorType.PRIMARY, "Oathbreaker", "Sideboard must contains 1 + 1 or 2 + 2 cards, but found: " + commanderNames.size() + " + " + signatureSpells.size());
                valid = false;
            }

            // check partners
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
                            addError(DeckValidatorErrorType.PRIMARY, commander.getName(), "Oathbreaker without Partner (" + commander.getName() + ')', true);
                            valid = false;
                        }
                    }
                }
            }

            // check spell color (one spell must be used by one oathbreaker)
            // xmage doesn't allows to select pairs of spell + oathbreaker, what's why it requires one color combo minimum
            for (Card spell : deck.getSideboard()) {
                if (signatureSpells.contains(spell.getName())) {
                    FilterMana spellColor = spell.getColorIdentity();
                    boolean haveSameColor = false;
                    for (Card commander : deck.getSideboard()) {
                        if (commanderNames.contains(commander.getName())) {
                            FilterMana commanderColor = commander.getColorIdentity();
                            if (ManaUtil.isColorIdentityCompatible(commanderColor, spellColor)) {
                                haveSameColor = true;
                            }
                        }
                    }
                    if (!haveSameColor) {
                        addError(DeckValidatorErrorType.PRIMARY, spell.getName(), "Signature Spell (can't find oathbreaker with compatible color identity: " + spell.getName() + " - " + spellColor + ")", true);
                        valid = false;
                    }
                }
            }
        }

        // no needs in cards check on wrong commanders
        if (!valid) {
            return false;
        }

        for (Card card : deck.getCards()) {
            if (!ManaUtil.isColorIdentityCompatible(allCommandersColor, card.getColorIdentity())) {
                addError(DeckValidatorErrorType.OTHER, card.getName(), "Invalid color (" + card.getColorIdentity() + ')', true);
                valid = false;
            }
        }

        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    addError(DeckValidatorErrorType.WRONG_SET, card.getName(), "Not allowed Set: " + card.getExpansionSetCode(), true);
                    valid = false;
                }
            }
        }
        return valid;
    }
}
