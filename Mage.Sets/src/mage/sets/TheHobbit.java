package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class TheHobbit extends ExpansionSet {

    private static final TheHobbit instance = new TheHobbit();

    public static TheHobbit getInstance() {
        return instance;
    }

    private TheHobbit() {
        super("The Hobbit", "HOB", ExpansionSet.buildDate(2026, 8, 14), SetType.EXPANSION);
        this.blockName = "The Hobbit"; // for sorting in GUI
        this.hasBasicLands = true;

        this.enablePlayBooster(198);

        cards.add(new SetCardInfo("An Unexpected Party", 29, Rarity.RARE, mage.cards.a.AnUnexpectedParty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("An Unexpected Party", 289, Rarity.RARE, mage.cards.a.AnUnexpectedParty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Attercop", 116, Rarity.COMMON, mage.cards.a.Attercop.class));
        cards.add(new SetCardInfo("Bard's Company", 146, Rarity.RARE, mage.cards.b.BardsCompany.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bard's Company", 210, Rarity.RARE, mage.cards.b.BardsCompany.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bard the Bowman", 145, Rarity.UNCOMMON, mage.cards.b.BardTheBowman.class));
        cards.add(new SetCardInfo("Beorn, Reluctant Host", 118, Rarity.COMMON, mage.cards.b.BeornReluctantHost.class));
        cards.add(new SetCardInfo("Bilbo Baggins, Burglar", 34, Rarity.COMMON, mage.cards.b.BilboBagginsBurglar.class));
        cards.add(new SetCardInfo("Bilbo's Deadly Slice", 62, Rarity.COMMON, mage.cards.b.BilbosDeadlySlice.class));
        cards.add(new SetCardInfo("Bilbo, Luckwearer", 32, Rarity.UNCOMMON, mage.cards.b.BilboLuckwearer.class));
        cards.add(new SetCardInfo("Bilbo, Thief in the Night", 219, Rarity.MYTHIC, mage.cards.b.BilboThiefInTheNight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bilbo, Thief in the Night", 255, Rarity.MYTHIC, mage.cards.b.BilboThiefInTheNight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bilbo, Thief in the Night", 33, Rarity.MYTHIC, mage.cards.b.BilboThiefInTheNight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bothersome Noisemaker", 89, Rarity.UNCOMMON, mage.cards.b.BothersomeNoisemaker.class));
        cards.add(new SetCardInfo("Burn, Burn, Tree and Fern", 90, Rarity.UNCOMMON, mage.cards.b.BurnBurnTreeAndFern.class));
        cards.add(new SetCardInfo("Crude Bent Blade", 63, Rarity.COMMON, mage.cards.c.CrudeBentBlade.class));
        cards.add(new SetCardInfo("Desolation Prowler", 64, Rarity.UNCOMMON, mage.cards.d.DesolationProwler.class));
        cards.add(new SetCardInfo("Desolation of Smaug", 226, Rarity.RARE, mage.cards.d.DesolationOfSmaug.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Desolation of Smaug", 262, Rarity.RARE, mage.cards.d.DesolationOfSmaug.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Desolation of Smaug", 93, Rarity.RARE, mage.cards.d.DesolationOfSmaug.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dori, Bearer of Friends", 94, Rarity.COMMON, mage.cards.d.DoriBearerOfFriends.class));
        cards.add(new SetCardInfo("Duskwatch Hunter", 153, Rarity.COMMON, mage.cards.d.DuskwatchHunter.class));
        cards.add(new SetCardInfo("Dwalin, Weaponmaster", 154, Rarity.RARE, mage.cards.d.DwalinWeaponmaster.class));
        cards.add(new SetCardInfo("Dwarven Mauler", 95, Rarity.UNCOMMON, mage.cards.d.DwarvenMauler.class));
        cards.add(new SetCardInfo("Dwarven Mattock", 172, Rarity.UNCOMMON, mage.cards.d.DwarvenMattock.class));
        cards.add(new SetCardInfo("Elvenking's Halls", 182, Rarity.COMMON, mage.cards.e.ElvenkingsHalls.class));
        cards.add(new SetCardInfo("Fateful Discovery", 220, Rarity.MYTHIC, mage.cards.f.FatefulDiscovery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fateful Discovery", 256, Rarity.MYTHIC, mage.cards.f.FatefulDiscovery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fateful Discovery", 40, Rarity.MYTHIC, mage.cards.f.FatefulDiscovery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fearsome Goblin Pair", 156, Rarity.UNCOMMON, mage.cards.f.FearsomeGoblinPair.class));
        cards.add(new SetCardInfo("Fili the Pathfinder", 14, Rarity.RARE, mage.cards.f.FiliThePathfinder.class));
        cards.add(new SetCardInfo("Forest", 193, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 198, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gathering of Darkness", 68, Rarity.UNCOMMON, mage.cards.g.GatheringOfDarkness.class));
        cards.add(new SetCardInfo("Gleaming Splendor", 275, Rarity.MYTHIC, mage.cards.g.GleamingSplendor.class));
        cards.add(new SetCardInfo("Gigantic Big Bear", 126, Rarity.RARE, mage.cards.g.GiganticBigBear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gigantic Big Bear", 307, Rarity.RARE, mage.cards.g.GiganticBigBear.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gloin the Mighty", 227, Rarity.UNCOMMON, mage.cards.g.GloinTheMighty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gloin the Mighty", 263, Rarity.UNCOMMON, mage.cards.g.GloinTheMighty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gloin the Mighty", 99, Rarity.UNCOMMON, mage.cards.g.GloinTheMighty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Goblin-town", 183, Rarity.COMMON, mage.cards.g.GoblinTown.class));
        cards.add(new SetCardInfo("Great Gilded Boat", 291, Rarity.RARE, mage.cards.g.GreatGildedBoat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Great Gilded Boat", 42, Rarity.RARE, mage.cards.g.GreatGildedBoat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron Hills", 185, Rarity.COMMON, mage.cards.i.IronHills.class));
        cards.add(new SetCardInfo("Iron Hills Blacksmith", 16, Rarity.UNCOMMON, mage.cards.i.IronHillsBlacksmith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron Hills Blacksmith", 216, Rarity.UNCOMMON, mage.cards.i.IronHillsBlacksmith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iron Hills Blacksmith", 252, Rarity.UNCOMMON, mage.cards.i.IronHillsBlacksmith.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 190, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 195, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Lake-town", 186, Rarity.COMMON, mage.cards.l.LakeTown.class));
        cards.add(new SetCardInfo("Lake-town Lookout", 18, Rarity.COMMON, mage.cards.l.LakeTownLookout.class));
        cards.add(new SetCardInfo("Lakeshore Apothecary", 43, Rarity.COMMON, mage.cards.l.LakeshoreApothecary.class));
        cards.add(new SetCardInfo("Large Bear", 159, Rarity.UNCOMMON, mage.cards.l.LargeBear.class));
        cards.add(new SetCardInfo("Long Lake Nuisance", 45, Rarity.COMMON, mage.cards.l.LongLakeNuisance.class));
        cards.add(new SetCardInfo("Long-Bodied Grey Dog", 1, Rarity.COMMON, mage.cards.l.LongBodiedGreyDog.class));
        cards.add(new SetCardInfo("Mirkwood", 188, Rarity.COMMON, mage.cards.m.Mirkwood.class));
        cards.add(new SetCardInfo("Mirkwood Pathmaker", 129, Rarity.UNCOMMON, mage.cards.m.MirkwoodPathmaker.class));
        cards.add(new SetCardInfo("Misty Mountains Raider", 105, Rarity.UNCOMMON, mage.cards.m.MistyMountainsRaider.class));
        cards.add(new SetCardInfo("Mountain", 192, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 197, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("My Precious", 176, Rarity.RARE, mage.cards.m.MyPrecious.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("My Precious", 235, Rarity.RARE, mage.cards.m.MyPrecious.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("My Precious", 271, Rarity.RARE, mage.cards.m.MyPrecious.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oin the Brave", 106, Rarity.COMMON, mage.cards.o.OinTheBrave.class));
        cards.add(new SetCardInfo("Ordinary Bear", 133, Rarity.COMMON, mage.cards.o.OrdinaryBear.class));
        cards.add(new SetCardInfo("Patient Instructor", 162, Rarity.COMMON, mage.cards.p.PatientInstructor.class));
        cards.add(new SetCardInfo("Plains", 189, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 194, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 313, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 314, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 315, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 316, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 317, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 318, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 319, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 320, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Quarrel", 135, Rarity.COMMON, mage.cards.q.Quarrel.class));
        cards.add(new SetCardInfo("Rage into the Valley", 79, Rarity.COMMON, mage.cards.r.RageIntoTheValley.class));
        cards.add(new SetCardInfo("Ravenhill Flock", 52, Rarity.UNCOMMON, mage.cards.r.RavenhillFlock.class));
        cards.add(new SetCardInfo("Riddles in the Dark", 292, Rarity.RARE, mage.cards.r.RiddlesInTheDark.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Riddles in the Dark", 53, Rarity.RARE, mage.cards.r.RiddlesInTheDark.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smaug the Magnificent", 110, Rarity.MYTHIC, mage.cards.s.SmaugTheMagnificent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smaug the Magnificent", 229, Rarity.MYTHIC, mage.cards.s.SmaugTheMagnificent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smaug the Magnificent", 249, Rarity.MYTHIC, mage.cards.s.SmaugTheMagnificent.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Smaug the Magnificent", 265, Rarity.MYTHIC, mage.cards.s.SmaugTheMagnificent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smaug's Fury", 111, Rarity.COMMON, mage.cards.s.SmaugsFury.class));
        cards.add(new SetCardInfo("Sting, Bilbo's Sword", 178, Rarity.RARE, mage.cards.s.StingBilbosSword.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sting, Bilbo's Sword", 237, Rarity.RARE, mage.cards.s.StingBilbosSword.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sting, Bilbo's Sword", 273, Rarity.RARE, mage.cards.s.StingBilbosSword.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 191, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 196, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("The Arkenstone", 170, Rarity.MYTHIC, mage.cards.t.TheArkenstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Arkenstone", 234, Rarity.MYTHIC, mage.cards.t.TheArkenstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Arkenstone", 247, Rarity.MYTHIC, mage.cards.t.TheArkenstone.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("The Arkenstone", 270, Rarity.MYTHIC, mage.cards.t.TheArkenstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Arkenstone", 283, Rarity.MYTHIC, mage.cards.t.TheArkenstone.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("The Chief Warg", 150, Rarity.UNCOMMON, mage.cards.t.TheChiefWarg.class));
        cards.add(new SetCardInfo("The Lonely Mountain", 187, Rarity.RARE, mage.cards.t.TheLonelyMountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Lonely Mountain", 207, Rarity.RARE, mage.cards.t.TheLonelyMountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Lonely Mountain", 248, Rarity.RARE, mage.cards.t.TheLonelyMountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Lonely Mountain", 284, Rarity.RARE, mage.cards.t.TheLonelyMountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Queen of Dale", 217, Rarity.MYTHIC, mage.cards.t.TheQueenOfDale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Queen of Dale", 24, Rarity.MYTHIC, mage.cards.t.TheQueenOfDale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Queen of Dale", 253, Rarity.MYTHIC, mage.cards.t.TheQueenOfDale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thorin Oakenshield", 165, Rarity.UNCOMMON, mage.cards.t.ThorinOakenshield.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thorin Oakenshield", 202, Rarity.UNCOMMON, mage.cards.t.ThorinOakenshield.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thorin, Mountain-king", 114, Rarity.MYTHIC, mage.cards.t.ThorinMountainKing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thorin, Mountain-king", 243, Rarity.MYTHIC, mage.cards.t.ThorinMountainKing.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Thorin, Mountain-king", 279, Rarity.MYTHIC, mage.cards.t.ThorinMountainKing.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Thranduil, Sindarin Liege", 166, Rarity.UNCOMMON, mage.cards.t.ThranduilSindarinLiege.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thranduil, Sindarin Liege", 233, Rarity.UNCOMMON, mage.cards.t.ThranduilSindarinLiege.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thranduil, Sindarin Liege", 269, Rarity.UNCOMMON, mage.cards.t.ThranduilSindarinLiege.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom, Bert, and William", 169, Rarity.RARE, mage.cards.t.TomBertAndWilliam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom, Bert, and William", 312, Rarity.RARE, mage.cards.t.TomBertAndWilliam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Well-Worn Spatula", 180, Rarity.COMMON, mage.cards.w.WellWornSpatula.class));
        cards.add(new SetCardInfo("Wilderland Scrounger", 141, Rarity.UNCOMMON, mage.cards.w.WilderlandScrounger.class));
        cards.add(new SetCardInfo("Wood Elves", 142, Rarity.COMMON, mage.cards.w.WoodElves.class));
    }
}
