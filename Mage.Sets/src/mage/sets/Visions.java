package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author North
 */
public final class Visions extends ExpansionSet {

    private static final Visions instance = new Visions();

    public static Visions getInstance() {
        return instance;
    }

    private Visions() {
        super("Visions", "VIS", ExpansionSet.buildDate(1997, 1, 11), SetType.EXPANSION);
        this.blockName = "Mirage";
        this.parentSet = Mirage.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Aku Djinn", 51, Rarity.RARE, mage.cards.a.AkuDjinn.class, RETRO_ART));
        cards.add(new SetCardInfo("Anvil of Bogardan", 141, Rarity.RARE, mage.cards.a.AnvilOfBogardan.class, RETRO_ART));
        cards.add(new SetCardInfo("Archangel", 1, Rarity.RARE, mage.cards.a.Archangel.class, RETRO_ART));
        cards.add(new SetCardInfo("Army Ants", 126, Rarity.UNCOMMON, mage.cards.a.ArmyAnts.class, RETRO_ART));
        cards.add(new SetCardInfo("Betrayal", 26, Rarity.COMMON, mage.cards.b.Betrayal.class, RETRO_ART));
        cards.add(new SetCardInfo("Blanket of Night", 52, Rarity.UNCOMMON, mage.cards.b.BlanketOfNight.class, RETRO_ART));
        cards.add(new SetCardInfo("Bogardan Phoenix", 76, Rarity.RARE, mage.cards.b.BogardanPhoenix.class, RETRO_ART));
        cards.add(new SetCardInfo("Brass-Talon Chimera", 142, Rarity.UNCOMMON, mage.cards.b.BrassTalonChimera.class, RETRO_ART));
        cards.add(new SetCardInfo("Breathstealer's Crypt", 127, Rarity.RARE, mage.cards.b.BreathstealersCrypt.class, RETRO_ART));
        cards.add(new SetCardInfo("Breezekeeper", 27, Rarity.COMMON, mage.cards.b.Breezekeeper.class, RETRO_ART));
        cards.add(new SetCardInfo("Brood of Cockroaches", 53, Rarity.UNCOMMON, mage.cards.b.BroodOfCockroaches.class, RETRO_ART));
        cards.add(new SetCardInfo("Bull Elephant", 101, Rarity.COMMON, mage.cards.b.BullElephant.class, RETRO_ART));
        cards.add(new SetCardInfo("Chronatog", 28, Rarity.RARE, mage.cards.c.Chronatog.class, RETRO_ART));
        cards.add(new SetCardInfo("City of Solitude", 102, Rarity.RARE, mage.cards.c.CityOfSolitude.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloud Elemental", 29, Rarity.COMMON, mage.cards.c.CloudElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Coercion", 54, Rarity.COMMON, mage.cards.c.Coercion.class, RETRO_ART));
        cards.add(new SetCardInfo("Coral Atoll", 160, Rarity.UNCOMMON, mage.cards.c.CoralAtoll.class, RETRO_ART));
        cards.add(new SetCardInfo("Corrosion", 128, Rarity.RARE, mage.cards.c.Corrosion.class, RETRO_ART));
        cards.add(new SetCardInfo("Creeping Mold", 103, Rarity.UNCOMMON, mage.cards.c.CreepingMold.class, RETRO_ART));
        cards.add(new SetCardInfo("Crypt Rats", 55, Rarity.COMMON, mage.cards.c.CryptRats.class, RETRO_ART));
        cards.add(new SetCardInfo("Daraja Griffin", 2, Rarity.UNCOMMON, mage.cards.d.DarajaGriffin.class, RETRO_ART));
        cards.add(new SetCardInfo("Dark Privilege", 56, Rarity.COMMON, mage.cards.d.DarkPrivilege.class, RETRO_ART));
        cards.add(new SetCardInfo("Death Watch", 57, Rarity.COMMON, mage.cards.d.DeathWatch.class, RETRO_ART));
        cards.add(new SetCardInfo("Desertion", 30, Rarity.RARE, mage.cards.d.Desertion.class, RETRO_ART));
        cards.add(new SetCardInfo("Desolation", 58, Rarity.UNCOMMON, mage.cards.d.Desolation.class, RETRO_ART));
        cards.add(new SetCardInfo("Diamond Kaleidoscope", 143, Rarity.RARE, mage.cards.d.DiamondKaleidoscope.class, RETRO_ART));
        cards.add(new SetCardInfo("Dormant Volcano", 161, Rarity.UNCOMMON, mage.cards.d.DormantVolcano.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Mask", 144, Rarity.UNCOMMON, mage.cards.d.DragonMask.class, RETRO_ART));
        cards.add(new SetCardInfo("Dream Tides", 31, Rarity.UNCOMMON, mage.cards.d.DreamTides.class, RETRO_ART));
        cards.add(new SetCardInfo("Dwarven Vigilantes", 77, Rarity.COMMON, mage.cards.d.DwarvenVigilantes.class, RETRO_ART));
        cards.add(new SetCardInfo("Elephant Grass", 104, Rarity.UNCOMMON, mage.cards.e.ElephantGrass.class, RETRO_ART));
        cards.add(new SetCardInfo("Elkin Lair", 78, Rarity.RARE, mage.cards.e.ElkinLair.class, RETRO_ART));
        cards.add(new SetCardInfo("Elven Cache", 105, Rarity.COMMON, mage.cards.e.ElvenCache.class, RETRO_ART));
        cards.add(new SetCardInfo("Emerald Charm", 106, Rarity.COMMON, mage.cards.e.EmeraldCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Equipoise", 3, Rarity.RARE, mage.cards.e.Equipoise.class, RETRO_ART));
        cards.add(new SetCardInfo("Everglades", 162, Rarity.UNCOMMON, mage.cards.e.Everglades.class, RETRO_ART));
        cards.add(new SetCardInfo("Eye of Singularity", 4, Rarity.RARE, mage.cards.e.EyeOfSingularity.class, RETRO_ART));
        cards.add(new SetCardInfo("Fallen Askari", 59, Rarity.COMMON, mage.cards.f.FallenAskari.class, RETRO_ART));
        cards.add(new SetCardInfo("Femeref Enchantress", 129, Rarity.RARE, mage.cards.f.FemerefEnchantress.class, RETRO_ART));
        cards.add(new SetCardInfo("Feral Instinct", 107, Rarity.COMMON, mage.cards.f.FeralInstinct.class, RETRO_ART));
        cards.add(new SetCardInfo("Fireblast", 79, Rarity.COMMON, mage.cards.f.Fireblast.class, RETRO_ART));
        cards.add(new SetCardInfo("Firestorm Hellkite", 130, Rarity.RARE, mage.cards.f.FirestormHellkite.class, RETRO_ART));
        cards.add(new SetCardInfo("Flooded Shoreline", 32, Rarity.RARE, mage.cards.f.FloodedShoreline.class, RETRO_ART));
        cards.add(new SetCardInfo("Forbidden Ritual", 60, Rarity.RARE, mage.cards.f.ForbiddenRitual.class, RETRO_ART));
        cards.add(new SetCardInfo("Foreshadow", 33, Rarity.UNCOMMON, mage.cards.f.Foreshadow.class, RETRO_ART));
        cards.add(new SetCardInfo("Freewind Falcon", 5, Rarity.COMMON, mage.cards.f.FreewindFalcon.class, RETRO_ART));
        cards.add(new SetCardInfo("Funeral Charm", 61, Rarity.COMMON, mage.cards.f.FuneralCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Caterpillar", 108, Rarity.COMMON, mage.cards.g.GiantCaterpillar.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Recruiter", 80, Rarity.UNCOMMON, mage.cards.g.GoblinRecruiter.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Swine-Rider", 81, Rarity.COMMON, mage.cards.g.GoblinSwineRider.class, RETRO_ART));
        cards.add(new SetCardInfo("Gossamer Chains", 6, Rarity.COMMON, mage.cards.g.GossamerChains.class, RETRO_ART));
        cards.add(new SetCardInfo("Griffin Canyon", 163, Rarity.RARE, mage.cards.g.GriffinCanyon.class, RETRO_ART));
        cards.add(new SetCardInfo("Guiding Spirit", 131, Rarity.RARE, mage.cards.g.GuidingSpirit.class, RETRO_ART));
        cards.add(new SetCardInfo("Hearth Charm", 82, Rarity.COMMON, mage.cards.h.HearthCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Heat Wave", 83, Rarity.UNCOMMON, mage.cards.h.HeatWave.class, RETRO_ART));
        cards.add(new SetCardInfo("Helm of Awakening", 145, Rarity.UNCOMMON, mage.cards.h.HelmOfAwakening.class, RETRO_ART));
        cards.add(new SetCardInfo("Honorable Passage", 7, Rarity.UNCOMMON, mage.cards.h.HonorablePassage.class, RETRO_ART));
        cards.add(new SetCardInfo("Hope Charm", 8, Rarity.COMMON, mage.cards.h.HopeCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Hulking Cyclops", 84, Rarity.UNCOMMON, mage.cards.h.HulkingCyclops.class, RETRO_ART));
        cards.add(new SetCardInfo("Impulse", 34, Rarity.COMMON, mage.cards.i.Impulse.class, RETRO_ART));
        cards.add(new SetCardInfo("Infantry Veteran", 9, Rarity.COMMON, mage.cards.i.InfantryVeteran.class, RETRO_ART));
        cards.add(new SetCardInfo("Infernal Harvest", 62, Rarity.COMMON, mage.cards.i.InfernalHarvest.class, RETRO_ART));
        cards.add(new SetCardInfo("Inspiration", 35, Rarity.COMMON, mage.cards.i.Inspiration.class, RETRO_ART));
        cards.add(new SetCardInfo("Iron-Heart Chimera", 146, Rarity.UNCOMMON, mage.cards.i.IronHeartChimera.class, RETRO_ART));
        cards.add(new SetCardInfo("Jamuraan Lion", 10, Rarity.COMMON, mage.cards.j.JamuraanLion.class, RETRO_ART));
        cards.add(new SetCardInfo("Juju Bubble", 147, Rarity.UNCOMMON, mage.cards.j.JujuBubble.class, RETRO_ART));
        cards.add(new SetCardInfo("Jungle Basin", 164, Rarity.UNCOMMON, mage.cards.j.JungleBasin.class, RETRO_ART));
        cards.add(new SetCardInfo("Kaervek's Spite", 63, Rarity.RARE, mage.cards.k.KaerveksSpite.class, RETRO_ART));
        cards.add(new SetCardInfo("Karoo", 165, Rarity.UNCOMMON, mage.cards.k.Karoo.class, RETRO_ART));
        cards.add(new SetCardInfo("Katabatic Winds", 109, Rarity.RARE, mage.cards.k.KatabaticWinds.class, RETRO_ART));
        cards.add(new SetCardInfo("Keeper of Kookus", 85, Rarity.COMMON, mage.cards.k.KeeperOfKookus.class, RETRO_ART));
        cards.add(new SetCardInfo("King Cheetah", 110, Rarity.COMMON, mage.cards.k.KingCheetah.class, RETRO_ART));
        cards.add(new SetCardInfo("Knight of the Mists", 36, Rarity.COMMON, mage.cards.k.KnightOfTheMists.class, RETRO_ART));
        cards.add(new SetCardInfo("Knight of Valor", 11, Rarity.COMMON, mage.cards.k.KnightOfValor.class, RETRO_ART));
        cards.add(new SetCardInfo("Kookus", 86, Rarity.RARE, mage.cards.k.Kookus.class, RETRO_ART));
        cards.add(new SetCardInfo("Kyscu Drake", 111, Rarity.UNCOMMON, mage.cards.k.KyscuDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Lead-Belly Chimera", 148, Rarity.UNCOMMON, mage.cards.l.LeadBellyChimera.class, RETRO_ART));
        cards.add(new SetCardInfo("Lichenthrope", 112, Rarity.RARE, mage.cards.l.Lichenthrope.class, RETRO_ART));
        cards.add(new SetCardInfo("Lightning Cloud", 87, Rarity.RARE, mage.cards.l.LightningCloud.class, RETRO_ART));
        cards.add(new SetCardInfo("Longbow Archer", 12, Rarity.UNCOMMON, mage.cards.l.LongbowArcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Magma Mine", 149, Rarity.UNCOMMON, mage.cards.m.MagmaMine.class, RETRO_ART));
        cards.add(new SetCardInfo("Man-o'-War", 37, Rarity.COMMON, mage.cards.m.ManOWar.class, RETRO_ART));
        cards.add(new SetCardInfo("Matopi Golem", 150, Rarity.UNCOMMON, mage.cards.m.MatopiGolem.class, RETRO_ART));
        cards.add(new SetCardInfo("Miraculous Recovery", 13, Rarity.UNCOMMON, mage.cards.m.MiraculousRecovery.class, RETRO_ART));
        cards.add(new SetCardInfo("Mob Mentality", 88, Rarity.UNCOMMON, mage.cards.m.MobMentality.class, RETRO_ART));
        cards.add(new SetCardInfo("Mortal Wound", 113, Rarity.COMMON, mage.cards.m.MortalWound.class, RETRO_ART));
        cards.add(new SetCardInfo("Mundungu", 132, Rarity.UNCOMMON, mage.cards.m.Mundungu.class, RETRO_ART));
        cards.add(new SetCardInfo("Mystic Veil", 38, Rarity.COMMON, mage.cards.m.MysticVeil.class, RETRO_ART));
        cards.add(new SetCardInfo("Natural Order", 114, Rarity.RARE, mage.cards.n.NaturalOrder.class, RETRO_ART));
        cards.add(new SetCardInfo("Necromancy", 64, Rarity.UNCOMMON, mage.cards.n.Necromancy.class, RETRO_ART));
        cards.add(new SetCardInfo("Necrosavant", 65, Rarity.RARE, mage.cards.n.Necrosavant.class, RETRO_ART));
        cards.add(new SetCardInfo("Nekrataal", 66, Rarity.UNCOMMON, mage.cards.n.Nekrataal.class, RETRO_ART));
        cards.add(new SetCardInfo("Ovinomancer", 39, Rarity.UNCOMMON, mage.cards.o.Ovinomancer.class, RETRO_ART));
        cards.add(new SetCardInfo("Panther Warriors", 115, Rarity.COMMON, mage.cards.p.PantherWarriors.class, RETRO_ART));
        cards.add(new SetCardInfo("Parapet", 14, Rarity.COMMON, mage.cards.p.Parapet.class, RETRO_ART));
        cards.add(new SetCardInfo("Peace Talks", 15, Rarity.UNCOMMON, mage.cards.p.PeaceTalks.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Marauder", 151, Rarity.RARE, mage.cards.p.PhyrexianMarauder.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Walker", 152, Rarity.COMMON, mage.cards.p.PhyrexianWalker.class, RETRO_ART));
        cards.add(new SetCardInfo("Pillar Tombs of Aku", 67, Rarity.RARE, mage.cards.p.PillarTombsOfAku.class, RETRO_ART));
        cards.add(new SetCardInfo("Prosperity", 40, Rarity.UNCOMMON, mage.cards.p.Prosperity.class, RETRO_ART));
        cards.add(new SetCardInfo("Pygmy Hippo", 133, Rarity.RARE, mage.cards.p.PygmyHippo.class, RETRO_ART));
        cards.add(new SetCardInfo("Python", 68, Rarity.COMMON, mage.cards.p.Python.class, RETRO_ART));
        cards.add(new SetCardInfo("Quicksand", 166, Rarity.UNCOMMON, mage.cards.q.Quicksand.class, RETRO_ART));
        cards.add(new SetCardInfo("Quirion Druid", 116, Rarity.RARE, mage.cards.q.QuirionDruid.class, RETRO_ART));
        cards.add(new SetCardInfo("Quirion Ranger", 117, Rarity.COMMON, mage.cards.q.QuirionRanger.class, RETRO_ART));
        cards.add(new SetCardInfo("Raging Gorilla", 90, Rarity.COMMON, mage.cards.r.RagingGorilla.class, RETRO_ART));
        cards.add(new SetCardInfo("Rainbow Efreet", 41, Rarity.RARE, mage.cards.r.RainbowEfreet.class, RETRO_ART));
        cards.add(new SetCardInfo("Relentless Assault", 91, Rarity.RARE, mage.cards.r.RelentlessAssault.class, RETRO_ART));
        cards.add(new SetCardInfo("Relic Ward", 16, Rarity.UNCOMMON, mage.cards.r.RelicWard.class, RETRO_ART));
        cards.add(new SetCardInfo("Remedy", 17, Rarity.COMMON, mage.cards.r.Remedy.class, RETRO_ART));
        cards.add(new SetCardInfo("Resistance Fighter", 18, Rarity.COMMON, mage.cards.r.ResistanceFighter.class, RETRO_ART));
        cards.add(new SetCardInfo("Retribution of the Meek", 19, Rarity.RARE, mage.cards.r.RetributionOfTheMeek.class, RETRO_ART));
        cards.add(new SetCardInfo("Righteous Aura", 20, Rarity.COMMON, mage.cards.r.RighteousAura.class, RETRO_ART));
        cards.add(new SetCardInfo("Righteous War", 134, Rarity.RARE, mage.cards.r.RighteousWar.class, RETRO_ART));
        cards.add(new SetCardInfo("River Boa", 118, Rarity.COMMON, mage.cards.r.RiverBoa.class, RETRO_ART));
        cards.add(new SetCardInfo("Rock Slide", 92, Rarity.COMMON, mage.cards.r.RockSlide.class, RETRO_ART));
        cards.add(new SetCardInfo("Rowen", 119, Rarity.RARE, mage.cards.r.Rowen.class, RETRO_ART));
        cards.add(new SetCardInfo("Sands of Time", 153, Rarity.RARE, mage.cards.s.SandsOfTime.class, RETRO_ART));
        cards.add(new SetCardInfo("Scalebane's Elite", 135, Rarity.UNCOMMON, mage.cards.s.ScalebanesElite.class, RETRO_ART));
        cards.add(new SetCardInfo("Shimmering Efreet", 42, Rarity.UNCOMMON, mage.cards.s.ShimmeringEfreet.class, RETRO_ART));
        cards.add(new SetCardInfo("Shrieking Drake", 43, Rarity.COMMON, mage.cards.s.ShriekingDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Simoon", 136, Rarity.UNCOMMON, mage.cards.s.Simoon.class, RETRO_ART));
        cards.add(new SetCardInfo("Sisay's Ring", 154, Rarity.COMMON, mage.cards.s.SisaysRing.class, RETRO_ART));
        cards.add(new SetCardInfo("Snake Basket", 155, Rarity.RARE, mage.cards.s.SnakeBasket.class, RETRO_ART));
        cards.add(new SetCardInfo("Solfatara", 93, Rarity.COMMON, mage.cards.s.Solfatara.class, RETRO_ART));
        cards.add(new SetCardInfo("Song of Blood", 94, Rarity.COMMON, mage.cards.s.SongOfBlood.class, RETRO_ART));
        cards.add(new SetCardInfo("Spider Climb", 120, Rarity.COMMON, mage.cards.s.SpiderClimb.class, RETRO_ART));
        cards.add(new SetCardInfo("Spitting Drake", 95, Rarity.UNCOMMON, mage.cards.s.SpittingDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Squandered Resources", 137, Rarity.RARE, mage.cards.s.SquanderedResources.class, RETRO_ART));
        cards.add(new SetCardInfo("Stampeding Wildebeests", 121, Rarity.UNCOMMON, mage.cards.s.StampedingWildebeests.class, RETRO_ART));
        cards.add(new SetCardInfo("Suleiman's Legacy", 138, Rarity.RARE, mage.cards.s.SuleimansLegacy.class, RETRO_ART));
        cards.add(new SetCardInfo("Summer Bloom", 122, Rarity.UNCOMMON, mage.cards.s.SummerBloom.class, RETRO_ART));
        cards.add(new SetCardInfo("Sun Clasp", 21, Rarity.COMMON, mage.cards.s.SunClasp.class, RETRO_ART));
        cards.add(new SetCardInfo("Talruum Champion", 97, Rarity.COMMON, mage.cards.t.TalruumChampion.class, RETRO_ART));
        cards.add(new SetCardInfo("Talruum Piper", 98, Rarity.UNCOMMON, mage.cards.t.TalruumPiper.class, RETRO_ART));
        cards.add(new SetCardInfo("Tar Pit Warrior", 70, Rarity.COMMON, mage.cards.t.TarPitWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Teferi's Honor Guard", 22, Rarity.UNCOMMON, mage.cards.t.TeferisHonorGuard.class, RETRO_ART));
        cards.add(new SetCardInfo("Teferi's Puzzle Box", 156, Rarity.RARE, mage.cards.t.TeferisPuzzleBox.class, RETRO_ART));
        cards.add(new SetCardInfo("Teferi's Realm", 44, Rarity.RARE, mage.cards.t.TeferisRealm.class, RETRO_ART));
        cards.add(new SetCardInfo("Tempest Drake", 139, Rarity.UNCOMMON, mage.cards.t.TempestDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Three Wishes", 45, Rarity.RARE, mage.cards.t.ThreeWishes.class, RETRO_ART));
        cards.add(new SetCardInfo("Tin-Wing Chimera", 157, Rarity.UNCOMMON, mage.cards.t.TinWingChimera.class, RETRO_ART));
        cards.add(new SetCardInfo("Tithe", 23, Rarity.RARE, mage.cards.t.Tithe.class, RETRO_ART));
        cards.add(new SetCardInfo("Tremor", 99, Rarity.COMMON, mage.cards.t.Tremor.class, RETRO_ART));
        cards.add(new SetCardInfo("Triangle of War", 158, Rarity.RARE, mage.cards.t.TriangleOfWar.class, RETRO_ART));
        cards.add(new SetCardInfo("Uktabi Orangutan", 123, Rarity.UNCOMMON, mage.cards.u.UktabiOrangutan.class, RETRO_ART));
        cards.add(new SetCardInfo("Undiscovered Paradise", 167, Rarity.RARE, mage.cards.u.UndiscoveredParadise.class, RETRO_ART));
        cards.add(new SetCardInfo("Undo", 47, Rarity.COMMON, mage.cards.u.Undo.class, RETRO_ART));
        cards.add(new SetCardInfo("Urborg Mindsucker", 71, Rarity.COMMON, mage.cards.u.UrborgMindsucker.class, RETRO_ART));
        cards.add(new SetCardInfo("Vampiric Tutor", 72, Rarity.RARE, mage.cards.v.VampiricTutor.class, RETRO_ART));
        cards.add(new SetCardInfo("Vampirism", 73, Rarity.UNCOMMON, mage.cards.v.Vampirism.class, RETRO_ART));
        cards.add(new SetCardInfo("Vanishing", 48, Rarity.COMMON, mage.cards.v.Vanishing.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Sandstalker", 100, Rarity.UNCOMMON, mage.cards.v.ViashinoSandstalker.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashivan Dragon", 140, Rarity.RARE, mage.cards.v.ViashivanDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Vision Charm", 49, Rarity.COMMON, mage.cards.v.VisionCharm.class, RETRO_ART));
        cards.add(new SetCardInfo("Wake of Vultures", 74, Rarity.COMMON, mage.cards.w.WakeOfVultures.class, RETRO_ART));
        cards.add(new SetCardInfo("Wand of Denial", 159, Rarity.RARE, mage.cards.w.WandOfDenial.class, RETRO_ART));
        cards.add(new SetCardInfo("Warrior's Honor", 24, Rarity.COMMON, mage.cards.w.WarriorsHonor.class, RETRO_ART));
        cards.add(new SetCardInfo("Warthog", 124, Rarity.COMMON, mage.cards.w.Warthog.class, RETRO_ART));
        cards.add(new SetCardInfo("Waterspout Djinn", 50, Rarity.UNCOMMON, mage.cards.w.WaterspoutDjinn.class, RETRO_ART));
        cards.add(new SetCardInfo("Wicked Reward", 75, Rarity.COMMON, mage.cards.w.WickedReward.class, RETRO_ART));
        cards.add(new SetCardInfo("Wind Shear", 125, Rarity.UNCOMMON, mage.cards.w.WindShear.class, RETRO_ART));
        cards.add(new SetCardInfo("Zhalfirin Crusader", 25, Rarity.RARE, mage.cards.z.ZhalfirinCrusader.class, RETRO_ART));
    }

    @Override
    public BoosterCollator createCollator() {
        return new VisionsCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/vis.html
// Using USA sequential collation
class VisionsCollator implements BoosterCollator {

    private final CardRun commonA = new CardRun(true, "59", "93", "108", "37", "8", "54", "92", "117", "27", "14", "62", "96", "120", "49", "11", "57", "94", "107", "37", "5", "61", "85", "101", "49", "11", "75", "96", "107", "36", "18", "57", "92", "106", "48", "10", "54", "99", "117", "38", "5", "62", "93", "101", "48", "8", "59", "85", "108", "38", "10", "75", "99", "106", "36", "14", "61", "94", "120", "27", "18");
    private final CardRun commonB = new CardRun(true, "55", "82", "115", "24", "34", "70", "90", "105", "17", "43", "56", "77", "118", "17", "26", "55", "90", "115", "21", "26", "74", "82", "118", "24", "29", "71", "79", "113", "6", "47", "74", "97", "110", "20", "29", "70", "77", "105", "21", "34", "71", "79", "110", "6", "43", "56", "97", "113", "20", "47");
    private final CardRun commonC = new CardRun(false, "68", "81", "124", "154", "9", "35", "152");
    // omitted "46" unimplemented Time and Tide
    private final CardRun uncommon = new CardRun(false, "157", "125", "40", "161", "7", "64", "111", "132", "83", "22", "148", "22", "53", "123", "142", "149", "13", "146", "122", "149", "66", "150", "31", "95", "15", "58", "122", "50", "84", "111", "31", "88", "150", "69", "135", "50", "135", "16", "166", "80", "162", "64", "103", "160", "88", "145", "52", "144", "136", "121", "148", "164", "12", "144", "123", "162", "100", "12", "42", "164", "7", "53", "104", "142", "84", "2", "73", "136", "16", "73", "161", "33", "98", "15", "58", "147", "39", "80", "157", "42", "100", "165", "147", "125", "139", "98", "160", "66", "95", "132", "126", "121", "39", "126", "13", "52", "104", "33", "145", "83", "166", "69", "103", "40", "146", "2", "139", "165");
    // omitted "89" unimplemented Ogre Enforcer
    private final CardRun rare = new CardRun(false, "151", "45", "137", "114", "131", "72", "143", "4", "140", "63", "28", "153", "109", "129", "60", "44", "23", "155", "76", "128", "30", "156", "112", "158", "51", "134", "25", "130", "78", "141", "32", "3", "116", "86", "67", "159", "1", "102", "91", "127", "41", "167", "119", "138", "65", "133", "19", "163", "87");

    private final BoosterStructure AAAAABBBBBC = new BoosterStructure(
        commonA, commonA, commonA, commonA, commonA,
        commonB, commonB, commonB, commonB, commonB,
        commonC
    );
    private final BoosterStructure AAAAAAAACCC = new BoosterStructure(
        commonA, commonA, commonA, commonA, commonA, commonA, commonA, commonA,
        commonC, commonC, commonC
    );
    private final BoosterStructure AAAAAAAAACC = new BoosterStructure(
        commonA, commonA, commonA, commonA, commonA, commonA, commonA, commonA, commonA,
        commonC, commonC
    );
    private final BoosterStructure U3 = new BoosterStructure(uncommon, uncommon, uncommon);
    private final BoosterStructure R1 = new BoosterStructure(rare);

    // guesses from lethe based on AAAAAAAACCC observed and likely sheet structure  
    private final RarityConfiguration commonRuns = new RarityConfiguration(
        AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC,
        AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC,
        AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC,
        AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC,
        AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC,
        AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC,
        AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC,
        AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC,
        AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC,
        AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC, AAAAABBBBBC,
        AAAAAAAACCC, AAAAAAAACCC, AAAAAAAACCC, AAAAAAAACCC, AAAAAAAACCC,
        AAAAAAAAACC
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(U3);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        return booster;
    }
}
