package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/dkm
 */
public class Deckmasters extends ExpansionSet {

    private static final Deckmasters instance = new Deckmasters();

    public static Deckmasters getInstance() {
        return instance;
    }

    private Deckmasters() {
        super("Deckmasters", "DKM", ExpansionSet.buildDate(2001, 12, 1), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Abyssal Specter", 1, Rarity.UNCOMMON, mage.cards.a.AbyssalSpecter.class, RETRO_ART));
        cards.add(new SetCardInfo("Balduvian Bears", 22, Rarity.COMMON, mage.cards.b.BalduvianBears.class, RETRO_ART));
        cards.add(new SetCardInfo("Balduvian Horde", 10, Rarity.RARE, mage.cards.b.BalduvianHorde.class, RETRO_ART));
        cards.add(new SetCardInfo("Barbed Sextant", 34, Rarity.COMMON, mage.cards.b.BarbedSextant.class, RETRO_ART));
        cards.add(new SetCardInfo("Bounty of the Hunt", 23, Rarity.UNCOMMON, mage.cards.b.BountyOfTheHunt.class, RETRO_ART));
        cards.add(new SetCardInfo("Contagion", 2, Rarity.UNCOMMON, mage.cards.c.Contagion.class, RETRO_ART));
        cards.add(new SetCardInfo("Dark Banishing", 3, Rarity.COMMON, mage.cards.d.DarkBanishing.class, RETRO_ART));
        cards.add(new SetCardInfo("Dark Ritual", 4, Rarity.COMMON, mage.cards.d.DarkRitual.class, RETRO_ART));
        cards.add(new SetCardInfo("Death Spark", 11, Rarity.UNCOMMON, mage.cards.d.DeathSpark.class, RETRO_ART));
        cards.add(new SetCardInfo("Elkin Bottle", 35, Rarity.RARE, mage.cards.e.ElkinBottle.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Bard", 24, Rarity.UNCOMMON, mage.cards.e.ElvishBard.class, RETRO_ART));
        cards.add(new SetCardInfo("Folk of the Pines", 25, Rarity.COMMON, mage.cards.f.FolkOfThePines.class, RETRO_ART));
        cards.add(new SetCardInfo("Forest", 48, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 49, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 50, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Foul Familiar", 5, Rarity.COMMON, mage.cards.f.FoulFamiliar.class, RETRO_ART));
        cards.add(new SetCardInfo("Fyndhorn Elves", 26, Rarity.COMMON, mage.cards.f.FyndhornElves.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Growth", 27, Rarity.COMMON, mage.cards.g.GiantGrowth.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Trap Door Spider", 33, Rarity.UNCOMMON, mage.cards.g.GiantTrapDoorSpider.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Mutant", 12, Rarity.UNCOMMON, mage.cards.g.GoblinMutant.class, RETRO_ART));
        cards.add(new SetCardInfo("Guerrilla Tactics", "13a", Rarity.COMMON, mage.cards.g.GuerrillaTactics.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Guerrilla Tactics", "13b", Rarity.COMMON, mage.cards.g.GuerrillaTactics.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Hurricane", 28, Rarity.UNCOMMON, mage.cards.h.Hurricane.class, RETRO_ART));
        cards.add(new SetCardInfo("Icy Manipulator", 36, Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Icy Manipulator", "36*", Rarity.UNCOMMON, mage.cards.i.IcyManipulator.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Incinerate", 14, Rarity.COMMON, mage.cards.i.Incinerate.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Incinerate", "14*", Rarity.COMMON, mage.cards.i.Incinerate.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Jokulhaups", 15, Rarity.RARE, mage.cards.j.Jokulhaups.class, RETRO_ART));
        cards.add(new SetCardInfo("Karplusan Forest", 39, Rarity.RARE, mage.cards.k.KarplusanForest.class, RETRO_ART));
        cards.add(new SetCardInfo("Lhurgoyf", 29, Rarity.RARE, mage.cards.l.Lhurgoyf.class, RETRO_ART));
        cards.add(new SetCardInfo("Lim-Dul's High Guard", "6a", Rarity.COMMON, mage.cards.l.LimDulsHighGuard.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lim-Dul's High Guard", "6b", Rarity.COMMON, mage.cards.l.LimDulsHighGuard.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 45, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 46, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 47, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Necropotence", 7, Rarity.RARE, mage.cards.n.Necropotence.class, RETRO_ART));
        cards.add(new SetCardInfo("Orcish Cannoneers", 17, Rarity.UNCOMMON, mage.cards.o.OrcishCannoneers.class, RETRO_ART));
        cards.add(new SetCardInfo("Phantasmal Fiend", "8a", Rarity.COMMON, mage.cards.p.PhantasmalFiend.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Phantasmal Fiend", "8b", Rarity.COMMON, mage.cards.p.PhantasmalFiend.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian War Beast", "37a", Rarity.COMMON, mage.cards.p.PhyrexianWarBeast.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian War Beast", "37b", Rarity.COMMON, mage.cards.p.PhyrexianWarBeast.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Pillage", 18, Rarity.UNCOMMON, mage.cards.p.Pillage.class, RETRO_ART));
        cards.add(new SetCardInfo("Pyroclasm", 19, Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class, RETRO_ART));
        cards.add(new SetCardInfo("Shatter", 20, Rarity.COMMON, mage.cards.s.Shatter.class, RETRO_ART));
        cards.add(new SetCardInfo("Soul Burn", 9, Rarity.COMMON, mage.cards.s.SoulBurn.class, RETRO_ART));
        cards.add(new SetCardInfo("Storm Shaman", "21a", Rarity.COMMON, mage.cards.s.StormShaman.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Storm Shaman", "21b", Rarity.COMMON, mage.cards.s.StormShaman.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Sulfurous Springs", 40, Rarity.RARE, mage.cards.s.SulfurousSprings.class, RETRO_ART));
        cards.add(new SetCardInfo("Swamp", 42, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 43, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 44, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Underground River", 41, Rarity.RARE, mage.cards.u.UndergroundRiver.class, RETRO_ART));
        cards.add(new SetCardInfo("Walking Wall", 38, Rarity.UNCOMMON, mage.cards.w.WalkingWall.class, RETRO_ART));
        cards.add(new SetCardInfo("Woolly Spider", 30, Rarity.COMMON, mage.cards.w.WoollySpider.class, RETRO_ART));
        cards.add(new SetCardInfo("Yavimaya Ancients", "31a", Rarity.COMMON, mage.cards.y.YavimayaAncients.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Yavimaya Ancients", "31b", Rarity.COMMON, mage.cards.y.YavimayaAncients.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Yavimaya Ants", 32, Rarity.UNCOMMON, mage.cards.y.YavimayaAnts.class, RETRO_ART));
    }
}
