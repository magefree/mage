package mage.sets;

import mage.cards.ExpansionSet;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AlaraReborn extends ExpansionSet {

    private static final AlaraReborn instance = new AlaraReborn();

    public static AlaraReborn getInstance() {
        return instance;
    }

    private AlaraReborn() {
        super("Alara Reborn", "ARB", ExpansionSet.buildDate(2009, 3, 25), SetType.EXPANSION);
        this.blockName = "Shards of Alara";
        this.parentSet = ShardsOfAlara.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.hasOnlyMulticolorCards = true;
        cards.add(new SetCardInfo("Anathemancer", 33, Rarity.UNCOMMON, mage.cards.a.Anathemancer.class));
        cards.add(new SetCardInfo("Architects of Will", 17, Rarity.COMMON, mage.cards.a.ArchitectsOfWill.class));
        cards.add(new SetCardInfo("Ardent Plea", 1, Rarity.UNCOMMON, mage.cards.a.ArdentPlea.class));
        cards.add(new SetCardInfo("Arsenal Thresher", 131, Rarity.COMMON, mage.cards.a.ArsenalThresher.class));
        cards.add(new SetCardInfo("Aven Mimeomancer", 2, Rarity.RARE, mage.cards.a.AvenMimeomancer.class));
        cards.add(new SetCardInfo("Bant Sojourners", 125, Rarity.COMMON, mage.cards.b.BantSojourners.class));
        cards.add(new SetCardInfo("Bant Sureblade", 143, Rarity.COMMON, mage.cards.b.BantSureblade.class));
        cards.add(new SetCardInfo("Behemoth Sledge", 65, Rarity.UNCOMMON, mage.cards.b.BehemothSledge.class));
        cards.add(new SetCardInfo("Bituminous Blast", 34, Rarity.UNCOMMON, mage.cards.b.BituminousBlast.class));
        cards.add(new SetCardInfo("Blitz Hellion", 49, Rarity.RARE, mage.cards.b.BlitzHellion.class));
        cards.add(new SetCardInfo("Bloodbraid Elf", 50, Rarity.UNCOMMON, mage.cards.b.BloodbraidElf.class));
        cards.add(new SetCardInfo("Brainbite", 18, Rarity.COMMON, mage.cards.b.Brainbite.class));
        cards.add(new SetCardInfo("Breath of Malfegor", 35, Rarity.COMMON, mage.cards.b.BreathOfMalfegor.class));
        cards.add(new SetCardInfo("Captured Sunlight", 66, Rarity.COMMON, mage.cards.c.CapturedSunlight.class));
        cards.add(new SetCardInfo("Cerodon Yearling", 96, Rarity.COMMON, mage.cards.c.CerodonYearling.class));
        cards.add(new SetCardInfo("Cloven Casting", 86, Rarity.RARE, mage.cards.c.ClovenCasting.class));
        cards.add(new SetCardInfo("Colossal Might", 51, Rarity.COMMON, mage.cards.c.ColossalMight.class));
        cards.add(new SetCardInfo("Crystallization", 144, Rarity.COMMON, mage.cards.c.Crystallization.class));
        cards.add(new SetCardInfo("Dauntless Escort", 67, Rarity.RARE, mage.cards.d.DauntlessEscort.class));
        cards.add(new SetCardInfo("Deadshot Minotaur", 52, Rarity.COMMON, mage.cards.d.DeadshotMinotaur.class));
        cards.add(new SetCardInfo("Deathbringer Thoctar", 36, Rarity.RARE, mage.cards.d.DeathbringerThoctar.class));
        cards.add(new SetCardInfo("Defiler of Souls", 37, Rarity.MYTHIC, mage.cards.d.DefilerOfSouls.class));
        cards.add(new SetCardInfo("Demonic Dread", 38, Rarity.COMMON, mage.cards.d.DemonicDread.class));
        cards.add(new SetCardInfo("Demonspine Whip", 39, Rarity.UNCOMMON, mage.cards.d.DemonspineWhip.class));
        cards.add(new SetCardInfo("Deny Reality", 19, Rarity.COMMON, mage.cards.d.DenyReality.class));
        cards.add(new SetCardInfo("Double Negative", 87, Rarity.UNCOMMON, mage.cards.d.DoubleNegative.class));
        cards.add(new SetCardInfo("Dragon Appeasement", 115, Rarity.UNCOMMON, mage.cards.d.DragonAppeasement.class));
        cards.add(new SetCardInfo("Dragon Broodmother", 53, Rarity.MYTHIC, mage.cards.d.DragonBroodmother.class));
        cards.add(new SetCardInfo("Drastic Revelation", 111, Rarity.UNCOMMON, mage.cards.d.DrasticRevelation.class));
        cards.add(new SetCardInfo("Enigma Sphinx", 106, Rarity.RARE, mage.cards.e.EnigmaSphinx.class));
        cards.add(new SetCardInfo("Enlisted Wurm", 68, Rarity.UNCOMMON, mage.cards.e.EnlistedWurm.class));
        cards.add(new SetCardInfo("Esper Sojourners", 107, Rarity.COMMON, mage.cards.e.EsperSojourners.class));
        cards.add(new SetCardInfo("Esper Stormblade", 132, Rarity.COMMON, mage.cards.e.EsperStormblade.class));
        cards.add(new SetCardInfo("Ethercaste Knight", 3, Rarity.COMMON, mage.cards.e.EthercasteKnight.class));
        cards.add(new SetCardInfo("Etherium Abomination", 20, Rarity.COMMON, mage.cards.e.EtheriumAbomination.class));
        cards.add(new SetCardInfo("Ethersworn Shieldmage", 4, Rarity.COMMON, mage.cards.e.EtherswornShieldmage.class));
        cards.add(new SetCardInfo("Etherwrought Page", 108, Rarity.UNCOMMON, mage.cards.e.EtherwroughtPage.class));
        cards.add(new SetCardInfo("Fieldmist Borderpost", 5, Rarity.COMMON, mage.cards.f.FieldmistBorderpost.class));
        cards.add(new SetCardInfo("Fight to the Death", 97, Rarity.RARE, mage.cards.f.FightToTheDeath.class));
        cards.add(new SetCardInfo("Filigree Angel", 6, Rarity.RARE, mage.cards.f.FiligreeAngel.class));
        cards.add(new SetCardInfo("Finest Hour", 126, Rarity.RARE, mage.cards.f.FinestHour.class));
        cards.add(new SetCardInfo("Firewild Borderpost", 54, Rarity.COMMON, mage.cards.f.FirewildBorderpost.class));
        cards.add(new SetCardInfo("Flurry of Wings", 127, Rarity.UNCOMMON, mage.cards.f.FlurryOfWings.class));
        cards.add(new SetCardInfo("Giant Ambush Beetle", 137, Rarity.UNCOMMON, mage.cards.g.GiantAmbushBeetle.class));
        cards.add(new SetCardInfo("Glassdust Hulk", 7, Rarity.COMMON, mage.cards.g.GlassdustHulk.class));
        cards.add(new SetCardInfo("Glory of Warfare", 98, Rarity.RARE, mage.cards.g.GloryOfWarfare.class));
        cards.add(new SetCardInfo("Gloryscale Viashino", 120, Rarity.UNCOMMON, mage.cards.g.GloryscaleViashino.class));
        cards.add(new SetCardInfo("Godtracker of Jund", 55, Rarity.COMMON, mage.cards.g.GodtrackerOfJund.class));
        cards.add(new SetCardInfo("Gorger Wurm", 56, Rarity.COMMON, mage.cards.g.GorgerWurm.class));
        cards.add(new SetCardInfo("Grixis Grimblade", 134, Rarity.COMMON, mage.cards.g.GrixisGrimblade.class));
        cards.add(new SetCardInfo("Grixis Sojourners", 112, Rarity.COMMON, mage.cards.g.GrixisSojourners.class));
        cards.add(new SetCardInfo("Grizzled Leotau", 69, Rarity.COMMON, mage.cards.g.GrizzledLeotau.class));
        cards.add(new SetCardInfo("Identity Crisis", 81, Rarity.RARE, mage.cards.i.IdentityCrisis.class));
        cards.add(new SetCardInfo("Igneous Pouncer", 40, Rarity.COMMON, mage.cards.i.IgneousPouncer.class));
        cards.add(new SetCardInfo("Illusory Demon", 21, Rarity.UNCOMMON, mage.cards.i.IllusoryDemon.class));
        cards.add(new SetCardInfo("Intimidation Bolt", 99, Rarity.UNCOMMON, mage.cards.i.IntimidationBolt.class));
        cards.add(new SetCardInfo("Jenara, Asura of War", 128, Rarity.MYTHIC, mage.cards.j.JenaraAsuraOfWar.class));
        cards.add(new SetCardInfo("Jhessian Zombies", 22, Rarity.COMMON, mage.cards.j.JhessianZombies.class));
        cards.add(new SetCardInfo("Jund Hackblade", 138, Rarity.COMMON, mage.cards.j.JundHackblade.class));
        cards.add(new SetCardInfo("Jund Sojourners", 116, Rarity.COMMON, mage.cards.j.JundSojourners.class));
        cards.add(new SetCardInfo("Karrthus, Tyrant of Jund", 117, Rarity.MYTHIC, mage.cards.k.KarrthusTyrantOfJund.class));
        cards.add(new SetCardInfo("Kathari Bomber", 41, Rarity.COMMON, mage.cards.k.KathariBomber.class));
        cards.add(new SetCardInfo("Kathari Remnant", 23, Rarity.UNCOMMON, mage.cards.k.KathariRemnant.class));
        cards.add(new SetCardInfo("Knight of New Alara", 70, Rarity.RARE, mage.cards.k.KnightOfNewAlara.class));
        cards.add(new SetCardInfo("Knotvine Paladin", 71, Rarity.RARE, mage.cards.k.KnotvinePaladin.class));
        cards.add(new SetCardInfo("Lavalanche", 118, Rarity.RARE, mage.cards.l.Lavalanche.class));
        cards.add(new SetCardInfo("Leonin Armorguard", 72, Rarity.COMMON, mage.cards.l.LeoninArmorguard.class));
        cards.add(new SetCardInfo("Lich Lord of Unx", 24, Rarity.RARE, mage.cards.l.LichLordOfUnx.class));
        cards.add(new SetCardInfo("Lightning Reaver", 42, Rarity.RARE, mage.cards.l.LightningReaver.class));
        cards.add(new SetCardInfo("Lord of Extinction", 91, Rarity.MYTHIC, mage.cards.l.LordOfExtinction.class));
        cards.add(new SetCardInfo("Lorescale Coatl", 101, Rarity.UNCOMMON, mage.cards.l.LorescaleCoatl.class));
        cards.add(new SetCardInfo("Madrush Cyclops", 119, Rarity.RARE, mage.cards.m.MadrushCyclops.class));
        cards.add(new SetCardInfo("Maelstrom Nexus", 130, Rarity.MYTHIC, mage.cards.m.MaelstromNexus.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 92, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Magefire Wings", 88, Rarity.COMMON, mage.cards.m.MagefireWings.class));
        cards.add(new SetCardInfo("Mage Slayer", 57, Rarity.UNCOMMON, mage.cards.m.MageSlayer.class));
        cards.add(new SetCardInfo("Marisi's Twinclaws", 140, Rarity.UNCOMMON, mage.cards.m.MarisisTwinclaws.class));
        cards.add(new SetCardInfo("Marrow Chomper", 93, Rarity.UNCOMMON, mage.cards.m.MarrowChomper.class));
        cards.add(new SetCardInfo("Mask of Riddles", 25, Rarity.UNCOMMON, mage.cards.m.MaskOfRiddles.class));
        cards.add(new SetCardInfo("Mayael's Aria", 121, Rarity.RARE, mage.cards.m.MayaelsAria.class));
        cards.add(new SetCardInfo("Meddling Mage", 8, Rarity.RARE, mage.cards.m.MeddlingMage.class));
        cards.add(new SetCardInfo("Messenger Falcons", 145, Rarity.UNCOMMON, mage.cards.m.MessengerFalcons.class));
        cards.add(new SetCardInfo("Mind Funeral", 26, Rarity.UNCOMMON, mage.cards.m.MindFuneral.class));
        cards.add(new SetCardInfo("Mistvein Borderpost", 27, Rarity.COMMON, mage.cards.m.MistveinBorderpost.class));
        cards.add(new SetCardInfo("Monstrous Carabid", 43, Rarity.COMMON, mage.cards.m.MonstrousCarabid.class));
        cards.add(new SetCardInfo("Morbid Bloom", 94, Rarity.UNCOMMON, mage.cards.m.MorbidBloom.class));
        cards.add(new SetCardInfo("Mycoid Shepherd", 73, Rarity.RARE, mage.cards.m.MycoidShepherd.class));
        cards.add(new SetCardInfo("Naya Hushblade", 141, Rarity.COMMON, mage.cards.n.NayaHushblade.class));
        cards.add(new SetCardInfo("Naya Sojourners", 122, Rarity.COMMON, mage.cards.n.NayaSojourners.class));
        cards.add(new SetCardInfo("Necromancer's Covenant", 82, Rarity.RARE, mage.cards.n.NecromancersCovenant.class));
        cards.add(new SetCardInfo("Nemesis of Reason", 28, Rarity.RARE, mage.cards.n.NemesisOfReason.class));
        cards.add(new SetCardInfo("Nulltread Gargantuan", 102, Rarity.UNCOMMON, mage.cards.n.NulltreadGargantuan.class));
        cards.add(new SetCardInfo("Offering to Asha", 9, Rarity.COMMON, mage.cards.o.OfferingToAsha.class));
        cards.add(new SetCardInfo("Pale Recluse", 74, Rarity.COMMON, mage.cards.p.PaleRecluse.class));
        cards.add(new SetCardInfo("Predatory Advantage", 58, Rarity.RARE, mage.cards.p.PredatoryAdvantage.class));
        cards.add(new SetCardInfo("Putrid Leech", 95, Rarity.COMMON, mage.cards.p.PutridLeech.class));
        cards.add(new SetCardInfo("Qasali Pridemage", 75, Rarity.COMMON, mage.cards.q.QasaliPridemage.class));
        cards.add(new SetCardInfo("Reborn Hope", 76, Rarity.UNCOMMON, mage.cards.r.RebornHope.class));
        cards.add(new SetCardInfo("Retaliator Griffin", 123, Rarity.RARE, mage.cards.r.RetaliatorGriffin.class));
        cards.add(new SetCardInfo("Rhox Brute", 59, Rarity.COMMON, mage.cards.r.RhoxBrute.class));
        cards.add(new SetCardInfo("Sages of the Anima", 103, Rarity.RARE, mage.cards.s.SagesOfTheAnima.class));
        cards.add(new SetCardInfo("Sanctum Plowbeast", 10, Rarity.COMMON, mage.cards.s.SanctumPlowbeast.class));
        cards.add(new SetCardInfo("Sangrite Backlash", 139, Rarity.COMMON, mage.cards.s.SangriteBacklash.class));
        cards.add(new SetCardInfo("Sanity Gnawers", 44, Rarity.UNCOMMON, mage.cards.s.SanityGnawers.class));
        cards.add(new SetCardInfo("Sen Triplets", 109, Rarity.MYTHIC, mage.cards.s.SenTriplets.class));
        cards.add(new SetCardInfo("Sewn-Eye Drake", 135, Rarity.COMMON, mage.cards.s.SewnEyeDrake.class));
        cards.add(new SetCardInfo("Shield of the Righteous", 11, Rarity.UNCOMMON, mage.cards.s.ShieldOfTheRighteous.class));
        cards.add(new SetCardInfo("Sigil Captain", 77, Rarity.UNCOMMON, mage.cards.s.SigilCaptain.class));
        cards.add(new SetCardInfo("Sigiled Behemoth", 79, Rarity.COMMON, mage.cards.s.SigiledBehemoth.class));
        cards.add(new SetCardInfo("Sigil of the Nayan Gods", 78, Rarity.COMMON, mage.cards.s.SigilOfTheNayanGods.class));
        cards.add(new SetCardInfo("Singe-Mind Ogre", 45, Rarity.COMMON, mage.cards.s.SingeMindOgre.class));
        cards.add(new SetCardInfo("Skyclaw Thrash", 89, Rarity.UNCOMMON, mage.cards.s.SkyclawThrash.class));
        cards.add(new SetCardInfo("Slave of Bolas", 136, Rarity.UNCOMMON, mage.cards.s.SlaveOfBolas.class));
        cards.add(new SetCardInfo("Soul Manipulation", 29, Rarity.COMMON, mage.cards.s.SoulManipulation.class));
        cards.add(new SetCardInfo("Soulquake", 30, Rarity.RARE, mage.cards.s.Soulquake.class));
        cards.add(new SetCardInfo("Sovereigns of Lost Alara", 12, Rarity.RARE, mage.cards.s.SovereignsOfLostAlara.class));
        cards.add(new SetCardInfo("Spellbound Dragon", 90, Rarity.RARE, mage.cards.s.SpellboundDragon.class));
        cards.add(new SetCardInfo("Spellbreaker Behemoth", 60, Rarity.RARE, mage.cards.s.SpellbreakerBehemoth.class));
        cards.add(new SetCardInfo("Sphinx of the Steel Wind", 110, Rarity.MYTHIC, mage.cards.s.SphinxOfTheSteelWind.class));
        cards.add(new SetCardInfo("Stormcaller's Boon", 13, Rarity.COMMON, mage.cards.s.StormcallersBoon.class));
        cards.add(new SetCardInfo("Stun Sniper", 100, Rarity.UNCOMMON, mage.cards.s.StunSniper.class));
        cards.add(new SetCardInfo("Tainted Sigil", 83, Rarity.UNCOMMON, mage.cards.t.TaintedSigil.class));
        cards.add(new SetCardInfo("Talon Trooper", 14, Rarity.COMMON, mage.cards.t.TalonTrooper.class));
        cards.add(new SetCardInfo("Terminate", 46, Rarity.COMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Thopter Foundry", 133, Rarity.UNCOMMON, mage.cards.t.ThopterFoundry.class));
        cards.add(new SetCardInfo("Thought Hemorrhage", 47, Rarity.RARE, mage.cards.t.ThoughtHemorrhage.class));
        cards.add(new SetCardInfo("Thraximundar", 113, Rarity.MYTHIC, mage.cards.t.Thraximundar.class));
        cards.add(new SetCardInfo("Time Sieve", 31, Rarity.RARE, mage.cards.t.TimeSieve.class));
        cards.add(new SetCardInfo("Trace of Abundance", 142, Rarity.COMMON, mage.cards.t.TraceOfAbundance.class));
        cards.add(new SetCardInfo("Unbender Tine", 15, Rarity.UNCOMMON, mage.cards.u.UnbenderTine.class));
        cards.add(new SetCardInfo("Unscythe, Killer of Kings", 114, Rarity.RARE, mage.cards.u.UnscytheKillerOfKings.class));
        cards.add(new SetCardInfo("Uril, the Miststalker", 124, Rarity.MYTHIC, mage.cards.u.UrilTheMiststalker.class));
        cards.add(new SetCardInfo("Valley Rannet", 61, Rarity.COMMON, mage.cards.v.ValleyRannet.class));
        cards.add(new SetCardInfo("Vectis Dominator", 84, Rarity.COMMON, mage.cards.v.VectisDominator.class));
        cards.add(new SetCardInfo("Vedalken Ghoul", 32, Rarity.COMMON, mage.cards.v.VedalkenGhoul.class));
        cards.add(new SetCardInfo("Vedalken Heretic", 104, Rarity.RARE, mage.cards.v.VedalkenHeretic.class));
        cards.add(new SetCardInfo("Veinfire Borderpost", 48, Rarity.COMMON, mage.cards.v.VeinfireBorderpost.class));
        cards.add(new SetCardInfo("Vengeful Rebirth", 62, Rarity.UNCOMMON, mage.cards.v.VengefulRebirth.class));
        cards.add(new SetCardInfo("Violent Outburst", 63, Rarity.COMMON, mage.cards.v.ViolentOutburst.class));
        cards.add(new SetCardInfo("Vithian Renegades", 64, Rarity.UNCOMMON, mage.cards.v.VithianRenegades.class));
        cards.add(new SetCardInfo("Wall of Denial", 16, Rarity.UNCOMMON, mage.cards.w.WallOfDenial.class));
        cards.add(new SetCardInfo("Wargate", 129, Rarity.RARE, mage.cards.w.Wargate.class));
        cards.add(new SetCardInfo("Wildfield Borderpost", 80, Rarity.COMMON, mage.cards.w.WildfieldBorderpost.class));
        cards.add(new SetCardInfo("Winged Coatl", 105, Rarity.COMMON, mage.cards.w.WingedCoatl.class));
        cards.add(new SetCardInfo("Zealous Persecution", 85, Rarity.UNCOMMON, mage.cards.z.ZealousPersecution.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new AlaraRebornCollator();
    }
}

// Booster collation info from https://vm1.substation33.com/tiera/t/lethe/arb.html
// Using USA collation
class AlaraRebornCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "51", "74", "122", "45", "52", "14", "134", "29", "75", "138", "43", "13", "59", "80", "135", "27", "7", "143", "46", "72", "96", "22", "134", "4", "80", "144", "17", "46", "96", "19", "7", "138", "105", "132", "95", "75", "51", "22", "4", "122", "56", "45", "143", "74", "29", "13", "48", "139", "56", "72", "17", "5", "144", "27", "43", "14", "139", "105", "52", "48", "132", "19", "59", "5", "135", "95");
    private final CardRun commonB = new CardRun(true, "78", "131", "9", "55", "40", "107", "69", "18", "112", "61", "10", "40", "125", "79", "20", "107", "3", "55", "35", "116", "32", "79", "63", "112", "3", "66", "88", "142", "41", "32", "63", "141", "84", "54", "116", "66", "35", "20", "131", "38", "9", "54", "141", "78", "41", "84", "18", "142", "69", "61", "125", "10", "38", "88");
    private final CardRun uncommonA = new CardRun(false, "1", "11", "23", "25", "34", "39", "62", "65", "68", "85", "89", "93", "99", "100", "101", "111", "120", "133", "136", "137", "140", "145");
    private final CardRun uncommonB = new CardRun(false, "15", "16", "21", "26", "33", "44", "50", "57", "64", "76", "77", "83", "87", "94", "102", "108", "115", "127");
    private final CardRun rare = new CardRun(false, "2", "2", "6", "6", "8", "8", "12", "12", "24", "24", "28", "28", "30", "30", "31", "31", "36", "36", "42", "42", "47", "47", "49", "49", "58", "58", "60", "60", "67", "67", "70", "70", "71", "71", "73", "73", "81", "81", "82", "82", "86", "86", "90", "90", "92", "92", "97", "97", "98", "98", "103", "103", "104", "104", "106", "106", "114", "114", "118", "118", "119", "119", "121", "121", "123", "123", "126", "126", "129", "129", "37", "53", "91", "109", "110", "113", "117", "124", "128", "130");
    private final CardRun land = new CardRun(false, "ALA_230", "ALA_231", "ALA_232", "ALA_233", "ALA_234", "ALA_235", "ALA_236", "ALA_237", "ALA_238", "ALA_239", "ALA_240", "ALA_241", "ALA_242", "ALA_243", "ALA_244", "ALA_245", "ALA_246", "ALA_247", "ALA_248", "ALA_249");

    private final BoosterStructure AAAAAABBBB = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB
    );
    private final BoosterStructure AAAAABBBBB = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB, commonB
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 5.5 A commons (11 / 2)
    // 4.5 B commons ( 9 / 2)
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AAAAAABBBB,
            AAAAABBBBB
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 1.65 A uncommons (33 / 20)
    // 1.35 B uncommons (27 / 20)
    // These numbers are the same for all sets with 60 uncommons in asymmetrical A/B print runs
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            ABB, ABB, ABB, ABB, ABB, ABB, ABB
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }

}
