package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelSuperHeroesCommander extends ExpansionSet {

    private static final MarvelSuperHeroesCommander instance = new MarvelSuperHeroesCommander();

    public static MarvelSuperHeroesCommander getInstance() {
        return instance;
    }

    private MarvelSuperHeroesCommander() {
        super("Marvel Super Heroes Commander", "MSC", ExpansionSet.buildDate(2026, 6, 26), SetType.SUPPLEMENTAL);
        this.blockName = "Marvel Super Heroes"; // for sorting in GUI
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("A.I.M. Bot", 529, Rarity.COMMON, mage.cards.a.AIMBot.class));
        cards.add(new SetCardInfo("Alien Symbiosis", 791, Rarity.UNCOMMON, mage.cards.a.AlienSymbiosis.class));
        cards.add(new SetCardInfo("Atlantean Skirmisher", 616, Rarity.UNCOMMON, mage.cards.a.AtlanteanSkirmisher.class));
        cards.add(new SetCardInfo("Aunt May", 768, Rarity.UNCOMMON, mage.cards.a.AuntMay.class));
        cards.add(new SetCardInfo("Big Bertha", 714, Rarity.UNCOMMON, mage.cards.b.BigBertha.class));
        cards.add(new SetCardInfo("Big Score", 802, Rarity.COMMON, mage.cards.b.BigScore.class));
        cards.add(new SetCardInfo("Big Wheel", 679, Rarity.COMMON, mage.cards.b.BigWheel.class));
        cards.add(new SetCardInfo("Black Widow, Natasha Romanoff", 846, Rarity.COMMON, mage.cards.b.BlackWidowNatashaRomanoff.class));
        cards.add(new SetCardInfo("Captain America, Team Leader", 5, Rarity.MYTHIC, mage.cards.c.CaptainAmericaTeamLeader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain America, Team Leader", 879, Rarity.MYTHIC, mage.cards.c.CaptainAmericaTeamLeader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Marvel, Apex Avenger", 78, Rarity.RARE, mage.cards.c.CaptainMarvelApexAvenger.class));
        cards.add(new SetCardInfo("City Pigeon", 769, Rarity.COMMON, mage.cards.c.CityPigeon.class));
        cards.add(new SetCardInfo("Common Crook", 792, Rarity.COMMON, mage.cards.c.CommonCrook.class));
        cards.add(new SetCardInfo("Commune with Dinosaurs", 814, Rarity.COMMON, mage.cards.c.CommuneWithDinosaurs.class));
        cards.add(new SetCardInfo("Costume Closet", 770, Rarity.UNCOMMON, mage.cards.c.CostumeCloset.class));
        cards.add(new SetCardInfo("Daily Bugle Reporters", 771, Rarity.COMMON, mage.cards.d.DailyBugleReporters.class));
        cards.add(new SetCardInfo("Dark Ritual", 793, Rarity.UNCOMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Deadly Dispute", 794, Rarity.COMMON, mage.cards.d.DeadlyDispute.class));
        cards.add(new SetCardInfo("Director Nick Fury", 81, Rarity.MYTHIC, mage.cards.d.DirectorNickFury.class));
        cards.add(new SetCardInfo("Doc Ock's Henchmen", 784, Rarity.COMMON, mage.cards.d.DocOcksHenchmen.class));
        cards.add(new SetCardInfo("Doc Ock, Sinister Scientist", 783, Rarity.COMMON, mage.cards.d.DocOckSinisterScientist.class));
        cards.add(new SetCardInfo("Doctor Doom, King of Latveria", 6, Rarity.MYTHIC, mage.cards.d.DoctorDoomKingOfLatveria.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doctor Doom, King of Latveria", 880, Rarity.MYTHIC, mage.cards.d.DoctorDoomKingOfLatveria.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Doom Blade", 576, Rarity.UNCOMMON, mage.cards.d.DoomBlade.class));
        cards.add(new SetCardInfo("Doomfall", 795, Rarity.UNCOMMON, mage.cards.d.Doomfall.class));
        cards.add(new SetCardInfo("Doomsday", 796, Rarity.RARE, mage.cards.d.Doomsday.class));
        cards.add(new SetCardInfo("Eerie Gravestone", 824, Rarity.COMMON, mage.cards.e.EerieGravestone.class));
        cards.add(new SetCardInfo("Farseek", 815, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Flash Thompson, Spider-Fan", 772, Rarity.UNCOMMON, mage.cards.f.FlashThompsonSpiderFan.class));
        cards.add(new SetCardInfo("Flora Colossus", 561, Rarity.RARE, mage.cards.f.FloraColossus.class));
        cards.add(new SetCardInfo("Flying Octobot", 785, Rarity.UNCOMMON, mage.cards.f.FlyingOctobot.class));
        cards.add(new SetCardInfo("Furious Strength", 544, Rarity.COMMON, mage.cards.f.FuriousStrength.class));
        cards.add(new SetCardInfo("Gallant Citizen", 820, Rarity.COMMON, mage.cards.g.GallantCitizen.class));
        cards.add(new SetCardInfo("Glamorous Grapplers", 536, Rarity.COMMON, mage.cards.g.GlamorousGrapplers.class));
        cards.add(new SetCardInfo("Grapeshot", 803, Rarity.COMMON, mage.cards.g.Grapeshot.class));
        cards.add(new SetCardInfo("Hammerhead, Maggia Boss", 659, Rarity.UNCOMMON, mage.cards.h.HammerheadMaggiaBoss.class));
        cards.add(new SetCardInfo("Happy Hogan, Dauntless Driver", 545, Rarity.COMMON, mage.cards.h.HappyHoganDauntlessDriver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Happy Hogan, Dauntless Driver", 849, Rarity.COMMON, mage.cards.h.HappyHoganDauntlessDriver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hawkeye, Clint Barton", 518, Rarity.UNCOMMON, mage.cards.h.HawkeyeClintBarton.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hawkeye, Clint Barton", 838, Rarity.UNCOMMON, mage.cards.h.HawkeyeClintBarton.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heroes' Hangout", 804, Rarity.UNCOMMON, mage.cards.h.HeroesHangout.class));
        cards.add(new SetCardInfo("Hit-Monkey", 723, Rarity.RARE, mage.cards.h.HitMonkey.class));
        cards.add(new SetCardInfo("Hobgoblin, Mantled Marauder", 805, Rarity.UNCOMMON, mage.cards.h.HobgoblinMantledMarauder.class));
        cards.add(new SetCardInfo("Hulk, Bruce Banner", 850, Rarity.UNCOMMON, mage.cards.h.HulkBruceBanner.class));
        cards.add(new SetCardInfo("Human Torch", 3, Rarity.MYTHIC, mage.cards.h.HumanTorch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Human Torch", 877, Rarity.MYTHIC, mage.cards.h.HumanTorch.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inner Demons Gangsters", 797, Rarity.COMMON, mage.cards.i.InnerDemonsGangsters.class));
        cards.add(new SetCardInfo("Invisible Woman", 1, Rarity.MYTHIC, mage.cards.i.InvisibleWoman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Invisible Woman", 875, Rarity.MYTHIC, mage.cards.i.InvisibleWoman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron Man, Armored Avenger", 33, Rarity.RARE, mage.cards.i.IronManArmoredAvenger.class));
        cards.add(new SetCardInfo("Iron Man, Bleeding Edge", 626, Rarity.RARE, mage.cards.i.IronManBleedingEdge.class));
        cards.add(new SetCardInfo("Lightning Bolt", 806, Rarity.UNCOMMON, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Lucky the Pizza Dog", 726, Rarity.RARE, mage.cards.l.LuckyThePizzaDog.class));
        cards.add(new SetCardInfo("Lurking Lizards", 816, Rarity.COMMON, mage.cards.l.LurkingLizards.class));
        cards.add(new SetCardInfo("MJ, Rising Star", 773, Rarity.UNCOMMON, mage.cards.m.MJRisingStar.class));
        cards.add(new SetCardInfo("Mandroid Squadron", 841, Rarity.COMMON, mage.cards.m.MandroidSquadron.class));
        cards.add(new SetCardInfo("Masked Meower", 807, Rarity.COMMON, mage.cards.m.MaskedMeower.class));
        cards.add(new SetCardInfo("Mist-Cloaked Herald", 760, Rarity.COMMON, mage.cards.m.MistCloakedHerald.class));
        cards.add(new SetCardInfo("Mister Fantastic", 2, Rarity.MYTHIC, mage.cards.m.MisterFantastic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mister Fantastic", 876, Rarity.MYTHIC, mage.cards.m.MisterFantastic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mob Lookout", 821, Rarity.COMMON, mage.cards.m.MobLookout.class));
        cards.add(new SetCardInfo("Pacifism", 575, Rarity.COMMON, mage.cards.p.Pacifism.class));
        cards.add(new SetCardInfo("Pumpkin Bombardment", 822, Rarity.COMMON, mage.cards.p.PumpkinBombardment.class));
        cards.add(new SetCardInfo("Rhino's Rampage", 823, Rarity.UNCOMMON, mage.cards.r.RhinosRampage.class));
        cards.add(new SetCardInfo("Robotics Mastery", 786, Rarity.UNCOMMON, mage.cards.r.RoboticsMastery.class));
        cards.add(new SetCardInfo("Rocket-Powered Goblin Glider", 825, Rarity.RARE, mage.cards.r.RocketPoweredGoblinGlider.class));
        cards.add(new SetCardInfo("Romantic Rendezvous", 808, Rarity.COMMON, mage.cards.r.RomanticRendezvous.class));
        cards.add(new SetCardInfo("Sadistic Slash", 666, Rarity.UNCOMMON, mage.cards.s.SadisticSlash.class));
        cards.add(new SetCardInfo("Savage Lands", 827, Rarity.UNCOMMON, mage.cards.s.SavageLands.class));
        cards.add(new SetCardInfo("Scout the City", 817, Rarity.COMMON, mage.cards.s.ScoutTheCity.class));
        cards.add(new SetCardInfo("Selfless Police Captain", 774, Rarity.COMMON, mage.cards.s.SelflessPoliceCaptain.class));
        cards.add(new SetCardInfo("Shock", 809, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Spectacular Tactics", 775, Rarity.COMMON, mage.cards.s.SpectacularTactics.class));
        cards.add(new SetCardInfo("Spider-Gwen, Free Spirit", 874, Rarity.COMMON, mage.cards.s.SpiderGwenFreeSpirit.class));
        cards.add(new SetCardInfo("Spider-Man, Web-Slinger", 776, Rarity.COMMON, mage.cards.s.SpiderManWebSlinger.class));
        cards.add(new SetCardInfo("Spider-Mobile", 826, Rarity.UNCOMMON, mage.cards.s.SpiderMobile.class));
        cards.add(new SetCardInfo("Spider-Rex, Daring Dino", 818, Rarity.COMMON, mage.cards.s.SpiderRexDaringDino.class));
        cards.add(new SetCardInfo("Spider-UK", 777, Rarity.UNCOMMON, mage.cards.s.SpiderUK.class));
        cards.add(new SetCardInfo("Starling, Aerial Ally", 778, Rarity.COMMON, mage.cards.s.StarlingAerialAlly.class));
        cards.add(new SetCardInfo("Stegron the Dinosaur Man", 810, Rarity.COMMON, mage.cards.s.StegronTheDinosaurMan.class));
        cards.add(new SetCardInfo("Sudden Strike", 779, Rarity.UNCOMMON, mage.cards.s.SuddenStrike.class));
        cards.add(new SetCardInfo("Swarm, Being of Bees", 799, Rarity.COMMON, mage.cards.s.SwarmBeingOfBees.class));
        cards.add(new SetCardInfo("T'Challa, the Black Panther", 7, Rarity.MYTHIC, mage.cards.t.TChallaTheBlackPanther.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("T'Challa, the Black Panther", 881, Rarity.MYTHIC, mage.cards.t.TChallaTheBlackPanther.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Taxi Driver", 811, Rarity.COMMON, mage.cards.t.TaxiDriver.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 828, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Terrific Team-Up", 819, Rarity.UNCOMMON, mage.cards.t.TerrificTeamUp.class));
        cards.add(new SetCardInfo("The Clone Saga", 782, Rarity.RARE, mage.cards.t.TheCloneSaga.class));
        cards.add(new SetCardInfo("The Fantastic Four", 741, Rarity.MYTHIC, mage.cards.t.TheFantasticFour.class));
        cards.add(new SetCardInfo("The Spot's Portal", 798, Rarity.UNCOMMON, mage.cards.t.TheSpotsPortal.class));
        cards.add(new SetCardInfo("The Thing", 4, Rarity.MYTHIC, mage.cards.t.TheThing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Thing", 878, Rarity.MYTHIC, mage.cards.t.TheThing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Think Twice", 787, Rarity.COMMON, mage.cards.t.ThinkTwice.class));
        cards.add(new SetCardInfo("Thriving Bluff", 578, Rarity.COMMON, mage.cards.t.ThrivingBluff.class));
        cards.add(new SetCardInfo("Thriving Grove", 579, Rarity.COMMON, mage.cards.t.ThrivingGrove.class));
        cards.add(new SetCardInfo("Thriving Heath", 580, Rarity.COMMON, mage.cards.t.ThrivingHeath.class));
        cards.add(new SetCardInfo("Thriving Isle", 581, Rarity.COMMON, mage.cards.t.ThrivingIsle.class));
        cards.add(new SetCardInfo("Thriving Moor", 582, Rarity.COMMON, mage.cards.t.ThrivingMoor.class));
        cards.add(new SetCardInfo("Time Warp", 788, Rarity.RARE, mage.cards.t.TimeWarp.class));
        cards.add(new SetCardInfo("Tombstone, Career Criminal", 577, Rarity.UNCOMMON, mage.cards.t.TombstoneCareerCriminal.class));
        cards.add(new SetCardInfo("Undead Hand Ninja", 670, Rarity.UNCOMMON, mage.cards.u.UndeadHandNinja.class));
        cards.add(new SetCardInfo("Unlucky Witness", 812, Rarity.UNCOMMON, mage.cards.u.UnluckyWitness.class));
        cards.add(new SetCardInfo("Unstable Experiment", 789, Rarity.COMMON, mage.cards.u.UnstableExperiment.class));
        cards.add(new SetCardInfo("Vault Guardsman", 612, Rarity.UNCOMMON, mage.cards.v.VaultGuardsman.class));
        cards.add(new SetCardInfo("Venom, Evil Unleashed", 800, Rarity.COMMON, mage.cards.v.VenomEvilUnleashed.class));
        cards.add(new SetCardInfo("Venomized Cat", 801, Rarity.COMMON, mage.cards.v.VenomizedCat.class));
        cards.add(new SetCardInfo("Vision, Synthezoid Avenger", 460, Rarity.RARE, mage.cards.v.VisionSynthezoidAvenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vision, Synthezoid Avenger", 119, Rarity.RARE, mage.cards.v.VisionSynthezoidAvenger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Void Helix", 674, Rarity.UNCOMMON, mage.cards.v.VoidHelix.class));
        cards.add(new SetCardInfo("Warriors of Wakanda", 565, Rarity.COMMON, mage.cards.w.WarriorsOfWakanda.class));
        cards.add(new SetCardInfo("Wild Pack Squad", 781, Rarity.COMMON, mage.cards.w.WildPackSquad.class));
        cards.add(new SetCardInfo("Wisecrack", 813, Rarity.UNCOMMON, mage.cards.w.Wisecrack.class));
        cards.add(new SetCardInfo("Zarda, the Power Princess", 615, Rarity.UNCOMMON, mage.cards.z.ZardaThePowerPrincess.class));

        //TODO: Correct these collectors numbers and rarities once confirmed
        cards.add(new SetCardInfo("Abomination, Irradiated Brute", "XXX1", Rarity.RARE, mage.cards.a.AbominationIrradiatedBrute.class));
        cards.add(new SetCardInfo("Loki, God of Lies", "XXX2", Rarity.RARE, mage.cards.l.LokiGodOfLies.class));
        cards.add(new SetCardInfo("M.O.D.O.K., Evil Intellect", "XXX3", Rarity.RARE, mage.cards.m.MODOKEvilIntellect.class));
        cards.add(new SetCardInfo("Thanos, Death's Consort", "XXX4", Rarity.RARE, mage.cards.t.ThanosDeathsConsort.class));
        cards.add(new SetCardInfo("Ultron, Machine Overlord", "XXX5", Rarity.RARE, mage.cards.u.UltronMachineOverlord.class));
    }
}
