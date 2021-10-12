
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */
public final class Archenemy extends ExpansionSet {

    private static final Archenemy instance = new Archenemy();

    public static Archenemy getInstance() {
        return instance;
    }

    private Archenemy() {
        super("Archenemy", "ARC", ExpansionSet.buildDate(2010, 6, 18), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";
        cards.add(new SetCardInfo("Aether Spellbomb", 102, Rarity.COMMON, mage.cards.a.AetherSpellbomb.class));
        cards.add(new SetCardInfo("Agony Warp", 76, Rarity.COMMON, mage.cards.a.AgonyWarp.class));
        cards.add(new SetCardInfo("Architects of Will", 77, Rarity.COMMON, mage.cards.a.ArchitectsOfWill.class));
        cards.add(new SetCardInfo("Armadillo Cloak", 78, Rarity.COMMON, mage.cards.a.ArmadilloCloak.class));
        cards.add(new SetCardInfo("Artisan of Kozilek", 123, Rarity.UNCOMMON, mage.cards.a.ArtisanOfKozilek.class));
        cards.add(new SetCardInfo("Avatar of Discord", 79, Rarity.RARE, mage.cards.a.AvatarOfDiscord.class));
        cards.add(new SetCardInfo("Avatar of Woe", 9, Rarity.RARE, mage.cards.a.AvatarOfWoe.class));
        cards.add(new SetCardInfo("Azorius Signet", 103, Rarity.COMMON, mage.cards.a.AzoriusSignet.class));
        cards.add(new SetCardInfo("Barren Moor", 124, Rarity.COMMON, mage.cards.b.BarrenMoor.class));
        cards.add(new SetCardInfo("Battering Craghorn", 30, Rarity.COMMON, mage.cards.b.BatteringCraghorn.class));
        cards.add(new SetCardInfo("Batwing Brume", 80, Rarity.UNCOMMON, mage.cards.b.BatwingBrume.class));
        cards.add(new SetCardInfo("Beacon of Unrest", 10, Rarity.RARE, mage.cards.b.BeaconOfUnrest.class));
        cards.add(new SetCardInfo("Bituminous Blast", 81, Rarity.UNCOMMON, mage.cards.b.BituminousBlast.class));
        cards.add(new SetCardInfo("Bog Witch", 11, Rarity.COMMON, mage.cards.b.BogWitch.class));
        cards.add(new SetCardInfo("Branching Bolt", 82, Rarity.COMMON, mage.cards.b.BranchingBolt.class));
        cards.add(new SetCardInfo("Breath of Darigaaz", 31, Rarity.UNCOMMON, mage.cards.b.BreathOfDarigaaz.class));
        cards.add(new SetCardInfo("Cemetery Reaper", 12, Rarity.RARE, mage.cards.c.CemeteryReaper.class));
        cards.add(new SetCardInfo("Chameleon Colossus", 52, Rarity.RARE, mage.cards.c.ChameleonColossus.class));
        cards.add(new SetCardInfo("Chandra's Outrage", 32, Rarity.COMMON, mage.cards.c.ChandrasOutrage.class));
        cards.add(new SetCardInfo("Colossal Might", 83, Rarity.COMMON, mage.cards.c.ColossalMight.class));
        cards.add(new SetCardInfo("Corpse Connoisseur", 13, Rarity.UNCOMMON, mage.cards.c.CorpseConnoisseur.class));
        cards.add(new SetCardInfo("Dimir Signet", 104, Rarity.COMMON, mage.cards.d.DimirSignet.class));
        cards.add(new SetCardInfo("Dragon Breath", 33, Rarity.COMMON, mage.cards.d.DragonBreath.class));
        cards.add(new SetCardInfo("Dragon Fodder", 34, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dragonspeaker Shaman", 36, Rarity.UNCOMMON, mage.cards.d.DragonspeakerShaman.class));
        cards.add(new SetCardInfo("Dragon Whelp", 35, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Dreamstone Hedron", 105, Rarity.UNCOMMON, mage.cards.d.DreamstoneHedron.class));
        cards.add(new SetCardInfo("Dregscape Zombie", 14, Rarity.COMMON, mage.cards.d.DregscapeZombie.class));
        cards.add(new SetCardInfo("Duplicant", 106, Rarity.RARE, mage.cards.d.Duplicant.class));
        cards.add(new SetCardInfo("Ethersworn Shieldmage", 84, Rarity.COMMON, mage.cards.e.EtherswornShieldmage.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 107, Rarity.UNCOMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Extractor Demon", 15, Rarity.RARE, mage.cards.e.ExtractorDemon.class));
        cards.add(new SetCardInfo("Feral Hydra", 53, Rarity.RARE, mage.cards.f.FeralHydra.class));
        cards.add(new SetCardInfo("Fertilid", 54, Rarity.COMMON, mage.cards.f.Fertilid.class));
        cards.add(new SetCardInfo("Festering Goblin", 16, Rarity.COMMON, mage.cards.f.FesteringGoblin.class));
        cards.add(new SetCardInfo("Fieldmist Borderpost", 85, Rarity.COMMON, mage.cards.f.FieldmistBorderpost.class));
        cards.add(new SetCardInfo("Fierce Empath", 55, Rarity.COMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Fireball", 37, Rarity.UNCOMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Fires of Yavimaya", 86, Rarity.UNCOMMON, mage.cards.f.FiresOfYavimaya.class));
        cards.add(new SetCardInfo("Flameblast Dragon", 38, Rarity.RARE, mage.cards.f.FlameblastDragon.class));
        cards.add(new SetCardInfo("Fog", 56, Rarity.COMMON, mage.cards.f.Fog.class));
        cards.add(new SetCardInfo("Forest", 148, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 149, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 150, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forgotten Ancient", 57, Rarity.RARE, mage.cards.f.ForgottenAncient.class));
        cards.add(new SetCardInfo("Furnace Whelp", 39, Rarity.UNCOMMON, mage.cards.f.FurnaceWhelp.class));
        cards.add(new SetCardInfo("Gathan Raiders", 40, Rarity.COMMON, mage.cards.g.GathanRaiders.class));
        cards.add(new SetCardInfo("Gleeful Sabotage", 58, Rarity.COMMON, mage.cards.g.GleefulSabotage.class));
        cards.add(new SetCardInfo("Graypelt Refuge", 125, Rarity.UNCOMMON, mage.cards.g.GraypeltRefuge.class));
        cards.add(new SetCardInfo("Gruul Signet", 108, Rarity.COMMON, mage.cards.g.GruulSignet.class));
        cards.add(new SetCardInfo("Harmonize", 59, Rarity.UNCOMMON, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Hellkite Charger", 41, Rarity.RARE, mage.cards.h.HellkiteCharger.class));
        cards.add(new SetCardInfo("Heroes' Reunion", 87, Rarity.UNCOMMON, mage.cards.h.HeroesReunion.class));
        cards.add(new SetCardInfo("Hunting Moa", 60, Rarity.UNCOMMON, mage.cards.h.HuntingMoa.class));
        cards.add(new SetCardInfo("Imperial Hellkite", 42, Rarity.RARE, mage.cards.i.ImperialHellkite.class));
        cards.add(new SetCardInfo("Incremental Blight", 17, Rarity.UNCOMMON, mage.cards.i.IncrementalBlight.class));
        cards.add(new SetCardInfo("Infectious Horror", 18, Rarity.COMMON, mage.cards.i.InfectiousHorror.class));
        cards.add(new SetCardInfo("Inferno Trap", 43, Rarity.UNCOMMON, mage.cards.i.InfernoTrap.class));
        cards.add(new SetCardInfo("Infest", 19, Rarity.UNCOMMON, mage.cards.i.Infest.class));
        cards.add(new SetCardInfo("Island", 139, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 140, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 141, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Juggernaut", 109, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Kaervek the Merciless", 88, Rarity.RARE, mage.cards.k.KaervekTheMerciless.class));
        cards.add(new SetCardInfo("Kamahl, Fist of Krosa", 61, Rarity.RARE, mage.cards.k.KamahlFistOfKrosa.class));
        cards.add(new SetCardInfo("Kazandu Refuge", 126, Rarity.UNCOMMON, mage.cards.k.KazanduRefuge.class));
        cards.add(new SetCardInfo("Khalni Garden", 127, Rarity.COMMON, mage.cards.k.KhalniGarden.class));
        cards.add(new SetCardInfo("Kilnmouth Dragon", 44, Rarity.RARE, mage.cards.k.KilnmouthDragon.class));
        cards.add(new SetCardInfo("Krosan Tusker", 62, Rarity.COMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Krosan Verge", 128, Rarity.UNCOMMON, mage.cards.k.KrosanVerge.class));
        cards.add(new SetCardInfo("Leaf Gilder", 63, Rarity.COMMON, mage.cards.l.LeafGilder.class));
        cards.add(new SetCardInfo("Leonin Abunas", 1, Rarity.RARE, mage.cards.l.LeoninAbunas.class));
        cards.add(new SetCardInfo("Lightning Greaves", 110, Rarity.UNCOMMON, mage.cards.l.LightningGreaves.class));
        cards.add(new SetCardInfo("Llanowar Reborn", 129, Rarity.UNCOMMON, mage.cards.l.LlanowarReborn.class));
        cards.add(new SetCardInfo("Lodestone Golem", 111, Rarity.RARE, mage.cards.l.LodestoneGolem.class));
        cards.add(new SetCardInfo("Magister Sphinx", 89, Rarity.RARE, mage.cards.m.MagisterSphinx.class));
        cards.add(new SetCardInfo("Makeshift Mannequin", 20, Rarity.UNCOMMON, mage.cards.m.MakeshiftMannequin.class));
        cards.add(new SetCardInfo("March of the Machines", 6, Rarity.RARE, mage.cards.m.MarchOfTheMachines.class));
        cards.add(new SetCardInfo("Master Transmuter", 7, Rarity.RARE, mage.cards.m.MasterTransmuter.class));
        cards.add(new SetCardInfo("Memnarch", 112, Rarity.RARE, mage.cards.m.Memnarch.class));
        cards.add(new SetCardInfo("Metallurgeon", 2, Rarity.UNCOMMON, mage.cards.m.Metallurgeon.class));
        cards.add(new SetCardInfo("Mistvein Borderpost", 90, Rarity.COMMON, mage.cards.m.MistveinBorderpost.class));
        cards.add(new SetCardInfo("Molimo, Maro-Sorcerer", 64, Rarity.RARE, mage.cards.m.MolimoMaroSorcerer.class));
        cards.add(new SetCardInfo("Mosswort Bridge", 130, Rarity.RARE, mage.cards.m.MosswortBridge.class));
        cards.add(new SetCardInfo("Mountain", 145, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 146, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 147, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nantuko Monastery", 131, Rarity.UNCOMMON, mage.cards.n.NantukoMonastery.class));
        cards.add(new SetCardInfo("Obelisk of Esper", 113, Rarity.COMMON, mage.cards.o.ObeliskOfEsper.class));
        cards.add(new SetCardInfo("Oblivion Ring", 3, Rarity.COMMON, mage.cards.o.OblivionRing.class));
        cards.add(new SetCardInfo("Pale Recluse", 91, Rarity.COMMON, mage.cards.p.PaleRecluse.class));
        cards.add(new SetCardInfo("Path to Exile", 4, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Plains", 137, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 138, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plummet", 65, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Primal Command", 66, Rarity.RARE, mage.cards.p.PrimalCommand.class));
        cards.add(new SetCardInfo("Rakdos Carnarium", 132, Rarity.COMMON, mage.cards.r.RakdosCarnarium.class));
        cards.add(new SetCardInfo("Rakdos Guildmage", 92, Rarity.UNCOMMON, mage.cards.r.RakdosGuildmage.class));
        cards.add(new SetCardInfo("Rakdos Signet", 114, Rarity.COMMON, mage.cards.r.RakdosSignet.class));
        cards.add(new SetCardInfo("Rancor", 67, Rarity.COMMON, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Reanimate", 21, Rarity.UNCOMMON, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", 22, Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Ryusei, the Falling Star", 45, Rarity.RARE, mage.cards.r.RyuseiTheFallingStar.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 68, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Sanctum Gargoyle", 5, Rarity.COMMON, mage.cards.s.SanctumGargoyle.class));
        cards.add(new SetCardInfo("Savage Twister", 93, Rarity.UNCOMMON, mage.cards.s.SavageTwister.class));
        cards.add(new SetCardInfo("Scion of Darkness", 23, Rarity.RARE, mage.cards.s.ScionOfDarkness.class));
        cards.add(new SetCardInfo("Secluded Steppe", 133, Rarity.COMMON, mage.cards.s.SecludedSteppe.class));
        cards.add(new SetCardInfo("Seething Song", 46, Rarity.COMMON, mage.cards.s.SeethingSong.class));
        cards.add(new SetCardInfo("Selesnya Guildmage", 94, Rarity.UNCOMMON, mage.cards.s.SelesnyaGuildmage.class));
        cards.add(new SetCardInfo("Shinen of Life's Roar", 69, Rarity.COMMON, mage.cards.s.ShinenOfLifesRoar.class));
        cards.add(new SetCardInfo("Shriekmaw", 24, Rarity.UNCOMMON, mage.cards.s.Shriekmaw.class));
        cards.add(new SetCardInfo("Sign in Blood", 25, Rarity.COMMON, mage.cards.s.SignInBlood.class));
        cards.add(new SetCardInfo("Skirk Commando", 47, Rarity.COMMON, mage.cards.s.SkirkCommando.class));
        cards.add(new SetCardInfo("Skirk Marauder", 48, Rarity.COMMON, mage.cards.s.SkirkMarauder.class));
        cards.add(new SetCardInfo("Skullcage", 115, Rarity.UNCOMMON, mage.cards.s.Skullcage.class));
        cards.add(new SetCardInfo("Sorcerer's Strongbox", 116, Rarity.UNCOMMON, mage.cards.s.SorcerersStrongbox.class));
        cards.add(new SetCardInfo("Spider Umbra", 70, Rarity.COMMON, mage.cards.s.SpiderUmbra.class));
        cards.add(new SetCardInfo("Spin into Myth", 8, Rarity.UNCOMMON, mage.cards.s.SpinIntoMyth.class));
        cards.add(new SetCardInfo("Sundering Titan", 118, Rarity.RARE, mage.cards.s.SunderingTitan.class));
        cards.add(new SetCardInfo("Sun Droplet", 117, Rarity.UNCOMMON, mage.cards.s.SunDroplet.class));
        cards.add(new SetCardInfo("Swamp", 142, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 143, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 144, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Synod Centurion", 119, Rarity.UNCOMMON, mage.cards.s.SynodCenturion.class));
        cards.add(new SetCardInfo("Synod Sanctum", 120, Rarity.UNCOMMON, mage.cards.s.SynodSanctum.class));
        cards.add(new SetCardInfo("Taurean Mauler", 49, Rarity.RARE, mage.cards.t.TaureanMauler.class));
        cards.add(new SetCardInfo("Terminate", 95, Rarity.COMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 134, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Thelonite Hermit", 71, Rarity.RARE, mage.cards.t.TheloniteHermit.class));
        cards.add(new SetCardInfo("Thran Dynamo", 121, Rarity.UNCOMMON, mage.cards.t.ThranDynamo.class));
        cards.add(new SetCardInfo("Thunderstaff", 122, Rarity.UNCOMMON, mage.cards.t.Thunderstaff.class));
        cards.add(new SetCardInfo("Torrent of Souls", 96, Rarity.UNCOMMON, mage.cards.t.TorrentOfSouls.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 135, Rarity.COMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Twisted Abomination", 26, Rarity.COMMON, mage.cards.t.TwistedAbomination.class));
        cards.add(new SetCardInfo("Two-Headed Dragon", 50, Rarity.RARE, mage.cards.t.TwoHeadedDragon.class));
        cards.add(new SetCardInfo("Unbender Tine", 97, Rarity.UNCOMMON, mage.cards.u.UnbenderTine.class));
        cards.add(new SetCardInfo("Unmake", 98, Rarity.COMMON, mage.cards.u.Unmake.class));
        cards.add(new SetCardInfo("Urborg Syphon-Mage", 27, Rarity.COMMON, mage.cards.u.UrborgSyphonMage.class));
        cards.add(new SetCardInfo("Vampiric Dragon", 99, Rarity.RARE, mage.cards.v.VampiricDragon.class));
        cards.add(new SetCardInfo("Verdeloth the Ancient", 72, Rarity.RARE, mage.cards.v.VerdelothTheAncient.class));
        cards.add(new SetCardInfo("Vitu-Ghazi, the City-Tree", 136, Rarity.UNCOMMON, mage.cards.v.VituGhaziTheCityTree.class));
        cards.add(new SetCardInfo("Volcanic Fallout", 51, Rarity.UNCOMMON, mage.cards.v.VolcanicFallout.class));
        cards.add(new SetCardInfo("Wall of Roots", 73, Rarity.COMMON, mage.cards.w.WallOfRoots.class));
        cards.add(new SetCardInfo("Watchwolf", 100, Rarity.UNCOMMON, mage.cards.w.Watchwolf.class));
        cards.add(new SetCardInfo("Wax // Wane", 101, Rarity.UNCOMMON, mage.cards.w.WaxWane.class));
        cards.add(new SetCardInfo("Wickerbough Elder", 74, Rarity.COMMON, mage.cards.w.WickerboughElder.class));
        cards.add(new SetCardInfo("Yavimaya Dryad", 75, Rarity.UNCOMMON, mage.cards.y.YavimayaDryad.class));
        cards.add(new SetCardInfo("Zombie Infestation", 28, Rarity.UNCOMMON, mage.cards.z.ZombieInfestation.class));
        cards.add(new SetCardInfo("Zombify", 29, Rarity.UNCOMMON, mage.cards.z.Zombify.class));
    }

}
