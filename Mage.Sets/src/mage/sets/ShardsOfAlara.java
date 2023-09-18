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
public final class ShardsOfAlara extends ExpansionSet {

    private static final ShardsOfAlara instance = new ShardsOfAlara();

    public static ShardsOfAlara getInstance() {
        return instance;
    }

    private ShardsOfAlara() {
        // release date of Shards of Alara was October 3rd, 2008. Was previously entered as August 27.
        super("Shards of Alara", "ALA", ExpansionSet.buildDate(2008, 10, 3), SetType.EXPANSION);
        this.blockName = "Shards of Alara";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Ad Nauseam", 63, Rarity.RARE, mage.cards.a.AdNauseam.class));
        cards.add(new SetCardInfo("Agony Warp", 153, Rarity.COMMON, mage.cards.a.AgonyWarp.class));
        cards.add(new SetCardInfo("Ajani Vengeant", 154, Rarity.MYTHIC, mage.cards.a.AjaniVengeant.class));
        cards.add(new SetCardInfo("Akrasan Squire", 1, Rarity.COMMON, mage.cards.a.AkrasanSquire.class));
        cards.add(new SetCardInfo("Algae Gharial", 123, Rarity.UNCOMMON, mage.cards.a.AlgaeGharial.class));
        cards.add(new SetCardInfo("Angelic Benediction", 3, Rarity.UNCOMMON, mage.cards.a.AngelicBenediction.class));
        cards.add(new SetCardInfo("Angel's Herald", 2, Rarity.UNCOMMON, mage.cards.a.AngelsHerald.class));
        cards.add(new SetCardInfo("Angelsong", 4, Rarity.COMMON, mage.cards.a.Angelsong.class));
        cards.add(new SetCardInfo("Arcane Sanctum", 220, Rarity.UNCOMMON, mage.cards.a.ArcaneSanctum.class));
        cards.add(new SetCardInfo("Archdemon of Unx", 64, Rarity.RARE, mage.cards.a.ArchdemonOfUnx.class));
        cards.add(new SetCardInfo("Banewasp Affliction", 65, Rarity.COMMON, mage.cards.b.BanewaspAffliction.class));
        cards.add(new SetCardInfo("Bant Battlemage", 5, Rarity.UNCOMMON, mage.cards.b.BantBattlemage.class));
        cards.add(new SetCardInfo("Bant Charm", 155, Rarity.UNCOMMON, mage.cards.b.BantCharm.class));
        cards.add(new SetCardInfo("Bant Panorama", 221, Rarity.COMMON, mage.cards.b.BantPanorama.class));
        cards.add(new SetCardInfo("Battlegrace Angel", 6, Rarity.RARE, mage.cards.b.BattlegraceAngel.class));
        cards.add(new SetCardInfo("Behemoth's Herald", 124, Rarity.UNCOMMON, mage.cards.b.BehemothsHerald.class));
        cards.add(new SetCardInfo("Blightning", 156, Rarity.COMMON, mage.cards.b.Blightning.class));
        cards.add(new SetCardInfo("Blister Beetle", 66, Rarity.COMMON, mage.cards.b.BlisterBeetle.class));
        cards.add(new SetCardInfo("Blood Cultist", 157, Rarity.UNCOMMON, mage.cards.b.BloodCultist.class));
        cards.add(new SetCardInfo("Bloodpyre Elemental", 93, Rarity.COMMON, mage.cards.b.BloodpyreElemental.class));
        cards.add(new SetCardInfo("Bloodthorn Taunter", 94, Rarity.COMMON, mage.cards.b.BloodthornTaunter.class));
        cards.add(new SetCardInfo("Bone Splinters", 67, Rarity.COMMON, mage.cards.b.BoneSplinters.class));
        cards.add(new SetCardInfo("Branching Bolt", 158, Rarity.COMMON, mage.cards.b.BranchingBolt.class));
        cards.add(new SetCardInfo("Brilliant Ultimatum", 159, Rarity.RARE, mage.cards.b.BrilliantUltimatum.class));
        cards.add(new SetCardInfo("Broodmate Dragon", 160, Rarity.RARE, mage.cards.b.BroodmateDragon.class));
        cards.add(new SetCardInfo("Bull Cerodon", 161, Rarity.UNCOMMON, mage.cards.b.BullCerodon.class));
        cards.add(new SetCardInfo("Caldera Hellion", 95, Rarity.RARE, mage.cards.c.CalderaHellion.class));
        cards.add(new SetCardInfo("Call to Heel", 32, Rarity.COMMON, mage.cards.c.CallToHeel.class));
        cards.add(new SetCardInfo("Cancel", 33, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Carrion Thrash", 162, Rarity.COMMON, mage.cards.c.CarrionThrash.class));
        cards.add(new SetCardInfo("Cathartic Adept", 34, Rarity.COMMON, mage.cards.c.CatharticAdept.class));
        cards.add(new SetCardInfo("Cavern Thoctar", 125, Rarity.COMMON, mage.cards.c.CavernThoctar.class));
        cards.add(new SetCardInfo("Clarion Ultimatum", 163, Rarity.RARE, mage.cards.c.ClarionUltimatum.class));
        cards.add(new SetCardInfo("Cloudheath Drake", 35, Rarity.COMMON, mage.cards.c.CloudheathDrake.class));
        cards.add(new SetCardInfo("Coma Veil", 36, Rarity.COMMON, mage.cards.c.ComaVeil.class));
        cards.add(new SetCardInfo("Corpse Connoisseur", 68, Rarity.UNCOMMON, mage.cards.c.CorpseConnoisseur.class));
        cards.add(new SetCardInfo("Courier's Capsule", 37, Rarity.COMMON, mage.cards.c.CouriersCapsule.class));
        cards.add(new SetCardInfo("Court Archers", 126, Rarity.COMMON, mage.cards.c.CourtArchers.class));
        cards.add(new SetCardInfo("Covenant of Minds", 38, Rarity.RARE, mage.cards.c.CovenantOfMinds.class));
        cards.add(new SetCardInfo("Cradle of Vitality", 7, Rarity.RARE, mage.cards.c.CradleOfVitality.class));
        cards.add(new SetCardInfo("Crucible of Fire", 96, Rarity.RARE, mage.cards.c.CrucibleOfFire.class));
        cards.add(new SetCardInfo("Cruel Ultimatum", 164, Rarity.RARE, mage.cards.c.CruelUltimatum.class));
        cards.add(new SetCardInfo("Crumbling Necropolis", 222, Rarity.UNCOMMON, mage.cards.c.CrumblingNecropolis.class));
        cards.add(new SetCardInfo("Cunning Lethemancer", 69, Rarity.RARE, mage.cards.c.CunningLethemancer.class));
        cards.add(new SetCardInfo("Cylian Elf", 127, Rarity.COMMON, mage.cards.c.CylianElf.class));
        cards.add(new SetCardInfo("Dawnray Archer", 39, Rarity.UNCOMMON, mage.cards.d.DawnrayArcher.class));
        cards.add(new SetCardInfo("Death Baron", 70, Rarity.RARE, mage.cards.d.DeathBaron.class));
        cards.add(new SetCardInfo("Deathgreeter", 71, Rarity.COMMON, mage.cards.d.Deathgreeter.class));
        cards.add(new SetCardInfo("Deft Duelist", 165, Rarity.COMMON, mage.cards.d.DeftDuelist.class));
        cards.add(new SetCardInfo("Demon's Herald", 72, Rarity.UNCOMMON, mage.cards.d.DemonsHerald.class));
        cards.add(new SetCardInfo("Dispeller's Capsule", 8, Rarity.COMMON, mage.cards.d.DispellersCapsule.class));
        cards.add(new SetCardInfo("Dragon Fodder", 97, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dragon's Herald", 98, Rarity.UNCOMMON, mage.cards.d.DragonsHerald.class));
        cards.add(new SetCardInfo("Dreg Reaver", 73, Rarity.COMMON, mage.cards.d.DregReaver.class));
        cards.add(new SetCardInfo("Dregscape Zombie", 74, Rarity.COMMON, mage.cards.d.DregscapeZombie.class));
        cards.add(new SetCardInfo("Druid of the Anima", 128, Rarity.COMMON, mage.cards.d.DruidOfTheAnima.class));
        cards.add(new SetCardInfo("Drumhunter", 129, Rarity.UNCOMMON, mage.cards.d.Drumhunter.class));
        cards.add(new SetCardInfo("Elspeth, Knight-Errant", 9, Rarity.MYTHIC, mage.cards.e.ElspethKnightErrant.class));
        cards.add(new SetCardInfo("Elvish Visionary", 130, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Empyrial Archangel", 166, Rarity.MYTHIC, mage.cards.e.EmpyrialArchangel.class));
        cards.add(new SetCardInfo("Esper Battlemage", 40, Rarity.UNCOMMON, mage.cards.e.EsperBattlemage.class));
        cards.add(new SetCardInfo("Esper Charm", 167, Rarity.UNCOMMON, mage.cards.e.EsperCharm.class));
        cards.add(new SetCardInfo("Esper Panorama", 223, Rarity.COMMON, mage.cards.e.EsperPanorama.class));
        cards.add(new SetCardInfo("Etherium Astrolabe", 41, Rarity.UNCOMMON, mage.cards.e.EtheriumAstrolabe.class));
        cards.add(new SetCardInfo("Etherium Sculptor", 42, Rarity.COMMON, mage.cards.e.EtheriumSculptor.class));
        cards.add(new SetCardInfo("Ethersworn Canonist", 10, Rarity.RARE, mage.cards.e.EtherswornCanonist.class));
        cards.add(new SetCardInfo("Excommunicate", 11, Rarity.COMMON, mage.cards.e.Excommunicate.class));
        cards.add(new SetCardInfo("Executioner's Capsule", 75, Rarity.COMMON, mage.cards.e.ExecutionersCapsule.class));
        cards.add(new SetCardInfo("Exuberant Firestoker", 99, Rarity.UNCOMMON, mage.cards.e.ExuberantFirestoker.class));
        cards.add(new SetCardInfo("Fatestitcher", 43, Rarity.UNCOMMON, mage.cards.f.Fatestitcher.class));
        cards.add(new SetCardInfo("Feral Hydra", 131, Rarity.RARE, mage.cards.f.FeralHydra.class));
        cards.add(new SetCardInfo("Filigree Sages", 44, Rarity.UNCOMMON, mage.cards.f.FiligreeSages.class));
        cards.add(new SetCardInfo("Fire-Field Ogre", 168, Rarity.UNCOMMON, mage.cards.f.FireFieldOgre.class));
        cards.add(new SetCardInfo("Flameblast Dragon", 100, Rarity.RARE, mage.cards.f.FlameblastDragon.class));
        cards.add(new SetCardInfo("Fleshbag Marauder", 76, Rarity.UNCOMMON, mage.cards.f.FleshbagMarauder.class));
        cards.add(new SetCardInfo("Forest", 246, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 247, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 248, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 249, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gather Specimens", 45, Rarity.RARE, mage.cards.g.GatherSpecimens.class));
        cards.add(new SetCardInfo("Gift of the Gargantuan", 132, Rarity.COMMON, mage.cards.g.GiftOfTheGargantuan.class));
        cards.add(new SetCardInfo("Glaze Fiend", 77, Rarity.COMMON, mage.cards.g.GlazeFiend.class));
        cards.add(new SetCardInfo("Goblin Assault", 101, Rarity.RARE, mage.cards.g.GoblinAssault.class));
        cards.add(new SetCardInfo("Goblin Deathraiders", 169, Rarity.COMMON, mage.cards.g.GoblinDeathraiders.class));
        cards.add(new SetCardInfo("Goblin Mountaineer", 102, Rarity.COMMON, mage.cards.g.GoblinMountaineer.class));
        cards.add(new SetCardInfo("Godsire", 170, Rarity.MYTHIC, mage.cards.g.Godsire.class));
        cards.add(new SetCardInfo("Godtoucher", 133, Rarity.COMMON, mage.cards.g.Godtoucher.class));
        cards.add(new SetCardInfo("Grixis Battlemage", 78, Rarity.UNCOMMON, mage.cards.g.GrixisBattlemage.class));
        cards.add(new SetCardInfo("Grixis Charm", 171, Rarity.UNCOMMON, mage.cards.g.GrixisCharm.class));
        cards.add(new SetCardInfo("Grixis Panorama", 224, Rarity.COMMON, mage.cards.g.GrixisPanorama.class));
        cards.add(new SetCardInfo("Guardians of Akrasa", 12, Rarity.COMMON, mage.cards.g.GuardiansOfAkrasa.class));
        cards.add(new SetCardInfo("Gustrider Exuberant", 13, Rarity.COMMON, mage.cards.g.GustriderExuberant.class));
        cards.add(new SetCardInfo("Hellkite Overlord", 172, Rarity.MYTHIC, mage.cards.h.HellkiteOverlord.class));
        cards.add(new SetCardInfo("Hell's Thunder", 103, Rarity.RARE, mage.cards.h.HellsThunder.class));
        cards.add(new SetCardInfo("Hindering Light", 173, Rarity.COMMON, mage.cards.h.HinderingLight.class));
        cards.add(new SetCardInfo("Hissing Iguanar", 104, Rarity.COMMON, mage.cards.h.HissingIguanar.class));
        cards.add(new SetCardInfo("Immortal Coil", 79, Rarity.RARE, mage.cards.i.ImmortalCoil.class));
        cards.add(new SetCardInfo("Incurable Ogre", 105, Rarity.COMMON, mage.cards.i.IncurableOgre.class));
        cards.add(new SetCardInfo("Infest", 80, Rarity.UNCOMMON, mage.cards.i.Infest.class));
        cards.add(new SetCardInfo("Invincible Hymn", 14, Rarity.RARE, mage.cards.i.InvincibleHymn.class));
        cards.add(new SetCardInfo("Island", 234, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 235, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 236, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 237, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jhessian Infiltrator", 174, Rarity.UNCOMMON, mage.cards.j.JhessianInfiltrator.class));
        cards.add(new SetCardInfo("Jhessian Lookout", 46, Rarity.COMMON, mage.cards.j.JhessianLookout.class));
        cards.add(new SetCardInfo("Jund Battlemage", 106, Rarity.UNCOMMON, mage.cards.j.JundBattlemage.class));
        cards.add(new SetCardInfo("Jund Charm", 175, Rarity.UNCOMMON, mage.cards.j.JundCharm.class));
        cards.add(new SetCardInfo("Jund Panorama", 225, Rarity.COMMON, mage.cards.j.JundPanorama.class));
        cards.add(new SetCardInfo("Jungle Shrine", 226, Rarity.UNCOMMON, mage.cards.j.JungleShrine.class));
        cards.add(new SetCardInfo("Jungle Weaver", 134, Rarity.COMMON, mage.cards.j.JungleWeaver.class));
        cards.add(new SetCardInfo("Kathari Screecher", 47, Rarity.COMMON, mage.cards.k.KathariScreecher.class));
        cards.add(new SetCardInfo("Kederekt Creeper", 176, Rarity.COMMON, mage.cards.k.KederektCreeper.class));
        cards.add(new SetCardInfo("Kederekt Leviathan", 48, Rarity.RARE, mage.cards.k.KederektLeviathan.class));
        cards.add(new SetCardInfo("Keeper of Progenitus", 135, Rarity.RARE, mage.cards.k.KeeperOfProgenitus.class));
        cards.add(new SetCardInfo("Kiss of the Amesha", 177, Rarity.UNCOMMON, mage.cards.k.KissOfTheAmesha.class));
        cards.add(new SetCardInfo("Knight-Captain of Eos", 17, Rarity.RARE, mage.cards.k.KnightCaptainOfEos.class));
        cards.add(new SetCardInfo("Knight of the Skyward Eye", 15, Rarity.COMMON, mage.cards.k.KnightOfTheSkywardEye.class));
        cards.add(new SetCardInfo("Knight of the White Orchid", 16, Rarity.RARE, mage.cards.k.KnightOfTheWhiteOrchid.class));
        cards.add(new SetCardInfo("Kresh the Bloodbraided", 178, Rarity.MYTHIC, mage.cards.k.KreshTheBloodbraided.class));
        cards.add(new SetCardInfo("Lich's Mirror", 210, Rarity.MYTHIC, mage.cards.l.LichsMirror.class));
        cards.add(new SetCardInfo("Lightning Talons", 107, Rarity.COMMON, mage.cards.l.LightningTalons.class));
        cards.add(new SetCardInfo("Lush Growth", 136, Rarity.COMMON, mage.cards.l.LushGrowth.class));
        cards.add(new SetCardInfo("Magma Spray", 108, Rarity.COMMON, mage.cards.m.MagmaSpray.class));
        cards.add(new SetCardInfo("Manaplasm", 138, Rarity.RARE, mage.cards.m.Manaplasm.class));
        cards.add(new SetCardInfo("Marble Chalice", 18, Rarity.COMMON, mage.cards.m.MarbleChalice.class));
        cards.add(new SetCardInfo("Master of Etherium", 49, Rarity.RARE, mage.cards.m.MasterOfEtherium.class));
        cards.add(new SetCardInfo("Mayael the Anima", 179, Rarity.MYTHIC, mage.cards.m.MayaelTheAnima.class));
        cards.add(new SetCardInfo("Memory Erosion", 50, Rarity.RARE, mage.cards.m.MemoryErosion.class));
        cards.add(new SetCardInfo("Metallurgeon", 19, Rarity.UNCOMMON, mage.cards.m.Metallurgeon.class));
        cards.add(new SetCardInfo("Mighty Emergence", 137, Rarity.UNCOMMON, mage.cards.m.MightyEmergence.class));
        cards.add(new SetCardInfo("Mindlock Orb", 51, Rarity.RARE, mage.cards.m.MindlockOrb.class));
        cards.add(new SetCardInfo("Minion Reflector", 211, Rarity.RARE, mage.cards.m.MinionReflector.class));
        cards.add(new SetCardInfo("Mosstodon", 139, Rarity.COMMON, mage.cards.m.Mosstodon.class));
        cards.add(new SetCardInfo("Mountain", 242, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 243, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 244, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 245, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mycoloth", 140, Rarity.RARE, mage.cards.m.Mycoloth.class));
        cards.add(new SetCardInfo("Naturalize", 141, Rarity.COMMON, mage.cards.n.Naturalize.class));
        cards.add(new SetCardInfo("Naya Battlemage", 142, Rarity.UNCOMMON, mage.cards.n.NayaBattlemage.class));
        cards.add(new SetCardInfo("Naya Charm", 180, Rarity.UNCOMMON, mage.cards.n.NayaCharm.class));
        cards.add(new SetCardInfo("Naya Panorama", 227, Rarity.COMMON, mage.cards.n.NayaPanorama.class));
        cards.add(new SetCardInfo("Necrogenesis", 181, Rarity.UNCOMMON, mage.cards.n.Necrogenesis.class));
        cards.add(new SetCardInfo("Obelisk of Bant", 212, Rarity.COMMON, mage.cards.o.ObeliskOfBant.class));
        cards.add(new SetCardInfo("Obelisk of Esper", 213, Rarity.COMMON, mage.cards.o.ObeliskOfEsper.class));
        cards.add(new SetCardInfo("Obelisk of Grixis", 214, Rarity.COMMON, mage.cards.o.ObeliskOfGrixis.class));
        cards.add(new SetCardInfo("Obelisk of Jund", 215, Rarity.COMMON, mage.cards.o.ObeliskOfJund.class));
        cards.add(new SetCardInfo("Obelisk of Naya", 216, Rarity.COMMON, mage.cards.o.ObeliskOfNaya.class));
        cards.add(new SetCardInfo("Oblivion Ring", 20, Rarity.COMMON, mage.cards.o.OblivionRing.class));
        cards.add(new SetCardInfo("Onyx Goblet", 81, Rarity.COMMON, mage.cards.o.OnyxGoblet.class));
        cards.add(new SetCardInfo("Ooze Garden", 143, Rarity.RARE, mage.cards.o.OozeGarden.class));
        cards.add(new SetCardInfo("Outrider of Jhess", 52, Rarity.COMMON, mage.cards.o.OutriderOfJhess.class));
        cards.add(new SetCardInfo("Plains", 230, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 231, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 232, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 233, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Predator Dragon", 109, Rarity.RARE, mage.cards.p.PredatorDragon.class));
        cards.add(new SetCardInfo("Prince of Thralls", 182, Rarity.MYTHIC, mage.cards.p.PrinceOfThralls.class));
        cards.add(new SetCardInfo("Protomatter Powder", 53, Rarity.UNCOMMON, mage.cards.p.ProtomatterPowder.class));
        cards.add(new SetCardInfo("Punish Ignorance", 183, Rarity.RARE, mage.cards.p.PunishIgnorance.class));
        cards.add(new SetCardInfo("Puppet Conjurer", 82, Rarity.UNCOMMON, mage.cards.p.PuppetConjurer.class));
        cards.add(new SetCardInfo("Qasali Ambusher", 184, Rarity.UNCOMMON, mage.cards.q.QasaliAmbusher.class));
        cards.add(new SetCardInfo("Quietus Spike", 217, Rarity.RARE, mage.cards.q.QuietusSpike.class));
        cards.add(new SetCardInfo("Rafiq of the Many", 185, Rarity.MYTHIC, mage.cards.r.RafiqOfTheMany.class));
        cards.add(new SetCardInfo("Rakeclaw Gargantuan", 186, Rarity.COMMON, mage.cards.r.RakeclawGargantuan.class));
        cards.add(new SetCardInfo("Ranger of Eos", 21, Rarity.RARE, mage.cards.r.RangerOfEos.class));
        cards.add(new SetCardInfo("Realm Razer", 187, Rarity.RARE, mage.cards.r.RealmRazer.class));
        cards.add(new SetCardInfo("Relic of Progenitus", 218, Rarity.COMMON, mage.cards.r.RelicOfProgenitus.class));
        cards.add(new SetCardInfo("Resounding Roar", 144, Rarity.COMMON, mage.cards.r.ResoundingRoar.class));
        cards.add(new SetCardInfo("Resounding Scream", 83, Rarity.COMMON, mage.cards.r.ResoundingScream.class));
        cards.add(new SetCardInfo("Resounding Silence", 22, Rarity.COMMON, mage.cards.r.ResoundingSilence.class));
        cards.add(new SetCardInfo("Resounding Thunder", 110, Rarity.COMMON, mage.cards.r.ResoundingThunder.class));
        cards.add(new SetCardInfo("Resounding Wave", 54, Rarity.COMMON, mage.cards.r.ResoundingWave.class));
        cards.add(new SetCardInfo("Rhox Charger", 145, Rarity.UNCOMMON, mage.cards.r.RhoxCharger.class));
        cards.add(new SetCardInfo("Rhox War Monk", 188, Rarity.UNCOMMON, mage.cards.r.RhoxWarMonk.class));
        cards.add(new SetCardInfo("Ridge Rannet", 111, Rarity.COMMON, mage.cards.r.RidgeRannet.class));
        cards.add(new SetCardInfo("Rip-Clan Crasher", 189, Rarity.COMMON, mage.cards.r.RipClanCrasher.class));
        cards.add(new SetCardInfo("Rockcaster Platoon", 23, Rarity.UNCOMMON, mage.cards.r.RockcasterPlatoon.class));
        cards.add(new SetCardInfo("Rockslide Elemental", 112, Rarity.UNCOMMON, mage.cards.r.RockslideElemental.class));
        cards.add(new SetCardInfo("Sacellum Godspeaker", 146, Rarity.RARE, mage.cards.s.SacellumGodspeaker.class));
        cards.add(new SetCardInfo("Salvage Titan", 84, Rarity.RARE, mage.cards.s.SalvageTitan.class));
        cards.add(new SetCardInfo("Sanctum Gargoyle", 24, Rarity.COMMON, mage.cards.s.SanctumGargoyle.class));
        cards.add(new SetCardInfo("Sangrite Surge", 190, Rarity.UNCOMMON, mage.cards.s.SangriteSurge.class));
        cards.add(new SetCardInfo("Sarkhan Vol", 191, Rarity.MYTHIC, mage.cards.s.SarkhanVol.class));
        cards.add(new SetCardInfo("Savage Hunger", 147, Rarity.COMMON, mage.cards.s.SavageHunger.class));
        cards.add(new SetCardInfo("Savage Lands", 228, Rarity.UNCOMMON, mage.cards.s.SavageLands.class));
        cards.add(new SetCardInfo("Scavenger Drake", 85, Rarity.UNCOMMON, mage.cards.s.ScavengerDrake.class));
        cards.add(new SetCardInfo("Scourge Devil", 113, Rarity.UNCOMMON, mage.cards.s.ScourgeDevil.class));
        cards.add(new SetCardInfo("Scourglass", 25, Rarity.RARE, mage.cards.s.Scourglass.class));
        cards.add(new SetCardInfo("Seaside Citadel", 229, Rarity.UNCOMMON, mage.cards.s.SeasideCitadel.class));
        cards.add(new SetCardInfo("Sedraxis Specter", 192, Rarity.RARE, mage.cards.s.SedraxisSpecter.class));
        cards.add(new SetCardInfo("Sedris, the Traitor King", 193, Rarity.MYTHIC, mage.cards.s.SedrisTheTraitorKing.class));
        cards.add(new SetCardInfo("Shadowfeed", 86, Rarity.COMMON, mage.cards.s.Shadowfeed.class));
        cards.add(new SetCardInfo("Sharding Sphinx", 55, Rarity.RARE, mage.cards.s.ShardingSphinx.class));
        cards.add(new SetCardInfo("Sharuum the Hegemon", 194, Rarity.MYTHIC, mage.cards.s.SharuumTheHegemon.class));
        cards.add(new SetCardInfo("Shore Snapper", 87, Rarity.COMMON, mage.cards.s.ShoreSnapper.class));
        cards.add(new SetCardInfo("Sighted-Caste Sorcerer", 26, Rarity.COMMON, mage.cards.s.SightedCasteSorcerer.class));
        cards.add(new SetCardInfo("Sigil Blessing", 195, Rarity.COMMON, mage.cards.s.SigilBlessing.class));
        cards.add(new SetCardInfo("Sigiled Paladin", 27, Rarity.UNCOMMON, mage.cards.s.SigiledPaladin.class));
        cards.add(new SetCardInfo("Sigil of Distinction", 219, Rarity.RARE, mage.cards.s.SigilOfDistinction.class));
        cards.add(new SetCardInfo("Skeletal Kathari", 88, Rarity.COMMON, mage.cards.s.SkeletalKathari.class));
        cards.add(new SetCardInfo("Skeletonize", 114, Rarity.UNCOMMON, mage.cards.s.Skeletonize.class));
        cards.add(new SetCardInfo("Skill Borrower", 56, Rarity.RARE, mage.cards.s.SkillBorrower.class));
        cards.add(new SetCardInfo("Skullmulcher", 148, Rarity.RARE, mage.cards.s.Skullmulcher.class));
        cards.add(new SetCardInfo("Soul's Fire", 115, Rarity.COMMON, mage.cards.s.SoulsFire.class));
        cards.add(new SetCardInfo("Soul's Grace", 28, Rarity.COMMON, mage.cards.s.SoulsGrace.class));
        cards.add(new SetCardInfo("Soul's Might", 149, Rarity.COMMON, mage.cards.s.SoulsMight.class));
        cards.add(new SetCardInfo("Spearbreaker Behemoth", 150, Rarity.RARE, mage.cards.s.SpearbreakerBehemoth.class));
        cards.add(new SetCardInfo("Spell Snip", 57, Rarity.COMMON, mage.cards.s.SpellSnip.class));
        cards.add(new SetCardInfo("Sphinx's Herald", 58, Rarity.UNCOMMON, mage.cards.s.SphinxsHerald.class));
        cards.add(new SetCardInfo("Sphinx Sovereign", 196, Rarity.MYTHIC, mage.cards.s.SphinxSovereign.class));
        cards.add(new SetCardInfo("Sprouting Thrinax", 197, Rarity.UNCOMMON, mage.cards.s.SproutingThrinax.class));
        cards.add(new SetCardInfo("Steelclad Serpent", 59, Rarity.COMMON, mage.cards.s.SteelcladSerpent.class));
        cards.add(new SetCardInfo("Steward of Valeron", 198, Rarity.COMMON, mage.cards.s.StewardOfValeron.class));
        cards.add(new SetCardInfo("Stoic Angel", 199, Rarity.RARE, mage.cards.s.StoicAngel.class));
        cards.add(new SetCardInfo("Sunseed Nurturer", 29, Rarity.UNCOMMON, mage.cards.s.SunseedNurturer.class));
        cards.add(new SetCardInfo("Swamp", 238, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 239, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 240, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 241, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swerve", 200, Rarity.UNCOMMON, mage.cards.s.Swerve.class));
        cards.add(new SetCardInfo("Tar Fiend", 89, Rarity.RARE, mage.cards.t.TarFiend.class));
        cards.add(new SetCardInfo("Tezzeret the Seeker", 60, Rarity.MYTHIC, mage.cards.t.TezzeretTheSeeker.class));
        cards.add(new SetCardInfo("Thorn-Thrash Viashino", 116, Rarity.COMMON, mage.cards.t.ThornThrashViashino.class));
        cards.add(new SetCardInfo("Thoughtcutter Agent", 201, Rarity.UNCOMMON, mage.cards.t.ThoughtcutterAgent.class));
        cards.add(new SetCardInfo("Thunder-Thrash Elder", 117, Rarity.UNCOMMON, mage.cards.t.ThunderThrashElder.class));
        cards.add(new SetCardInfo("Tidehollow Sculler", 202, Rarity.UNCOMMON, mage.cards.t.TidehollowSculler.class));
        cards.add(new SetCardInfo("Tidehollow Strix", 203, Rarity.COMMON, mage.cards.t.TidehollowStrix.class));
        cards.add(new SetCardInfo("Titanic Ultimatum", 204, Rarity.RARE, mage.cards.t.TitanicUltimatum.class));
        cards.add(new SetCardInfo("Topan Ascetic", 151, Rarity.UNCOMMON, mage.cards.t.TopanAscetic.class));
        cards.add(new SetCardInfo("Tortoise Formation", 61, Rarity.COMMON, mage.cards.t.TortoiseFormation.class));
        cards.add(new SetCardInfo("Tower Gargoyle", 205, Rarity.UNCOMMON, mage.cards.t.TowerGargoyle.class));
        cards.add(new SetCardInfo("Undead Leotau", 90, Rarity.COMMON, mage.cards.u.UndeadLeotau.class));
        cards.add(new SetCardInfo("Vectis Silencers", 62, Rarity.COMMON, mage.cards.v.VectisSilencers.class));
        cards.add(new SetCardInfo("Vein Drinker", 91, Rarity.RARE, mage.cards.v.VeinDrinker.class));
        cards.add(new SetCardInfo("Viashino Skeleton", 118, Rarity.COMMON, mage.cards.v.ViashinoSkeleton.class));
        cards.add(new SetCardInfo("Vicious Shadows", 119, Rarity.RARE, mage.cards.v.ViciousShadows.class));
        cards.add(new SetCardInfo("Violent Ultimatum", 206, Rarity.RARE, mage.cards.v.ViolentUltimatum.class));
        cards.add(new SetCardInfo("Viscera Dragger", 92, Rarity.COMMON, mage.cards.v.VisceraDragger.class));
        cards.add(new SetCardInfo("Vithian Stinger", 120, Rarity.COMMON, mage.cards.v.VithianStinger.class));
        cards.add(new SetCardInfo("Volcanic Submersion", 121, Rarity.COMMON, mage.cards.v.VolcanicSubmersion.class));
        cards.add(new SetCardInfo("Waveskimmer Aven", 207, Rarity.COMMON, mage.cards.w.WaveskimmerAven.class));
        cards.add(new SetCardInfo("Welkin Guide", 30, Rarity.COMMON, mage.cards.w.WelkinGuide.class));
        cards.add(new SetCardInfo("Where Ancients Tread", 122, Rarity.RARE, mage.cards.w.WhereAncientsTread.class));
        cards.add(new SetCardInfo("Wild Nacatl", 152, Rarity.COMMON, mage.cards.w.WildNacatl.class));
        cards.add(new SetCardInfo("Windwright Mage", 208, Rarity.COMMON, mage.cards.w.WindwrightMage.class));
        cards.add(new SetCardInfo("Woolly Thoctar", 209, Rarity.UNCOMMON, mage.cards.w.WoollyThoctar.class));
        cards.add(new SetCardInfo("Yoked Plowbeast", 31, Rarity.COMMON, mage.cards.y.YokedPlowbeast.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ShardsOfAlaraCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/ala.html
// Using USA collation
class ShardsOfAlaraCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "134", "116", "42", "18", "71", "195", "126", "118", "32", "11", "92", "128", "116", "37", "31", "65", "165", "126", "110", "46", "22", "74", "127", "115", "62", "30", "67", "165", "147", "104", "59", "18", "92", "144", "118", "42", "1", "73", "195", "128", "111", "59", "11", "65", "134", "104", "62", "22", "71", "176", "144", "115", "46", "30", "73", "127", "110", "32", "31", "74", "176", "147", "111", "37", "1", "67");
    private final CardRun commonB = new CardRun(true, "189", "130", "93", "28", "198", "47", "136", "203", "88", "12", "169", "102", "130", "26", "93", "153", "61", "86", "203", "47", "28", "139", "207", "130", "97", "88", "198", "26", "61", "203", "36", "24", "86", "189", "136", "93", "12", "153", "102", "87", "207", "36", "28", "88", "169", "139", "97", "24", "189", "61", "136", "153", "47", "26", "87", "198", "139", "102", "24", "169", "36", "86", "207", "12", "97", "87");
    private final CardRun commonC1 = new CardRun(true, "221", "13", "149", "90", "213", "108", "8", "133", "34", "156", "107", "4", "90", "221", "214", "66", "125", "57", "8", "121", "218", "54", "149", "15", "83", "227", "33", "186", "94", "141", "4", "66", "215", "34", "107", "227", "81", "213", "133", "13", "54", "108", "156", "141", "214", "83", "94", "15", "125", "33", "215", "121", "186", "81", "57");
    private final CardRun commonC2 = new CardRun(true, "223", "75", "162", "120", "132", "52", "216", "208", "225", "218", "173", "120", "152", "75", "52", "20", "162", "225", "77", "132", "212", "158", "105", "224", "162", "35", "77", "223", "216", "132", "208", "75", "158", "52", "224", "212", "152", "105", "208", "35", "216", "20", "223", "173", "152", "224", "77", "120", "35", "158", "20", "225", "212", "173", "105");
    private final CardRun uncommonA = new CardRun(true, "161", "129", "43", "184", "228", "171", "5", "226", "205", "114", "202", "76", "142", "155", "43", "197", "27", "222", "168", "106", "188", "85", "142", "180", "40", "209", "229", "5", "220", "168", "161", "112", "80", "184", "145", "155", "44", "27", "228", "157", "175", "106", "76", "205", "222", "129", "197", "40", "19", "209", "220", "157", "114", "175", "85", "145", "188", "229", "44", "180", "19", "171", "112", "226", "202", "80");
    private final CardRun uncommonB = new CardRun(true, "82", "124", "23", "181", "29", "137", "98", "68", "174", "39", "123", "99", "78", "167", "53", "117", "2", "181", "200", "39", "113", "124", "68", "177", "41", "201", "3", "99", "151", "58", "123", "117", "72", "200", "53", "2", "137", "82", "167", "113", "201", "29", "72", "190", "41", "78", "23", "151", "174", "98", "177", "3", "58", "190");
    private final CardRun rareA = new CardRun(true, "55", "100", "183", "89", "159", "148", "6", "219", "70", "192", "95", "48", "187", "49", "183", "140", "21", "217", "70", "204", "100", "164", "95", "49", "148", "187", "25", "211", "163", "89", "160", "150", "109", "17", "48", "199", "25", "138", "163", "64", "206", "91", "103", "219", "45", "199", "6", "140", "159", "55", "164", "64", "109", "217", "17", "160", "138", "103", "206", "45", "204", "211", "21", "91", "150", "192");
    private final CardRun rareB = new CardRun(true, "69", "196", "10", "84", "122", "194", "51", "16", "210", "131", "96", "56", "154", "146", "122", "7", "191", "14", "50", "170", "84", "135", "119", "193", "38", "79", "10", "182", "131", "51", "185", "63", "101", "143", "178", "14", "56", "119", "9", "135", "69", "172", "38", "96", "146", "179", "7", "50", "63", "166", "101", "16", "60", "79", "143");
    private final CardRun land = new CardRun(false, "230", "231", "232", "233", "234", "235", "236", "237", "238", "239", "240", "241", "242", "243", "244", "245", "246", "247", "248", "249");

    private final BoosterStructure AAABC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC2C2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAABBBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rareA);
    private final BoosterStructure R2 = new BoosterStructure(rareB);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAABBC2C2C2C2C2,
            AAABBC2C2C2C2C2,
            AAABBC2C2C2C2C2,
            AAABBC2C2C2C2C2,
            AAABBBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBBC2C2,
            AAAABBBBC2C2
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 1.65 A uncommons (33 / 20)
    // 1.35 B uncommons (27 / 20)
    // These numbers are the same for all sets with 60 uncommons in asymmetrical A/B print runs
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB, AAB,
            ABB, ABB, ABB, ABB, ABB, ABB, ABB
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(
            R1, R1, R1, R1, R1, R1,
            R2, R2, R2, R2, R2
    );
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
