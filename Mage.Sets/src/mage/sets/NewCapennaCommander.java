package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class NewCapennaCommander extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Kros, Defense Contractor", "Perrie, the Pulveriser");
    private static final NewCapennaCommander instance = new NewCapennaCommander();

    public static NewCapennaCommander getInstance() {
        return instance;
    }

    private NewCapennaCommander() {
        super("New Capenna Commander", "NCC", ExpansionSet.buildDate(2022, 4, 29), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when shield counters are implemented
    }
}
