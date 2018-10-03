
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class GuildsOfRavnicaGuildKits extends ExpansionSet {

    private static final GuildsOfRavnicaGuildKits instance = new GuildsOfRavnicaGuildKits();

    public static GuildsOfRavnicaGuildKits getInstance() {
        return instance;
    }

    private GuildsOfRavnicaGuildKits() {
        super("Guilds of Ravnica Guild Kits", "GK1", ExpansionSet.buildDate(2018, 11, 2), SetType.SUPPLEMENTAL);
        this.blockName = "Guild Kits";
        this.hasBasicLands = true;
        
        cards.add(new SetCardInfo("Abrupt Decay", 57, Rarity.RARE, mage.cards.a.AbruptDecay.class));
        cards.add(new SetCardInfo("Advent of the Wurm", 107, Rarity.RARE, mage.cards.a.AdventOfTheWurm.class));
        cards.add(new SetCardInfo("Agrus Kos, Wojek Veteran", 83, Rarity.RARE, mage.cards.a.AgrusKosWojekVeteran.class));
        cards.add(new SetCardInfo("Armada Wurm", 108, Rarity.MYTHIC, mage.cards.a.ArmadaWurm.class));
        cards.add(new SetCardInfo("Aurelia, the Warleader", 77, Rarity.MYTHIC, mage.cards.a.AureliaTheWarleader.class));
        cards.add(new SetCardInfo("Bomber Corps", 80, Rarity.COMMON, mage.cards.b.BomberCorps.class));
        cards.add(new SetCardInfo("Boros Charm", 84, Rarity.UNCOMMON, mage.cards.b.BorosCharm.class));
        cards.add(new SetCardInfo("Boros Elite", 78, Rarity.UNCOMMON, mage.cards.b.BorosElite.class));
        cards.add(new SetCardInfo("Boros Garrison", 98, Rarity.COMMON, mage.cards.b.BorosGarrison.class));
        cards.add(new SetCardInfo("Boros Keyrune", 96, Rarity.UNCOMMON, mage.cards.b.BorosKeyrune.class));
        cards.add(new SetCardInfo("Boros Reckoner", 85, Rarity.RARE, mage.cards.b.BorosReckoner.class));
        cards.add(new SetCardInfo("Boros Signet", 97, Rarity.COMMON, mage.cards.b.BorosSignet.class));
        cards.add(new SetCardInfo("Boros Swiftblade", 86, Rarity.UNCOMMON, mage.cards.b.BorosSwiftblade.class));
        cards.add(new SetCardInfo("Brightflame", 87, Rarity.RARE, mage.cards.b.Brightflame.class));
        cards.add(new SetCardInfo("Call of the Conclave", 109, Rarity.UNCOMMON, mage.cards.c.CallOfTheConclave.class));
        cards.add(new SetCardInfo("Call of the Nightwing", 8, Rarity.UNCOMMON, mage.cards.c.CallOfTheNightwing.class));
        cards.add(new SetCardInfo("Centaur Healer", 110, Rarity.COMMON, mage.cards.c.CentaurHealer.class));
        cards.add(new SetCardInfo("Cerebral Vortex", 35, Rarity.RARE, mage.cards.c.CerebralVortex.class));
        cards.add(new SetCardInfo("Char", 28, Rarity.RARE, mage.cards.c.Char.class));
        cards.add(new SetCardInfo("Circu, Dimir Lobotomist", 9, Rarity.RARE, mage.cards.c.CircuDimirLobotomist.class));
        cards.add(new SetCardInfo("Consuming Aberration", 10, Rarity.RARE, mage.cards.c.ConsumingAberration.class));
        cards.add(new SetCardInfo("Daring Skyjek", 79, Rarity.COMMON, mage.cards.d.DaringSkyjek.class));
        cards.add(new SetCardInfo("Darkblast", 51, Rarity.UNCOMMON, mage.cards.d.Darkblast.class));
        cards.add(new SetCardInfo("Deadbridge Chant", 58, Rarity.MYTHIC, mage.cards.d.DeadbridgeChant.class));
        cards.add(new SetCardInfo("Deadbridge Goliath", 55, Rarity.RARE, mage.cards.d.DeadbridgeGoliath.class));
        cards.add(new SetCardInfo("Deathrite Shaman", 59, Rarity.RARE, mage.cards.d.DeathriteShaman.class));
        cards.add(new SetCardInfo("Devouring Light", 103, Rarity.UNCOMMON, mage.cards.d.DevouringLight.class));
        cards.add(new SetCardInfo("Dimir Aqueduct", 23, Rarity.COMMON, mage.cards.d.DimirAqueduct.class));
        cards.add(new SetCardInfo("Dimir Charm", 11, Rarity.UNCOMMON, mage.cards.d.DimirCharm.class));
        cards.add(new SetCardInfo("Dimir Doppelganger", 12, Rarity.RARE, mage.cards.d.DimirDoppelganger.class));
        cards.add(new SetCardInfo("Dimir Guildmage", 13, Rarity.UNCOMMON, mage.cards.d.DimirGuildmage.class));
        cards.add(new SetCardInfo("Dimir Signet", 22, Rarity.COMMON, mage.cards.d.DimirSignet.class));
        cards.add(new SetCardInfo("Dinrova Horror", 14, Rarity.UNCOMMON, mage.cards.d.DinrovaHorror.class));
        cards.add(new SetCardInfo("Djinn Illuminatus", 36, Rarity.RARE, mage.cards.d.DjinnIlluminatus.class));
        cards.add(new SetCardInfo("Drown in Filth", 60, Rarity.COMMON, mage.cards.d.DrownInFilth.class));
        cards.add(new SetCardInfo("Dryad Militant", 111, Rarity.UNCOMMON, mage.cards.d.DryadMilitant.class));
        cards.add(new SetCardInfo("Electrickery", 29, Rarity.COMMON, mage.cards.e.Electrickery.class));
        cards.add(new SetCardInfo("Electrolyze", 37, Rarity.UNCOMMON, mage.cards.e.Electrolyze.class));
        cards.add(new SetCardInfo("Elves of Deep Shadow", 56, Rarity.COMMON, mage.cards.e.ElvesOfDeepShadow.class));
        cards.add(new SetCardInfo("Etrata, the Silencer", 1, Rarity.RARE, mage.cards.e.EtrataTheSilencer.class));
        cards.add(new SetCardInfo("Firemane Angel", 88, Rarity.RARE, mage.cards.f.FiremaneAngel.class));
        cards.add(new SetCardInfo("Firemane Avenger", 89, Rarity.RARE, mage.cards.f.FiremaneAvenger.class));
        cards.add(new SetCardInfo("Forest", 76, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 127, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frenzied Goblin", 81, Rarity.UNCOMMON, mage.cards.f.FrenziedGoblin.class));
        cards.add(new SetCardInfo("Gather Courage", 105, Rarity.COMMON, mage.cards.g.GatherCourage.class));
        cards.add(new SetCardInfo("Gaze of Granite", 61, Rarity.RARE, mage.cards.g.GazeOfGranite.class));
        cards.add(new SetCardInfo("Gelectrode", 38, Rarity.UNCOMMON, mage.cards.g.Gelectrode.class));
        cards.add(new SetCardInfo("Glare of Subdual", 112, Rarity.RARE, mage.cards.g.GlareOfSubdual.class));
        cards.add(new SetCardInfo("Glimpse the Unthinkable", 15, Rarity.RARE, mage.cards.g.GlimpseTheUnthinkable.class));
        cards.add(new SetCardInfo("Goblin Rally", 30, Rarity.UNCOMMON, mage.cards.g.GoblinRally.class));
        cards.add(new SetCardInfo("Golgari Charm", 62, Rarity.UNCOMMON, mage.cards.g.GolgariCharm.class));
        cards.add(new SetCardInfo("Golgari Rot Farm", 74, Rarity.COMMON, mage.cards.g.GolgariRotFarm.class));
        cards.add(new SetCardInfo("Golgari Signet", 73, Rarity.COMMON, mage.cards.g.GolgariSignet.class));
        cards.add(new SetCardInfo("Grave-Shell Scarab", 63, Rarity.RARE, mage.cards.g.GraveShellScarab.class));
        cards.add(new SetCardInfo("Grisly Salvage", 64, Rarity.COMMON, mage.cards.g.GrislySalvage.class));
        cards.add(new SetCardInfo("Grove of the Guardian", 124, Rarity.RARE, mage.cards.g.GroveOfTheGuardian.class));
        cards.add(new SetCardInfo("Growing Ranks", 113, Rarity.RARE, mage.cards.g.GrowingRanks.class));
        cards.add(new SetCardInfo("Guttersnipe", 31, Rarity.UNCOMMON, mage.cards.g.Guttersnipe.class));
        cards.add(new SetCardInfo("Hour of Reckoning", 104, Rarity.RARE, mage.cards.h.HourOfReckoning.class));
        cards.add(new SetCardInfo("Hypersonic Dragon", 39, Rarity.RARE, mage.cards.h.HypersonicDragon.class));
        cards.add(new SetCardInfo("Invoke the Firemind", 40, Rarity.RARE, mage.cards.i.InvokeTheFiremind.class));
        cards.add(new SetCardInfo("Island", 24, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 48, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Izoni, Thousand-Eyed", 50, Rarity.RARE, mage.cards.i.IzoniThousandEyed.class));
        cards.add(new SetCardInfo("Izzet Boilerworks", 47, Rarity.COMMON, mage.cards.i.IzzetBoilerworks.class));
        cards.add(new SetCardInfo("Izzet Charm", 41, Rarity.UNCOMMON, mage.cards.i.IzzetCharm.class));
        cards.add(new SetCardInfo("Izzet Signet", 46, Rarity.COMMON, mage.cards.i.IzzetSignet.class));
        cards.add(new SetCardInfo("Jarad, Golgari Lich Lord", 65, Rarity.MYTHIC, mage.cards.j.JaradGolgariLichLord.class));
        cards.add(new SetCardInfo("Korozda Guildmage", 66, Rarity.UNCOMMON, mage.cards.k.KorozdaGuildmage.class));
        cards.add(new SetCardInfo("Last Gasp", 5, Rarity.COMMON, mage.cards.l.LastGasp.class));
        cards.add(new SetCardInfo("Lazav, Dimir Mastermind", 16, Rarity.MYTHIC, mage.cards.l.LazavDimirMastermind.class));
        cards.add(new SetCardInfo("Legion Loyalist", 82, Rarity.RARE, mage.cards.l.LegionLoyalist.class));
        cards.add(new SetCardInfo("Lightning Helix", 90, Rarity.UNCOMMON, mage.cards.l.LightningHelix.class));
        cards.add(new SetCardInfo("Lotleth Troll", 67, Rarity.RARE, mage.cards.l.LotlethTroll.class));
        cards.add(new SetCardInfo("Loxodon Hierarch", 114, Rarity.RARE, mage.cards.l.LoxodonHierarch.class));
        cards.add(new SetCardInfo("Martial Glory", 91, Rarity.COMMON, mage.cards.m.MartialGlory.class));
        cards.add(new SetCardInfo("Master Warcraft", 92, Rarity.RARE, mage.cards.m.MasterWarcraft.class));
        cards.add(new SetCardInfo("Mirko Vosk, Mind Drinker", 17, Rarity.RARE, mage.cards.m.MirkoVoskMindDrinker.class));
        cards.add(new SetCardInfo("Mizzium Mortars", 32, Rarity.RARE, mage.cards.m.MizziumMortars.class));
        cards.add(new SetCardInfo("Moroii", 18, Rarity.UNCOMMON, mage.cards.m.Moroii.class));
        cards.add(new SetCardInfo("Mountain", 49, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 101, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Netherborn Phalanx", 6, Rarity.UNCOMMON, mage.cards.n.NetherbornPhalanx.class));
        cards.add(new SetCardInfo("Nightveil Specter", 19, Rarity.RARE, mage.cards.n.NightveilSpecter.class));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 26, Rarity.RARE, mage.cards.n.NivMizzetTheFiremind.class));
        cards.add(new SetCardInfo("Nivix Guildmage", 42, Rarity.UNCOMMON, mage.cards.n.NivixGuildmage.class));
        cards.add(new SetCardInfo("Plains", 100, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 126, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pollenbright Wings", 115, Rarity.UNCOMMON, mage.cards.p.PollenbrightWings.class));
        cards.add(new SetCardInfo("Privileged Position", 116, Rarity.RARE, mage.cards.p.PrivilegedPosition.class));
        cards.add(new SetCardInfo("Putrefy", 68, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Pyromatics", 33, Rarity.COMMON, mage.cards.p.Pyromatics.class));
        cards.add(new SetCardInfo("Razia, Boros Archangel", 93, Rarity.RARE, mage.cards.r.RaziaBorosArchangel.class));
        cards.add(new SetCardInfo("Ribbons of Night", 7, Rarity.UNCOMMON, mage.cards.r.RibbonsOfNight.class));
        cards.add(new SetCardInfo("Savra, Queen of the Golgari", 69, Rarity.RARE, mage.cards.s.SavraQueenOfTheGolgari.class));
        cards.add(new SetCardInfo("Scatter the Seeds", 106, Rarity.COMMON, mage.cards.s.ScatterTheSeeds.class));
        cards.add(new SetCardInfo("Selesnya Charm", 117, Rarity.UNCOMMON, mage.cards.s.SelesnyaCharm.class));
        cards.add(new SetCardInfo("Selesnya Evangel", 118, Rarity.COMMON, mage.cards.s.SelesnyaEvangel.class));
        cards.add(new SetCardInfo("Selesnya Guildmage", 119, Rarity.UNCOMMON, mage.cards.s.SelesnyaGuildmage.class));
        cards.add(new SetCardInfo("Selesnya Sanctuary", 125, Rarity.COMMON, mage.cards.s.SelesnyaSanctuary.class));
        cards.add(new SetCardInfo("Selesnya Signet", 123, Rarity.COMMON, mage.cards.s.SelesnyaSignet.class));
        cards.add(new SetCardInfo("Shambling Shell", 70, Rarity.COMMON, mage.cards.s.ShamblingShell.class));
        cards.add(new SetCardInfo("Shattering Spree", 34, Rarity.UNCOMMON, mage.cards.s.ShatteringSpree.class));
        cards.add(new SetCardInfo("Sisters of Stone Death", 71, Rarity.RARE, mage.cards.s.SistersOfStoneDeath.class));
        cards.add(new SetCardInfo("Slum Reaper", 52, Rarity.UNCOMMON, mage.cards.s.SlumReaper.class));
        cards.add(new SetCardInfo("Spark Trooper", 94, Rarity.RARE, mage.cards.s.SparkTrooper.class));
        cards.add(new SetCardInfo("Stinkweed Imp", 53, Rarity.COMMON, mage.cards.s.StinkweedImp.class));
        cards.add(new SetCardInfo("Stitch in Time", 43, Rarity.RARE, mage.cards.s.StitchInTime.class));
        cards.add(new SetCardInfo("Stolen Identity", 2, Rarity.RARE, mage.cards.s.StolenIdentity.class));
        cards.add(new SetCardInfo("Sundering Growth", 120, Rarity.COMMON, mage.cards.s.SunderingGrowth.class));
        cards.add(new SetCardInfo("Sunhome Guildmage", 95, Rarity.UNCOMMON, mage.cards.s.SunhomeGuildmage.class));
        cards.add(new SetCardInfo("Sunhome, Fortress of the Legion", 99, Rarity.UNCOMMON, mage.cards.s.SunhomeFortressOfTheLegion.class));
        cards.add(new SetCardInfo("Swamp", 25, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 75, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Syncopate", 3, Rarity.UNCOMMON, mage.cards.s.Syncopate.class));
        cards.add(new SetCardInfo("Szadek, Lord of Secrets", 20, Rarity.RARE, mage.cards.s.SzadekLordOfSecrets.class));
        cards.add(new SetCardInfo("Telling Time", 4, Rarity.UNCOMMON, mage.cards.t.TellingTime.class));
        cards.add(new SetCardInfo("Thunderheads", 27, Rarity.UNCOMMON, mage.cards.t.Thunderheads.class));
        cards.add(new SetCardInfo("Tibor and Lumia", 44, Rarity.RARE, mage.cards.t.TiborAndLumia.class));
        cards.add(new SetCardInfo("Tolsimir Wolfblood", 121, Rarity.RARE, mage.cards.t.TolsimirWolfblood.class));
        cards.add(new SetCardInfo("Treasured Find", 72, Rarity.UNCOMMON, mage.cards.t.TreasuredFind.class));
        cards.add(new SetCardInfo("Trostani, Selesnya's Voice", 102, Rarity.MYTHIC, mage.cards.t.TrostaniSelesnyasVoice.class));
        cards.add(new SetCardInfo("Turn // Burn", 45, Rarity.UNCOMMON, mage.cards.t.TurnBurn.class));
        cards.add(new SetCardInfo("Vigor Mortis", 54, Rarity.UNCOMMON, mage.cards.v.VigorMortis.class));
        cards.add(new SetCardInfo("Warped Physique", 21, Rarity.UNCOMMON, mage.cards.w.WarpedPhysique.class));
        cards.add(new SetCardInfo("Watchwolf", 122, Rarity.UNCOMMON, mage.cards.w.Watchwolf.class));
    }
}
