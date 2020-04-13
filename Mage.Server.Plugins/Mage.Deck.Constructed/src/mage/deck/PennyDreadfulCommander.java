package mage.deck;

import mage.abilities.Ability;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.keyword.CompanionAbility;
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
        Set<Card> commanders = new HashSet<>();
        Card companion = null;

        if (deck.getSideboard().size() == 1) {
            commanders.add(deck.getSideboard().iterator().next());
        } else if (deck.getSideboard().size() == 2) {
            Iterator<Card> iter = deck.getSideboard().iterator();
            Card card1 = iter.next();
            Card card2 = iter.next();
            if (card1.getAbilities().stream().anyMatch(ability -> ability instanceof CompanionAbility)) {
                companion = card1;
                commanders.add(card2);
            } else if (card2.getAbilities().stream().anyMatch(ability -> ability instanceof CompanionAbility)) {
                companion = card2;
                commanders.add(card1);
            } else {
                commanders.add(card1);
                commanders.add(card2);
            }
        } else if (deck.getSideboard().size() == 3) {
            Iterator<Card> iter = deck.getSideboard().iterator();
            Card card1 = iter.next();
            Card card2 = iter.next();
            Card card3 = iter.next();
            if (card1.getAbilities().stream().anyMatch(ability -> ability instanceof CompanionAbility)) {
                companion = card1;
                commanders.add(card2);
                commanders.add(card3);
            } else if (card2.getAbilities().stream().anyMatch(ability -> ability instanceof CompanionAbility)) {
                companion = card2;
                commanders.add(card1);
                commanders.add(card3);
            } else if (card3.getAbilities().stream().anyMatch(ability -> ability instanceof CompanionAbility)) {
                companion = card3;
                commanders.add(card1);
                commanders.add(card2);
            } else {
                invalid.put("Commander", "Sideboard must contain only the commander(s) and up to 1 companion");
                valid = false;
            }
        } else {
            invalid.put("Commander", "Sideboard must contain only the commander(s) and up to 1 companion");
            valid = false;
        }

        if (companion != null && deck.getCards().size() + deck.getSideboard().size() != 101) {
            invalid.put("Deck", "Must contain " + 101 + " cards (companion doesn't count for deck size): has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        } else if (companion == null && deck.getCards().size() + deck.getSideboard().size() != 100) {
            invalid.put("Deck", "Must contain " + 100 + " cards: has " + (deck.getCards().size() + deck.getSideboard().size()) + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        valid = checkCounts(1, counts) && valid;

        generatePennyDreadfulHash();
        for (String wantedCard : counts.keySet()) {
            if (!(pdAllowed.containsKey(wantedCard))) {
                invalid.put(wantedCard, "Banned");
                valid = false;
            }
        }

        Set<String> commanderNames = new HashSet<>();
        for (Card commander : commanders) {
            commanderNames.add(commander.getName());
        }
        for (Card commander : commanders) {
            if (bannedCommander.contains(commander.getName())) {
                invalid.put("Commander", "Commander banned (" + commander.getName() + ')');
                valid = false;
            }
            if ((!commander.isCreature() || !commander.isLegendary())
                    && (!commander.isPlaneswalker() || !commander.getAbilities().contains(CanBeYourCommanderAbility.getInstance()))) {
                invalid.put("Commander", "Commander invalid (" + commander.getName() + ')');
                valid = false;
            }
            if (commanders.size() == 2) {
                if (!commander.getAbilities().contains(PartnerAbility.getInstance())) {
                    boolean partnersWith = commander.getAbilities()
                            .stream()
                            .filter(PartnerWithAbility.class::isInstance)
                            .map(PartnerWithAbility.class::cast)
                            .map(PartnerWithAbility::getPartnerName)
                            .anyMatch(commanderNames::contains);
                    if (!partnersWith) {
                        invalid.put("Commander", "Commander without Partner (" + commander.getName() + ')');
                        valid = false;
                    }
                }
            }
            ManaUtil.collectColorIdentity(colorIdentity, commander.getColorIdentity());
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
        // Check for companion legality
        if (companion != null) {
            Set<Card> cards = new HashSet<>(deck.getCards());
            cards.addAll(commanders);
            for (Ability ability : companion.getAbilities()) {
                if (ability instanceof CompanionAbility) {
                    CompanionAbility companionAbility = (CompanionAbility) ability;
                    if (!companionAbility.isLegal(cards)) {
                        invalid.put(companion.getName(), "Deck invalid for companion");
                        valid = false;
                    }
                    break;
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
