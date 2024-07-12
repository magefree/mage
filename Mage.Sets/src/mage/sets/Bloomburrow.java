package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class Bloomburrow extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Coruscation Mage", "Finch Formation", "Flowerfoot Swordmaster", "Iridescent Vinelasher", "Manifold Mouse", "Splash Lasher", "Steampath Charger", "Tender Wildguide", "Thundertrap Trainer", "Warren Warleader");
    private static final Bloomburrow instance = new Bloomburrow();

    public static Bloomburrow getInstance() {
        return instance;
    }

    private Bloomburrow() {
        super("Bloomburrow", "BLB", ExpansionSet.buildDate(2024, 8, 2), SetType.EXPANSION);
        this.blockName = "Bloomburrow"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Bark-Knuckle Boxer", 164, Rarity.UNCOMMON, mage.cards.b.BarkKnuckleBoxer.class));
        cards.add(new SetCardInfo("Barkform Harvester", 243, Rarity.COMMON, mage.cards.b.BarkformHarvester.class));
        cards.add(new SetCardInfo("Brambleguard Captain", 127, Rarity.UNCOMMON, mage.cards.b.BrambleguardCaptain.class));
        cards.add(new SetCardInfo("Brambleguard Veteran", 165, Rarity.UNCOMMON, mage.cards.b.BrambleguardVeteran.class));
        cards.add(new SetCardInfo("Brave-Kin Duo", 3, Rarity.COMMON, mage.cards.b.BraveKinDuo.class));
        cards.add(new SetCardInfo("Brazen Collector", 128, Rarity.UNCOMMON, mage.cards.b.BrazenCollector.class));
        cards.add(new SetCardInfo("Bria, Riptide Rogue", 379, Rarity.MYTHIC, mage.cards.b.BriaRiptideRogue.class));
        cards.add(new SetCardInfo("Brightblade Stoat", 4, Rarity.UNCOMMON, mage.cards.b.BrightbladeStoat.class));
        cards.add(new SetCardInfo("Byrke, Long Ear of the Law", 380, Rarity.MYTHIC, mage.cards.b.ByrkeLongEarOfTheLaw.class));
        cards.add(new SetCardInfo("Byway Barterer", 129, Rarity.RARE, mage.cards.b.BywayBarterer.class));
        cards.add(new SetCardInfo("Cache Grab", 167, Rarity.COMMON, mage.cards.c.CacheGrab.class));
        cards.add(new SetCardInfo("Carrot Cake", 7, Rarity.COMMON, mage.cards.c.CarrotCake.class));
        cards.add(new SetCardInfo("Corpseberry Cultivator", 210, Rarity.COMMON, mage.cards.c.CorpseberryCultivator.class));
        cards.add(new SetCardInfo("Coruscation Mage", 131, Rarity.UNCOMMON, mage.cards.c.CoruscationMage.class));
        cards.add(new SetCardInfo("Diresight", 91, Rarity.COMMON, mage.cards.d.Diresight.class));
        cards.add(new SetCardInfo("Downwind Ambusher", 92, Rarity.UNCOMMON, mage.cards.d.DownwindAmbusher.class));
        cards.add(new SetCardInfo("Dreamdew Entrancer", 211, Rarity.RARE, mage.cards.d.DreamdewEntrancer.class));
        cards.add(new SetCardInfo("Early Winter", 93, Rarity.COMMON, mage.cards.e.EarlyWinter.class));
        cards.add(new SetCardInfo("Feather of Flight", 13, Rarity.UNCOMMON, mage.cards.f.FeatherOfFlight.class));
        cards.add(new SetCardInfo("Fell", 95, Rarity.UNCOMMON, mage.cards.f.Fell.class));
        cards.add(new SetCardInfo("Finch Formation", 50, Rarity.COMMON, mage.cards.f.FinchFormation.class));
        cards.add(new SetCardInfo("Flowerfoot Swordmaster", 14, Rarity.UNCOMMON, mage.cards.f.FlowerfootSwordmaster.class));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Galewind Moose", 173, Rarity.UNCOMMON, mage.cards.g.GalewindMoose.class));
        cards.add(new SetCardInfo("Harnesser of Storms", 137, Rarity.UNCOMMON, mage.cards.h.HarnesserOfStorms.class));
        cards.add(new SetCardInfo("Head of the Homestead", 216, Rarity.COMMON, mage.cards.h.HeadOfTheHomestead.class));
        cards.add(new SetCardInfo("Hop to It", 16, Rarity.UNCOMMON, mage.cards.h.HopToIt.class));
        cards.add(new SetCardInfo("Hugs, Grisly Guardian", 218, Rarity.MYTHIC, mage.cards.h.HugsGrislyGuardian.class));
        cards.add(new SetCardInfo("Hunter's Talent", 179, Rarity.UNCOMMON, mage.cards.h.HuntersTalent.class));
        cards.add(new SetCardInfo("Iridescent Vinelasher", 99, Rarity.RARE, mage.cards.i.IridescentVinelasher.class));
        cards.add(new SetCardInfo("Island", 266, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Junkblade Bruiser", 220, Rarity.COMMON, mage.cards.j.JunkbladeBruiser.class));
        cards.add(new SetCardInfo("Lilypad Village", 255, Rarity.UNCOMMON, mage.cards.l.LilypadVillage.class));
        cards.add(new SetCardInfo("Lumra, Bellow of the Woods", 183, Rarity.MYTHIC, mage.cards.l.LumraBellowOfTheWoods.class));
        cards.add(new SetCardInfo("Lupinflower Village", 256, Rarity.UNCOMMON, mage.cards.l.LupinflowerVillage.class));
        cards.add(new SetCardInfo("Mabel, Heir to Cragflame", 224, Rarity.RARE, mage.cards.m.MabelHeirToCragflame.class));
        cards.add(new SetCardInfo("Maha, Its Feathers Night", 100, Rarity.MYTHIC, mage.cards.m.MahaItsFeathersNight.class));
        cards.add(new SetCardInfo("Might of the Meek", 144, Rarity.COMMON, mage.cards.m.MightOfTheMeek.class));
        cards.add(new SetCardInfo("Mind Drill Assailant", 225, Rarity.COMMON, mage.cards.m.MindDrillAssailant.class));
        cards.add(new SetCardInfo("Moonrise Cleric", 226, Rarity.COMMON, mage.cards.m.MoonriseCleric.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mudflat Village", 257, Rarity.UNCOMMON, mage.cards.m.MudflatVillage.class));
        cards.add(new SetCardInfo("Muerra, Trash Tactician", 227, Rarity.RARE, mage.cards.m.MuerraTrashTactician.class));
        cards.add(new SetCardInfo("Oakhollow Village", 258, Rarity.UNCOMMON, mage.cards.o.OakhollowVillage.class));
        cards.add(new SetCardInfo("Pearl of Wisdom", 64, Rarity.COMMON, mage.cards.p.PearlOfWisdom.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plumecreed Mentor", 228, Rarity.UNCOMMON, mage.cards.p.PlumecreedMentor.class));
        cards.add(new SetCardInfo("Pond Prophet", 229, Rarity.COMMON, mage.cards.p.PondProphet.class));
        cards.add(new SetCardInfo("Quaketusk Boar", 146, Rarity.UNCOMMON, mage.cards.q.QuaketuskBoar.class));
        cards.add(new SetCardInfo("Repel Calamity", 27, Rarity.UNCOMMON, mage.cards.r.RepelCalamity.class));
        cards.add(new SetCardInfo("Rockface Village", 259, Rarity.UNCOMMON, mage.cards.r.RockfaceVillage.class));
        cards.add(new SetCardInfo("Run Away Together", 67, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Salvation Swan", 28, Rarity.RARE, mage.cards.s.SalvationSwan.class));
        cards.add(new SetCardInfo("Seedglaive Mentor", 231, Rarity.UNCOMMON, mage.cards.s.SeedglaiveMentor.class));
        cards.add(new SetCardInfo("Seedpod Squire", 232, Rarity.COMMON, mage.cards.s.SeedpodSquire.class));
        cards.add(new SetCardInfo("Shoreline Looter", 70, Rarity.UNCOMMON, mage.cards.s.ShorelineLooter.class));
        cards.add(new SetCardInfo("Shrike Force", 31, Rarity.UNCOMMON, mage.cards.s.ShrikeForce.class));
        cards.add(new SetCardInfo("Splash Lasher", 73, Rarity.UNCOMMON, mage.cards.s.SplashLasher.class));
        cards.add(new SetCardInfo("Steampath Charger", 153, Rarity.COMMON, mage.cards.s.SteampathCharger.class));
        cards.add(new SetCardInfo("Stormcatch Mentor", 234, Rarity.UNCOMMON, mage.cards.s.StormcatchMentor.class));
        cards.add(new SetCardInfo("Sunshower Druid", 195, Rarity.COMMON, mage.cards.s.SunshowerDruid.class));
        cards.add(new SetCardInfo("Sunspine Lynx", 155, Rarity.RARE, mage.cards.s.SunspineLynx.class));
        cards.add(new SetCardInfo("Swamp", 270, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Tempest Angler", 235, Rarity.COMMON, mage.cards.t.TempestAngler.class));
        cards.add(new SetCardInfo("Tender Wildguide", 196, Rarity.RARE, mage.cards.t.TenderWildguide.class));
        cards.add(new SetCardInfo("Thistledown Players", 35, Rarity.COMMON, mage.cards.t.ThistledownPlayers.class));
        cards.add(new SetCardInfo("Thornvault Forager", 197, Rarity.RARE, mage.cards.t.ThornvaultForager.class));
        cards.add(new SetCardInfo("Thundertrap Trainer", 78, Rarity.RARE, mage.cards.t.ThundertrapTrainer.class));
        cards.add(new SetCardInfo("Tidecaller Mentor", 236, Rarity.UNCOMMON, mage.cards.t.TidecallerMentor.class));
        cards.add(new SetCardInfo("Treetop Sentries", 201, Rarity.COMMON, mage.cards.t.TreetopSentries.class));
        cards.add(new SetCardInfo("Valley Rotcaller", 119, Rarity.RARE, mage.cards.v.ValleyRotcaller.class));
        cards.add(new SetCardInfo("Veteran Guardmouse", 237, Rarity.COMMON, mage.cards.v.VeteranGuardmouse.class));
        cards.add(new SetCardInfo("Wandertale Mentor", 240, Rarity.UNCOMMON, mage.cards.w.WandertaleMentor.class));
        cards.add(new SetCardInfo("Warren Warleader", 38, Rarity.MYTHIC, mage.cards.w.WarrenWarleader.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is implemented
    }
}
