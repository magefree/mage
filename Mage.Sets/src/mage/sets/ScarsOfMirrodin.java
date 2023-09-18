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
 * @author nantuko84
 */
public final class ScarsOfMirrodin extends ExpansionSet {

    private static final ScarsOfMirrodin instance = new ScarsOfMirrodin();

    public static ScarsOfMirrodin getInstance() {
        return instance;
    }

    private ScarsOfMirrodin() {
        super("Scars of Mirrodin", "SOM", ExpansionSet.buildDate(2010, 10, 1), SetType.EXPANSION);
        this.blockName = "Scars of Mirrodin";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Abuna Acolyte", 1, Rarity.UNCOMMON, mage.cards.a.AbunaAcolyte.class));
        cards.add(new SetCardInfo("Accorder's Shield", 136, Rarity.COMMON, mage.cards.a.AccordersShield.class));
        cards.add(new SetCardInfo("Acid Web Spider", 108, Rarity.UNCOMMON, mage.cards.a.AcidWebSpider.class));
        cards.add(new SetCardInfo("Alpha Tyrranax", 109, Rarity.COMMON, mage.cards.a.AlphaTyrranax.class));
        cards.add(new SetCardInfo("Arc Trail", 81, Rarity.UNCOMMON, mage.cards.a.ArcTrail.class));
        cards.add(new SetCardInfo("Argent Sphinx", 28, Rarity.RARE, mage.cards.a.ArgentSphinx.class));
        cards.add(new SetCardInfo("Argentum Armor", 137, Rarity.RARE, mage.cards.a.ArgentumArmor.class));
        cards.add(new SetCardInfo("Arrest", 2, Rarity.COMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Asceticism", 110, Rarity.RARE, mage.cards.a.Asceticism.class));
        cards.add(new SetCardInfo("Assault Strobe", 82, Rarity.COMMON, mage.cards.a.AssaultStrobe.class));
        cards.add(new SetCardInfo("Auriok Edgewright", 3, Rarity.UNCOMMON, mage.cards.a.AuriokEdgewright.class));
        cards.add(new SetCardInfo("Auriok Replica", 138, Rarity.COMMON, mage.cards.a.AuriokReplica.class));
        cards.add(new SetCardInfo("Auriok Sunchaser", 4, Rarity.COMMON, mage.cards.a.AuriokSunchaser.class));
        cards.add(new SetCardInfo("Barbed Battlegear", 139, Rarity.UNCOMMON, mage.cards.b.BarbedBattlegear.class));
        cards.add(new SetCardInfo("Barrage Ogre", 83, Rarity.UNCOMMON, mage.cards.b.BarrageOgre.class));
        cards.add(new SetCardInfo("Bellowing Tanglewurm", 111, Rarity.UNCOMMON, mage.cards.b.BellowingTanglewurm.class));
        cards.add(new SetCardInfo("Blackcleave Cliffs", 224, Rarity.RARE, mage.cards.b.BlackcleaveCliffs.class));
        cards.add(new SetCardInfo("Blackcleave Goblin", 54, Rarity.COMMON, mage.cards.b.BlackcleaveGoblin.class));
        cards.add(new SetCardInfo("Bladed Pinions", 140, Rarity.COMMON, mage.cards.b.BladedPinions.class));
        cards.add(new SetCardInfo("Blade-Tribe Berserkers", 84, Rarity.COMMON, mage.cards.b.BladeTribeBerserkers.class));
        cards.add(new SetCardInfo("Bleak Coven Vampires", 55, Rarity.COMMON, mage.cards.b.BleakCovenVampires.class));
        cards.add(new SetCardInfo("Blight Mamba", 112, Rarity.COMMON, mage.cards.b.BlightMamba.class));
        cards.add(new SetCardInfo("Blistergrub", 56, Rarity.COMMON, mage.cards.b.Blistergrub.class));
        cards.add(new SetCardInfo("Bloodshot Trainee", 85, Rarity.UNCOMMON, mage.cards.b.BloodshotTrainee.class));
        cards.add(new SetCardInfo("Blunt the Assault", 113, Rarity.COMMON, mage.cards.b.BluntTheAssault.class));
        cards.add(new SetCardInfo("Bonds of Quicksilver", 29, Rarity.COMMON, mage.cards.b.BondsOfQuicksilver.class));
        cards.add(new SetCardInfo("Carapace Forger", 114, Rarity.COMMON, mage.cards.c.CarapaceForger.class));
        cards.add(new SetCardInfo("Carnifex Demon", 57, Rarity.RARE, mage.cards.c.CarnifexDemon.class));
        cards.add(new SetCardInfo("Carrion Call", 115, Rarity.UNCOMMON, mage.cards.c.CarrionCall.class));
        cards.add(new SetCardInfo("Cerebral Eruption", 86, Rarity.RARE, mage.cards.c.CerebralEruption.class));
        cards.add(new SetCardInfo("Chimeric Mass", 141, Rarity.RARE, mage.cards.c.ChimericMass.class));
        cards.add(new SetCardInfo("Chrome Steed", 142, Rarity.COMMON, mage.cards.c.ChromeSteed.class));
        cards.add(new SetCardInfo("Clone Shell", 143, Rarity.UNCOMMON, mage.cards.c.CloneShell.class));
        cards.add(new SetCardInfo("Contagion Clasp", 144, Rarity.UNCOMMON, mage.cards.c.ContagionClasp.class));
        cards.add(new SetCardInfo("Contagion Engine", 145, Rarity.RARE, mage.cards.c.ContagionEngine.class));
        cards.add(new SetCardInfo("Contagious Nim", 58, Rarity.COMMON, mage.cards.c.ContagiousNim.class));
        cards.add(new SetCardInfo("Copperhorn Scout", 116, Rarity.COMMON, mage.cards.c.CopperhornScout.class));
        cards.add(new SetCardInfo("Copperline Gorge", 225, Rarity.RARE, mage.cards.c.CopperlineGorge.class));
        cards.add(new SetCardInfo("Copper Myr", 146, Rarity.COMMON, mage.cards.c.CopperMyr.class));
        cards.add(new SetCardInfo("Corpse Cur", 147, Rarity.COMMON, mage.cards.c.CorpseCur.class));
        cards.add(new SetCardInfo("Corrupted Harvester", 59, Rarity.UNCOMMON, mage.cards.c.CorruptedHarvester.class));
        cards.add(new SetCardInfo("Culling Dais", 148, Rarity.UNCOMMON, mage.cards.c.CullingDais.class));
        cards.add(new SetCardInfo("Cystbearer", 117, Rarity.COMMON, mage.cards.c.Cystbearer.class));
        cards.add(new SetCardInfo("Darkslick Drake", 30, Rarity.UNCOMMON, mage.cards.d.DarkslickDrake.class));
        cards.add(new SetCardInfo("Darkslick Shores", 226, Rarity.RARE, mage.cards.d.DarkslickShores.class));
        cards.add(new SetCardInfo("Darksteel Axe", 149, Rarity.UNCOMMON, mage.cards.d.DarksteelAxe.class));
        cards.add(new SetCardInfo("Darksteel Juggernaut", 150, Rarity.RARE, mage.cards.d.DarksteelJuggernaut.class));
        cards.add(new SetCardInfo("Darksteel Myr", 151, Rarity.UNCOMMON, mage.cards.d.DarksteelMyr.class));
        cards.add(new SetCardInfo("Darksteel Sentinel", 152, Rarity.UNCOMMON, mage.cards.d.DarksteelSentinel.class));
        cards.add(new SetCardInfo("Dispense Justice", 5, Rarity.UNCOMMON, mage.cards.d.DispenseJustice.class));
        cards.add(new SetCardInfo("Disperse", 31, Rarity.COMMON, mage.cards.d.Disperse.class));
        cards.add(new SetCardInfo("Dissipation Field", 32, Rarity.RARE, mage.cards.d.DissipationField.class));
        cards.add(new SetCardInfo("Dross Hopper", 60, Rarity.COMMON, mage.cards.d.DrossHopper.class));
        cards.add(new SetCardInfo("Echo Circlet", 153, Rarity.COMMON, mage.cards.e.EchoCirclet.class));
        cards.add(new SetCardInfo("Elspeth Tirel", 6, Rarity.MYTHIC, mage.cards.e.ElspethTirel.class));
        cards.add(new SetCardInfo("Embersmith", 87, Rarity.UNCOMMON, mage.cards.e.Embersmith.class));
        cards.add(new SetCardInfo("Engulfing Slagwurm", 118, Rarity.RARE, mage.cards.e.EngulfingSlagwurm.class));
        cards.add(new SetCardInfo("Etched Champion", 154, Rarity.RARE, mage.cards.e.EtchedChampion.class));
        cards.add(new SetCardInfo("Exsanguinate", 61, Rarity.UNCOMMON, mage.cards.e.Exsanguinate.class));
        cards.add(new SetCardInfo("Ezuri, Renegade Leader", 119, Rarity.RARE, mage.cards.e.EzuriRenegadeLeader.class));
        cards.add(new SetCardInfo("Ezuri's Archers", 120, Rarity.COMMON, mage.cards.e.EzurisArchers.class));
        cards.add(new SetCardInfo("Ezuri's Brigade", 121, Rarity.RARE, mage.cards.e.EzurisBrigade.class));
        cards.add(new SetCardInfo("Ferrovore", 88, Rarity.COMMON, mage.cards.f.Ferrovore.class));
        cards.add(new SetCardInfo("Flameborn Hellion", 89, Rarity.COMMON, mage.cards.f.FlamebornHellion.class));
        cards.add(new SetCardInfo("Flesh Allergy", 62, Rarity.UNCOMMON, mage.cards.f.FleshAllergy.class));
        cards.add(new SetCardInfo("Flight Spellbomb", 155, Rarity.COMMON, mage.cards.f.FlightSpellbomb.class));
        cards.add(new SetCardInfo("Forest", 246, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 247, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 248, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 249, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fulgent Distraction", 7, Rarity.COMMON, mage.cards.f.FulgentDistraction.class));
        cards.add(new SetCardInfo("Fume Spitter", 63, Rarity.COMMON, mage.cards.f.FumeSpitter.class));
        cards.add(new SetCardInfo("Furnace Celebration", 90, Rarity.UNCOMMON, mage.cards.f.FurnaceCelebration.class));
        cards.add(new SetCardInfo("Galvanic Blast", 91, Rarity.COMMON, mage.cards.g.GalvanicBlast.class));
        cards.add(new SetCardInfo("Genesis Wave", 122, Rarity.RARE, mage.cards.g.GenesisWave.class));
        cards.add(new SetCardInfo("Geth, Lord of the Vault", 64, Rarity.MYTHIC, mage.cards.g.GethLordOfTheVault.class));
        cards.add(new SetCardInfo("Ghalma's Warden", 8, Rarity.COMMON, mage.cards.g.GhalmasWarden.class));
        cards.add(new SetCardInfo("Glimmerpoint Stag", 9, Rarity.UNCOMMON, mage.cards.g.GlimmerpointStag.class));
        cards.add(new SetCardInfo("Glimmerpost", 227, Rarity.COMMON, mage.cards.g.Glimmerpost.class));
        cards.add(new SetCardInfo("Glint Hawk", 10, Rarity.COMMON, mage.cards.g.GlintHawk.class));
        cards.add(new SetCardInfo("Glint Hawk Idol", 156, Rarity.COMMON, mage.cards.g.GlintHawkIdol.class));
        cards.add(new SetCardInfo("Goblin Gaveleer", 92, Rarity.COMMON, mage.cards.g.GoblinGaveleer.class));
        cards.add(new SetCardInfo("Golden Urn", 158, Rarity.COMMON, mage.cards.g.GoldenUrn.class));
        cards.add(new SetCardInfo("Gold Myr", 157, Rarity.COMMON, mage.cards.g.GoldMyr.class));
        cards.add(new SetCardInfo("Golem Artisan", 159, Rarity.UNCOMMON, mage.cards.g.GolemArtisan.class));
        cards.add(new SetCardInfo("Golem Foundry", 160, Rarity.COMMON, mage.cards.g.GolemFoundry.class));
        cards.add(new SetCardInfo("Golem's Heart", 161, Rarity.UNCOMMON, mage.cards.g.GolemsHeart.class));
        cards.add(new SetCardInfo("Grafted Exoskeleton", 162, Rarity.UNCOMMON, mage.cards.g.GraftedExoskeleton.class));
        cards.add(new SetCardInfo("Grand Architect", 33, Rarity.RARE, mage.cards.g.GrandArchitect.class));
        cards.add(new SetCardInfo("Grasp of Darkness", 65, Rarity.COMMON, mage.cards.g.GraspOfDarkness.class));
        cards.add(new SetCardInfo("Grindclock", 163, Rarity.RARE, mage.cards.g.Grindclock.class));
        cards.add(new SetCardInfo("Halt Order", 34, Rarity.UNCOMMON, mage.cards.h.HaltOrder.class));
        cards.add(new SetCardInfo("Hand of the Praetors", 66, Rarity.RARE, mage.cards.h.HandOfThePraetors.class));
        cards.add(new SetCardInfo("Heavy Arbalest", 164, Rarity.UNCOMMON, mage.cards.h.HeavyArbalest.class));
        cards.add(new SetCardInfo("Hoard-Smelter Dragon", 93, Rarity.RARE, mage.cards.h.HoardSmelterDragon.class));
        cards.add(new SetCardInfo("Horizon Spellbomb", 165, Rarity.COMMON, mage.cards.h.HorizonSpellbomb.class));
        cards.add(new SetCardInfo("Ichorclaw Myr", 166, Rarity.COMMON, mage.cards.i.IchorclawMyr.class));
        cards.add(new SetCardInfo("Ichor Rats", 67, Rarity.UNCOMMON, mage.cards.i.IchorRats.class));
        cards.add(new SetCardInfo("Indomitable Archangel", 11, Rarity.MYTHIC, mage.cards.i.IndomitableArchangel.class));
        cards.add(new SetCardInfo("Inexorable Tide", 35, Rarity.RARE, mage.cards.i.InexorableTide.class));
        cards.add(new SetCardInfo("Infiltration Lens", 167, Rarity.UNCOMMON, mage.cards.i.InfiltrationLens.class));
        cards.add(new SetCardInfo("Instill Infection", 68, Rarity.COMMON, mage.cards.i.InstillInfection.class));
        cards.add(new SetCardInfo("Iron Myr", 168, Rarity.COMMON, mage.cards.i.IronMyr.class));
        cards.add(new SetCardInfo("Island", 234, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 235, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 236, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 237, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kemba, Kha Regent", 12, Rarity.RARE, mage.cards.k.KembaKhaRegent.class));
        cards.add(new SetCardInfo("Kemba's Skyguard", 13, Rarity.COMMON, mage.cards.k.KembasSkyguard.class));
        cards.add(new SetCardInfo("Koth of the Hammer", 94, Rarity.MYTHIC, mage.cards.k.KothOfTheHammer.class));
        cards.add(new SetCardInfo("Kuldotha Forgemaster", 169, Rarity.RARE, mage.cards.k.KuldothaForgemaster.class));
        cards.add(new SetCardInfo("Kuldotha Phoenix", 95, Rarity.RARE, mage.cards.k.KuldothaPhoenix.class));
        cards.add(new SetCardInfo("Kuldotha Rebirth", 96, Rarity.COMMON, mage.cards.k.KuldothaRebirth.class));
        cards.add(new SetCardInfo("Leaden Myr", 170, Rarity.COMMON, mage.cards.l.LeadenMyr.class));
        cards.add(new SetCardInfo("Leonin Arbiter", 14, Rarity.RARE, mage.cards.l.LeoninArbiter.class));
        cards.add(new SetCardInfo("Liege of the Tangle", 123, Rarity.MYTHIC, mage.cards.l.LiegeOfTheTangle.class));
        cards.add(new SetCardInfo("Lifesmith", 124, Rarity.UNCOMMON, mage.cards.l.Lifesmith.class));
        cards.add(new SetCardInfo("Liquimetal Coating", 171, Rarity.UNCOMMON, mage.cards.l.LiquimetalCoating.class));
        cards.add(new SetCardInfo("Livewire Lash", 172, Rarity.RARE, mage.cards.l.LivewireLash.class));
        cards.add(new SetCardInfo("Loxodon Wayfarer", 15, Rarity.COMMON, mage.cards.l.LoxodonWayfarer.class));
        cards.add(new SetCardInfo("Lumengrid Drake", 36, Rarity.COMMON, mage.cards.l.LumengridDrake.class));
        cards.add(new SetCardInfo("Lux Cannon", 173, Rarity.MYTHIC, mage.cards.l.LuxCannon.class));
        cards.add(new SetCardInfo("Melt Terrain", 97, Rarity.COMMON, mage.cards.m.MeltTerrain.class));
        cards.add(new SetCardInfo("Memnite", 174, Rarity.UNCOMMON, mage.cards.m.Memnite.class));
        cards.add(new SetCardInfo("Memoricide", 69, Rarity.RARE, mage.cards.m.Memoricide.class));
        cards.add(new SetCardInfo("Mimic Vat", 175, Rarity.RARE, mage.cards.m.MimicVat.class));
        cards.add(new SetCardInfo("Mindslaver", 176, Rarity.MYTHIC, mage.cards.m.Mindslaver.class));
        cards.add(new SetCardInfo("Molder Beast", 125, Rarity.COMMON, mage.cards.m.MolderBeast.class));
        cards.add(new SetCardInfo("Molten Psyche", 98, Rarity.RARE, mage.cards.m.MoltenPsyche.class));
        cards.add(new SetCardInfo("Molten-Tail Masticore", 177, Rarity.MYTHIC, mage.cards.m.MoltenTailMasticore.class));
        cards.add(new SetCardInfo("Moriok Reaver", 70, Rarity.COMMON, mage.cards.m.MoriokReaver.class));
        cards.add(new SetCardInfo("Moriok Replica", 178, Rarity.COMMON, mage.cards.m.MoriokReplica.class));
        cards.add(new SetCardInfo("Mountain", 242, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 243, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 244, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 245, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mox Opal", 179, Rarity.MYTHIC, mage.cards.m.MoxOpal.class));
        cards.add(new SetCardInfo("Myr Battlesphere", 180, Rarity.RARE, mage.cards.m.MyrBattlesphere.class));
        cards.add(new SetCardInfo("Myr Galvanizer", 181, Rarity.UNCOMMON, mage.cards.m.MyrGalvanizer.class));
        cards.add(new SetCardInfo("Myr Propagator", 182, Rarity.RARE, mage.cards.m.MyrPropagator.class));
        cards.add(new SetCardInfo("Myr Reservoir", 183, Rarity.RARE, mage.cards.m.MyrReservoir.class));
        cards.add(new SetCardInfo("Myrsmith", 16, Rarity.UNCOMMON, mage.cards.m.Myrsmith.class));
        cards.add(new SetCardInfo("Necrogen Censer", 184, Rarity.COMMON, mage.cards.n.NecrogenCenser.class));
        cards.add(new SetCardInfo("Necrogen Scudder", 71, Rarity.UNCOMMON, mage.cards.n.NecrogenScudder.class));
        cards.add(new SetCardInfo("Necropede", 185, Rarity.UNCOMMON, mage.cards.n.Necropede.class));
        cards.add(new SetCardInfo("Necrotic Ooze", 72, Rarity.RARE, mage.cards.n.NecroticOoze.class));
        cards.add(new SetCardInfo("Neurok Invisimancer", 37, Rarity.COMMON, mage.cards.n.NeurokInvisimancer.class));
        cards.add(new SetCardInfo("Neurok Replica", 186, Rarity.COMMON, mage.cards.n.NeurokReplica.class));
        cards.add(new SetCardInfo("Nihil Spellbomb", 187, Rarity.COMMON, mage.cards.n.NihilSpellbomb.class));
        cards.add(new SetCardInfo("Nim Deathmantle", 188, Rarity.RARE, mage.cards.n.NimDeathmantle.class));
        cards.add(new SetCardInfo("Ogre Geargrabber", 99, Rarity.UNCOMMON, mage.cards.o.OgreGeargrabber.class));
        cards.add(new SetCardInfo("Origin Spellbomb", 189, Rarity.COMMON, mage.cards.o.OriginSpellbomb.class));
        cards.add(new SetCardInfo("Oxidda Daredevil", 100, Rarity.COMMON, mage.cards.o.OxiddaDaredevil.class));
        cards.add(new SetCardInfo("Oxidda Scrapmelter", 101, Rarity.UNCOMMON, mage.cards.o.OxiddaScrapmelter.class));
        cards.add(new SetCardInfo("Painful Quandary", 73, Rarity.RARE, mage.cards.p.PainfulQuandary.class));
        cards.add(new SetCardInfo("Painsmith", 74, Rarity.UNCOMMON, mage.cards.p.Painsmith.class));
        cards.add(new SetCardInfo("Palladium Myr", 190, Rarity.UNCOMMON, mage.cards.p.PalladiumMyr.class));
        cards.add(new SetCardInfo("Panic Spellbomb", 191, Rarity.COMMON, mage.cards.p.PanicSpellbomb.class));
        cards.add(new SetCardInfo("Perilous Myr", 192, Rarity.COMMON, mage.cards.p.PerilousMyr.class));
        cards.add(new SetCardInfo("Plague Stinger", 75, Rarity.COMMON, mage.cards.p.PlagueStinger.class));
        cards.add(new SetCardInfo("Plains", 230, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 231, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 232, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 233, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plated Seastrider", 38, Rarity.COMMON, mage.cards.p.PlatedSeastrider.class));
        cards.add(new SetCardInfo("Platinum Emperion", 193, Rarity.MYTHIC, mage.cards.p.PlatinumEmperion.class));
        cards.add(new SetCardInfo("Precursor Golem", 194, Rarity.RARE, mage.cards.p.PrecursorGolem.class));
        cards.add(new SetCardInfo("Prototype Portal", 195, Rarity.RARE, mage.cards.p.PrototypePortal.class));
        cards.add(new SetCardInfo("Psychic Miasma", 76, Rarity.COMMON, mage.cards.p.PsychicMiasma.class));
        cards.add(new SetCardInfo("Putrefax", 126, Rarity.RARE, mage.cards.p.Putrefax.class));
        cards.add(new SetCardInfo("Quicksilver Gargantuan", 39, Rarity.MYTHIC, mage.cards.q.QuicksilverGargantuan.class));
        cards.add(new SetCardInfo("Ratchet Bomb", 196, Rarity.RARE, mage.cards.r.RatchetBomb.class));
        cards.add(new SetCardInfo("Razorfield Thresher", 197, Rarity.COMMON, mage.cards.r.RazorfieldThresher.class));
        cards.add(new SetCardInfo("Razor Hippogriff", 17, Rarity.UNCOMMON, mage.cards.r.RazorHippogriff.class));
        cards.add(new SetCardInfo("Razorverge Thicket", 228, Rarity.RARE, mage.cards.r.RazorvergeThicket.class));
        cards.add(new SetCardInfo("Relic Putrescence", 77, Rarity.COMMON, mage.cards.r.RelicPutrescence.class));
        cards.add(new SetCardInfo("Revoke Existence", 18, Rarity.COMMON, mage.cards.r.RevokeExistence.class));
        cards.add(new SetCardInfo("Riddlesmith", 40, Rarity.UNCOMMON, mage.cards.r.Riddlesmith.class));
        cards.add(new SetCardInfo("Rusted Relic", 199, Rarity.UNCOMMON, mage.cards.r.RustedRelic.class));
        cards.add(new SetCardInfo("Rust Tick", 198, Rarity.UNCOMMON, mage.cards.r.RustTick.class));
        cards.add(new SetCardInfo("Saberclaw Golem", 200, Rarity.COMMON, mage.cards.s.SaberclawGolem.class));
        cards.add(new SetCardInfo("Salvage Scout", 19, Rarity.COMMON, mage.cards.s.SalvageScout.class));
        cards.add(new SetCardInfo("Scoria Elemental", 102, Rarity.COMMON, mage.cards.s.ScoriaElemental.class));
        cards.add(new SetCardInfo("Scrapdiver Serpent", 41, Rarity.COMMON, mage.cards.s.ScrapdiverSerpent.class));
        cards.add(new SetCardInfo("Screeching Silcaw", 42, Rarity.COMMON, mage.cards.s.ScreechingSilcaw.class));
        cards.add(new SetCardInfo("Seachrome Coast", 229, Rarity.RARE, mage.cards.s.SeachromeCoast.class));
        cards.add(new SetCardInfo("Seize the Initiative", 20, Rarity.COMMON, mage.cards.s.SeizeTheInitiative.class));
        cards.add(new SetCardInfo("Semblance Anvil", 201, Rarity.RARE, mage.cards.s.SemblanceAnvil.class));
        cards.add(new SetCardInfo("Shape Anew", 43, Rarity.RARE, mage.cards.s.ShapeAnew.class));
        cards.add(new SetCardInfo("Shatter", 103, Rarity.COMMON, mage.cards.s.Shatter.class));
        cards.add(new SetCardInfo("Silver Myr", 202, Rarity.COMMON, mage.cards.s.SilverMyr.class));
        cards.add(new SetCardInfo("Skinrender", 78, Rarity.UNCOMMON, mage.cards.s.Skinrender.class));
        cards.add(new SetCardInfo("Skithiryx, the Blight Dragon", 79, Rarity.MYTHIC, mage.cards.s.SkithiryxTheBlightDragon.class));
        cards.add(new SetCardInfo("Sky-Eel School", 44, Rarity.COMMON, mage.cards.s.SkyEelSchool.class));
        cards.add(new SetCardInfo("Slice in Twain", 127, Rarity.UNCOMMON, mage.cards.s.SliceInTwain.class));
        cards.add(new SetCardInfo("Snapsail Glider", 203, Rarity.COMMON, mage.cards.s.SnapsailGlider.class));
        cards.add(new SetCardInfo("Soliton", 204, Rarity.COMMON, mage.cards.s.Soliton.class));
        cards.add(new SetCardInfo("Soul Parry", 21, Rarity.COMMON, mage.cards.s.SoulParry.class));
        cards.add(new SetCardInfo("Spikeshot Elder", 104, Rarity.RARE, mage.cards.s.SpikeshotElder.class));
        cards.add(new SetCardInfo("Steady Progress", 45, Rarity.COMMON, mage.cards.s.SteadyProgress.class));
        cards.add(new SetCardInfo("Steel Hellkite", 205, Rarity.RARE, mage.cards.s.SteelHellkite.class));
        cards.add(new SetCardInfo("Stoic Rebuttal", 46, Rarity.COMMON, mage.cards.s.StoicRebuttal.class));
        cards.add(new SetCardInfo("Strata Scythe", 206, Rarity.RARE, mage.cards.s.StrataScythe.class));
        cards.add(new SetCardInfo("Strider Harness", 207, Rarity.COMMON, mage.cards.s.StriderHarness.class));
        cards.add(new SetCardInfo("Sunblast Angel", 22, Rarity.RARE, mage.cards.s.SunblastAngel.class));
        cards.add(new SetCardInfo("Sunspear Shikari", 23, Rarity.COMMON, mage.cards.s.SunspearShikari.class));
        cards.add(new SetCardInfo("Swamp", 238, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 239, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 240, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 241, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Body and Mind", 208, Rarity.MYTHIC, mage.cards.s.SwordOfBodyAndMind.class));
        cards.add(new SetCardInfo("Sylvok Lifestaff", 209, Rarity.COMMON, mage.cards.s.SylvokLifestaff.class));
        cards.add(new SetCardInfo("Sylvok Replica", 210, Rarity.COMMON, mage.cards.s.SylvokReplica.class));
        cards.add(new SetCardInfo("Tainted Strike", 80, Rarity.COMMON, mage.cards.t.TaintedStrike.class));
        cards.add(new SetCardInfo("Tangle Angler", 128, Rarity.UNCOMMON, mage.cards.t.TangleAngler.class));
        cards.add(new SetCardInfo("Tel-Jilad Defiance", 129, Rarity.COMMON, mage.cards.t.TelJiladDefiance.class));
        cards.add(new SetCardInfo("Tel-Jilad Fallen", 130, Rarity.COMMON, mage.cards.t.TelJiladFallen.class));
        cards.add(new SetCardInfo("Tempered Steel", 24, Rarity.RARE, mage.cards.t.TemperedSteel.class));
        cards.add(new SetCardInfo("Throne of Geth", 211, Rarity.UNCOMMON, mage.cards.t.ThroneOfGeth.class));
        cards.add(new SetCardInfo("Thrummingbird", 47, Rarity.UNCOMMON, mage.cards.t.Thrummingbird.class));
        cards.add(new SetCardInfo("Tower of Calamities", 212, Rarity.RARE, mage.cards.t.TowerOfCalamities.class));
        cards.add(new SetCardInfo("Trigon of Corruption", 213, Rarity.UNCOMMON, mage.cards.t.TrigonOfCorruption.class));
        cards.add(new SetCardInfo("Trigon of Infestation", 214, Rarity.UNCOMMON, mage.cards.t.TrigonOfInfestation.class));
        cards.add(new SetCardInfo("Trigon of Mending", 215, Rarity.UNCOMMON, mage.cards.t.TrigonOfMending.class));
        cards.add(new SetCardInfo("Trigon of Rage", 216, Rarity.UNCOMMON, mage.cards.t.TrigonOfRage.class));
        cards.add(new SetCardInfo("Trigon of Thought", 217, Rarity.UNCOMMON, mage.cards.t.TrigonOfThought.class));
        cards.add(new SetCardInfo("Trinket Mage", 48, Rarity.UNCOMMON, mage.cards.t.TrinketMage.class));
        cards.add(new SetCardInfo("True Conviction", 25, Rarity.RARE, mage.cards.t.TrueConviction.class));
        cards.add(new SetCardInfo("Tumble Magnet", 218, Rarity.COMMON, mage.cards.t.TumbleMagnet.class));
        cards.add(new SetCardInfo("Tunnel Ignus", 105, Rarity.RARE, mage.cards.t.TunnelIgnus.class));
        cards.add(new SetCardInfo("Turn Aside", 49, Rarity.COMMON, mage.cards.t.TurnAside.class));
        cards.add(new SetCardInfo("Turn to Slag", 106, Rarity.COMMON, mage.cards.t.TurnToSlag.class));
        cards.add(new SetCardInfo("Twisted Image", 50, Rarity.UNCOMMON, mage.cards.t.TwistedImage.class));
        cards.add(new SetCardInfo("Untamed Might", 131, Rarity.COMMON, mage.cards.u.UntamedMight.class));
        cards.add(new SetCardInfo("Vault Skyward", 51, Rarity.COMMON, mage.cards.v.VaultSkyward.class));
        cards.add(new SetCardInfo("Vector Asp", 219, Rarity.COMMON, mage.cards.v.VectorAsp.class));
        cards.add(new SetCardInfo("Vedalken Certarch", 52, Rarity.COMMON, mage.cards.v.VedalkenCertarch.class));
        cards.add(new SetCardInfo("Venser's Journal", 220, Rarity.RARE, mage.cards.v.VensersJournal.class));
        cards.add(new SetCardInfo("Venser, the Sojourner", 135, Rarity.MYTHIC, mage.cards.v.VenserTheSojourner.class));
        cards.add(new SetCardInfo("Vigil for the Lost", 26, Rarity.UNCOMMON, mage.cards.v.VigilForTheLost.class));
        cards.add(new SetCardInfo("Viridian Revel", 132, Rarity.UNCOMMON, mage.cards.v.ViridianRevel.class));
        cards.add(new SetCardInfo("Volition Reins", 53, Rarity.UNCOMMON, mage.cards.v.VolitionReins.class));
        cards.add(new SetCardInfo("Vulshok Heartstoker", 107, Rarity.COMMON, mage.cards.v.VulshokHeartstoker.class));
        cards.add(new SetCardInfo("Vulshok Replica", 221, Rarity.COMMON, mage.cards.v.VulshokReplica.class));
        cards.add(new SetCardInfo("Wall of Tanglecord", 222, Rarity.COMMON, mage.cards.w.WallOfTanglecord.class));
        cards.add(new SetCardInfo("Whitesun's Passage", 27, Rarity.COMMON, mage.cards.w.WhitesunsPassage.class));
        cards.add(new SetCardInfo("Wing Puncture", 133, Rarity.COMMON, mage.cards.w.WingPuncture.class));
        cards.add(new SetCardInfo("Withstand Death", 134, Rarity.COMMON, mage.cards.w.WithstandDeath.class));
        cards.add(new SetCardInfo("Wurmcoil Engine", 223, Rarity.MYTHIC, mage.cards.w.WurmcoilEngine.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ScarsOfMirrodinCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/som.html
// Using USA collation
class ScarsOfMirrodinCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "70", "8", "189", "38", "106", "166", "65", "23", "221", "112", "138", "75", "84", "168", "13", "29", "157", "68", "117", "156", "89", "210", "70", "44", "200", "10", "170", "63", "130", "4", "168", "38", "157", "88", "8", "204", "65", "29", "221", "106", "23", "200", "130", "166", "75", "36", "189", "89", "13", "204", "4", "138", "68", "44", "202", "84", "112", "210", "63", "10", "156", "36", "88", "202", "117", "170");
    private final CardRun commonB = new CardRun(true, "114", "58", "207", "109", "18", "146", "107", "37", "142", "114", "147", "41", "2", "192", "107", "52", "146", "125", "18", "209", "129", "178", "58", "91", "192", "55", "52", "207", "125", "2", "186", "91", "142", "129", "37", "209", "109", "103", "178", "41", "18", "207", "58", "186", "103", "129", "146", "41", "91", "178", "55", "37", "147", "125", "186", "114", "52", "209", "55", "103", "192", "109", "107", "147", "2", "142");
    private final CardRun commonC1 = new CardRun(true, "116", "54", "184", "97", "15", "160", "113", "51", "203", "54", "153", "82", "21", "155", "120", "45", "187", "60", "97", "153", "46", "15", "133", "45", "165", "227", "92", "184", "31", "7", "60", "116", "187", "227", "96", "219", "51", "21", "191", "120", "77", "165", "82", "219", "133", "92", "203", "46", "77", "155", "113", "7", "96", "31", "191");
    private final CardRun commonC2 = new CardRun(true, "197", "56", "27", "102", "136", "19", "140", "20", "76", "49", "131", "222", "158", "56", "136", "80", "20", "131", "140", "100", "158", "160", "80", "49", "20", "134", "222", "27", "218", "42", "100", "76", "197", "140", "102", "131", "42", "80", "218", "19", "197", "158", "134", "42", "56", "100", "19", "222", "136", "27", "49", "102", "134", "76", "218");
    private final CardRun uncommonA = new CardRun(true, "78", "108", "171", "90", "47", "185", "101", "132", "217", "3", "214", "71", "167", "127", "53", "159", "85", "161", "71", "216", "26", "149", "62", "164", "124", "47", "161", "61", "81", "3", "185", "26", "143", "62", "50", "213", "90", "216", "124", "214", "30", "167", "17", "139", "61", "1", "171", "85", "30", "217", "81", "108", "139", "1", "149", "78", "127", "159", "101", "53", "213", "17", "132", "164", "50", "143");
    private final CardRun uncommonB = new CardRun(true, "59", "151", "115", "9", "211", "40", "144", "59", "198", "83", "174", "128", "48", "199", "5", "9", "152", "148", "67", "99", "151", "34", "16", "211", "174", "87", "115", "198", "40", "74", "181", "215", "83", "111", "190", "34", "162", "16", "144", "67", "152", "111", "181", "87", "148", "5", "74", "199", "128", "190", "99", "215", "48", "162");
    private final CardRun rareA = new CardRun(true, "180", "95", "119", "201", "177", "66", "122", "14", "205", "226", "220", "43", "223", "95", "121", "69", "212", "22", "183", "229", "173", "28", "73", "194", "33", "98", "205", "25", "179", "224", "182", "122", "93", "201", "22", "180", "43", "64", "188", "229", "212", "119", "145", "28", "69", "194", "14", "135", "163", "93", "183", "224", "220", "25", "39", "188", "66", "182", "121", "98", "145", "73", "11", "226", "163", "33");
    private final CardRun rareB = new CardRun(true, "154", "104", "225", "193", "24", "172", "118", "105", "175", "72", "169", "228", "141", "208", "35", "150", "86", "126", "118", "12", "195", "176", "72", "32", "137", "105", "123", "150", "24", "196", "225", "141", "57", "154", "206", "6", "228", "104", "110", "137", "126", "175", "35", "169", "94", "195", "172", "57", "12", "86", "206", "79", "110", "196", "32");
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
    private final BoosterStructure AAABC2C2C2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB,
            commonC2, commonC2, commonC2, commonC2, commonC2, commonC2
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
    private final BoosterStructure AAABBBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2, commonC2
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

            AAABC2C2C2C2C2C2,
            AAABBC2C2C2C2C2,
            AAABBC2C2C2C2C2,
            AAABBBC2C2C2C2,
            AAABBBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
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
