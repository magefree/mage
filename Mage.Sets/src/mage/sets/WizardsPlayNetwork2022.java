package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pw22
 */
public class WizardsPlayNetwork2022 extends ExpansionSet {

    private static final WizardsPlayNetwork2022 instance = new WizardsPlayNetwork2022();

    public static WizardsPlayNetwork2022 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2022() {
        super("Wizards Play Network 2022", "PW22", ExpansionSet.buildDate(2022, 3, 5), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Atsushi, the Blazing Sky", 3, Rarity.MYTHIC, mage.cards.a.AtsushiTheBlazingSky.class));
        cards.add(new SetCardInfo("Consider", 1, Rarity.RARE, mage.cards.c.Consider.class));
        cards.add(new SetCardInfo("Dismember", 5, Rarity.RARE, mage.cards.d.Dismember.class));
        cards.add(new SetCardInfo("Fateful Absence", 2, Rarity.RARE, mage.cards.f.FatefulAbsence.class));
        cards.add(new SetCardInfo("Psychosis Crawler", 6, Rarity.RARE, mage.cards.p.PsychosisCrawler.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 4, Rarity.RARE, mage.cards.s.SwiftfootBoots.class));
    }
}
