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
 * @author JayDi85
 */
public final class UltimateMasters extends ExpansionSet {

    private static final UltimateMasters instance = new UltimateMasters();

    public static UltimateMasters getInstance() {
        return instance;
    }

    private UltimateMasters() {
        super("Ultimate Masters", "UMA", ExpansionSet.buildDate(2018, 12, 7), SetType.SUPPLEMENTAL);
        this.blockName = "Reprint";
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Aethersnipe", 44, Rarity.COMMON, mage.cards.a.Aethersnipe.class));
        cards.add(new SetCardInfo("Akroan Crusader", 121, Rarity.COMMON, mage.cards.a.AkroanCrusader.class));
        cards.add(new SetCardInfo("All Is Dust", 1, Rarity.RARE, mage.cards.a.AllIsDust.class));
        cards.add(new SetCardInfo("Ancestor's Chosen", 9, Rarity.UNCOMMON, mage.cards.a.AncestorsChosen.class));
        cards.add(new SetCardInfo("Ancient Tomb", 236, Rarity.RARE, mage.cards.a.AncientTomb.class));
        cards.add(new SetCardInfo("Angel of Despair", 196, Rarity.UNCOMMON, mage.cards.a.AngelOfDespair.class));
        cards.add(new SetCardInfo("Angelic Renewal", 10, Rarity.COMMON, mage.cards.a.AngelicRenewal.class));
        cards.add(new SetCardInfo("Anger", 122, Rarity.UNCOMMON, mage.cards.a.Anger.class));
        cards.add(new SetCardInfo("Appetite for Brains", 83, Rarity.UNCOMMON, mage.cards.a.AppetiteForBrains.class));
        cards.add(new SetCardInfo("Apprentice Necromancer", 84, Rarity.UNCOMMON, mage.cards.a.ApprenticeNecromancer.class));
        cards.add(new SetCardInfo("Archaeomancer", 45, Rarity.COMMON, mage.cards.a.Archaeomancer.class));
        cards.add(new SetCardInfo("Arena Athlete", 123, Rarity.COMMON, mage.cards.a.ArenaAthlete.class));
        cards.add(new SetCardInfo("Artisan of Kozilek", 2, Rarity.UNCOMMON, mage.cards.a.ArtisanOfKozilek.class));
        cards.add(new SetCardInfo("Back to Basics", 46, Rarity.RARE, mage.cards.b.BackToBasics.class));
        cards.add(new SetCardInfo("Balefire Dragon", 124, Rarity.MYTHIC, mage.cards.b.BalefireDragon.class));
        cards.add(new SetCardInfo("Basking Rootwalla", 156, Rarity.COMMON, mage.cards.b.BaskingRootwalla.class));
        cards.add(new SetCardInfo("Beckon Apparition", 211, Rarity.COMMON, mage.cards.b.BeckonApparition.class));
        cards.add(new SetCardInfo("Become Immense", 157, Rarity.UNCOMMON, mage.cards.b.BecomeImmense.class));
        cards.add(new SetCardInfo("Bitterblossom", 85, Rarity.MYTHIC, mage.cards.b.Bitterblossom.class));
        cards.add(new SetCardInfo("Blast of Genius", 197, Rarity.UNCOMMON, mage.cards.b.BlastOfGenius.class));
        cards.add(new SetCardInfo("Bloodflow Connoisseur", 86, Rarity.COMMON, mage.cards.b.BloodflowConnoisseur.class));
        cards.add(new SetCardInfo("Boar Umbra", 158, Rarity.UNCOMMON, mage.cards.b.BoarUmbra.class));
        cards.add(new SetCardInfo("Boneyard Wurm", 159, Rarity.UNCOMMON, mage.cards.b.BoneyardWurm.class));
        cards.add(new SetCardInfo("Brawn", 160, Rarity.UNCOMMON, mage.cards.b.Brawn.class));
        cards.add(new SetCardInfo("Brazen Scourge", 125, Rarity.UNCOMMON, mage.cards.b.BrazenScourge.class));
        cards.add(new SetCardInfo("Bridge from Below", 87, Rarity.RARE, mage.cards.b.BridgeFromBelow.class));
        cards.add(new SetCardInfo("Buried Alive", 88, Rarity.UNCOMMON, mage.cards.b.BuriedAlive.class));
        cards.add(new SetCardInfo("Canker Abomination", 212, Rarity.COMMON, mage.cards.c.CankerAbomination.class));
        cards.add(new SetCardInfo("Cathodion", 226, Rarity.COMMON, mage.cards.c.Cathodion.class));
        cards.add(new SetCardInfo("Cavern of Souls", 237, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Celestial Colonnade", 238, Rarity.RARE, mage.cards.c.CelestialColonnade.class));
        cards.add(new SetCardInfo("Chainer's Edict", 89, Rarity.UNCOMMON, mage.cards.c.ChainersEdict.class));
        cards.add(new SetCardInfo("Circular Logic", 47, Rarity.UNCOMMON, mage.cards.c.CircularLogic.class));
        cards.add(new SetCardInfo("Conflagrate", 126, Rarity.UNCOMMON, mage.cards.c.Conflagrate.class));
        cards.add(new SetCardInfo("Containment Priest", 11, Rarity.RARE, mage.cards.c.ContainmentPriest.class));
        cards.add(new SetCardInfo("Conviction", 12, Rarity.COMMON, mage.cards.c.Conviction.class));
        cards.add(new SetCardInfo("Countersquall", 198, Rarity.UNCOMMON, mage.cards.c.Countersquall.class));
        cards.add(new SetCardInfo("Creeping Tar Pit", 239, Rarity.RARE, mage.cards.c.CreepingTarPit.class));
        cards.add(new SetCardInfo("Crow of Dark Tidings", 90, Rarity.COMMON, mage.cards.c.CrowOfDarkTidings.class));
        cards.add(new SetCardInfo("Crushing Canopy", 161, Rarity.COMMON, mage.cards.c.CrushingCanopy.class));
        cards.add(new SetCardInfo("Dakmor Salvage", 240, Rarity.UNCOMMON, mage.cards.d.DakmorSalvage.class));
        cards.add(new SetCardInfo("Dark Dabbling", 91, Rarity.COMMON, mage.cards.d.DarkDabbling.class));
        cards.add(new SetCardInfo("Dark Depths", 241, Rarity.MYTHIC, mage.cards.d.DarkDepths.class));
        cards.add(new SetCardInfo("Dawn Charm", 13, Rarity.UNCOMMON, mage.cards.d.DawnCharm.class));
        cards.add(new SetCardInfo("Daybreak Coronet", 14, Rarity.RARE, mage.cards.d.DaybreakCoronet.class));
        cards.add(new SetCardInfo("Death Denied", 92, Rarity.COMMON, mage.cards.d.DeathDenied.class));
        cards.add(new SetCardInfo("Defy Gravity", 48, Rarity.COMMON, mage.cards.d.DefyGravity.class));
        cards.add(new SetCardInfo("Demonic Tutor", 93, Rarity.RARE, mage.cards.d.DemonicTutor.class));
        cards.add(new SetCardInfo("Deranged Assistant", 49, Rarity.COMMON, mage.cards.d.DerangedAssistant.class));
        cards.add(new SetCardInfo("Desolate Lighthouse", 242, Rarity.RARE, mage.cards.d.DesolateLighthouse.class));
        cards.add(new SetCardInfo("Desperate Ritual", 127, Rarity.UNCOMMON, mage.cards.d.DesperateRitual.class));
        cards.add(new SetCardInfo("Devoted Druid", 162, Rarity.UNCOMMON, mage.cards.d.DevotedDruid.class));
        cards.add(new SetCardInfo("Dig Through Time", 50, Rarity.RARE, mage.cards.d.DigThroughTime.class));
        cards.add(new SetCardInfo("Dimir Guildmage", 213, Rarity.COMMON, mage.cards.d.DimirGuildmage.class));
        cards.add(new SetCardInfo("Disrupting Shoal", 51, Rarity.RARE, mage.cards.d.DisruptingShoal.class));
        cards.add(new SetCardInfo("Double Cleave", 214, Rarity.COMMON, mage.cards.d.DoubleCleave.class));
        cards.add(new SetCardInfo("Dreamscape Artist", 52, Rarity.UNCOMMON, mage.cards.d.DreamscapeArtist.class));
        cards.add(new SetCardInfo("Eel Umbra", 53, Rarity.COMMON, mage.cards.e.EelUmbra.class));
        cards.add(new SetCardInfo("Eldrazi Conscription", 3, Rarity.RARE, mage.cards.e.EldraziConscription.class));
        cards.add(new SetCardInfo("Emancipation Angel", 15, Rarity.UNCOMMON, mage.cards.e.EmancipationAngel.class));
        cards.add(new SetCardInfo("Emrakul, the Aeons Torn", 4, Rarity.MYTHIC, mage.cards.e.EmrakulTheAeonsTorn.class));
        cards.add(new SetCardInfo("Engineered Explosives", 227, Rarity.RARE, mage.cards.e.EngineeredExplosives.class));
        cards.add(new SetCardInfo("Entomb", 94, Rarity.RARE, mage.cards.e.Entomb.class));
        cards.add(new SetCardInfo("Eternal Witness", 163, Rarity.UNCOMMON, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Faith's Fetters", 16, Rarity.COMMON, mage.cards.f.FaithsFetters.class));
        cards.add(new SetCardInfo("Faithless Looting", 128, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Fauna Shaman", 164, Rarity.RARE, mage.cards.f.FaunaShaman.class));
        cards.add(new SetCardInfo("Fecundity", 165, Rarity.UNCOMMON, mage.cards.f.Fecundity.class));
        cards.add(new SetCardInfo("Fiend Hunter", 17, Rarity.UNCOMMON, mage.cards.f.FiendHunter.class));
        cards.add(new SetCardInfo("Fiery Temper", 129, Rarity.COMMON, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Fire // Ice", 225, Rarity.COMMON, mage.cards.f.FireIce.class));
        cards.add(new SetCardInfo("Firewing Phoenix", 130, Rarity.UNCOMMON, mage.cards.f.FirewingPhoenix.class));
        cards.add(new SetCardInfo("Flagstones of Trokair", 243, Rarity.RARE, mage.cards.f.FlagstonesOfTrokair.class));
        cards.add(new SetCardInfo("Flight of Fancy", 54, Rarity.COMMON, mage.cards.f.FlightOfFancy.class));
        cards.add(new SetCardInfo("Foil", 55, Rarity.COMMON, mage.cards.f.Foil.class));
        cards.add(new SetCardInfo("Forbidden Alchemy", 56, Rarity.UNCOMMON, mage.cards.f.ForbiddenAlchemy.class));
        cards.add(new SetCardInfo("Frantic Search", 57, Rarity.COMMON, mage.cards.f.FranticSearch.class));
        cards.add(new SetCardInfo("Fulminator Mage", 215, Rarity.RARE, mage.cards.f.FulminatorMage.class));
        cards.add(new SetCardInfo("Fume Spitter", 95, Rarity.COMMON, mage.cards.f.FumeSpitter.class));
        cards.add(new SetCardInfo("Furnace Celebration", 131, Rarity.UNCOMMON, mage.cards.f.FurnaceCelebration.class));
        cards.add(new SetCardInfo("Gaddock Teeg", 199, Rarity.RARE, mage.cards.g.GaddockTeeg.class));
        cards.add(new SetCardInfo("Gamble", 132, Rarity.RARE, mage.cards.g.Gamble.class));
        cards.add(new SetCardInfo("Garna, the Bloodflame", 200, Rarity.UNCOMMON, mage.cards.g.GarnaTheBloodflame.class));
        cards.add(new SetCardInfo("Generator Servant", 133, Rarity.COMMON, mage.cards.g.GeneratorServant.class));
        cards.add(new SetCardInfo("Ghoulcaller's Accomplice", 96, Rarity.COMMON, mage.cards.g.GhoulcallersAccomplice.class));
        cards.add(new SetCardInfo("Ghoulsteed", 97, Rarity.UNCOMMON, mage.cards.g.Ghoulsteed.class));
        cards.add(new SetCardInfo("Glen Elendra Archmage", 58, Rarity.RARE, mage.cards.g.GlenElendraArchmage.class));
        cards.add(new SetCardInfo("Gods Willing", 18, Rarity.COMMON, mage.cards.g.GodsWilling.class));
        cards.add(new SetCardInfo("Golgari Brownscale", 166, Rarity.COMMON, mage.cards.g.GolgariBrownscale.class));
        cards.add(new SetCardInfo("Golgari Charm", 201, Rarity.UNCOMMON, mage.cards.g.GolgariCharm.class));
        cards.add(new SetCardInfo("Golgari Grave-Troll", 167, Rarity.RARE, mage.cards.g.GolgariGraveTroll.class));
        cards.add(new SetCardInfo("Golgari Thug", 98, Rarity.UNCOMMON, mage.cards.g.GolgariThug.class));
        cards.add(new SetCardInfo("Goryo's Vengeance", 99, Rarity.RARE, mage.cards.g.GoryosVengeance.class));
        cards.add(new SetCardInfo("Grave Scrabbler", 100, Rarity.COMMON, mage.cards.g.GraveScrabbler.class));
        cards.add(new SetCardInfo("Grave Strength", 101, Rarity.UNCOMMON, mage.cards.g.GraveStrength.class));
        cards.add(new SetCardInfo("Groundskeeper", 168, Rarity.COMMON, mage.cards.g.Groundskeeper.class));
        cards.add(new SetCardInfo("Gurmag Angler", 102, Rarity.COMMON, mage.cards.g.GurmagAngler.class));
        cards.add(new SetCardInfo("Heap Doll", 228, Rarity.UNCOMMON, mage.cards.h.HeapDoll.class));
        cards.add(new SetCardInfo("Heliod's Pilgrim", 19, Rarity.COMMON, mage.cards.h.HeliodsPilgrim.class));
        cards.add(new SetCardInfo("Hero of Iroas", 20, Rarity.UNCOMMON, mage.cards.h.HeroOfIroas.class));
        cards.add(new SetCardInfo("Hero of Leina Tower", 169, Rarity.UNCOMMON, mage.cards.h.HeroOfLeinaTower.class));
        cards.add(new SetCardInfo("Hissing Iguanar", 134, Rarity.COMMON, mage.cards.h.HissingIguanar.class));
        cards.add(new SetCardInfo("Hooting Mandrills", 170, Rarity.COMMON, mage.cards.h.HootingMandrills.class));
        cards.add(new SetCardInfo("Hyena Umbra", 21, Rarity.COMMON, mage.cards.h.HyenaUmbra.class));
        cards.add(new SetCardInfo("Icatian Crier", 22, Rarity.COMMON, mage.cards.i.IcatianCrier.class));
        cards.add(new SetCardInfo("Ingot Chewer", 135, Rarity.COMMON, mage.cards.i.IngotChewer.class));
        cards.add(new SetCardInfo("Iridescent Drake", 59, Rarity.UNCOMMON, mage.cards.i.IridescentDrake.class));
        cards.add(new SetCardInfo("Just the Wind", 60, Rarity.COMMON, mage.cards.j.JustTheWind.class));
        cards.add(new SetCardInfo("Karakas", 244, Rarity.MYTHIC, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Karn Liberated", 5, Rarity.MYTHIC, mage.cards.k.KarnLiberated.class));
        cards.add(new SetCardInfo("Kitchen Finks", 216, Rarity.UNCOMMON, mage.cards.k.KitchenFinks.class));
        cards.add(new SetCardInfo("Kodama's Reach", 171, Rarity.COMMON, mage.cards.k.KodamasReach.class));
        cards.add(new SetCardInfo("Kozilek, Butcher of Truth", 6, Rarity.MYTHIC, mage.cards.k.KozilekButcherOfTruth.class));
        cards.add(new SetCardInfo("Laboratory Maniac", 61, Rarity.UNCOMMON, mage.cards.l.LaboratoryManiac.class));
        cards.add(new SetCardInfo("Last Gasp", 103, Rarity.COMMON, mage.cards.l.LastGasp.class));
        cards.add(new SetCardInfo("Lava Spike", 136, Rarity.UNCOMMON, mage.cards.l.LavaSpike.class));
        cards.add(new SetCardInfo("Lavaclaw Reaches", 245, Rarity.RARE, mage.cards.l.LavaclawReaches.class));
        cards.add(new SetCardInfo("Leovold, Emissary of Trest", 202, Rarity.MYTHIC, mage.cards.l.LeovoldEmissaryOfTrest.class));
        cards.add(new SetCardInfo("Life from the Loam", 172, Rarity.RARE, mage.cards.l.LifeFromTheLoam.class));
        cards.add(new SetCardInfo("Liliana of the Veil", 104, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Living Lore", 62, Rarity.UNCOMMON, mage.cards.l.LivingLore.class));
        cards.add(new SetCardInfo("Lord of Extinction", 203, Rarity.MYTHIC, mage.cards.l.LordOfExtinction.class));
        cards.add(new SetCardInfo("Lotus-Eye Mystics", 23, Rarity.COMMON, mage.cards.l.LotusEyeMystics.class));
        cards.add(new SetCardInfo("Mad Prophet", 137, Rarity.COMMON, mage.cards.m.MadProphet.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 204, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Mage-Ring Network", 246, Rarity.UNCOMMON, mage.cards.m.MageRingNetwork.class));
        cards.add(new SetCardInfo("Magmaw", 138, Rarity.UNCOMMON, mage.cards.m.Magmaw.class));
        cards.add(new SetCardInfo("Magus of the Bazaar", 63, Rarity.RARE, mage.cards.m.MagusOfTheBazaar.class));
        cards.add(new SetCardInfo("Mahamoti Djinn", 64, Rarity.UNCOMMON, mage.cards.m.MahamotiDjinn.class));
        cards.add(new SetCardInfo("Malevolent Whispers", 139, Rarity.UNCOMMON, mage.cards.m.MalevolentWhispers.class));
        cards.add(new SetCardInfo("Mammoth Umbra", 24, Rarity.COMMON, mage.cards.m.MammothUmbra.class));
        cards.add(new SetCardInfo("Mana Vault", 229, Rarity.MYTHIC, mage.cards.m.ManaVault.class));
        cards.add(new SetCardInfo("Marang River Prowler", 65, Rarity.UNCOMMON, mage.cards.m.MarangRiverProwler.class));
        cards.add(new SetCardInfo("Mark of the Vampire", 105, Rarity.COMMON, mage.cards.m.MarkOfTheVampire.class));
        cards.add(new SetCardInfo("Martyr of Sands", 25, Rarity.COMMON, mage.cards.m.MartyrOfSands.class));
        cards.add(new SetCardInfo("Mikaeus, the Unhallowed", 106, Rarity.MYTHIC, mage.cards.m.MikaeusTheUnhallowed.class));
        cards.add(new SetCardInfo("Miming Slime", 173, Rarity.COMMON, mage.cards.m.MimingSlime.class));
        cards.add(new SetCardInfo("Miraculous Recovery", 26, Rarity.UNCOMMON, mage.cards.m.MiraculousRecovery.class));
        cards.add(new SetCardInfo("Mistveil Plains", 247, Rarity.UNCOMMON, mage.cards.m.MistveilPlains.class));
        cards.add(new SetCardInfo("Moan of the Unhallowed", 107, Rarity.COMMON, mage.cards.m.MoanOfTheUnhallowed.class));
        cards.add(new SetCardInfo("Molten Birth", 140, Rarity.COMMON, mage.cards.m.MoltenBirth.class));
        cards.add(new SetCardInfo("Murderous Redcap", 217, Rarity.UNCOMMON, mage.cards.m.MurderousRedcap.class));
        cards.add(new SetCardInfo("Myr Servitor", 230, Rarity.COMMON, mage.cards.m.MyrServitor.class));
        cards.add(new SetCardInfo("Mystic Retrieval", 66, Rarity.UNCOMMON, mage.cards.m.MysticRetrieval.class));
        cards.add(new SetCardInfo("Nightbird's Clutches", 141, Rarity.COMMON, mage.cards.n.NightbirdsClutches.class));
        cards.add(new SetCardInfo("Noble Hierarch", 174, Rarity.RARE, mage.cards.n.NobleHierarch.class));
        cards.add(new SetCardInfo("Nourishing Shoal", 175, Rarity.RARE, mage.cards.n.NourishingShoal.class));
        cards.add(new SetCardInfo("Offalsnout", 108, Rarity.COMMON, mage.cards.o.Offalsnout.class));
        cards.add(new SetCardInfo("Olivia's Dragoon", 109, Rarity.COMMON, mage.cards.o.OliviasDragoon.class));
        cards.add(new SetCardInfo("Patchwork Gnomes", 231, Rarity.COMMON, mage.cards.p.PatchworkGnomes.class));
        cards.add(new SetCardInfo("Pattern of Rebirth", 176, Rarity.RARE, mage.cards.p.PatternOfRebirth.class));
        cards.add(new SetCardInfo("Penumbra Wurm", 177, Rarity.UNCOMMON, mage.cards.p.PenumbraWurm.class));
        cards.add(new SetCardInfo("Phalanx Leader", 27, Rarity.UNCOMMON, mage.cards.p.PhalanxLeader.class));
        cards.add(new SetCardInfo("Phyrexian Altar", 232, Rarity.RARE, mage.cards.p.PhyrexianAltar.class));
        cards.add(new SetCardInfo("Phyrexian Tower", 248, Rarity.RARE, mage.cards.p.PhyrexianTower.class));
        cards.add(new SetCardInfo("Platinum Emperion", 233, Rarity.MYTHIC, mage.cards.p.PlatinumEmperion.class));
        cards.add(new SetCardInfo("Plumeveil", 218, Rarity.UNCOMMON, mage.cards.p.Plumeveil.class));
        cards.add(new SetCardInfo("Prey Upon", 178, Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Prismatic Lens", 234, Rarity.UNCOMMON, mage.cards.p.PrismaticLens.class));
        cards.add(new SetCardInfo("Pulse of Murasa", 179, Rarity.COMMON, mage.cards.p.PulseOfMurasa.class));
        cards.add(new SetCardInfo("Raging Ravine", 249, Rarity.RARE, mage.cards.r.RagingRavine.class));
        cards.add(new SetCardInfo("Raid Bombardment", 142, Rarity.COMMON, mage.cards.r.RaidBombardment.class));
        cards.add(new SetCardInfo("Rakdos Shred-Freak", 219, Rarity.COMMON, mage.cards.r.RakdosShredFreak.class));
        cards.add(new SetCardInfo("Rally the Peasants", 28, Rarity.UNCOMMON, mage.cards.r.RallyThePeasants.class));
        cards.add(new SetCardInfo("Reanimate", 110, Rarity.RARE, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Reckless Charge", 143, Rarity.COMMON, mage.cards.r.RecklessCharge.class));
        cards.add(new SetCardInfo("Reckless Wurm", 144, Rarity.COMMON, mage.cards.r.RecklessWurm.class));
        cards.add(new SetCardInfo("Repel the Darkness", 29, Rarity.COMMON, mage.cards.r.RepelTheDarkness.class));
        cards.add(new SetCardInfo("Resurrection", 30, Rarity.COMMON, mage.cards.r.Resurrection.class));
        cards.add(new SetCardInfo("Reveillark", 31, Rarity.RARE, mage.cards.r.Reveillark.class));
        cards.add(new SetCardInfo("Reviving Vapors", 205, Rarity.UNCOMMON, mage.cards.r.RevivingVapors.class));
        cards.add(new SetCardInfo("Reya Dawnbringer", 32, Rarity.RARE, mage.cards.r.ReyaDawnbringer.class));
        cards.add(new SetCardInfo("Rise from the Tides", 67, Rarity.UNCOMMON, mage.cards.r.RiseFromTheTides.class));
        cards.add(new SetCardInfo("Rogue's Passage", 250, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Rolling Temblor", 145, Rarity.UNCOMMON, mage.cards.r.RollingTemblor.class));
        cards.add(new SetCardInfo("Ronom Unicorn", 33, Rarity.COMMON, mage.cards.r.RonomUnicorn.class));
        cards.add(new SetCardInfo("Rune Snag", 68, Rarity.COMMON, mage.cards.r.RuneSnag.class));
        cards.add(new SetCardInfo("Runed Halo", 34, Rarity.RARE, mage.cards.r.RunedHalo.class));
        cards.add(new SetCardInfo("Safehold Elite", 220, Rarity.COMMON, mage.cards.s.SafeholdElite.class));
        cards.add(new SetCardInfo("Sanitarium Skeleton", 111, Rarity.COMMON, mage.cards.s.SanitariumSkeleton.class));
        cards.add(new SetCardInfo("Satyr Wayfinder", 180, Rarity.COMMON, mage.cards.s.SatyrWayfinder.class));
        cards.add(new SetCardInfo("Scuzzback Marauders", 221, Rarity.COMMON, mage.cards.s.ScuzzbackMarauders.class));
        cards.add(new SetCardInfo("Seismic Assault", 146, Rarity.RARE, mage.cards.s.SeismicAssault.class));
        cards.add(new SetCardInfo("Seize the Day", 147, Rarity.RARE, mage.cards.s.SeizeTheDay.class));
        cards.add(new SetCardInfo("Shed Weakness", 181, Rarity.COMMON, mage.cards.s.ShedWeakness.class));
        cards.add(new SetCardInfo("Shielding Plax", 222, Rarity.COMMON, mage.cards.s.ShieldingPlax.class));
        cards.add(new SetCardInfo("Shirei, Shizo's Caretaker", 112, Rarity.UNCOMMON, mage.cards.s.ShireiShizosCaretaker.class));
        cards.add(new SetCardInfo("Shriekmaw", 113, Rarity.UNCOMMON, mage.cards.s.Shriekmaw.class));
        cards.add(new SetCardInfo("Sigarda, Host of Herons", 206, Rarity.MYTHIC, mage.cards.s.SigardaHostOfHerons.class));
        cards.add(new SetCardInfo("Sigil of the New Dawn", 35, Rarity.UNCOMMON, mage.cards.s.SigilOfTheNewDawn.class));
        cards.add(new SetCardInfo("Skyspear Cavalry", 36, Rarity.COMMON, mage.cards.s.SkyspearCavalry.class));
        cards.add(new SetCardInfo("Skywing Aven", 69, Rarity.COMMON, mage.cards.s.SkywingAven.class));
        cards.add(new SetCardInfo("Sleight of Hand", 70, Rarity.UNCOMMON, mage.cards.s.SleightOfHand.class));
        cards.add(new SetCardInfo("Slippery Bogle", 223, Rarity.UNCOMMON, mage.cards.s.SlipperyBogle.class));
        cards.add(new SetCardInfo("Slum Reaper", 114, Rarity.COMMON, mage.cards.s.SlumReaper.class));
        cards.add(new SetCardInfo("Snake Umbra", 182, Rarity.UNCOMMON, mage.cards.s.SnakeUmbra.class));
        cards.add(new SetCardInfo("Snapcaster Mage", 71, Rarity.MYTHIC, mage.cards.s.SnapcasterMage.class));
        cards.add(new SetCardInfo("Songs of the Damned", 115, Rarity.UNCOMMON, mage.cards.s.SongsOfTheDamned.class));
        cards.add(new SetCardInfo("Soul's Fire", 148, Rarity.COMMON, mage.cards.s.SoulsFire.class));
        cards.add(new SetCardInfo("Sovereigns of Lost Alara", 207, Rarity.RARE, mage.cards.s.SovereignsOfLostAlara.class));
        cards.add(new SetCardInfo("Sparkspitter", 149, Rarity.COMMON, mage.cards.s.Sparkspitter.class));
        cards.add(new SetCardInfo("Spider Spawning", 183, Rarity.UNCOMMON, mage.cards.s.SpiderSpawning.class));
        cards.add(new SetCardInfo("Spider Umbra", 184, Rarity.COMMON, mage.cards.s.SpiderUmbra.class));
        cards.add(new SetCardInfo("Spirit Cairn", 37, Rarity.UNCOMMON, mage.cards.s.SpiritCairn.class));
        cards.add(new SetCardInfo("Spoils of the Vault", 116, Rarity.RARE, mage.cards.s.SpoilsOfTheVault.class));
        cards.add(new SetCardInfo("Squee, Goblin Nabob", 150, Rarity.RARE, mage.cards.s.SqueeGoblinNabob.class));
        cards.add(new SetCardInfo("Staunch-Hearted Warrior", 185, Rarity.COMMON, mage.cards.s.StaunchHeartedWarrior.class));
        cards.add(new SetCardInfo("Stingerfling Spider", 186, Rarity.UNCOMMON, mage.cards.s.StingerflingSpider.class));
        cards.add(new SetCardInfo("Stirring Wildwood", 251, Rarity.RARE, mage.cards.s.StirringWildwood.class));
        cards.add(new SetCardInfo("Stitched Drake", 72, Rarity.COMMON, mage.cards.s.StitchedDrake.class));
        cards.add(new SetCardInfo("Stitcher's Apprentice", 73, Rarity.COMMON, mage.cards.s.StitchersApprentice.class));
        cards.add(new SetCardInfo("Stream of Consciousness", 74, Rarity.UNCOMMON, mage.cards.s.StreamOfConsciousness.class));
        cards.add(new SetCardInfo("Sublime Archangel", 38, Rarity.RARE, mage.cards.s.SublimeArchangel.class));
        cards.add(new SetCardInfo("Sultai Skullkeeper", 75, Rarity.COMMON, mage.cards.s.SultaiSkullkeeper.class));
        cards.add(new SetCardInfo("Swift Reckoning", 39, Rarity.UNCOMMON, mage.cards.s.SwiftReckoning.class));
        cards.add(new SetCardInfo("Talrand, Sky Summoner", 76, Rarity.RARE, mage.cards.t.TalrandSkySummoner.class));
        cards.add(new SetCardInfo("Tarmogoyf", 187, Rarity.MYTHIC, mage.cards.t.Tarmogoyf.class));
        cards.add(new SetCardInfo("Tasigur, the Golden Fang", 117, Rarity.RARE, mage.cards.t.TasigurTheGoldenFang.class));
        cards.add(new SetCardInfo("Temporal Manipulation", 77, Rarity.MYTHIC, mage.cards.t.TemporalManipulation.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 252, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Tethmos High Priest", 40, Rarity.COMMON, mage.cards.t.TethmosHighPriest.class));
        cards.add(new SetCardInfo("Thermo-Alchemist", 151, Rarity.COMMON, mage.cards.t.ThermoAlchemist.class));
        cards.add(new SetCardInfo("Thespian's Stage", 253, Rarity.RARE, mage.cards.t.ThespiansStage.class));
        cards.add(new SetCardInfo("Think Twice", 78, Rarity.COMMON, mage.cards.t.ThinkTwice.class));
        cards.add(new SetCardInfo("Through the Breach", 152, Rarity.RARE, mage.cards.t.ThroughTheBreach.class));
        cards.add(new SetCardInfo("Travel Preparations", 188, Rarity.UNCOMMON, mage.cards.t.TravelPreparations.class));
        cards.add(new SetCardInfo("Treasure Cruise", 79, Rarity.COMMON, mage.cards.t.TreasureCruise.class));
        cards.add(new SetCardInfo("Turn to Mist", 224, Rarity.COMMON, mage.cards.t.TurnToMist.class));
        cards.add(new SetCardInfo("Twins of Maurer Estate", 118, Rarity.COMMON, mage.cards.t.TwinsOfMaurerEstate.class));
        cards.add(new SetCardInfo("Ulamog's Crusher", 8, Rarity.COMMON, mage.cards.u.UlamogsCrusher.class));
        cards.add(new SetCardInfo("Ulamog, the Infinite Gyre", 7, Rarity.MYTHIC, mage.cards.u.UlamogTheInfiniteGyre.class));
        cards.add(new SetCardInfo("Unburial Rites", 119, Rarity.UNCOMMON, mage.cards.u.UnburialRites.class));
        cards.add(new SetCardInfo("Undying Rage", 153, Rarity.COMMON, mage.cards.u.UndyingRage.class));
        cards.add(new SetCardInfo("Unholy Hunger", 120, Rarity.COMMON, mage.cards.u.UnholyHunger.class));
        cards.add(new SetCardInfo("Unstable Mutation", 80, Rarity.UNCOMMON, mage.cards.u.UnstableMutation.class));
        cards.add(new SetCardInfo("Urban Evolution", 208, Rarity.UNCOMMON, mage.cards.u.UrbanEvolution.class));
        cards.add(new SetCardInfo("Urborg, Tomb of Yawgmoth", 254, Rarity.RARE, mage.cards.u.UrborgTombOfYawgmoth.class));
        cards.add(new SetCardInfo("Vengeful Rebirth", 209, Rarity.UNCOMMON, mage.cards.v.VengefulRebirth.class));
        cards.add(new SetCardInfo("Vengevine", 189, Rarity.MYTHIC, mage.cards.v.Vengevine.class));
        cards.add(new SetCardInfo("Verdant Eidolon", 190, Rarity.COMMON, mage.cards.v.VerdantEidolon.class));
        cards.add(new SetCardInfo("Vessel of Endless Rest", 235, Rarity.COMMON, mage.cards.v.VesselOfEndlessRest.class));
        cards.add(new SetCardInfo("Vexing Devil", 154, Rarity.RARE, mage.cards.v.VexingDevil.class));
        cards.add(new SetCardInfo("Visions of Beyond", 81, Rarity.RARE, mage.cards.v.VisionsOfBeyond.class));
        cards.add(new SetCardInfo("Walker of the Grove", 191, Rarity.COMMON, mage.cards.w.WalkerOfTheGrove.class));
        cards.add(new SetCardInfo("Wall of Reverence", 41, Rarity.RARE, mage.cards.w.WallOfReverence.class));
        cards.add(new SetCardInfo("Wandering Champion", 42, Rarity.COMMON, mage.cards.w.WanderingChampion.class));
        cards.add(new SetCardInfo("Warleader's Helix", 210, Rarity.UNCOMMON, mage.cards.w.WarleadersHelix.class));
        cards.add(new SetCardInfo("Whirlwind Adept", 82, Rarity.COMMON, mage.cards.w.WhirlwindAdept.class));
        cards.add(new SetCardInfo("Wickerbough Elder", 192, Rarity.COMMON, mage.cards.w.WickerboughElder.class));
        cards.add(new SetCardInfo("Wild Hunger", 193, Rarity.UNCOMMON, mage.cards.w.WildHunger.class));
        cards.add(new SetCardInfo("Wild Mongrel", 194, Rarity.COMMON, mage.cards.w.WildMongrel.class));
        cards.add(new SetCardInfo("Wingsteed Rider", 43, Rarity.COMMON, mage.cards.w.WingsteedRider.class));
        cards.add(new SetCardInfo("Woodfall Primus", 195, Rarity.RARE, mage.cards.w.WoodfallPrimus.class));
        cards.add(new SetCardInfo("Young Pyromancer", 155, Rarity.UNCOMMON, mage.cards.y.YoungPyromancer.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new UltimateMastersCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/uma.html
// Using Japanese collation for common/uncommon, rare collation inferred from other sets
class UltimateMastersCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "79", "221", "44", "137", "60", "43", "68", "212", "73", "134", "82", "21", "57", "225", "54", "180", "75", "226", "78", "144", "68", "16", "69", "129", "44", "252", "79", "72", "221", "49", "137", "45", "43", "82", "225", "78", "144", "73", "21", "54", "212", "57", "180", "60", "226", "68", "134", "75", "21", "69", "129", "44", "252", "49", "72", "221", "79", "137", "45", "16", "82", "212", "57", "134", "78", "43", "54", "226", "60", "180", "69", "225", "73", "144", "45", "21", "75", "129", "44", "221", "79", "72", "252", "49", "144", "60", "16", "82", "212", "68", "137", "78", "43", "54", "226", "73", "180", "57", "225", "45", "134", "75", "16", "69", "129", "49", "252", "72");
    private final CardRun commonB = new CardRun(true, "90", "173", "191", "96", "194", "55", "100", "168", "102", "53", "161", "114", "170", "92", "111", "190", "102", "156", "95", "231", "171", "118", "48", "185", "109", "108", "181", "91", "192", "105", "166", "179", "107", "173", "120", "86", "168", "103", "184", "156", "100", "194", "55", "96", "178", "90", "48", "191", "114", "170", "118", "111", "190", "109", "168", "108", "231", "171", "105", "53", "161", "103", "92", "156", "91", "170", "86", "178", "185", "107", "184", "120", "95", "166", "90", "173", "179", "96", "194", "48", "118", "191", "102", "55", "190", "114", "192", "100", "111", "161", "109", "181", "231", "92", "171", "108", "53", "185", "107", "95", "166", "91", "192", "105", "178", "179", "103", "184", "86", "120", "181");
    private final CardRun commonC = new CardRun(true, "140", "8", "40", "121", "24", "29", "142", "213", "30", "128", "235", "12", "133", "220", "23", "143", "222", "42", "123", "230", "33", "135", "213", "24", "151", "214", "19", "141", "224", "40", "149", "25", "133", "29", "153", "8", "36", "142", "211", "18", "121", "23", "22", "140", "219", "24", "148", "220", "12", "143", "213", "10", "151", "235", "40", "123", "222", "30", "141", "230", "18", "128", "8", "19", "135", "219", "23", "121", "25", "153", "33", "148", "214", "10", "142", "222", "22", "149", "29", "42", "140", "224", "33", "128", "220", "12", "133", "211", "36", "153", "219", "42", "143", "214", "30", "141", "235", "19", "151", "230", "10", "135", "224", "22", "149", "25", "123", "18", "148", "211", "36");
    private final CardRun uncommonA = new CardRun(true, "136", "183", "80", "209", "74", "9", "113", "13", "127", "17", "228", "223", "246", "217", "247", "209", "165", "216", "115", "26", "198", "163", "83", "136", "52", "74", "183", "9", "130", "80", "127", "223", "17", "13", "247", "113", "228", "246", "216", "165", "26", "136", "217", "115", "163", "198", "83", "183", "13", "74", "209", "127", "17", "80", "223", "52", "136", "113", "228", "130", "165", "247", "216", "115", "26", "246", "217", "198", "9", "209", "13", "163", "80", "183", "83", "130", "223", "17", "165", "52", "74", "113", "127", "246", "9", "217", "228", "216", "83", "247", "52", "115", "130", "26", "198", "163");
    private final CardRun uncommonB = new CardRun(true, "112", "188", "65", "158", "84", "125", "193", "56", "182", "196", "131", "39", "200", "169", "28", "97", "35", "160", "62", "155", "205", "89", "20", "98", "122", "47", "250", "208", "39", "70", "88", "177", "197", "67", "125", "119", "234", "37", "66", "126", "65", "188", "131", "157", "101", "145", "20", "162", "61", "28", "210", "240", "186", "139", "64", "218", "15", "138", "201", "159", "27", "169", "84", "193", "2", "97", "205", "88", "35", "56", "112", "200", "59", "196", "70", "155", "89", "208", "122", "62", "182", "61", "158", "119", "47", "126", "15", "67", "145", "101", "64", "197", "98", "162", "250", "138", "66", "186", "37", "160", "210", "2", "157", "201", "234", "159", "218", "139", "27", "59", "177", "240");
    private final CardRun rare = new CardRun(false, "1", "3", "11", "14", "31", "32", "34", "38", "41", "46", "50", "51", "58", "63", "76", "81", "87", "93", "94", "99", "110", "116", "117", "132", "146", "147", "150", "152", "154", "164", "167", "172", "174", "175", "176", "195", "199", "204", "207", "215", "227", "232", "236", "238", "239", "242", "243", "245", "248", "249", "251", "253", "254");
    private final CardRun mythic = new CardRun(false, "4", "5", "6", "7", "71", "77", "85", "104", "106", "124", "187", "189", "202", "203", "206", "229", "233", "237", "241", "244");
    private final CardRun foilCommon = new CardRun(false, "8", "10", "12", "16", "18", "19", "21", "22", "23", "24", "25", "29", "30", "33", "36", "40", "42", "43", "44", "45", "48", "49", "53", "54", "55", "57", "60", "68", "69", "72", "73", "75", "78", "79", "82", "86", "90", "91", "92", "95", "96", "100", "102", "103", "105", "107", "108", "109", "111", "114", "118", "120", "121", "123", "128", "129", "133", "134", "135", "137", "140", "141", "142", "143", "144", "148", "149", "151", "153", "156", "161", "166", "168", "170", "171", "173", "178", "179", "180", "181", "184", "185", "190", "191", "192", "194", "211", "212", "213", "214", "219", "220", "221", "222", "224", "225", "226", "230", "231", "235", "252");
    private final CardRun foilUncommon = new CardRun(false, "2", "9", "13", "15", "17", "20", "26", "27", "28", "35", "37", "39", "47", "52", "56", "59", "61", "62", "64", "65", "66", "67", "70", "74", "80", "83", "84", "88", "89", "97", "98", "101", "112", "113", "115", "119", "122", "125", "126", "127", "130", "131", "136", "138", "139", "145", "155", "157", "158", "159", "160", "162", "163", "165", "169", "177", "182", "183", "186", "188", "193", "196", "197", "198", "200", "201", "205", "208", "209", "210", "216", "217", "218", "223", "228", "234", "240", "246", "247", "250");

    private final BoosterStructure AABBBBCCCC = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure AAABBBCCCC = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure AAABBBBCCC = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );

    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure BBB = new BoosterStructure(uncommonB, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure M1 = new BoosterStructure(mythic);
    private final BoosterStructure FC = new BoosterStructure(foilCommon);
    private final BoosterStructure FU = new BoosterStructure(foilUncommon);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 2.67 A commons (270 / 101)
    // 3.66 B commons (370 / 101)
    // 3.66 C commons (370 / 101)
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC
    );
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 0.9 A uncommons (72 / 80)
    // 2.1 B uncommons (168 / 80)
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, ABB, BBB
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1, R1, R1, R1, R1, R1, R1, M1);
    private final RarityConfiguration foilRuns = new RarityConfiguration(
            FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC,
            FC, FC, FC, FC, FC, FC, FC, FC,
            FU, FU, FU, FU, FU, FU, FU, FU,
            FU, FU, FU, FU, FU, FU, FU, FU,
            FU, FU, FU, FU, FU, FU, FU, FU,
            R1, R1, R1, R1, R1, R1, R1, M1
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
