
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author escplan9
 */
public final class WelcomeDeck2017 extends ExpansionSet {

    private static final WelcomeDeck2017 instance = new WelcomeDeck2017();

    public static WelcomeDeck2017 getInstance() {
        return instance;
    }

    private WelcomeDeck2017() {
        super("Welcome Deck 2017", "W17", ExpansionSet.buildDate(2017, 4, 15), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Divine Verdict", 1, Rarity.COMMON, mage.cards.d.DivineVerdict.class));
        cards.add(new SetCardInfo("Glory Seeker", 2, Rarity.COMMON, mage.cards.g.GlorySeeker.class));
        cards.add(new SetCardInfo("Serra Angel", 3, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Standing Troops", 4, Rarity.COMMON, mage.cards.s.StandingTroops.class));
        cards.add(new SetCardInfo("Stormfront Pegasus", 5, Rarity.UNCOMMON, mage.cards.s.StormfrontPegasus.class));
        cards.add(new SetCardInfo("Victory's Herald", 6, Rarity.RARE, mage.cards.v.VictorysHerald.class));
        cards.add(new SetCardInfo("Air Elemental", 7, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Coral Merfolk", 8, Rarity.COMMON, mage.cards.c.CoralMerfolk.class));
        cards.add(new SetCardInfo("Drag Under", 9, Rarity.COMMON, mage.cards.d.DragUnder.class));
        cards.add(new SetCardInfo("Inspiration", 10, Rarity.COMMON, mage.cards.i.Inspiration.class));
        cards.add(new SetCardInfo("Sleep Paralysis", 11, Rarity.COMMON, mage.cards.s.SleepParalysis.class));
        cards.add(new SetCardInfo("Sphinx of Magosi", 12, Rarity.RARE, mage.cards.s.SphinxOfMagosi.class));
        cards.add(new SetCardInfo("Stealer of Secrets", 13, Rarity.COMMON, mage.cards.s.StealerOfSecrets.class));
        cards.add(new SetCardInfo("Tricks of the Trade", 14, Rarity.COMMON, mage.cards.t.TricksOfTheTrade.class));
        cards.add(new SetCardInfo("Bloodhunter Bat", 15, Rarity.COMMON, mage.cards.b.BloodhunterBat.class));
        cards.add(new SetCardInfo("Certain Death", 16, Rarity.COMMON, mage.cards.c.CertainDeath.class));
        cards.add(new SetCardInfo("Nightmare", 17, Rarity.RARE, mage.cards.n.Nightmare.class));
        cards.add(new SetCardInfo("Raise Dead", 18, Rarity.COMMON, mage.cards.r.RaiseDead.class));
        cards.add(new SetCardInfo("Sengir Vampire", 19, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Untamed Hunger", 20, Rarity.COMMON, mage.cards.u.UntamedHunger.class));
        cards.add(new SetCardInfo("Falkenrath Reaver", 21, Rarity.COMMON, mage.cards.f.FalkenrathReaver.class));
        cards.add(new SetCardInfo("Shivan Dragon", 22, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Thundering Giant", 23, Rarity.COMMON, mage.cards.t.ThunderingGiant.class));
        cards.add(new SetCardInfo("Garruk's Horde", 24, Rarity.RARE, mage.cards.g.GarruksHorde.class));
        cards.add(new SetCardInfo("Oakenform", 25, Rarity.COMMON, mage.cards.o.Oakenform.class));
        cards.add(new SetCardInfo("Rabid Bite", 26, Rarity.COMMON, mage.cards.r.RabidBite.class));
        cards.add(new SetCardInfo("Rootwalla", 27, Rarity.COMMON, mage.cards.r.Rootwalla.class));
        cards.add(new SetCardInfo("Stalking Tiger", 28, Rarity.COMMON, mage.cards.s.StalkingTiger.class));
        cards.add(new SetCardInfo("Stampeding Rhino", 29, Rarity.COMMON, mage.cards.s.StampedingRhino.class));
        cards.add(new SetCardInfo("Wing Snare", 30, Rarity.UNCOMMON, mage.cards.w.WingSnare.class));
    }
}
