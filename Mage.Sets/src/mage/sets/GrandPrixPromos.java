
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class GrandPrixPromos extends ExpansionSet {

    private static final GrandPrixPromos instance = new GrandPrixPromos();

    public static GrandPrixPromos getInstance() {
        return instance;
    }

    private GrandPrixPromos() {
        super("Grand Prix Promos", "PGPX", ExpansionSet.buildDate(2011, 6, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("All Is Dust", "2013b", Rarity.RARE, mage.cards.a.AllIsDust.class));
        cards.add(new SetCardInfo("Batterskull", 2014, Rarity.MYTHIC, mage.cards.b.Batterskull.class));
        cards.add(new SetCardInfo("Call of the Herd", 2008, Rarity.RARE, mage.cards.c.CallOfTheHerd.class));
        cards.add(new SetCardInfo("Chrome Mox", 2009, Rarity.RARE, mage.cards.c.ChromeMox.class));
        cards.add(new SetCardInfo("Forest", "2018f", Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Goblin Guide", "2012a", Rarity.RARE, mage.cards.g.GoblinGuide.class));
        cards.add(new SetCardInfo("Griselbrand", 2015, Rarity.MYTHIC, mage.cards.g.Griselbrand.class));
        cards.add(new SetCardInfo("Island", "2018b", Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Lotus Cobra", "2012b", Rarity.RARE, mage.cards.l.LotusCobra.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 2011, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Mountain", "2018d", Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Mutavault", 2018, Rarity.RARE, mage.cards.m.Mutavault.class));
        cards.add(new SetCardInfo("Plains", "2018a", Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Primeval Titan", "2013a", Rarity.MYTHIC, mage.cards.p.PrimevalTitan.class));
        cards.add(new SetCardInfo("Progenitus", 2017, Rarity.MYTHIC, mage.cards.p.Progenitus.class));
        cards.add(new SetCardInfo("Spiritmonger", 2007, Rarity.RARE, mage.cards.s.Spiritmonger.class));
        cards.add(new SetCardInfo("Stoneforge Mystic", 2016, Rarity.RARE, mage.cards.s.StoneforgeMystic.class));
        cards.add(new SetCardInfo("Swamp", "2018c", Rarity.LAND, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("Sword of Feast and Famine", "2016b", Rarity.RARE, mage.cards.s.SwordOfFeastAndFamine.class));
        cards.add(new SetCardInfo("Umezawa's Jitte", 2010, Rarity.RARE, mage.cards.u.UmezawasJitte.class));
    }

}
