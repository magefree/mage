package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/gn3
 *
 * @author TheElk801
 */
public class GameNightFreeForAll extends ExpansionSet {

    private static final GameNightFreeForAll instance = new GameNightFreeForAll();

    public static GameNightFreeForAll getInstance() {
        return instance;
    }

    private GameNightFreeForAll() {
        super("Game Night: Free-for-All", "GN3", ExpansionSet.buildDate(2022, 10, 14), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Abrade", 67, Rarity.COMMON, mage.cards.a.Abrade.class));
        cards.add(new SetCardInfo("Ancestral Blade", 6, Rarity.COMMON, mage.cards.a.AncestralBlade.class));
        cards.add(new SetCardInfo("Ancient Hellkite", 68, Rarity.RARE, mage.cards.a.AncientHellkite.class));
        cards.add(new SetCardInfo("Angler Drake", 22, Rarity.UNCOMMON, mage.cards.a.AnglerDrake.class));
        cards.add(new SetCardInfo("Angler Turtle", 23, Rarity.RARE, mage.cards.a.AnglerTurtle.class));
        cards.add(new SetCardInfo("Argentum Armor", 113, Rarity.RARE, mage.cards.a.ArgentumArmor.class));
        cards.add(new SetCardInfo("Banisher Priest", 7, Rarity.UNCOMMON, mage.cards.b.BanisherPriest.class));
        cards.add(new SetCardInfo("Beast Whisperer", 89, Rarity.RARE, mage.cards.b.BeastWhisperer.class));
        cards.add(new SetCardInfo("Blaze", 69, Rarity.UNCOMMON, mage.cards.b.Blaze.class));
        cards.add(new SetCardInfo("Bloodsoaked Altar", 43, Rarity.UNCOMMON, mage.cards.b.BloodsoakedAltar.class));
        cards.add(new SetCardInfo("Bloodthirsty Blade", 114, Rarity.UNCOMMON, mage.cards.b.BloodthirstyBlade.class));
        cards.add(new SetCardInfo("Brineborn Cutthroat", 24, Rarity.UNCOMMON, mage.cards.b.BrinebornCutthroat.class));
        cards.add(new SetCardInfo("Broken Wings", 90, Rarity.COMMON, mage.cards.b.BrokenWings.class));
        cards.add(new SetCardInfo("Bushmeat Poacher", 44, Rarity.COMMON, mage.cards.b.BushmeatPoacher.class));
        cards.add(new SetCardInfo("Captain of the Watch", 8, Rarity.RARE, mage.cards.c.CaptainOfTheWatch.class));
        cards.add(new SetCardInfo("Colossus Hammer", 115, Rarity.UNCOMMON, mage.cards.c.ColossusHammer.class));
        cards.add(new SetCardInfo("Counterspell", 25, Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Crucible of Fire", 70, Rarity.RARE, mage.cards.c.CrucibleOfFire.class));
        cards.add(new SetCardInfo("Danitha Capashen, Paragon", 9, Rarity.UNCOMMON, mage.cards.d.DanithaCapashenParagon.class));
        cards.add(new SetCardInfo("Demon of Loathing", 45, Rarity.RARE, mage.cards.d.DemonOfLoathing.class));
        cards.add(new SetCardInfo("Demonic Embrace", 46, Rarity.RARE, mage.cards.d.DemonicEmbrace.class));
        cards.add(new SetCardInfo("Diluvian Primordial", 26, Rarity.RARE, mage.cards.d.DiluvianPrimordial.class));
        cards.add(new SetCardInfo("Doom Blade", 47, Rarity.UNCOMMON, mage.cards.d.DoomBlade.class));
        cards.add(new SetCardInfo("Doomed Dissenter", 48, Rarity.COMMON, mage.cards.d.DoomedDissenter.class));
        cards.add(new SetCardInfo("Dragon Egg", 71, Rarity.COMMON, mage.cards.d.DragonEgg.class));
        cards.add(new SetCardInfo("Dragon Hatchling", 72, Rarity.COMMON, mage.cards.d.DragonHatchling.class));
        cards.add(new SetCardInfo("Dragon Mage", 73, Rarity.UNCOMMON, mage.cards.d.DragonMage.class));
        cards.add(new SetCardInfo("Dragon Tempest", 74, Rarity.UNCOMMON, mage.cards.d.DragonTempest.class));
        cards.add(new SetCardInfo("Dragonspeaker Shaman", 75, Rarity.UNCOMMON, mage.cards.d.DragonspeakerShaman.class));
        cards.add(new SetCardInfo("Drakuseth, Maw of Flames", 76, Rarity.RARE, mage.cards.d.DrakusethMawOfFlames.class));
        cards.add(new SetCardInfo("Dusk Legion Zealot", 49, Rarity.COMMON, mage.cards.d.DuskLegionZealot.class));
        cards.add(new SetCardInfo("Dwynen's Elite", 91, Rarity.UNCOMMON, mage.cards.d.DwynensElite.class));
        cards.add(new SetCardInfo("Elven Ambush", 92, Rarity.UNCOMMON, mage.cards.e.ElvenAmbush.class));
        cards.add(new SetCardInfo("Elvish Archdruid", 93, Rarity.RARE, mage.cards.e.ElvishArchdruid.class));
        cards.add(new SetCardInfo("Elvish Rejuvenator", 94, Rarity.COMMON, mage.cards.e.ElvishRejuvenator.class));
        cards.add(new SetCardInfo("Elvish Skysweeper", 95, Rarity.COMMON, mage.cards.e.ElvishSkysweeper.class));
        cards.add(new SetCardInfo("Elvish Visionary", 96, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("End-Raze Forerunners", 97, Rarity.RARE, mage.cards.e.EndRazeForerunners.class));
        cards.add(new SetCardInfo("Fact or Fiction", 27, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Flameblast Dragon", 77, Rarity.RARE, mage.cards.f.FlameblastDragon.class));
        cards.add(new SetCardInfo("Flametongue Kavu", 78, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class));
        cards.add(new SetCardInfo("Fleshbag Marauder", 50, Rarity.UNCOMMON, mage.cards.f.FleshbagMarauder.class));
        cards.add(new SetCardInfo("Fog Bank", 28, Rarity.UNCOMMON, mage.cards.f.FogBank.class));
        cards.add(new SetCardInfo("Forbidding Spirit", 10, Rarity.UNCOMMON, mage.cards.f.ForbiddingSpirit.class));
        cards.add(new SetCardInfo("Forest", 134, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 135, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 136, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Furnace Whelp", 79, Rarity.UNCOMMON, mage.cards.f.FurnaceWhelp.class));
        cards.add(new SetCardInfo("Gavony Unhallowed", 51, Rarity.COMMON, mage.cards.g.GavonyUnhallowed.class));
        cards.add(new SetCardInfo("Gifted Aetherborn", 52, Rarity.UNCOMMON, mage.cards.g.GiftedAetherborn.class));
        cards.add(new SetCardInfo("Goblin Motivator", 80, Rarity.COMMON, mage.cards.g.GoblinMotivator.class));
        cards.add(new SetCardInfo("Gravewaker", 53, Rarity.RARE, mage.cards.g.Gravewaker.class));
        cards.add(new SetCardInfo("Greatsword", 116, Rarity.UNCOMMON, mage.cards.g.Greatsword.class));
        cards.add(new SetCardInfo("Heavenly Blademaster", 11, Rarity.RARE, mage.cards.h.HeavenlyBlademaster.class));
        cards.add(new SetCardInfo("Howling Golem", 117, Rarity.COMMON, mage.cards.h.HowlingGolem.class));
        cards.add(new SetCardInfo("Illusory Ambusher", 29, Rarity.UNCOMMON, mage.cards.i.IllusoryAmbusher.class));
        cards.add(new SetCardInfo("Imaryll, Elfhame Elite", 5, Rarity.MYTHIC, mage.cards.i.ImaryllElfhameElite.class));
        cards.add(new SetCardInfo("Immaculate Magistrate", 98, Rarity.RARE, mage.cards.i.ImmaculateMagistrate.class));
        cards.add(new SetCardInfo("Impulse", 30, Rarity.COMMON, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Invigorate", 99, Rarity.UNCOMMON, mage.cards.i.Invigorate.class));
        cards.add(new SetCardInfo("Island", 125, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 126, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 127, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jeering Homunculus", 31, Rarity.COMMON, mage.cards.j.JeeringHomunculus.class));
        cards.add(new SetCardInfo("Joraga Visionary", 100, Rarity.COMMON, mage.cards.j.JoragaVisionary.class));
        cards.add(new SetCardInfo("Kargan Dragonrider", 81, Rarity.COMMON, mage.cards.k.KarganDragonrider.class));
        cards.add(new SetCardInfo("Kitesail Apprentice", 12, Rarity.COMMON, mage.cards.k.KitesailApprentice.class));
        cards.add(new SetCardInfo("Knollspine Dragon", 82, Rarity.RARE, mage.cards.k.KnollspineDragon.class));
        cards.add(new SetCardInfo("Kor Duelist", 13, Rarity.UNCOMMON, mage.cards.k.KorDuelist.class));
        cards.add(new SetCardInfo("Kor Outfitter", 14, Rarity.COMMON, mage.cards.k.KorOutfitter.class));
        cards.add(new SetCardInfo("Lightning Bolt", 83, Rarity.UNCOMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Liliana's Mastery", 54, Rarity.RARE, mage.cards.l.LilianasMastery.class));
        cards.add(new SetCardInfo("Llanowar Elves", 101, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Llanowar Tribe", 102, Rarity.UNCOMMON, mage.cards.l.LlanowarTribe.class));
        cards.add(new SetCardInfo("Lord of the Accursed", 55, Rarity.UNCOMMON, mage.cards.l.LordOfTheAccursed.class));
        cards.add(new SetCardInfo("Maalfeld Twins", 56, Rarity.COMMON, mage.cards.m.MaalfeldTwins.class));
        cards.add(new SetCardInfo("Maeve, Insidious Singer", 2, Rarity.MYTHIC, mage.cards.m.MaeveInsidiousSinger.class));
        cards.add(new SetCardInfo("Mana Geyser", 84, Rarity.COMMON, mage.cards.m.ManaGeyser.class));
        cards.add(new SetCardInfo("Moan of the Unhallowed", 57, Rarity.COMMON, mage.cards.m.MoanOfTheUnhallowed.class));
        cards.add(new SetCardInfo("Moonsilver Spear", 118, Rarity.RARE, mage.cards.m.MoonsilverSpear.class));
        cards.add(new SetCardInfo("Mountain", 131, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 132, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 133, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murmuring Mystic", 32, Rarity.UNCOMMON, mage.cards.m.MurmuringMystic.class));
        cards.add(new SetCardInfo("Nogi, Draco-Zealot", 4, Rarity.MYTHIC, mage.cards.n.NogiDracoZealot.class));
        cards.add(new SetCardInfo("Overrun", 103, Rarity.UNCOMMON, mage.cards.o.Overrun.class));
        cards.add(new SetCardInfo("Path to Exile", 15, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Pilgrim of the Ages", 16, Rarity.COMMON, mage.cards.p.PilgrimOfTheAges.class));
        cards.add(new SetCardInfo("Plains", 122, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 123, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 124, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plea for Power", 33, Rarity.RARE, mage.cards.p.PleaForPower.class));
        cards.add(new SetCardInfo("Precognitive Perception", 34, Rarity.RARE, mage.cards.p.PrecognitivePerception.class));
        cards.add(new SetCardInfo("Priest of the Blood Rite", 58, Rarity.RARE, mage.cards.p.PriestOfTheBloodRite.class));
        cards.add(new SetCardInfo("Pull from Tomorrow", 35, Rarity.RARE, mage.cards.p.PullFromTomorrow.class));
        cards.add(new SetCardInfo("Rabid Bite", 104, Rarity.COMMON, mage.cards.r.RabidBite.class));
        cards.add(new SetCardInfo("Ram Through", 105, Rarity.COMMON, mage.cards.r.RamThrough.class));
        cards.add(new SetCardInfo("Rapacious Dragon", 85, Rarity.COMMON, mage.cards.r.RapaciousDragon.class));
        cards.add(new SetCardInfo("Ravenous Chupacabra", 59, Rarity.UNCOMMON, mage.cards.r.RavenousChupacabra.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", 60, Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Regrowth", 106, Rarity.UNCOMMON, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Repulse", 36, Rarity.COMMON, mage.cards.r.Repulse.class));
        cards.add(new SetCardInfo("Ring of Thune", 119, Rarity.UNCOMMON, mage.cards.r.RingOfThune.class));
        cards.add(new SetCardInfo("Run Away Together", 37, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Sea Gate Oracle", 38, Rarity.COMMON, mage.cards.s.SeaGateOracle.class));
        cards.add(new SetCardInfo("Seize the Spoils", 86, Rarity.COMMON, mage.cards.s.SeizeTheSpoils.class));
        cards.add(new SetCardInfo("Serra Angel", 17, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shivan Dragon", 87, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Sign in Blood", 61, Rarity.COMMON, mage.cards.s.SignInBlood.class));
        cards.add(new SetCardInfo("Split Decision", 39, Rarity.UNCOMMON, mage.cards.s.SplitDecision.class));
        cards.add(new SetCardInfo("Strength of Arms", 18, Rarity.COMMON, mage.cards.s.StrengthOfArms.class));
        cards.add(new SetCardInfo("Supernatural Stamina", 62, Rarity.COMMON, mage.cards.s.SupernaturalStamina.class));
        cards.add(new SetCardInfo("Supreme Will", 40, Rarity.UNCOMMON, mage.cards.s.SupremeWill.class));
        cards.add(new SetCardInfo("Swamp", 128, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 129, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 130, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Vengeance", 120, Rarity.RARE, mage.cards.s.SwordOfVengeance.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 19, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Sylvan Messenger", 107, Rarity.UNCOMMON, mage.cards.s.SylvanMessenger.class));
        cards.add(new SetCardInfo("Talrand's Invocation", 41, Rarity.UNCOMMON, mage.cards.t.TalrandsInvocation.class));
        cards.add(new SetCardInfo("Taunting Elf", 108, Rarity.COMMON, mage.cards.t.TauntingElf.class));
        cards.add(new SetCardInfo("Thorn Lieutenant", 109, Rarity.RARE, mage.cards.t.ThornLieutenant.class));
        cards.add(new SetCardInfo("Thornweald Archer", 110, Rarity.COMMON, mage.cards.t.ThornwealdArcher.class));
        cards.add(new SetCardInfo("Trusty Machete", 121, Rarity.UNCOMMON, mage.cards.t.TrustyMachete.class));
        cards.add(new SetCardInfo("Valorous Stance", 20, Rarity.UNCOMMON, mage.cards.v.ValorousStance.class));
        cards.add(new SetCardInfo("Vilis, Broker of Blood", 63, Rarity.RARE, mage.cards.v.VilisBrokerOfBlood.class));
        cards.add(new SetCardInfo("Village Rites", 64, Rarity.COMMON, mage.cards.v.VillageRites.class));
        cards.add(new SetCardInfo("Vogar, Necropolis Tyrant", 3, Rarity.MYTHIC, mage.cards.v.VogarNecropolisTyrant.class));
        cards.add(new SetCardInfo("Vow of Duty", 21, Rarity.UNCOMMON, mage.cards.v.VowOfDuty.class));
        cards.add(new SetCardInfo("Vow of Flight", 42, Rarity.UNCOMMON, mage.cards.v.VowOfFlight.class));
        cards.add(new SetCardInfo("Vow of Lightning", 88, Rarity.UNCOMMON, mage.cards.v.VowOfLightning.class));
        cards.add(new SetCardInfo("Vow of Torment", 65, Rarity.UNCOMMON, mage.cards.v.VowOfTorment.class));
        cards.add(new SetCardInfo("Vow of Wildness", 111, Rarity.UNCOMMON, mage.cards.v.VowOfWildness.class));
        cards.add(new SetCardInfo("Wirewood Pride", 112, Rarity.COMMON, mage.cards.w.WirewoodPride.class));
        cards.add(new SetCardInfo("Zamriel, Seraph of Steel", 1, Rarity.MYTHIC, mage.cards.z.ZamrielSeraphOfSteel.class));
    }
}
