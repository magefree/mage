package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Foundations extends ExpansionSet {

    private static final Foundations instance = new Foundations();

    public static Foundations getInstance() {
        return instance;
    }

    private Foundations() {
        super("Foundations", "FDN", ExpansionSet.buildDate(2024, 11, 15), SetType.EXPANSION);
        this.blockName = "Foundations"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Ajani's Pridemate", 135, Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Ajani, Caller of the Pride", 134, Rarity.MYTHIC, mage.cards.a.AjaniCallerOfThePride.class));
        cards.add(new SetCardInfo("Angelic Edict", 490, Rarity.COMMON, mage.cards.a.AngelicEdict.class));
        cards.add(new SetCardInfo("Anthem of Champions", 116, Rarity.RARE, mage.cards.a.AnthemOfChampions.class));
        cards.add(new SetCardInfo("Arcane Epiphany", 29, Rarity.UNCOMMON, mage.cards.a.ArcaneEpiphany.class));
        cards.add(new SetCardInfo("Ball Lightning", 618, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Bloodtithe Collector", 516, Rarity.UNCOMMON, mage.cards.b.BloodtitheCollector.class));
        cards.add(new SetCardInfo("Cloudblazer", 653, Rarity.UNCOMMON, mage.cards.c.Cloudblazer.class));
        cards.add(new SetCardInfo("Crossway Troublemakers", 518, Rarity.RARE, mage.cards.c.CrosswayTroublemakers.class));
        cards.add(new SetCardInfo("Crypt Feaster", 59, Rarity.COMMON, mage.cards.c.CryptFeaster.class));
        cards.add(new SetCardInfo("Darksteel Colossus", 671, Rarity.MYTHIC, mage.cards.d.DarksteelColossus.class));
        cards.add(new SetCardInfo("Day of Judgment", 140, Rarity.RARE, mage.cards.d.DayOfJudgment.class));
        cards.add(new SetCardInfo("Deathmark", 601, Rarity.UNCOMMON, mage.cards.d.Deathmark.class));
        cards.add(new SetCardInfo("Demonic Pact", 602, Rarity.MYTHIC, mage.cards.d.DemonicPact.class));
        cards.add(new SetCardInfo("Devout Decree", 571, Rarity.UNCOMMON, mage.cards.d.DevoutDecree.class));
        cards.add(new SetCardInfo("Disenchant", 572, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Duress", 606, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Elspeth's Smite", 493, Rarity.UNCOMMON, mage.cards.e.ElspethsSmite.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 219, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Felidar Savior", 12, Rarity.COMMON, mage.cards.f.FelidarSavior.class));
        cards.add(new SetCardInfo("Flashfreeze", 590, Rarity.UNCOMMON, mage.cards.f.Flashfreeze.class));
        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Growth", 223, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gigantosaurus", 718, Rarity.RARE, mage.cards.g.Gigantosaurus.class));
        cards.add(new SetCardInfo("Gilded Lotus", 725, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Halana and Alena, Partners", 659, Rarity.RARE, mage.cards.h.HalanaAndAlenaPartners.class));
        cards.add(new SetCardInfo("Harmless Offering", 625, Rarity.RARE, mage.cards.h.HarmlessOffering.class));
        cards.add(new SetCardInfo("Helpful Hunter", 16, Rarity.COMMON, mage.cards.h.HelpfulHunter.class));
        cards.add(new SetCardInfo("Hero's Downfall", 175, Rarity.UNCOMMON, mage.cards.h.HerosDownfall.class));
        cards.add(new SetCardInfo("Hidetsugu's Second Rite", 202, Rarity.UNCOMMON, mage.cards.h.HidetsugusSecondRite.class));
        cards.add(new SetCardInfo("Highborn Vampire", 522, Rarity.COMMON, mage.cards.h.HighbornVampire.class));
        cards.add(new SetCardInfo("Ingenious Leonin", 495, Rarity.UNCOMMON, mage.cards.i.IngeniousLeonin.class));
        cards.add(new SetCardInfo("Island", 274, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jazal Goldmane", 497, Rarity.RARE, mage.cards.j.JazalGoldmane.class));
        cards.add(new SetCardInfo("Juggernaut", 255, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Leonin Skyhunter", 498, Rarity.UNCOMMON, mage.cards.l.LeoninSkyhunter.class));
        cards.add(new SetCardInfo("Leonin Vanguard", 499, Rarity.UNCOMMON, mage.cards.l.LeoninVanguard.class));
        cards.add(new SetCardInfo("Lightshell Duo", 157, Rarity.COMMON, mage.cards.l.LightshellDuo.class));
        cards.add(new SetCardInfo("Liliana, Dreadhorde General", 176, Rarity.MYTHIC, mage.cards.l.LilianaDreadhordeGeneral.class));
        cards.add(new SetCardInfo("Llanowar Elves", 227, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lyra Dawnbringer", 707, Rarity.MYTHIC, mage.cards.l.LyraDawnbringer.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 661, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Mindsparker", 628, Rarity.UNCOMMON, mage.cards.m.Mindsparker.class));
        cards.add(new SetCardInfo("Mold Adder", 640, Rarity.UNCOMMON, mage.cards.m.MoldAdder.class));
        cards.add(new SetCardInfo("Moment of Craving", 524, Rarity.COMMON, mage.cards.m.MomentOfCraving.class));
        cards.add(new SetCardInfo("Moment of Triumph", 500, Rarity.COMMON, mage.cards.m.MomentOfTriumph.class));
        cards.add(new SetCardInfo("Mossborn Hydra", 107, Rarity.RARE, mage.cards.m.MossbornHydra.class));
        cards.add(new SetCardInfo("Mountain", 278, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystical Teachings", 594, Rarity.UNCOMMON, mage.cards.m.MysticalTeachings.class));
        cards.add(new SetCardInfo("Negate", 710, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Offer Immortality", 525, Rarity.COMMON, mage.cards.o.OfferImmortality.class));
        cards.add(new SetCardInfo("Omniscience", 161, Rarity.MYTHIC, mage.cards.o.Omniscience.class));
        cards.add(new SetCardInfo("Pacifism", 501, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Phyrexian Arena", 180, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prideful Parent", 21, Rarity.COMMON, mage.cards.p.PridefulParent.class));
        cards.add(new SetCardInfo("Progenitus", 244, Rarity.MYTHIC, mage.cards.p.Progenitus.class));
        cards.add(new SetCardInfo("Pyromancer's Goggles", 677, Rarity.MYTHIC, mage.cards.p.PyromancersGoggles.class));
        cards.add(new SetCardInfo("Refute", 48, Rarity.COMMON, mage.cards.r.Refute.class));
        cards.add(new SetCardInfo("Savannah Lions", 146, Rarity.UNCOMMON, mage.cards.s.SavannahLions.class));
        cards.add(new SetCardInfo("Searslicer Goblin", 93, Rarity.RARE, mage.cards.s.SearslicerGoblin.class));
        cards.add(new SetCardInfo("Serra Angel", 147, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shivan Dragon", 206, Rarity.UNCOMMON, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 257, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Sorcerous Spyglass", 679, Rarity.UNCOMMON, mage.cards.s.SorcerousSpyglass.class));
        cards.add(new SetCardInfo("Soul-Guide Lantern", 680, Rarity.UNCOMMON, mage.cards.s.SoulGuideLantern.class));
        cards.add(new SetCardInfo("Stromkirk Bloodthief", 185, Rarity.UNCOMMON, mage.cards.s.StromkirkBloodthief.class));
        cards.add(new SetCardInfo("Swamp", 276, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Abandon", 696, Rarity.RARE, mage.cards.t.TempleOfAbandon.class));
        cards.add(new SetCardInfo("Temple of Deceit", 697, Rarity.RARE, mage.cards.t.TempleOfDeceit.class));
        cards.add(new SetCardInfo("Temple of Enlightenment", 698, Rarity.RARE, mage.cards.t.TempleOfEnlightenment.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 699, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class));
        cards.add(new SetCardInfo("Temple of Malady", 700, Rarity.RARE, mage.cards.t.TempleOfMalady.class));
        cards.add(new SetCardInfo("Temple of Malice", 701, Rarity.RARE, mage.cards.t.TempleOfMalice.class));
        cards.add(new SetCardInfo("Temple of Mystery", 702, Rarity.RARE, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("Temple of Plenty", 703, Rarity.RARE, mage.cards.t.TempleOfPlenty.class));
        cards.add(new SetCardInfo("Temple of Silence", 704, Rarity.RARE, mage.cards.t.TempleOfSilence.class));
        cards.add(new SetCardInfo("Temple of Triumph", 705, Rarity.RARE, mage.cards.t.TempleOfTriumph.class));
        cards.add(new SetCardInfo("Think Twice", 165, Rarity.COMMON, mage.cards.t.ThinkTwice.class));
        cards.add(new SetCardInfo("Time Stop", 166, Rarity.RARE, mage.cards.t.TimeStop.class));
        cards.add(new SetCardInfo("Uncharted Haven", 564, Rarity.COMMON, mage.cards.u.UnchartedHaven.class));
        cards.add(new SetCardInfo("Unsummon", 599, Rarity.COMMON, mage.cards.u.Unsummon.class));
        cards.add(new SetCardInfo("Untamed Hunger", 529, Rarity.COMMON, mage.cards.u.UntamedHunger.class));
        cards.add(new SetCardInfo("Vampire Interloper", 530, Rarity.COMMON, mage.cards.v.VampireInterloper.class));
        cards.add(new SetCardInfo("Vampire Neonate", 531, Rarity.COMMON, mage.cards.v.VampireNeonate.class));
        cards.add(new SetCardInfo("Vampire Spawn", 532, Rarity.COMMON, mage.cards.v.VampireSpawn.class));
        cards.add(new SetCardInfo("Vengeful Bloodwitch", 76, Rarity.UNCOMMON, mage.cards.v.VengefulBloodwitch.class));
        cards.add(new SetCardInfo("Vivien Reid", 234, Rarity.MYTHIC, mage.cards.v.VivienReid.class));
    }
}
