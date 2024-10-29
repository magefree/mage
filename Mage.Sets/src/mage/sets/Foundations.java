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

        cards.add(new SetCardInfo("Adventuring Gear", 249, Rarity.UNCOMMON, mage.cards.a.AdventuringGear.class));
        cards.add(new SetCardInfo("Ajani's Pridemate", 135, Rarity.UNCOMMON, mage.cards.a.AjanisPridemate.class));
        cards.add(new SetCardInfo("Ajani, Caller of the Pride", 134, Rarity.MYTHIC, mage.cards.a.AjaniCallerOfThePride.class));
        cards.add(new SetCardInfo("Angel of Finality", 136, Rarity.RARE, mage.cards.a.AngelOfFinality.class));
        cards.add(new SetCardInfo("Angelic Edict", 490, Rarity.COMMON, mage.cards.a.AngelicEdict.class));
        cards.add(new SetCardInfo("Anthem of Champions", 116, Rarity.RARE, mage.cards.a.AnthemOfChampions.class));
        cards.add(new SetCardInfo("Arahbo, the First Fang", 2, Rarity.RARE, mage.cards.a.ArahboTheFirstFang.class));
        cards.add(new SetCardInfo("Arcane Epiphany", 29, Rarity.UNCOMMON, mage.cards.a.ArcaneEpiphany.class));
        cards.add(new SetCardInfo("Ball Lightning", 618, Rarity.RARE, mage.cards.b.BallLightning.class));
        cards.add(new SetCardInfo("Balmor, Battlemage Captain", 237, Rarity.UNCOMMON, mage.cards.b.BalmorBattlemageCaptain.class));
        cards.add(new SetCardInfo("Billowing Shriekmass", 56, Rarity.UNCOMMON, mage.cards.b.BillowingShriekmass.class));
        cards.add(new SetCardInfo("Blanchwood Armor", 213, Rarity.UNCOMMON, mage.cards.b.BlanchwoodArmor.class));
        cards.add(new SetCardInfo("Bloodtithe Collector", 516, Rarity.UNCOMMON, mage.cards.b.BloodtitheCollector.class));
        cards.add(new SetCardInfo("Burst Lightning", 192, Rarity.COMMON, mage.cards.b.BurstLightning.class));
        cards.add(new SetCardInfo("Clinquant Skymage", 33, Rarity.UNCOMMON, mage.cards.c.ClinquantSkymage.class));
        cards.add(new SetCardInfo("Cloudblazer", 653, Rarity.UNCOMMON, mage.cards.c.Cloudblazer.class));
        cards.add(new SetCardInfo("Consuming Aberration", 238, Rarity.RARE, mage.cards.c.ConsumingAberration.class));
        cards.add(new SetCardInfo("Crossway Troublemakers", 518, Rarity.RARE, mage.cards.c.CrosswayTroublemakers.class));
        cards.add(new SetCardInfo("Crypt Feaster", 59, Rarity.COMMON, mage.cards.c.CryptFeaster.class));
        cards.add(new SetCardInfo("Darksteel Colossus", 671, Rarity.MYTHIC, mage.cards.d.DarksteelColossus.class));
        cards.add(new SetCardInfo("Day of Judgment", 140, Rarity.RARE, mage.cards.d.DayOfJudgment.class));
        cards.add(new SetCardInfo("Deathmark", 601, Rarity.UNCOMMON, mage.cards.d.Deathmark.class));
        cards.add(new SetCardInfo("Demonic Pact", 602, Rarity.MYTHIC, mage.cards.d.DemonicPact.class));
        cards.add(new SetCardInfo("Devout Decree", 571, Rarity.UNCOMMON, mage.cards.d.DevoutDecree.class));
        cards.add(new SetCardInfo("Disenchant", 572, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Doubling Season", 216, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class));
        cards.add(new SetCardInfo("Duress", 606, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Elspeth's Smite", 493, Rarity.UNCOMMON, mage.cards.e.ElspethsSmite.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 219, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Elvish Regrower", 104, Rarity.UNCOMMON, mage.cards.e.ElvishRegrower.class));
        cards.add(new SetCardInfo("Empyrean Eagle", 239, Rarity.UNCOMMON, mage.cards.e.EmpyreanEagle.class));
        cards.add(new SetCardInfo("Erudite Wizard", 37, Rarity.COMMON, mage.cards.e.EruditeWizard.class));
        cards.add(new SetCardInfo("Etali, Primal Storm", 194, Rarity.RARE, mage.cards.e.EtaliPrimalStorm.class));
        cards.add(new SetCardInfo("Faebloom Trick", 38, Rarity.UNCOMMON, mage.cards.f.FaebloomTrick.class));
        cards.add(new SetCardInfo("Felidar Savior", 12, Rarity.COMMON, mage.cards.f.FelidarSavior.class));
        cards.add(new SetCardInfo("Fiery Annihilation", 86, Rarity.UNCOMMON, mage.cards.f.FieryAnnihilation.class));
        cards.add(new SetCardInfo("Flashfreeze", 590, Rarity.UNCOMMON, mage.cards.f.Flashfreeze.class));
        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giada, Font of Hope", 298, Rarity.RARE, mage.cards.g.GiadaFontOfHope.class));
        cards.add(new SetCardInfo("Giant Growth", 223, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gigantosaurus", 718, Rarity.RARE, mage.cards.g.Gigantosaurus.class));
        cards.add(new SetCardInfo("Gilded Lotus", 725, Rarity.RARE, mage.cards.g.GildedLotus.class));
        cards.add(new SetCardInfo("Grappling Kraken", 39, Rarity.UNCOMMON, mage.cards.g.GrapplingKraken.class));
        cards.add(new SetCardInfo("Halana and Alena, Partners", 659, Rarity.RARE, mage.cards.h.HalanaAndAlenaPartners.class));
        cards.add(new SetCardInfo("Harmless Offering", 625, Rarity.RARE, mage.cards.h.HarmlessOffering.class));
        cards.add(new SetCardInfo("Heartfire Immolator", 201, Rarity.UNCOMMON, mage.cards.h.HeartfireImmolator.class));
        cards.add(new SetCardInfo("Helpful Hunter", 16, Rarity.COMMON, mage.cards.h.HelpfulHunter.class));
        cards.add(new SetCardInfo("Herald of Eternal Dawn", 17, Rarity.MYTHIC, mage.cards.h.HeraldOfEternalDawn.class));
        cards.add(new SetCardInfo("Hero's Downfall", 175, Rarity.UNCOMMON, mage.cards.h.HerosDownfall.class));
        cards.add(new SetCardInfo("Hidetsugu's Second Rite", 202, Rarity.UNCOMMON, mage.cards.h.HidetsugusSecondRite.class));
        cards.add(new SetCardInfo("Highborn Vampire", 522, Rarity.COMMON, mage.cards.h.HighbornVampire.class));
        cards.add(new SetCardInfo("Homunculus Horde", 41, Rarity.RARE, mage.cards.h.HomunculusHorde.class));
        cards.add(new SetCardInfo("Imprisoned in the Moon", 156, Rarity.RARE, mage.cards.i.ImprisonedInTheMoon.class));
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
        cards.add(new SetCardInfo("Muldrotha, the Gravetide", 243, Rarity.MYTHIC, mage.cards.m.MuldrothaTheGravetide.class));
        cards.add(new SetCardInfo("Mystical Teachings", 594, Rarity.UNCOMMON, mage.cards.m.MysticalTeachings.class));
        cards.add(new SetCardInfo("Negate", 710, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nessian Hornbeetle", 229, Rarity.UNCOMMON, mage.cards.n.NessianHornbeetle.class));
        cards.add(new SetCardInfo("Niv-Mizzet, Visionary", 123, Rarity.MYTHIC, mage.cards.n.NivMizzetVisionary.class));
        cards.add(new SetCardInfo("Offer Immortality", 525, Rarity.COMMON, mage.cards.o.OfferImmortality.class));
        cards.add(new SetCardInfo("Omniscience", 161, Rarity.MYTHIC, mage.cards.o.Omniscience.class));
        cards.add(new SetCardInfo("Pacifism", 501, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Painful Quandary", 179, Rarity.RARE, mage.cards.p.PainfulQuandary.class));
        cards.add(new SetCardInfo("Phyrexian Arena", 180, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prideful Parent", 21, Rarity.COMMON, mage.cards.p.PridefulParent.class));
        cards.add(new SetCardInfo("Progenitus", 244, Rarity.MYTHIC, mage.cards.p.Progenitus.class));
        cards.add(new SetCardInfo("Pyromancer's Goggles", 677, Rarity.MYTHIC, mage.cards.p.PyromancersGoggles.class));
        cards.add(new SetCardInfo("Refute", 48, Rarity.COMMON, mage.cards.r.Refute.class));
        cards.add(new SetCardInfo("Rogue's Passage", 264, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Savannah Lions", 146, Rarity.UNCOMMON, mage.cards.s.SavannahLions.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 232, Rarity.RARE, mage.cards.s.ScavengingOoze.class));
        cards.add(new SetCardInfo("Searslicer Goblin", 93, Rarity.RARE, mage.cards.s.SearslicerGoblin.class));
        cards.add(new SetCardInfo("Seeker's Folly", 69, Rarity.UNCOMMON, mage.cards.s.SeekersFolly.class));
        cards.add(new SetCardInfo("Seismic Rupture", 205, Rarity.UNCOMMON, mage.cards.s.SeismicRupture.class));
        cards.add(new SetCardInfo("Serra Angel", 147, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shivan Dragon", 206, Rarity.UNCOMMON, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Skyship Buccaneer", 50, Rarity.UNCOMMON, mage.cards.s.SkyshipBuccaneer.class));
        cards.add(new SetCardInfo("Slagstorm", 207, Rarity.RARE, mage.cards.s.Slagstorm.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 257, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Sorcerous Spyglass", 679, Rarity.UNCOMMON, mage.cards.s.SorcerousSpyglass.class));
        cards.add(new SetCardInfo("Soul-Guide Lantern", 680, Rarity.UNCOMMON, mage.cards.s.SoulGuideLantern.class));
        cards.add(new SetCardInfo("Stroke of Midnight", 148, Rarity.UNCOMMON, mage.cards.s.StrokeOfMidnight.class));
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
