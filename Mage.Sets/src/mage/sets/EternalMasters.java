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
 * @author fireshoes
 */
public final class EternalMasters extends ExpansionSet {

    private static final EternalMasters instance = new EternalMasters();

    public static EternalMasters getInstance() {
        return instance;
    }

    private EternalMasters() {
        super("Eternal Masters", "EMA", ExpansionSet.buildDate(2016, 6, 10), SetType.SUPPLEMENTAL);
        this.blockName = "Reprint";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Abundant Growth", 156, Rarity.COMMON, mage.cards.a.AbundantGrowth.class));
        cards.add(new SetCardInfo("Ancestral Mask", 157, Rarity.UNCOMMON, mage.cards.a.AncestralMask.class));
        cards.add(new SetCardInfo("Animate Dead", 78, Rarity.UNCOMMON, mage.cards.a.AnimateDead.class));
        cards.add(new SetCardInfo("Annihilate", 79, Rarity.UNCOMMON, mage.cards.a.Annihilate.class));
        cards.add(new SetCardInfo("Arcanis the Omnipotent", 39, Rarity.RARE, mage.cards.a.ArcanisTheOmnipotent.class));
        cards.add(new SetCardInfo("Argothian Enchantress", 158, Rarity.MYTHIC, mage.cards.a.ArgothianEnchantress.class));
        cards.add(new SetCardInfo("Armadillo Cloak", 195, Rarity.UNCOMMON, mage.cards.a.ArmadilloCloak.class));
        cards.add(new SetCardInfo("Ashnod's Altar", 218, Rarity.UNCOMMON, mage.cards.a.AshnodsAltar.class));
        cards.add(new SetCardInfo("Avarax", 117, Rarity.COMMON, mage.cards.a.Avarax.class));
        cards.add(new SetCardInfo("Aven Riftwatcher", 1, Rarity.COMMON, mage.cards.a.AvenRiftwatcher.class));
        cards.add(new SetCardInfo("Balance", 2, Rarity.MYTHIC, mage.cards.b.Balance.class));
        cards.add(new SetCardInfo("Baleful Strix", 196, Rarity.RARE, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Ballynock Cohort", 3, Rarity.COMMON, mage.cards.b.BallynockCohort.class));
        cards.add(new SetCardInfo("Battle Squadron", 118, Rarity.UNCOMMON, mage.cards.b.BattleSquadron.class));
        cards.add(new SetCardInfo("Beetleback Chief", 119, Rarity.UNCOMMON, mage.cards.b.BeetlebackChief.class));
        cards.add(new SetCardInfo("Benevolent Bodyguard", 4, Rarity.COMMON, mage.cards.b.BenevolentBodyguard.class));
        cards.add(new SetCardInfo("Blightsoil Druid", 80, Rarity.COMMON, mage.cards.b.BlightsoilDruid.class));
        cards.add(new SetCardInfo("Blood Artist", 81, Rarity.UNCOMMON, mage.cards.b.BloodArtist.class));
        cards.add(new SetCardInfo("Bloodbraid Elf", 197, Rarity.UNCOMMON, mage.cards.b.BloodbraidElf.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 236, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Blossoming Sands", 237, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Borderland Marauder", 120, Rarity.COMMON, mage.cards.b.BorderlandMarauder.class));
        cards.add(new SetCardInfo("Brago, King Eternal", 198, Rarity.RARE, mage.cards.b.BragoKingEternal.class));
        cards.add(new SetCardInfo("Braids, Cabal Minion", 82, Rarity.RARE, mage.cards.b.BraidsCabalMinion.class));
        cards.add(new SetCardInfo("Brainstorm", 40, Rarity.UNCOMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Brawn", 159, Rarity.UNCOMMON, mage.cards.b.Brawn.class));
        cards.add(new SetCardInfo("Burning Vengeance", 121, Rarity.UNCOMMON, mage.cards.b.BurningVengeance.class));
        cards.add(new SetCardInfo("Cabal Therapy", 83, Rarity.UNCOMMON, mage.cards.c.CabalTherapy.class));
        cards.add(new SetCardInfo("Calciderm", 5, Rarity.UNCOMMON, mage.cards.c.Calciderm.class));
        cards.add(new SetCardInfo("Call the Skybreaker", 214, Rarity.RARE, mage.cards.c.CallTheSkybreaker.class));
        cards.add(new SetCardInfo("Carbonize", 122, Rarity.COMMON, mage.cards.c.Carbonize.class));
        cards.add(new SetCardInfo("Carrion Feeder", 84, Rarity.COMMON, mage.cards.c.CarrionFeeder.class));
        cards.add(new SetCardInfo("Centaur Chieftain", 160, Rarity.UNCOMMON, mage.cards.c.CentaurChieftain.class));
        cards.add(new SetCardInfo("Cephalid Sage", 41, Rarity.COMMON, mage.cards.c.CephalidSage.class));
        cards.add(new SetCardInfo("Chain Lightning", 123, Rarity.UNCOMMON, mage.cards.c.ChainLightning.class));
        cards.add(new SetCardInfo("Chrome Mox", 219, Rarity.MYTHIC, mage.cards.c.ChromeMox.class));
        cards.add(new SetCardInfo("Civic Wayfinder", 161, Rarity.COMMON, mage.cards.c.CivicWayfinder.class));
        cards.add(new SetCardInfo("Coalition Honor Guard", 6, Rarity.COMMON, mage.cards.c.CoalitionHonorGuard.class));
        cards.add(new SetCardInfo("Commune with the Gods", 162, Rarity.COMMON, mage.cards.c.CommuneWithTheGods.class));
        cards.add(new SetCardInfo("Control Magic", 42, Rarity.RARE, mage.cards.c.ControlMagic.class));
        cards.add(new SetCardInfo("Counterspell", 43, Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Crater Hellion", 124, Rarity.RARE, mage.cards.c.CraterHellion.class));
        cards.add(new SetCardInfo("Dack Fayden", 199, Rarity.MYTHIC, mage.cards.d.DackFayden.class));
        cards.add(new SetCardInfo("Daze", 44, Rarity.UNCOMMON, mage.cards.d.Daze.class));
        cards.add(new SetCardInfo("Deadbridge Shaman", 85, Rarity.COMMON, mage.cards.d.DeadbridgeShaman.class));
        cards.add(new SetCardInfo("Deathrite Shaman", 215, Rarity.RARE, mage.cards.d.DeathriteShaman.class));
        cards.add(new SetCardInfo("Deep Analysis", 45, Rarity.COMMON, mage.cards.d.DeepAnalysis.class));
        cards.add(new SetCardInfo("Desperate Ravings", 125, Rarity.COMMON, mage.cards.d.DesperateRavings.class));
        cards.add(new SetCardInfo("Diminishing Returns", 46, Rarity.RARE, mage.cards.d.DiminishingReturns.class));
        cards.add(new SetCardInfo("Dismal Backwater", 238, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Dragon Egg", 126, Rarity.COMMON, mage.cards.d.DragonEgg.class));
        cards.add(new SetCardInfo("Dream Twist", 47, Rarity.COMMON, mage.cards.d.DreamTwist.class));
        cards.add(new SetCardInfo("Dualcaster Mage", 127, Rarity.RARE, mage.cards.d.DualcasterMage.class));
        cards.add(new SetCardInfo("Duplicant", 220, Rarity.RARE, mage.cards.d.Duplicant.class));
        cards.add(new SetCardInfo("Duress", 86, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Eight-and-a-Half-Tails", 7, Rarity.RARE, mage.cards.e.EightAndAHalfTails.class));
        cards.add(new SetCardInfo("Elephant Guide", 163, Rarity.COMMON, mage.cards.e.ElephantGuide.class));
        cards.add(new SetCardInfo("Elite Vanguard", 8, Rarity.COMMON, mage.cards.e.EliteVanguard.class));
        cards.add(new SetCardInfo("Elvish Vanguard", 164, Rarity.COMMON, mage.cards.e.ElvishVanguard.class));
        cards.add(new SetCardInfo("Emmessi Tome", 221, Rarity.UNCOMMON, mage.cards.e.EmmessiTome.class));
        cards.add(new SetCardInfo("Emperor Crocodile", 165, Rarity.COMMON, mage.cards.e.EmperorCrocodile.class));
        cards.add(new SetCardInfo("Enlightened Tutor", 9, Rarity.RARE, mage.cards.e.EnlightenedTutor.class));
        cards.add(new SetCardInfo("Entomb", 87, Rarity.RARE, mage.cards.e.Entomb.class));
        cards.add(new SetCardInfo("Extract from Darkness", 200, Rarity.UNCOMMON, mage.cards.e.ExtractFromDarkness.class));
        cards.add(new SetCardInfo("Eyeblight's Ending", 88, Rarity.COMMON, mage.cards.e.EyeblightsEnding.class));
        cards.add(new SetCardInfo("Fact or Fiction", 48, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Faithless Looting", 128, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Faith's Fetters", 10, Rarity.UNCOMMON, mage.cards.f.FaithsFetters.class));
        cards.add(new SetCardInfo("Fervent Cathar", 129, Rarity.COMMON, mage.cards.f.FerventCathar.class));
        cards.add(new SetCardInfo("Field of Souls", 11, Rarity.UNCOMMON, mage.cards.f.FieldOfSouls.class));
        cards.add(new SetCardInfo("Firebolt", 130, Rarity.COMMON, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("Flame Jab", 131, Rarity.UNCOMMON, mage.cards.f.FlameJab.class));
        cards.add(new SetCardInfo("Flame-Kin Zealot", 201, Rarity.UNCOMMON, mage.cards.f.FlameKinZealot.class));
        cards.add(new SetCardInfo("Flinthoof Boar", 166, Rarity.UNCOMMON, mage.cards.f.FlinthoofBoar.class));
        cards.add(new SetCardInfo("Fog", 167, Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Force of Will", 49, Rarity.MYTHIC, mage.cards.f.ForceOfWill.class));
        cards.add(new SetCardInfo("Future Sight", 50, Rarity.RARE, mage.cards.f.FutureSight.class));
        cards.add(new SetCardInfo("Gaea's Blessing", 168, Rarity.UNCOMMON, mage.cards.g.GaeasBlessing.class));
        cards.add(new SetCardInfo("Gamble", 132, Rarity.RARE, mage.cards.g.Gamble.class));
        cards.add(new SetCardInfo("Gaseous Form", 51, Rarity.COMMON, mage.cards.g.GaseousForm.class));
        cards.add(new SetCardInfo("Ghitu Slinger", 133, Rarity.UNCOMMON, mage.cards.g.GhituSlinger.class));
        cards.add(new SetCardInfo("Giant Solifuge", 216, Rarity.RARE, mage.cards.g.GiantSolifuge.class));
        cards.add(new SetCardInfo("Giant Tortoise", 52, Rarity.COMMON, mage.cards.g.GiantTortoise.class));
        cards.add(new SetCardInfo("Glacial Wall", 53, Rarity.COMMON, mage.cards.g.GlacialWall.class));
        cards.add(new SetCardInfo("Glare of Subdual", 202, Rarity.RARE, mage.cards.g.GlareOfSubdual.class));
        cards.add(new SetCardInfo("Glimmerpoint Stag", 12, Rarity.UNCOMMON, mage.cards.g.GlimmerpointStag.class));
        cards.add(new SetCardInfo("Goblin Charbelcher", 222, Rarity.RARE, mage.cards.g.GoblinCharbelcher.class));
        cards.add(new SetCardInfo("Goblin Trenches", 203, Rarity.RARE, mage.cards.g.GoblinTrenches.class));
        cards.add(new SetCardInfo("Gravedigger", 89, Rarity.COMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Green Sun's Zenith", 169, Rarity.RARE, mage.cards.g.GreenSunsZenith.class));
        cards.add(new SetCardInfo("Harmonize", 170, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Havoc Demon", 90, Rarity.UNCOMMON, mage.cards.h.HavocDemon.class));
        cards.add(new SetCardInfo("Heritage Druid", 171, Rarity.RARE, mage.cards.h.HeritageDruid.class));
        cards.add(new SetCardInfo("Honden of Cleansing Fire", 13, Rarity.UNCOMMON, mage.cards.h.HondenOfCleansingFire.class));
        cards.add(new SetCardInfo("Honden of Infinite Rage", 134, Rarity.UNCOMMON, mage.cards.h.HondenOfInfiniteRage.class));
        cards.add(new SetCardInfo("Honden of Life's Web", 172, Rarity.UNCOMMON, mage.cards.h.HondenOfLifesWeb.class));
        cards.add(new SetCardInfo("Honden of Night's Reach", 91, Rarity.UNCOMMON, mage.cards.h.HondenOfNightsReach.class));
        cards.add(new SetCardInfo("Honden of Seeing Winds", 54, Rarity.UNCOMMON, mage.cards.h.HondenOfSeeingWinds.class));
        cards.add(new SetCardInfo("Humble", 14, Rarity.COMMON, mage.cards.h.Humble.class));
        cards.add(new SetCardInfo("Hydroblast", 55, Rarity.UNCOMMON, mage.cards.h.Hydroblast.class));
        cards.add(new SetCardInfo("Hymn to Tourach", 92, Rarity.UNCOMMON, mage.cards.h.HymnToTourach.class));
        cards.add(new SetCardInfo("Ichorid", 93, Rarity.RARE, mage.cards.i.Ichorid.class));
        cards.add(new SetCardInfo("Imperious Perfect", 173, Rarity.RARE, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Inkwell Leviathan", 56, Rarity.RARE, mage.cards.i.InkwellLeviathan.class));
        cards.add(new SetCardInfo("Innocent Blood", 94, Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Intangible Virtue", 15, Rarity.UNCOMMON, mage.cards.i.IntangibleVirtue.class));
        cards.add(new SetCardInfo("Invigorate", 174, Rarity.UNCOMMON, mage.cards.i.Invigorate.class));
        cards.add(new SetCardInfo("Isochron Scepter", 223, Rarity.RARE, mage.cards.i.IsochronScepter.class));
        cards.add(new SetCardInfo("Jace, the Mind Sculptor", 57, Rarity.MYTHIC, mage.cards.j.JaceTheMindSculptor.class));
        cards.add(new SetCardInfo("Jareth, Leonine Titan", 16, Rarity.RARE, mage.cards.j.JarethLeonineTitan.class));
        cards.add(new SetCardInfo("Jetting Glasskite", 58, Rarity.UNCOMMON, mage.cards.j.JettingGlasskite.class));
        cards.add(new SetCardInfo("Juggernaut", 224, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Jungle Hollow", 239, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Karakas", 240, Rarity.MYTHIC, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Karmic Guide", 17, Rarity.RARE, mage.cards.k.KarmicGuide.class));
        cards.add(new SetCardInfo("Keldon Champion", 135, Rarity.UNCOMMON, mage.cards.k.KeldonChampion.class));
        cards.add(new SetCardInfo("Keldon Marauders", 136, Rarity.COMMON, mage.cards.k.KeldonMarauders.class));
        cards.add(new SetCardInfo("Kird Ape", 137, Rarity.COMMON, mage.cards.k.KirdApe.class));
        cards.add(new SetCardInfo("Kor Hookmaster", 18, Rarity.COMMON, mage.cards.k.KorHookmaster.class));
        cards.add(new SetCardInfo("Llanowar Elves", 175, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Lys Alana Huntmaster", 176, Rarity.COMMON, mage.cards.l.LysAlanaHuntmaster.class));
        cards.add(new SetCardInfo("Lys Alana Scarblade", 95, Rarity.UNCOMMON, mage.cards.l.LysAlanaScarblade.class));
        cards.add(new SetCardInfo("Maelstrom Wanderer", 204, Rarity.MYTHIC, mage.cards.m.MaelstromWanderer.class));
        cards.add(new SetCardInfo("Malicious Affliction", 96, Rarity.RARE, mage.cards.m.MaliciousAffliction.class));
        cards.add(new SetCardInfo("Mana Crypt", 225, Rarity.MYTHIC, mage.cards.m.ManaCrypt.class));
        cards.add(new SetCardInfo("Man-o'-War", 59, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Maze of Ith", 241, Rarity.RARE, mage.cards.m.MazeOfIth.class));
        cards.add(new SetCardInfo("Memory Lapse", 60, Rarity.COMMON, mage.cards.m.MemoryLapse.class));
        cards.add(new SetCardInfo("Merfolk Looter", 61, Rarity.UNCOMMON, mage.cards.m.MerfolkLooter.class));
        cards.add(new SetCardInfo("Mesa Enchantress", 19, Rarity.UNCOMMON, mage.cards.m.MesaEnchantress.class));
        cards.add(new SetCardInfo("Millikin", 226, Rarity.UNCOMMON, mage.cards.m.Millikin.class));
        cards.add(new SetCardInfo("Mindless Automaton", 227, Rarity.UNCOMMON, mage.cards.m.MindlessAutomaton.class));
        cards.add(new SetCardInfo("Mishra's Factory", 242, Rarity.UNCOMMON, mage.cards.m.MishrasFactory.class));
        cards.add(new SetCardInfo("Mistral Charger", 20, Rarity.COMMON, mage.cards.m.MistralCharger.class));
        cards.add(new SetCardInfo("Mogg Fanatic", 138, Rarity.COMMON, mage.cards.m.MoggFanatic.class));
        cards.add(new SetCardInfo("Mogg War Marshal", 139, Rarity.COMMON, mage.cards.m.MoggWarMarshal.class));
        cards.add(new SetCardInfo("Monk Idealist", 21, Rarity.COMMON, mage.cards.m.MonkIdealist.class));
        cards.add(new SetCardInfo("Mother of Runes", 22, Rarity.RARE, mage.cards.m.MotherOfRunes.class));
        cards.add(new SetCardInfo("Mystical Tutor", 62, Rarity.RARE, mage.cards.m.MysticalTutor.class));
        cards.add(new SetCardInfo("Natural Order", 177, Rarity.MYTHIC, mage.cards.n.NaturalOrder.class));
        cards.add(new SetCardInfo("Nature's Claim", 178, Rarity.COMMON, mage.cards.n.NaturesClaim.class));
        cards.add(new SetCardInfo("Nausea", 97, Rarity.COMMON, mage.cards.n.Nausea.class));
        cards.add(new SetCardInfo("Necropotence", 98, Rarity.MYTHIC, mage.cards.n.Necropotence.class));
        cards.add(new SetCardInfo("Nekrataal", 99, Rarity.UNCOMMON, mage.cards.n.Nekrataal.class));
        cards.add(new SetCardInfo("Nevinyrral's Disk", 228, Rarity.RARE, mage.cards.n.NevinyrralsDisk.class));
        cards.add(new SetCardInfo("Night's Whisper", 100, Rarity.COMMON, mage.cards.n.NightsWhisper.class));
        cards.add(new SetCardInfo("Nimble Mongoose", 179, Rarity.COMMON, mage.cards.n.NimbleMongoose.class));
        cards.add(new SetCardInfo("Oona's Grace", 63, Rarity.COMMON, mage.cards.o.OonasGrace.class));
        cards.add(new SetCardInfo("Orcish Oriflamme", 140, Rarity.COMMON, mage.cards.o.OrcishOriflamme.class));
        cards.add(new SetCardInfo("Pacifism", 23, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Peregrine Drake", 64, Rarity.COMMON, mage.cards.p.PeregrineDrake.class));
        cards.add(new SetCardInfo("Phantom Monster", 65, Rarity.COMMON, mage.cards.p.PhantomMonster.class));
        cards.add(new SetCardInfo("Phyrexian Gargantua", 101, Rarity.UNCOMMON, mage.cards.p.PhyrexianGargantua.class));
        cards.add(new SetCardInfo("Phyrexian Ingester", 66, Rarity.UNCOMMON, mage.cards.p.PhyrexianIngester.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 102, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Pilgrim's Eye", 229, Rarity.COMMON, mage.cards.p.PilgrimsEye.class));
        cards.add(new SetCardInfo("Plague Witch", 103, Rarity.COMMON, mage.cards.p.PlagueWitch.class));
        cards.add(new SetCardInfo("Price of Progress", 141, Rarity.UNCOMMON, mage.cards.p.PriceOfProgress.class));
        cards.add(new SetCardInfo("Prismatic Lens", 230, Rarity.UNCOMMON, mage.cards.p.PrismaticLens.class));
        cards.add(new SetCardInfo("Prodigal Sorcerer", 67, Rarity.UNCOMMON, mage.cards.p.ProdigalSorcerer.class));
        cards.add(new SetCardInfo("Prowling Pangolin", 104, Rarity.COMMON, mage.cards.p.ProwlingPangolin.class));
        cards.add(new SetCardInfo("Pyroblast", 142, Rarity.UNCOMMON, mage.cards.p.Pyroblast.class));
        cards.add(new SetCardInfo("Pyrokinesis", 143, Rarity.RARE, mage.cards.p.Pyrokinesis.class));
        cards.add(new SetCardInfo("Quiet Speculation", 68, Rarity.UNCOMMON, mage.cards.q.QuietSpeculation.class));
        cards.add(new SetCardInfo("Raise the Alarm", 24, Rarity.COMMON, mage.cards.r.RaiseTheAlarm.class));
        cards.add(new SetCardInfo("Rally the Peasants", 25, Rarity.COMMON, mage.cards.r.RallyThePeasants.class));
        cards.add(new SetCardInfo("Rancor", 180, Rarity.UNCOMMON, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Reckless Charge", 144, Rarity.COMMON, mage.cards.r.RecklessCharge.class));
        cards.add(new SetCardInfo("Regal Force", 181, Rarity.RARE, mage.cards.r.RegalForce.class));
        cards.add(new SetCardInfo("Relic of Progenitus", 231, Rarity.UNCOMMON, mage.cards.r.RelicOfProgenitus.class));
        cards.add(new SetCardInfo("Roar of the Wurm", 182, Rarity.UNCOMMON, mage.cards.r.RoarOfTheWurm.class));
        cards.add(new SetCardInfo("Roots", 183, Rarity.COMMON, mage.cards.r.Roots.class));
        cards.add(new SetCardInfo("Rorix Bladewing", 145, Rarity.RARE, mage.cards.r.RorixBladewing.class));
        cards.add(new SetCardInfo("Rugged Highlands", 243, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Scoured Barrens", 244, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Screeching Skaab", 69, Rarity.COMMON, mage.cards.s.ScreechingSkaab.class));
        cards.add(new SetCardInfo("Seal of Cleansing", 26, Rarity.COMMON, mage.cards.s.SealOfCleansing.class));
        cards.add(new SetCardInfo("Seal of Strength", 184, Rarity.COMMON, mage.cards.s.SealOfStrength.class));
        cards.add(new SetCardInfo("Second Thoughts", 27, Rarity.COMMON, mage.cards.s.SecondThoughts.class));
        cards.add(new SetCardInfo("Seismic Stomp", 146, Rarity.COMMON, mage.cards.s.SeismicStomp.class));
        cards.add(new SetCardInfo("Sengir Autocrat", 105, Rarity.UNCOMMON, mage.cards.s.SengirAutocrat.class));
        cards.add(new SetCardInfo("Sensei's Divining Top", 232, Rarity.RARE, mage.cards.s.SenseisDiviningTop.class));
        cards.add(new SetCardInfo("Sentinel Spider", 185, Rarity.COMMON, mage.cards.s.SentinelSpider.class));
        cards.add(new SetCardInfo("Serendib Efreet", 70, Rarity.RARE, mage.cards.s.SerendibEfreet.class));
        cards.add(new SetCardInfo("Serra Angel", 28, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shaman of the Pack", 205, Rarity.UNCOMMON, mage.cards.s.ShamanOfThePack.class));
        cards.add(new SetCardInfo("Shardless Agent", 206, Rarity.RARE, mage.cards.s.ShardlessAgent.class));
        cards.add(new SetCardInfo("Shelter", 29, Rarity.COMMON, mage.cards.s.Shelter.class));
        cards.add(new SetCardInfo("Shoreline Ranger", 71, Rarity.COMMON, mage.cards.s.ShorelineRanger.class));
        cards.add(new SetCardInfo("Siege-Gang Commander", 147, Rarity.RARE, mage.cards.s.SiegeGangCommander.class));
        cards.add(new SetCardInfo("Silent Departure", 72, Rarity.COMMON, mage.cards.s.SilentDeparture.class));
        cards.add(new SetCardInfo("Silvos, Rogue Elemental", 186, Rarity.RARE, mage.cards.s.SilvosRogueElemental.class));
        cards.add(new SetCardInfo("Sinkhole", 106, Rarity.RARE, mage.cards.s.Sinkhole.class));
        cards.add(new SetCardInfo("Skulking Ghost", 107, Rarity.COMMON, mage.cards.s.SkulkingGhost.class));
        cards.add(new SetCardInfo("Sneak Attack", 148, Rarity.MYTHIC, mage.cards.s.SneakAttack.class));
        cards.add(new SetCardInfo("Soulcatcher", 30, Rarity.UNCOMMON, mage.cards.s.Soulcatcher.class));
        cards.add(new SetCardInfo("Sphinx of the Steel Wind", 207, Rarity.MYTHIC, mage.cards.s.SphinxOfTheSteelWind.class));
        cards.add(new SetCardInfo("Sprite Noble", 73, Rarity.UNCOMMON, mage.cards.s.SpriteNoble.class));
        cards.add(new SetCardInfo("Squadron Hawk", 31, Rarity.COMMON, mage.cards.s.SquadronHawk.class));
        cards.add(new SetCardInfo("Stingscourger", 149, Rarity.COMMON, mage.cards.s.Stingscourger.class));
        cards.add(new SetCardInfo("Stupefying Touch", 74, Rarity.COMMON, mage.cards.s.StupefyingTouch.class));
        cards.add(new SetCardInfo("Sulfuric Vortex", 150, Rarity.RARE, mage.cards.s.SulfuricVortex.class));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 245, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 32, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvan Library", 187, Rarity.RARE, mage.cards.s.SylvanLibrary.class));
        cards.add(new SetCardInfo("Sylvan Might", 188, Rarity.COMMON, mage.cards.s.SylvanMight.class));
        cards.add(new SetCardInfo("Thornweald Archer", 189, Rarity.COMMON, mage.cards.t.ThornwealdArcher.class));
        cards.add(new SetCardInfo("Thornwood Falls", 246, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thunderclap Wyvern", 208, Rarity.UNCOMMON, mage.cards.t.ThunderclapWyvern.class));
        cards.add(new SetCardInfo("Ticking Gnomes", 233, Rarity.UNCOMMON, mage.cards.t.TickingGnomes.class));
        cards.add(new SetCardInfo("Tidal Wave", 75, Rarity.COMMON, mage.cards.t.TidalWave.class));
        cards.add(new SetCardInfo("Timberwatch Elf", 190, Rarity.UNCOMMON, mage.cards.t.TimberwatchElf.class));
        cards.add(new SetCardInfo("Tooth and Claw", 151, Rarity.UNCOMMON, mage.cards.t.ToothAndClaw.class));
        cards.add(new SetCardInfo("Torrent of Souls", 217, Rarity.UNCOMMON, mage.cards.t.TorrentOfSouls.class));
        cards.add(new SetCardInfo("Toxic Deluge", 108, Rarity.RARE, mage.cards.t.ToxicDeluge.class));
        cards.add(new SetCardInfo("Tragic Slip", 109, Rarity.COMMON, mage.cards.t.TragicSlip.class));
        cards.add(new SetCardInfo("Tranquil Cove", 247, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Trygon Predator", 209, Rarity.UNCOMMON, mage.cards.t.TrygonPredator.class));
        cards.add(new SetCardInfo("Twisted Abomination", 110, Rarity.COMMON, mage.cards.t.TwistedAbomination.class));
        cards.add(new SetCardInfo("Undying Rage", 152, Rarity.COMMON, mage.cards.u.UndyingRage.class));
        cards.add(new SetCardInfo("Unexpectedly Absent", 33, Rarity.RARE, mage.cards.u.UnexpectedlyAbsent.class));
        cards.add(new SetCardInfo("Urborg Uprising", 111, Rarity.COMMON, mage.cards.u.UrborgUprising.class));
        cards.add(new SetCardInfo("Vampiric Tutor", 112, Rarity.MYTHIC, mage.cards.v.VampiricTutor.class));
        cards.add(new SetCardInfo("Victimize", 113, Rarity.UNCOMMON, mage.cards.v.Victimize.class));
        cards.add(new SetCardInfo("Vindicate", 210, Rarity.RARE, mage.cards.v.Vindicate.class));
        cards.add(new SetCardInfo("Visara the Dreadful", 114, Rarity.RARE, mage.cards.v.VisaraTheDreadful.class));
        cards.add(new SetCardInfo("Void", 211, Rarity.RARE, mage.cards.v.Void.class));
        cards.add(new SetCardInfo("Wakedancer", 116, Rarity.COMMON, mage.cards.w.Wakedancer.class));
        cards.add(new SetCardInfo("Wake of Vultures", 115, Rarity.COMMON, mage.cards.w.WakeOfVultures.class));
        cards.add(new SetCardInfo("Wall of Omens", 34, Rarity.UNCOMMON, mage.cards.w.WallOfOmens.class));
        cards.add(new SetCardInfo("Warden of Evos Isle", 76, Rarity.COMMON, mage.cards.w.WardenOfEvosIsle.class));
        cards.add(new SetCardInfo("War Priest of Thune", 35, Rarity.UNCOMMON, mage.cards.w.WarPriestOfThune.class));
        cards.add(new SetCardInfo("Wasteland", 248, Rarity.RARE, mage.cards.w.Wasteland.class));
        cards.add(new SetCardInfo("Wee Dragonauts", 212, Rarity.UNCOMMON, mage.cards.w.WeeDragonauts.class));
        cards.add(new SetCardInfo("Welkin Guide", 36, Rarity.COMMON, mage.cards.w.WelkinGuide.class));
        cards.add(new SetCardInfo("Werebear", 191, Rarity.COMMON, mage.cards.w.Werebear.class));
        cards.add(new SetCardInfo("Whitemane Lion", 37, Rarity.COMMON, mage.cards.w.WhitemaneLion.class));
        cards.add(new SetCardInfo("Wildfire Emissary", 153, Rarity.COMMON, mage.cards.w.WildfireEmissary.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 249, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Winter Orb", 234, Rarity.RARE, mage.cards.w.WinterOrb.class));
        cards.add(new SetCardInfo("Wirewood Symbiote", 192, Rarity.UNCOMMON, mage.cards.w.WirewoodSymbiote.class));
        cards.add(new SetCardInfo("Wonder", 77, Rarity.UNCOMMON, mage.cards.w.Wonder.class));
        cards.add(new SetCardInfo("Worldgorger Dragon", 154, Rarity.MYTHIC, mage.cards.w.WorldgorgerDragon.class));
        cards.add(new SetCardInfo("Worn Powerstone", 235, Rarity.UNCOMMON, mage.cards.w.WornPowerstone.class));
        cards.add(new SetCardInfo("Wrath of God", 38, Rarity.RARE, mage.cards.w.WrathOfGod.class));
        cards.add(new SetCardInfo("Xantid Swarm", 193, Rarity.RARE, mage.cards.x.XantidSwarm.class));
        cards.add(new SetCardInfo("Yavimaya Enchantress", 194, Rarity.COMMON, mage.cards.y.YavimayaEnchantress.class));
        cards.add(new SetCardInfo("Young Pyromancer", 155, Rarity.UNCOMMON, mage.cards.y.YoungPyromancer.class));
        cards.add(new SetCardInfo("Zealous Persecution", 213, Rarity.UNCOMMON, mage.cards.z.ZealousPersecution.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new EternalMastersCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/ema.html
// Using USA collation for all rarities
// Foil rare sheet used for regular rares as regular rare sheet is not known
class EternalMastersCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "25", "85", "153", "4", "84", "117", "36", "104", "126", "1", "116", "129", "24", "107", "138", "27", "94", "136", "29", "111", "149", "8", "100", "139", "3", "80", "125", "14", "86", "152", "31", "103", "126", "4", "84", "153", "36", "85", "120", "25", "104", "117", "1", "111", "149", "27", "107", "129", "24", "94", "136", "8", "116", "139", "3", "80", "138", "29", "100", "152", "31", "103", "125", "14", "86", "120");
    private final CardRun commonB = new CardRun(true, "51", "162", "74", "189", "52", "176", "53", "188", "63", "184", "76", "179", "60", "161", "69", "183", "72", "156", "51", "164", "41", "194", "64", "184", "76", "176", "52", "189", "74", "188", "63", "162", "53", "161", "60", "156", "69", "183", "72", "194", "41", "179", "64", "164", "51", "189", "53", "184", "52", "188", "76", "176", "63", "161", "69", "162", "74", "156", "72", "183", "60", "194", "41", "179", "64", "164");
    private final CardRun commonC1 = new CardRun(true, "237", "165", "243", "144", "88", "236", "71", "6", "140", "115", "238", "109", "146", "20", "239", "47", "89", "128", "244", "23", "247", "237", "137", "75", "178", "110", "21", "144", "45", "243", "115", "163", "6", "140", "71", "88", "238", "128", "165", "239", "75", "109", "236", "137", "178", "20", "45", "89", "146", "21", "163", "47", "244", "110", "23");
    private final CardRun commonC2 = new CardRun(true, "185", "246", "122", "37", "97", "43", "245", "191", "102", "249", "59", "167", "175", "26", "130", "229", "247", "65", "18", "185", "245", "43", "102", "191", "246", "59", "37", "97", "122", "249", "175", "167", "65", "245", "18", "185", "229", "130", "102", "26", "43", "229", "59", "249", "97", "122", "37", "191", "246", "65", "167", "175", "18", "130", "26");
    private final CardRun uncommonA = new CardRun(true, "123", "205", "182", "32", "61", "172", "195", "192", "133", "79", "227", "11", "170", "201", "155", "180", "12", "151", "28", "166", "119", "54", "160", "5", "10", "226", "131", "190", "118", "90", "134", "200", "11", "79", "121", "182", "99", "135", "201", "32", "174", "224", "197", "227", "172", "48", "133", "205", "190", "119", "159", "28", "192", "61", "123", "195", "12", "170", "155", "226", "54", "166", "151", "180", "118", "192", "10", "121", "159", "48", "174", "135", "99", "200", "160", "32", "195", "224", "123", "172", "205", "79", "5", "133", "170", "131", "61", "134", "166", "201", "28", "155", "227", "180", "119", "182", "54", "90", "11", "197", "226", "151", "190", "12", "131", "99", "118", "5", "134", "159", "121", "48", "90", "135", "197", "174", "224", "10", "200", "160");
    private final CardRun uncommonB = new CardRun(true, "68", "101", "212", "15", "233", "66", "81", "30", "58", "141", "92", "213", "55", "105", "34", "83", "77", "113", "221", "217", "235", "142", "67", "242", "157", "13", "208", "44", "91", "230", "168", "73", "209", "40", "19", "58", "231", "78", "35", "218", "212", "81", "141", "95", "233", "68", "92", "30", "142", "101", "66", "15", "213", "34", "55", "105", "217", "83", "67", "235", "44", "113", "242", "221", "77", "95", "168", "78", "13", "68", "208", "230", "157", "91", "73", "141", "105", "19", "40", "209", "66", "231", "81", "30", "218", "101", "58", "83", "212", "77", "142", "92", "213", "15", "233", "113", "55", "34", "67", "221", "35", "217", "44", "91", "235", "168", "242", "13", "157", "208", "95", "218", "73", "19", "231", "78", "209", "40", "230", "35");
    private final CardRun rare = new CardRun(true, "114", "87", "186", "57", "106", "214", "56", "9", "93", "228", "22", "203", "62", "187", "148", "143", "215", "70", "108", "240", "232", "33", "206", "17", "193", "177", "145", "216", "82", "124", "223", "234", "38", "210", "16", "196", "207", "147", "220", "96", "143", "199", "241", "39", "211", "22", "198", "225", "150", "222", "7", "145", "9", "248", "42", "108", "33", "202", "219", "169", "186", "127", "147", "106", "132", "46", "56", "38", "228", "204", "171", "187", "114", "150", "154", "214", "50", "223", "39", "232", "158", "173", "193", "17", "169", "87", "215", "203", "93", "42", "234", "112", "181", "196", "96", "171", "124", "216", "206", "127", "46", "241", "98", "70", "198", "62", "173", "49", "220", "210", "16", "50", "248", "2", "82", "202", "132", "181", "7", "222", "211");
    private final CardRun foilCommon = new CardRun(true, "1", "80", "41", "156", "117", "236", "24", "249", "63", "178", "137", "3", "84", "43", "161", "120", "237", "25", "103", "64", "179", "138", "4", "85", "45", "162", "122", "238", "26", "104", "65", "183", "139", "6", "86", "47", "163", "125", "239", "27", "107", "69", "184", "140", "8", "88", "51", "164", "126", "243", "29", "109", "71", "185", "144", "14", "89", "52", "165", "128", "244", "229", "110", "72", "188", "146", "18", "94", "53", "167", "129", "245", "36", "111", "74", "189", "149", "20", "97", "59", "175", "130", "246", "37", "115", "75", "191", "152", "21", "100", "60", "176", "136", "247", "31", "116", "76", "194", "153", "23", "102");
    private final CardRun foilUncommon = new CardRun(true, "5", "78", "40", "195", "118", "218", "157", "30", "105", "135", "174", "10", "79", "44", "197", "119", "221", "159", "32", "212", "141", "180", "11", "81", "48", "200", "121", "224", "160", "34", "213", "142", "182", "12", "83", "54", "201", "123", "226", "166", "35", "217", "151", "190", "13", "90", "55", "205", "131", "227", "168", "113", "233", "155", "192", "15", "91", "58", "208", "133", "230", "170", "73", "235", "99", "67", "19", "92", "61", "209", "134", "231", "172", "77", "242", "101", "68", "28", "95", "66");

    private final BoosterStructure AABBC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure FC = new BoosterStructure(foilCommon);
    private final BoosterStructure FU = new BoosterStructure(foilUncommon);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration foilRuns = new RarityConfiguration(
            FC, FC, FC, FC, FC, FC, FC, FC, FC, FC,
            FU, FU, FU,
            R1
    );

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(foilRuns.getNext().makeRun());
        return booster;
    }
}
