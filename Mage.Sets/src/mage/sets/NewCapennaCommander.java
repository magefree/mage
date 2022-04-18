package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class NewCapennaCommander extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Henzie \"Toolbox\" Torre");

    private static final NewCapennaCommander instance = new NewCapennaCommander();

    public static NewCapennaCommander getInstance() {
        return instance;
    }

    private NewCapennaCommander() {
        super("New Capenna Commander", "NCC", ExpansionSet.buildDate(2022, 4, 29), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bennie Bracks, Zoologist", 86, Rarity.MYTHIC, mage.cards.b.BennieBracksZoologist.class));
        cards.add(new SetCardInfo("Damning Verdict", 15, Rarity.RARE, mage.cards.d.DamningVerdict.class));
        cards.add(new SetCardInfo("Kitt Kanto, Mayhem Diva", 4, Rarity.MYTHIC, mage.cards.k.KittKantoMayhemDiva.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when shield counters are implemented
    }
}
