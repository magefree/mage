package mage.sets;

import mage.cards.AdventureCard;
import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ThroneOfEldraine extends ExpansionSet {

    private static final ThroneOfEldraine instance = new ThroneOfEldraine();

    public static ThroneOfEldraine getInstance() {
        return instance;
    }

    private ThroneOfEldraine() {
        super("Throne of Eldraine", "ELD", ExpansionSet.buildDate(2019, 10, 4), SetType.EXPANSION);
        this.blockName = "Throne of Eldraine";
        this.hasBoosters = true;
        this.hasBasicLands = false;// false until basics officially spoiled
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 269; // unconfirmed for now

        cards.add(new SetCardInfo("Alela, Artful Provocateur", 324, Rarity.MYTHIC, mage.cards.a.AlelaArtfulProvocateur.class));
        cards.add(new SetCardInfo("All That Glitters", 2, Rarity.UNCOMMON, mage.cards.a.AllThatGlitters.class));
        cards.add(new SetCardInfo("Animating Faerie", 38, Rarity.UNCOMMON, mage.cards.a.AnimatingFaerie.class));
        cards.add(new SetCardInfo("Arcane Signet", 331, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Arcanist's Owl", 206, Rarity.UNCOMMON, mage.cards.a.ArcanistsOwl.class));
        cards.add(new SetCardInfo("Bake into a Pie", 76, Rarity.COMMON, mage.cards.b.BakeIntoAPie.class));
        cards.add(new SetCardInfo("Banish into Fable", 325, Rarity.RARE, mage.cards.b.BanishIntoFable.class));
        cards.add(new SetCardInfo("Beanstalk Giant", 149, Rarity.UNCOMMON, mage.cards.b.BeanstalkGiant.class));
        cards.add(new SetCardInfo("Belle of the Brawl", 78, Rarity.UNCOMMON, mage.cards.b.BelleOfTheBrawl.class));
        cards.add(new SetCardInfo("Blow Your House Down", 114, Rarity.COMMON, mage.cards.b.BlowYourHouseDown.class));
        cards.add(new SetCardInfo("Bramblefort Fink", 311, Rarity.UNCOMMON, mage.cards.b.BramblefortFink.class));
        cards.add(new SetCardInfo("Brimstone Trebuchet", 116, Rarity.COMMON, mage.cards.b.BrimstoneTrebuchet.class));
        cards.add(new SetCardInfo("Burning-Yard Trainer", 117, Rarity.UNCOMMON, mage.cards.b.BurningYardTrainer.class));
        cards.add(new SetCardInfo("Chittering Witch", 319, Rarity.RARE, mage.cards.c.ChitteringWitch.class));
        cards.add(new SetCardInfo("Chulane, Teller of Tales", 326, Rarity.MYTHIC, mage.cards.c.ChulaneTellerOfTales.class));
        cards.add(new SetCardInfo("Command Tower", 333, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Corridor Monitor", 41, Rarity.COMMON, mage.cards.c.CorridorMonitor.class));
        cards.add(new SetCardInfo("Crystal Slipper", 119, Rarity.COMMON, mage.cards.c.CrystalSlipper.class));
        cards.add(new SetCardInfo("Embercleave", 120, Rarity.MYTHIC, mage.cards.e.Embercleave.class));
        cards.add(new SetCardInfo("Embereth Paladin", 121, Rarity.COMMON, mage.cards.e.EmberethPaladin.class));
        cards.add(new SetCardInfo("Embereth Shieldbreaker", 122, Rarity.UNCOMMON, mage.cards.e.EmberethShieldbreaker.class));
        cards.add(new SetCardInfo("Embereth Skyblazer", 321, Rarity.RARE, mage.cards.e.EmberethSkyblazer.class));
        cards.add(new SetCardInfo("Enchanted Carriage", 218, Rarity.UNCOMMON, mage.cards.e.EnchantedCarriage.class));
        cards.add(new SetCardInfo("Eye Collector", 86, Rarity.COMMON, mage.cards.e.EyeCollector.class));
        cards.add(new SetCardInfo("Faerie Formation", 316, Rarity.RARE, mage.cards.f.FaerieFormation.class));
        cards.add(new SetCardInfo("Faerie Guidemother", 11, Rarity.COMMON, mage.cards.f.FaerieGuidemother.class));
        cards.add(new SetCardInfo("Faerie Vandal", 45, Rarity.UNCOMMON, mage.cards.f.FaerieVandal.class));
        cards.add(new SetCardInfo("Fireborn Knight", 210, Rarity.UNCOMMON, mage.cards.f.FirebornKnight.class));
        cards.add(new SetCardInfo("Flaxen Intruder", 155, Rarity.UNCOMMON, mage.cards.f.FlaxenIntruder.class));
        cards.add(new SetCardInfo("Foulmire Knight", 90, Rarity.UNCOMMON, mage.cards.f.FoulmireKnight.class));
        cards.add(new SetCardInfo("Frogify", 47, Rarity.UNCOMMON, mage.cards.f.Frogify.class));
        cards.add(new SetCardInfo("Garrison Griffin", 305, Rarity.COMMON, mage.cards.g.GarrisonGriffin.class));
        cards.add(new SetCardInfo("Garruk, Cursed Huntsman", 191, Rarity.MYTHIC, mage.cards.g.GarrukCursedHuntsman.class));
        cards.add(new SetCardInfo("Giant Killer", 14, Rarity.RARE, mage.cards.g.GiantKiller.class));
        cards.add(new SetCardInfo("Gilded Goose", 160, Rarity.RARE, mage.cards.g.GildedGoose.class));
        cards.add(new SetCardInfo("Gluttonous Troll", 327, Rarity.RARE, mage.cards.g.GluttonousTroll.class));
        cards.add(new SetCardInfo("Golden Egg", 220, Rarity.COMMON, mage.cards.g.GoldenEgg.class));
        cards.add(new SetCardInfo("Heraldic Banner", 222, Rarity.UNCOMMON, mage.cards.h.HeraldicBanner.class));
        cards.add(new SetCardInfo("Inspiring Veteran", 194, Rarity.UNCOMMON, mage.cards.i.InspiringVeteran.class));
        cards.add(new SetCardInfo("Joust", 129, Rarity.UNCOMMON, mage.cards.j.Joust.class));
        cards.add(new SetCardInfo("Jousting Dummy", 224, Rarity.COMMON, mage.cards.j.JoustingDummy.class));
        cards.add(new SetCardInfo("Keeper of Fables", 163, Rarity.UNCOMMON, mage.cards.k.KeeperOfFables.class));
        cards.add(new SetCardInfo("Knights' Charge", 328, Rarity.RARE, mage.cards.k.KnightsCharge.class));
        cards.add(new SetCardInfo("Korvold, Fae-Cursed King", 329, Rarity.MYTHIC, mage.cards.k.KorvoldFaeCursedKing.class));
        cards.add(new SetCardInfo("Lovestruck Beast", 165, Rarity.RARE, mage.cards.l.LovestruckBeast.class));
        cards.add(new SetCardInfo("Mace of the Valiant", 314, Rarity.RARE, mage.cards.m.MaceOfTheValiant.class));
        cards.add(new SetCardInfo("Maraleaf Pixie", 196, Rarity.UNCOMMON, mage.cards.m.MaraleafPixie.class));
        cards.add(new SetCardInfo("Midnight Clock", 54, Rarity.RARE, mage.cards.m.MidnightClock.class));
        cards.add(new SetCardInfo("Mystical Dispute", 58, Rarity.UNCOMMON, mage.cards.m.MysticalDispute.class));
        cards.add(new SetCardInfo("Oko's Accomplices", 310, Rarity.COMMON, mage.cards.o.OkosAccomplices.class));
        cards.add(new SetCardInfo("Oko's Hospitality", 312, Rarity.RARE, mage.cards.o.OkosHospitality.class));
        cards.add(new SetCardInfo("Oko, Thief of Crowns", 197, Rarity.MYTHIC, mage.cards.o.OkoThiefOfCrowns.class));
        cards.add(new SetCardInfo("Once Upon a Time", 169, Rarity.RARE, mage.cards.o.OnceUponATime.class));
        cards.add(new SetCardInfo("Order of Midnight", 99, Rarity.UNCOMMON, mage.cards.o.OrderOfMidnight.class));
        cards.add(new SetCardInfo("Piper of the Swarm", 100, Rarity.RARE, mage.cards.p.PiperOfTheSwarm.class));
        cards.add(new SetCardInfo("Rankle, Master of Pranks", 101, Rarity.MYTHIC, mage.cards.r.RankleMasterOfPranks.class));
        cards.add(new SetCardInfo("Return to Nature", 173, Rarity.COMMON, mage.cards.r.ReturnToNature.class));
        cards.add(new SetCardInfo("Rosethorn Acolyte", 174, Rarity.COMMON, mage.cards.r.RosethornAcolyte.class));
        cards.add(new SetCardInfo("Rowan's Battleguard", 306, Rarity.UNCOMMON, mage.cards.r.RowansBattleguard.class));
        cards.add(new SetCardInfo("Rowan's Stalwarts", 307, Rarity.RARE, mage.cards.r.RowansStalwarts.class));
        cards.add(new SetCardInfo("Rowan, Fearless Sparkmage", 304, Rarity.MYTHIC, mage.cards.r.RowanFearlessSparkmage.class));
        cards.add(new SetCardInfo("Run Away Together", 62, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Savvy Hunter", 200, Rarity.UNCOMMON, mage.cards.s.SavvyHunter.class));
        cards.add(new SetCardInfo("Shimmer Dragon", 317, Rarity.RARE, mage.cards.s.ShimmerDragon.class));
        cards.add(new SetCardInfo("Shinechaser", 201, Rarity.UNCOMMON, mage.cards.s.Shinechaser.class));
        cards.add(new SetCardInfo("Shining Armor", 29, Rarity.COMMON, mage.cards.s.ShiningArmor.class));
        cards.add(new SetCardInfo("Silverflame Ritual", 30, Rarity.COMMON, mage.cards.s.SilverflameRitual.class));
        cards.add(new SetCardInfo("Silverwing Squadron", 315, Rarity.RARE, mage.cards.s.SilverwingSquadron.class));
        cards.add(new SetCardInfo("Slaying Fire", 143, Rarity.UNCOMMON, mage.cards.s.SlayingFire.class));
        cards.add(new SetCardInfo("Smitten Swordmaster", 105, Rarity.COMMON, mage.cards.s.SmittenSwordmaster.class));
        cards.add(new SetCardInfo("Steelbane Hydra", 322, Rarity.RARE, mage.cards.s.SteelbaneHydra.class));
        cards.add(new SetCardInfo("Steelclaw Lance", 202, Rarity.UNCOMMON, mage.cards.s.SteelclawLance.class));
        cards.add(new SetCardInfo("Syr Gwyn, Hero of Ashvale", 330, Rarity.MYTHIC, mage.cards.s.SyrGwynHeroOfAshvale.class));
        cards.add(new SetCardInfo("Syr Konrad, the Grim", 107, Rarity.UNCOMMON, mage.cards.s.SyrKonradTheGrim.class));
        cards.add(new SetCardInfo("Taste of Death", 320, Rarity.RARE, mage.cards.t.TasteOfDeath.class));
        cards.add(new SetCardInfo("The Circle of Loyalty", 9, Rarity.MYTHIC, mage.cards.t.TheCircleOfLoyalty.class));
        cards.add(new SetCardInfo("The Royal Scions", 199, Rarity.MYTHIC, mage.cards.t.TheRoyalScions.class));
        cards.add(new SetCardInfo("Thorn Mammoth", 323, Rarity.RARE, mage.cards.t.ThornMammoth.class));
        cards.add(new SetCardInfo("Thornwood Falls", 313, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Tome Raider", 68, Rarity.COMMON, mage.cards.t.TomeRaider.class));
        cards.add(new SetCardInfo("Tome of Legends", 332, Rarity.RARE, mage.cards.t.TomeOfLegends.class));
        cards.add(new SetCardInfo("Tournament Grounds", 248, Rarity.UNCOMMON, mage.cards.t.TournamentGrounds.class));
        cards.add(new SetCardInfo("Turn into a Pumpkin", 69, Rarity.UNCOMMON, mage.cards.t.TurnIntoAPumpkin.class));
        cards.add(new SetCardInfo("Wicked Guardian", 109, Rarity.COMMON, mage.cards.w.WickedGuardian.class));
        cards.add(new SetCardInfo("Wicked Wolf", 181, Rarity.RARE, mage.cards.w.WickedWolf.class));
        cards.add(new SetCardInfo("Wildwood Tracker", 183, Rarity.COMMON, mage.cards.w.WildwoodTracker.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 308, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Wintermoor Commander", 205, Rarity.UNCOMMON, mage.cards.w.WintermoorCommander.class));
        cards.add(new SetCardInfo("Wishful Merfolk", 73, Rarity.COMMON, mage.cards.w.WishfulMerfolk.class));
        cards.add(new SetCardInfo("Witch's Cottage", 249, Rarity.COMMON, mage.cards.w.WitchsCottage.class));
        cards.add(new SetCardInfo("Witch's Vengeance", 111, Rarity.RARE, mage.cards.w.WitchsVengeance.class));
        cards.add(new SetCardInfo("Witching Well", 74, Rarity.COMMON, mage.cards.w.WitchingWell.class));
        cards.add(new SetCardInfo("Workshop Elders", 318, Rarity.RARE, mage.cards.w.WorkshopElders.class));

        // This is here to prevent the incomplete adventure implementation from causing problems and will be removed
        cards.removeIf(setCardInfo -> AdventureCard.class.isAssignableFrom(setCardInfo.getCardClass()));
    }
}
