package mage.deck;

import mage.abilities.Ability;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.filter.FilterMana;
import mage.util.ManaUtil;

import java.util.*;

/**
 * @author spjspj
 */
public class FreeformCommander extends Constructed {

    protected List<String> bannedCommander = new ArrayList<>();
    private static final Map<String, Integer> pdAllowed = new HashMap<>();

    public FreeformCommander() {
        this("Freeform Commander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            setCodes.add(set.getCode());
        }

        // no banned cards
        this.banned.clear();
    }

    public FreeformCommander(String name) {
        super(name);
    }

    @Override
    public int getDeckMinSize() {
        return 98;
    }

    @Override
    public int getSideboardMinSize() {
        return 1;
    }

    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        FilterMana colorIdentity = new FilterMana();

        if (deck.getCards().size() + deck.getSideboard().size() != 100) {
            invalid.put("Deck", "Must contain " + 100 + " cards: has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
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

        if (deck.getSideboard().isEmpty() || deck.getSideboard().size() > 2) {
            invalid.put("Commander", "Sideboard must contain only the commander(s)");
            valid = false;
        } else {
            Set<String> commanderNames = new HashSet<>();
            for (Card commander : deck.getSideboard()) {
                commanderNames.add(commander.getName());
            }
            for (Card commander : deck.getSideboard()) {
                if (!(commander.isCreature()
                        || commander.isLegendary())) {
                    invalid.put("Commander", "For Freeform Commander, the commander must be a creature or be legendary. Yours was: " + commander.getName());
                    valid = false;
                }
                if (deck.getSideboard().size() == 2 && !commander.getAbilities().contains(PartnerAbility.getInstance())) {
                    boolean partnersWith = false;
                    for (Ability ability : commander.getAbilities()) {
                        if (ability instanceof PartnerWithAbility
                                && commanderNames.contains(((PartnerWithAbility) ability).getPartnerName())) {
                            partnersWith = true;
                            break;
                        }
                    }
                    if (!partnersWith) {
                        invalid.put("Commander", "Commander without Partner (" + commander.getName() + ')');
                        valid = false;
                    }
                }
                ManaUtil.collectColorIdentity(colorIdentity, commander.getColorIdentity());
            }
        }

        // no needs in cards check on wrong commanders
        if (!valid) {
            return false;
        }

        for (Card card : deck.getCards()) {
            if (!ManaUtil.isColorIdentityCompatible(colorIdentity, card.getColorIdentity())) {
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
}
