package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class Kaldheim extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList(
            "Alrund's Epiphany",
            "Augury Raven",
            "Behold the Multiverse",
            "Cosmos Charger",
            "Crush the Weak",
            "Doomskar Oracle",
            "Doomskar Titan",
            "Dual Strike",
            "Dwarven Reinforcements",
            "Gods' Hall Guardian",
            "Iron Verdict",
            "Karfell Harbinger",
            "Kaya's Onslaught",
            "Mammoth Growth",
            "Mystic Reflection",
            "Niko Defies Destiny",
            "Poison the Cup",
            "Quakebringer",
            "Ranar the Ever-Watchful",
            "Ravenform",
            "Return Upon the Tide",
            "Rise of the Dread Marn",
            "Sarulf's Packmate",
            "Saw It Coming",
            "Scorn Effigy",
            "Shepherd of the Cosmos",
            "Starnheim Unleashed",
            "Tergrid's Shadow"
    );

    private static final Kaldheim instance = new Kaldheim();

    public static Kaldheim getInstance() {
        return instance;
    }

    private Kaldheim() {
        super("Kaldheim", "KHM", ExpansionSet.buildDate(2021, 2, 5), SetType.EXPANSION);
        this.blockName = "Kaldheim";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;
        this.maxCardNumberInBooster = 285;

        cards.add(new SetCardInfo("Absorb Identity", 383, Rarity.UNCOMMON, mage.cards.a.AbsorbIdentity.class));
        cards.add(new SetCardInfo("Aegar, the Freezing Flame", 200, Rarity.UNCOMMON, mage.cards.a.AegarTheFreezingFlame.class));
        cards.add(new SetCardInfo("Alpine Meadow", 248, Rarity.COMMON, mage.cards.a.AlpineMeadow.class));
        cards.add(new SetCardInfo("Alrund's Epiphany", 41, Rarity.MYTHIC, mage.cards.a.AlrundsEpiphany.class));
        cards.add(new SetCardInfo("Annul", 42, Rarity.COMMON, mage.cards.a.Annul.class));
        cards.add(new SetCardInfo("Arctic Treeline", 249, Rarity.COMMON, mage.cards.a.ArcticTreeline.class));
        cards.add(new SetCardInfo("Armed and Armored", 379, Rarity.UNCOMMON, mage.cards.a.ArmedAndArmored.class));
        cards.add(new SetCardInfo("Augury Raven", 44, Rarity.COMMON, mage.cards.a.AuguryRaven.class));
        cards.add(new SetCardInfo("Avalanche Caller", 45, Rarity.UNCOMMON, mage.cards.a.AvalancheCaller.class));
        cards.add(new SetCardInfo("Axgard Armory", 250, Rarity.UNCOMMON, mage.cards.a.AxgardArmory.class));
        cards.add(new SetCardInfo("Axgard Braggart", 1, Rarity.COMMON, mage.cards.a.AxgardBraggart.class));
        cards.add(new SetCardInfo("Axgard Cavalry", 121, Rarity.COMMON, mage.cards.a.AxgardCavalry.class));
        cards.add(new SetCardInfo("Barkchannel Pathway", 251, Rarity.RARE, mage.cards.b.BarkchannelPathway.class));
        cards.add(new SetCardInfo("Basalt Ravager", 122, Rarity.UNCOMMON, mage.cards.b.BasaltRavager.class));
        cards.add(new SetCardInfo("Battle for Bretagard", 203, Rarity.RARE, mage.cards.b.BattleForBretagard.class));
        cards.add(new SetCardInfo("Battle of Frost and Fire", 204, Rarity.RARE, mage.cards.b.BattleOfFrostAndFire.class));
        cards.add(new SetCardInfo("Bearded Axe", 388, Rarity.UNCOMMON, mage.cards.b.BeardedAxe.class));
        cards.add(new SetCardInfo("Behold the Multiverse", 46, Rarity.COMMON, mage.cards.b.BeholdTheMultiverse.class));
        cards.add(new SetCardInfo("Beskir Shieldmate", 4, Rarity.COMMON, mage.cards.b.BeskirShieldmate.class));
        cards.add(new SetCardInfo("Binding the Old Gods", 206, Rarity.UNCOMMON, mage.cards.b.BindingTheOldGods.class));
        cards.add(new SetCardInfo("Blightstep Pathway", 252, Rarity.RARE, mage.cards.b.BlightstepPathway.class));
        cards.add(new SetCardInfo("Blizzard Brawl", 162, Rarity.UNCOMMON, mage.cards.b.BlizzardBrawl.class));
        cards.add(new SetCardInfo("Bloodline Pretender", 235, Rarity.UNCOMMON, mage.cards.b.BloodlinePretender.class));
        cards.add(new SetCardInfo("Bloodsky Berserker", 80, Rarity.UNCOMMON, mage.cards.b.BloodskyBerserker.class));
        cards.add(new SetCardInfo("Bound in Gold", 5, Rarity.COMMON, mage.cards.b.BoundInGold.class));
        cards.add(new SetCardInfo("Bretagard Stronghold", 253, Rarity.UNCOMMON, mage.cards.b.BretagardStronghold.class));
        cards.add(new SetCardInfo("Broken Wings", 164, Rarity.COMMON, mage.cards.b.BrokenWings.class));
        cards.add(new SetCardInfo("Calamity Bearer", 125, Rarity.RARE, mage.cards.c.CalamityBearer.class));
        cards.add(new SetCardInfo("Canopy Tactician", 378, Rarity.RARE, mage.cards.c.CanopyTactician.class));
        cards.add(new SetCardInfo("Cinderheart Giant", 126, Rarity.COMMON, mage.cards.c.CinderheartGiant.class));
        cards.add(new SetCardInfo("Clarion Spirit", 6, Rarity.UNCOMMON, mage.cards.c.ClarionSpirit.class));
        cards.add(new SetCardInfo("Cleaving Reaper", 376, Rarity.RARE, mage.cards.c.CleavingReaper.class));
        cards.add(new SetCardInfo("Crush the Weak", 128, Rarity.UNCOMMON, mage.cards.c.CrushTheWeak.class));
        cards.add(new SetCardInfo("Cyclone Summoner", 52, Rarity.RARE, mage.cards.c.CycloneSummoner.class));
        cards.add(new SetCardInfo("Darkbore Pathway", 254, Rarity.RARE, mage.cards.d.DarkborePathway.class));
        cards.add(new SetCardInfo("Deathknell Berserker", 83, Rarity.COMMON, mage.cards.d.DeathknellBerserker.class));
        cards.add(new SetCardInfo("Disdainful Stroke", 54, Rarity.COMMON, mage.cards.d.DisdainfulStroke.class));
        cards.add(new SetCardInfo("Divine Gambit", 8, Rarity.UNCOMMON, mage.cards.d.DivineGambit.class));
        cards.add(new SetCardInfo("Dogged Pursuit", 85, Rarity.COMMON, mage.cards.d.DoggedPursuit.class));
        cards.add(new SetCardInfo("Doomskar Oracle", 10, Rarity.COMMON, mage.cards.d.DoomskarOracle.class));
        cards.add(new SetCardInfo("Draugr's Helm", 88, Rarity.UNCOMMON, mage.cards.d.DraugrsHelm.class));
        cards.add(new SetCardInfo("Dread Rider", 89, Rarity.COMMON, mage.cards.d.DreadRider.class));
        cards.add(new SetCardInfo("Duskwielder", 91, Rarity.COMMON, mage.cards.d.Duskwielder.class));
        cards.add(new SetCardInfo("Dwarven Reinforcements", 134, Rarity.COMMON, mage.cards.d.DwarvenReinforcements.class));
        cards.add(new SetCardInfo("Egon, God of Death", 92, Rarity.RARE, mage.cards.e.EgonGodOfDeath.class));
        cards.add(new SetCardInfo("Elderfang Disciple", 93, Rarity.COMMON, mage.cards.e.ElderfangDisciple.class));
        cards.add(new SetCardInfo("Elderfang Ritualist", 385, Rarity.UNCOMMON, mage.cards.e.ElderfangRitualist.class));
        cards.add(new SetCardInfo("Elderleaf Mentor", 165, Rarity.COMMON, mage.cards.e.ElderleafMentor.class));
        cards.add(new SetCardInfo("Elven Ambush", 391, Rarity.UNCOMMON, mage.cards.e.ElvenAmbush.class));
        cards.add(new SetCardInfo("Elvish Warmaster", 167, Rarity.RARE, mage.cards.e.ElvishWarmaster.class));
        cards.add(new SetCardInfo("Eradicator Valkyrie", 94, Rarity.MYTHIC, mage.cards.e.EradicatorValkyrie.class));
        cards.add(new SetCardInfo("Esika's Chariot", 169, Rarity.RARE, mage.cards.e.EsikasChariot.class));
        cards.add(new SetCardInfo("Esika, God of the Tree", 168, Rarity.MYTHIC, mage.cards.e.EsikaGodOfTheTree.class));
        cards.add(new SetCardInfo("Faceless Haven", 255, Rarity.RARE, mage.cards.f.FacelessHaven.class));
        cards.add(new SetCardInfo("Fall of the Impostor", 208, Rarity.UNCOMMON, mage.cards.f.FallOfTheImpostor.class));
        cards.add(new SetCardInfo("Fearless Liberator", 135, Rarity.UNCOMMON, mage.cards.f.FearlessLiberator.class));
        cards.add(new SetCardInfo("Feed the Serpent", 95, Rarity.COMMON, mage.cards.f.FeedTheSerpent.class));
        cards.add(new SetCardInfo("Fire Giant's Fury", 389, Rarity.UNCOMMON, mage.cards.f.FireGiantsFury.class));
        cards.add(new SetCardInfo("Firja's Retribution", 210, Rarity.RARE, mage.cards.f.FirjasRetribution.class));
        cards.add(new SetCardInfo("Firja, Judge of Valor", 209, Rarity.UNCOMMON, mage.cards.f.FirjaJudgeOfValor.class));
        cards.add(new SetCardInfo("Forest", 398, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forging the Tyrite Sword", 211, Rarity.UNCOMMON, mage.cards.f.ForgingTheTyriteSword.class));
        cards.add(new SetCardInfo("Frost Bite", 138, Rarity.COMMON, mage.cards.f.FrostBite.class));
        cards.add(new SetCardInfo("Frostpeak Yeti", 57, Rarity.COMMON, mage.cards.f.FrostpeakYeti.class));
        cards.add(new SetCardInfo("Frostpyre Arcanist", 58, Rarity.UNCOMMON, mage.cards.f.FrostpyreArcanist.class));
        cards.add(new SetCardInfo("Funeral Longboat", 238, Rarity.COMMON, mage.cards.f.FuneralLongboat.class));
        cards.add(new SetCardInfo("Gates of Istfell", 256, Rarity.UNCOMMON, mage.cards.g.GatesOfIstfell.class));
        cards.add(new SetCardInfo("Giant's Amulet", 59, Rarity.UNCOMMON, mage.cards.g.GiantsAmulet.class));
        cards.add(new SetCardInfo("Giant's Grasp", 384, Rarity.UNCOMMON, mage.cards.g.GiantsGrasp.class));
        cards.add(new SetCardInfo("Gilded Assault Cart", 390, Rarity.UNCOMMON, mage.cards.g.GildedAssaultCart.class));
        cards.add(new SetCardInfo("Glacial Floodplain", 257, Rarity.COMMON, mage.cards.g.GlacialFloodplain.class));
        cards.add(new SetCardInfo("Gladewalker Ritualist", 392, Rarity.UNCOMMON, mage.cards.g.GladewalkerRitualist.class));
        cards.add(new SetCardInfo("Glittering Frost", 171, Rarity.COMMON, mage.cards.g.GlitteringFrost.class));
        cards.add(new SetCardInfo("Gnottvold Recluse", 172, Rarity.COMMON, mage.cards.g.GnottvoldRecluse.class));
        cards.add(new SetCardInfo("Gnottvold Slumbermound", 258, Rarity.UNCOMMON, mage.cards.g.GnottvoldSlumbermound.class));
        cards.add(new SetCardInfo("Gods' Hall Guardian", 13, Rarity.COMMON, mage.cards.g.GodsHallGuardian.class));
        cards.add(new SetCardInfo("Goldmaw Champion", 14, Rarity.COMMON, mage.cards.g.GoldmawChampion.class));
        cards.add(new SetCardInfo("Goldspan Dragon", 139, Rarity.MYTHIC, mage.cards.g.GoldspanDragon.class));
        cards.add(new SetCardInfo("Goldvein Pick", 239, Rarity.COMMON, mage.cards.g.GoldveinPick.class));
        cards.add(new SetCardInfo("Grim Draugr", 96, Rarity.COMMON, mage.cards.g.GrimDraugr.class));
        cards.add(new SetCardInfo("Guardian Gladewalker", 174, Rarity.COMMON, mage.cards.g.GuardianGladewalker.class));
        cards.add(new SetCardInfo("Hagi Mob", 140, Rarity.COMMON, mage.cards.h.HagiMob.class));
        cards.add(new SetCardInfo("Hailstorm Valkyrie", 97, Rarity.UNCOMMON, mage.cards.h.HailstormValkyrie.class));
        cards.add(new SetCardInfo("Halvar, God of Battle", 15, Rarity.MYTHIC, mage.cards.h.HalvarGodOfBattle.class));
        cards.add(new SetCardInfo("Harald Unites the Elves", 213, Rarity.RARE, mage.cards.h.HaraldUnitesTheElves.class));
        cards.add(new SetCardInfo("Harald, King of Skemfar", 212, Rarity.UNCOMMON, mage.cards.h.HaraldKingOfSkemfar.class));
        cards.add(new SetCardInfo("Hengegate Pathway", 260, Rarity.RARE, mage.cards.h.HengegatePathway.class));
        cards.add(new SetCardInfo("Highland Forest", 261, Rarity.COMMON, mage.cards.h.HighlandForest.class));
        cards.add(new SetCardInfo("Ice Tunnel", 262, Rarity.COMMON, mage.cards.i.IceTunnel.class));
        cards.add(new SetCardInfo("Infernal Pet", 99, Rarity.COMMON, mage.cards.i.InfernalPet.class));
        cards.add(new SetCardInfo("Inga Rune-Eyes", 64, Rarity.UNCOMMON, mage.cards.i.IngaRuneEyes.class));
        cards.add(new SetCardInfo("Invasion of the Giants", 215, Rarity.UNCOMMON, mage.cards.i.InvasionOfTheGiants.class));
        cards.add(new SetCardInfo("Iron Verdict", 17, Rarity.COMMON, mage.cards.i.IronVerdict.class));
        cards.add(new SetCardInfo("Island", 395, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaspera Sentinel", 178, Rarity.COMMON, mage.cards.j.JasperaSentinel.class));
        cards.add(new SetCardInfo("Kardur's Vicious Return", 217, Rarity.UNCOMMON, mage.cards.k.KardursViciousReturn.class));
        cards.add(new SetCardInfo("Karfell Kennel-Master", 101, Rarity.COMMON, mage.cards.k.KarfellKennelMaster.class));
        cards.add(new SetCardInfo("Kaya the Inexorable", 218, Rarity.MYTHIC, mage.cards.k.KayaTheInexorable.class));
        cards.add(new SetCardInfo("Kaya's Onslaught", 18, Rarity.UNCOMMON, mage.cards.k.KayasOnslaught.class));
        cards.add(new SetCardInfo("King Harald's Revenge", 180, Rarity.COMMON, mage.cards.k.KingHaraldsRevenge.class));
        cards.add(new SetCardInfo("Koll, the Forgemaster", 220, Rarity.UNCOMMON, mage.cards.k.KollTheForgemaster.class));
        cards.add(new SetCardInfo("Kolvori, God of Kinship", 181, Rarity.RARE, mage.cards.k.KolvoriGodOfKinship.class));
        cards.add(new SetCardInfo("Koma's Faithful", 102, Rarity.COMMON, mage.cards.k.KomasFaithful.class));
        cards.add(new SetCardInfo("Koma, Cosmos Serpent", 221, Rarity.MYTHIC, mage.cards.k.KomaCosmosSerpent.class));
        cards.add(new SetCardInfo("Littjara Kinseekers", 66, Rarity.COMMON, mage.cards.l.LittjaraKinseekers.class));
        cards.add(new SetCardInfo("Magda, Brazen Outlaw", 142, Rarity.RARE, mage.cards.m.MagdaBrazenOutlaw.class));
        cards.add(new SetCardInfo("Mammoth Growth", 183, Rarity.COMMON, mage.cards.m.MammothGrowth.class));
        cards.add(new SetCardInfo("Masked Vandal", 184, Rarity.COMMON, mage.cards.m.MaskedVandal.class));
        cards.add(new SetCardInfo("Maskwood Nexus", 240, Rarity.RARE, mage.cards.m.MaskwoodNexus.class));
        cards.add(new SetCardInfo("Mountain", 397, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Narfi, Betrayer King", 224, Rarity.UNCOMMON, mage.cards.n.NarfiBetrayerKing.class));
        cards.add(new SetCardInfo("Niko Aris", 225, Rarity.MYTHIC, mage.cards.n.NikoAris.class));
        cards.add(new SetCardInfo("Path to the World Tree", 186, Rarity.UNCOMMON, mage.cards.p.PathToTheWorldTree.class));
        cards.add(new SetCardInfo("Pilfering Hawk", 71, Rarity.COMMON, mage.cards.p.PilferingHawk.class));
        cards.add(new SetCardInfo("Plains", 394, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyre of Heroes", 241, Rarity.RARE, mage.cards.p.PyreOfHeroes.class));
        cards.add(new SetCardInfo("Raiders' Karve", 242, Rarity.COMMON, mage.cards.r.RaidersKarve.class));
        cards.add(new SetCardInfo("Raise the Draugr", 105, Rarity.COMMON, mage.cards.r.RaiseTheDraugr.class));
        cards.add(new SetCardInfo("Rally the Ranks", 20, Rarity.RARE, mage.cards.r.RallyTheRanks.class));
        cards.add(new SetCardInfo("Rampage of the Valkyries", 393, Rarity.UNCOMMON, mage.cards.r.RampageOfTheValkyries.class));
        cards.add(new SetCardInfo("Ravenform", 72, Rarity.COMMON, mage.cards.r.Ravenform.class));
        cards.add(new SetCardInfo("Realmwalker", 188, Rarity.RARE, mage.cards.r.Realmwalker.class));
        cards.add(new SetCardInfo("Reckless Crew", 148, Rarity.RARE, mage.cards.r.RecklessCrew.class));
        cards.add(new SetCardInfo("Renegade Reaper", 386, Rarity.UNCOMMON, mage.cards.r.RenegadeReaper.class));
        cards.add(new SetCardInfo("Replicating Ring", 244, Rarity.UNCOMMON, mage.cards.r.ReplicatingRing.class));
        cards.add(new SetCardInfo("Revitalize", 23, Rarity.COMMON, mage.cards.r.Revitalize.class));
        cards.add(new SetCardInfo("Rimewood Falls", 266, Rarity.COMMON, mage.cards.r.RimewoodFalls.class));
        cards.add(new SetCardInfo("Rise of the Dread Marn", 107, Rarity.RARE, mage.cards.r.RiseOfTheDreadMarn.class));
        cards.add(new SetCardInfo("Roots of Wisdom", 190, Rarity.COMMON, mage.cards.r.RootsOfWisdom.class));
        cards.add(new SetCardInfo("Run Amok", 147, Rarity.COMMON, mage.cards.r.RunAmok.class));
        cards.add(new SetCardInfo("Run Ashore", 74, Rarity.COMMON, mage.cards.r.RunAshore.class));
        cards.add(new SetCardInfo("Rune of Flight", 75, Rarity.UNCOMMON, mage.cards.r.RuneOfFlight.class));
        cards.add(new SetCardInfo("Sarulf's Packmate", 192, Rarity.COMMON, mage.cards.s.SarulfsPackmate.class));
        cards.add(new SetCardInfo("Sarulf, Realm Eater", 228, Rarity.RARE, mage.cards.s.SarulfRealmEater.class));
        cards.add(new SetCardInfo("Saw It Coming", 76, Rarity.UNCOMMON, mage.cards.s.SawItComing.class));
        cards.add(new SetCardInfo("Scorn Effigy", 246, Rarity.COMMON, mage.cards.s.ScornEffigy.class));
        cards.add(new SetCardInfo("Sculptor of Winter", 193, Rarity.COMMON, mage.cards.s.SculptorOfWinter.class));
        cards.add(new SetCardInfo("Seize the Spoils", 149, Rarity.COMMON, mage.cards.s.SeizeTheSpoils.class));
        cards.add(new SetCardInfo("Shepherd of the Cosmos", 28, Rarity.UNCOMMON, mage.cards.s.ShepherdOfTheCosmos.class));
        cards.add(new SetCardInfo("Showdown of the Skalds", 229, Rarity.RARE, mage.cards.s.ShowdownOfTheSkalds.class));
        cards.add(new SetCardInfo("Sigrid, God-Favored", 29, Rarity.RARE, mage.cards.s.SigridGodFavored.class));
        cards.add(new SetCardInfo("Skemfar Avenger", 109, Rarity.RARE, mage.cards.s.SkemfarAvenger.class));
        cards.add(new SetCardInfo("Skemfar Elderhall", 268, Rarity.UNCOMMON, mage.cards.s.SkemfarElderhall.class));
        cards.add(new SetCardInfo("Skemfar Shadowsage", 110, Rarity.UNCOMMON, mage.cards.s.SkemfarShadowsage.class));
        cards.add(new SetCardInfo("Snakeskin Veil", 194, Rarity.COMMON, mage.cards.s.SnakeskinVeil.class));
        cards.add(new SetCardInfo("Snow-Covered Forest", 284, Rarity.LAND, mage.cards.s.SnowCoveredForest.class));
        cards.add(new SetCardInfo("Snow-Covered Island", 278, Rarity.LAND, mage.cards.s.SnowCoveredIsland.class));
        cards.add(new SetCardInfo("Snow-Covered Mountain", 282, Rarity.LAND, mage.cards.s.SnowCoveredMountain.class));
        cards.add(new SetCardInfo("Snow-Covered Plains", 276, Rarity.LAND, mage.cards.s.SnowCoveredPlains.class));
        cards.add(new SetCardInfo("Snow-Covered Swamp", 280, Rarity.LAND, mage.cards.s.SnowCoveredSwamp.class));
        cards.add(new SetCardInfo("Snowfield Sinkhole", 269, Rarity.COMMON, mage.cards.s.SnowfieldSinkhole.class));
        cards.add(new SetCardInfo("Spectral Steel", 30, Rarity.UNCOMMON, mage.cards.s.SpectralSteel.class));
        cards.add(new SetCardInfo("Spirit of the Aldergard", 195, Rarity.UNCOMMON, mage.cards.s.SpiritOfTheAldergard.class));
        cards.add(new SetCardInfo("Squash", 152, Rarity.COMMON, mage.cards.s.Squash.class));
        cards.add(new SetCardInfo("Starnheim Aspirant", 380, Rarity.UNCOMMON, mage.cards.s.StarnheimAspirant.class));
        cards.add(new SetCardInfo("Strategic Planning", 77, Rarity.COMMON, mage.cards.s.StrategicPlanning.class));
        cards.add(new SetCardInfo("Sulfurous Mire", 270, Rarity.COMMON, mage.cards.s.SulfurousMire.class));
        cards.add(new SetCardInfo("Surtland Elementalist", 375, Rarity.RARE, mage.cards.s.SurtlandElementalist.class));
        cards.add(new SetCardInfo("Surtland Flinger", 377, Rarity.RARE, mage.cards.s.SurtlandFlinger.class));
        cards.add(new SetCardInfo("Swamp", 396, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tergrid's Shadow", 113, Rarity.UNCOMMON, mage.cards.t.TergridsShadow.class));
        cards.add(new SetCardInfo("The Trickster-God's Heist", 232, Rarity.UNCOMMON, mage.cards.t.TheTricksterGodsHeist.class));
        cards.add(new SetCardInfo("The World Tree", 275, Rarity.RARE, mage.cards.t.TheWorldTree.class));
        cards.add(new SetCardInfo("Thornmantle Striker", 387, Rarity.UNCOMMON, mage.cards.t.ThornmantleStriker.class));
        cards.add(new SetCardInfo("Toralf, God of Fury", 154, Rarity.MYTHIC, mage.cards.t.ToralfGodOfFury.class));
        cards.add(new SetCardInfo("Tormentor's Helm", 155, Rarity.COMMON, mage.cards.t.TormentorsHelm.class));
        cards.add(new SetCardInfo("Toski, Bearer of Secrets", 197, Rarity.RARE, mage.cards.t.ToskiBearerOfSecrets.class));
        cards.add(new SetCardInfo("Tyvar Kell", 198, Rarity.MYTHIC, mage.cards.t.TyvarKell.class));
        cards.add(new SetCardInfo("Undersea Invader", 78, Rarity.COMMON, mage.cards.u.UnderseaInvader.class));
        cards.add(new SetCardInfo("Usher of the Fallen", 35, Rarity.UNCOMMON, mage.cards.u.UsherOfTheFallen.class));
        cards.add(new SetCardInfo("Valkyrie Harbinger", 374, Rarity.RARE, mage.cards.v.ValkyrieHarbinger.class));
        cards.add(new SetCardInfo("Varragoth, Bloodsky Sire", 115, Rarity.RARE, mage.cards.v.VarragothBloodskySire.class));
        cards.add(new SetCardInfo("Vault Robber", 158, Rarity.COMMON, mage.cards.v.VaultRobber.class));
        cards.add(new SetCardInfo("Vega, the Watcher", 233, Rarity.UNCOMMON, mage.cards.v.VegaTheWatcher.class));
        cards.add(new SetCardInfo("Village Rites", 117, Rarity.COMMON, mage.cards.v.VillageRites.class));
        cards.add(new SetCardInfo("Volatile Fjord", 273, Rarity.COMMON, mage.cards.v.VolatileFjord.class));
        cards.add(new SetCardInfo("Waking the Trolls", 234, Rarity.RARE, mage.cards.w.WakingTheTrolls.class));
        cards.add(new SetCardInfo("Warchanter Skald", 381, Rarity.UNCOMMON, mage.cards.w.WarchanterSkald.class));
        cards.add(new SetCardInfo("Weathered Runestone", 247, Rarity.UNCOMMON, mage.cards.w.WeatheredRunestone.class));
        cards.add(new SetCardInfo("Wings of the Cosmos", 39, Rarity.COMMON, mage.cards.w.WingsOfTheCosmos.class));
        cards.add(new SetCardInfo("Withercrown", 119, Rarity.COMMON, mage.cards.w.Withercrown.class));
        cards.add(new SetCardInfo("Woodland Chasm", 274, Rarity.COMMON, mage.cards.w.WoodlandChasm.class));
        cards.add(new SetCardInfo("Youthful Valkyrie", 382, Rarity.UNCOMMON, mage.cards.y.YouthfulValkyrie.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is fully implemented
    }
}
