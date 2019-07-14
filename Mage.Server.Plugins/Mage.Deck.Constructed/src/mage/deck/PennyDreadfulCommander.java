package mage.deck;

import mage.abilities.Ability;
import mage.abilities.common.CanBeYourCommanderAbility;
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
import java.util.Map.Entry;

/**
 * @author spjspj
 */
public class PennyDreadfulCommander extends Constructed {

    protected List<String> bannedCommander = new ArrayList<>();
    private static final Map<String, Integer> pdAllowed = new HashMap<>();
    private static boolean setupAllowed = false;

    public PennyDreadfulCommander() {
        this("Penny Dreadful Commander");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
    }

    public PennyDreadfulCommander(String name) {
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

        List<String> basicLandNames = new ArrayList<>(Arrays.asList("Forest", "Island", "Mountain", "Swamp", "Plains", "Wastes"));
        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                if (!basicLandNames.contains(entry.getKey())) {
                    invalid.put(entry.getKey(), "Too many: " + entry.getValue());
                    valid = false;
                }
            }
        }

        generatePennyDreadfulHash();
        for (String wantedCard : counts.keySet()) {
            if (!(pdAllowed.containsKey(wantedCard))) {
                invalid.put(wantedCard, "Banned");
                valid = false;
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
                if ((!commander.isCreature() || !commander.isLegendary())
                        && (!commander.isPlaneswalker() || !commander.getAbilities().contains(CanBeYourCommanderAbility.getInstance()))) {
                    invalid.put("Commander", "Commander invalid (" + commander.getName() + ')');
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
        for (Card card : deck.getCards()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    invalid.put(card.getName(), "Not allowed Set: " + card.getExpansionSetCode());
                    valid = false;
                }
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

    public void generatePennyDreadfulHash() {
        if (setupAllowed == false) {
            setupAllowed = true;
        } else {
            return;
        }

        Properties properties = new Properties();
        try {
            properties.load(PennyDreadfulCommander.class.getResourceAsStream("pennydreadful.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (final Entry<Object, Object> entry : properties.entrySet()) {
            pdAllowed.put((String) entry.getKey(), 1);
        }
    }
}
