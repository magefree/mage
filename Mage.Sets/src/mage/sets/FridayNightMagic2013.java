package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f13
 */
public class FridayNightMagic2013 extends ExpansionSet {

    private static final FridayNightMagic2013 instance = new FridayNightMagic2013();

    public static FridayNightMagic2013 getInstance() {
        return instance;
    }

    private FridayNightMagic2013() {
        super("Friday Night Magic 2013", "F13", ExpansionSet.buildDate(2013, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Call of the Conclave", 4, Rarity.RARE, mage.cards.c.CallOfTheConclave.class));
        cards.add(new SetCardInfo("Dimir Charm", 8, Rarity.RARE, mage.cards.d.DimirCharm.class));
        cards.add(new SetCardInfo("Experiment One", 9, Rarity.RARE, mage.cards.e.ExperimentOne.class));
        cards.add(new SetCardInfo("Farseek", 3, Rarity.RARE, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Ghor-Clan Rampager", 10, Rarity.RARE, mage.cards.g.GhorClanRampager.class));
        cards.add(new SetCardInfo("Grisly Salvage", 11, Rarity.RARE, mage.cards.g.GrislySalvage.class));
        cards.add(new SetCardInfo("Izzet Charm", 6, Rarity.RARE, mage.cards.i.IzzetCharm.class));
        cards.add(new SetCardInfo("Judge's Familiar", 5, Rarity.RARE, mage.cards.j.JudgesFamiliar.class));
        cards.add(new SetCardInfo("Rakdos Cackler", 7, Rarity.RARE, mage.cards.r.RakdosCackler.class));
        cards.add(new SetCardInfo("Reliquary Tower", 2, Rarity.RARE, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Searing Spear", 1, Rarity.RARE, mage.cards.s.SearingSpear.class));
        cards.add(new SetCardInfo("Sin Collector", 12, Rarity.RARE, mage.cards.s.SinCollector.class));
     }
}
