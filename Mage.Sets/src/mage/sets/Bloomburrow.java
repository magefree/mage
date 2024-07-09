package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Bloomburrow extends ExpansionSet {

    private static final Bloomburrow instance = new Bloomburrow();

    public static Bloomburrow getInstance() {
        return instance;
    }

    private Bloomburrow() {
        super("Bloomburrow", "BLB", ExpansionSet.buildDate(2024, 8, 2), SetType.EXPANSION);
        this.blockName = "Bloomburrow"; // for sorting in GUI
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Brave-Kin Duo", 3, Rarity.COMMON, mage.cards.b.BraveKinDuo.class));
        cards.add(new SetCardInfo("Bria, Riptide Rogue", 379, Rarity.MYTHIC, mage.cards.b.BriaRiptideRogue.class));
        cards.add(new SetCardInfo("Byrke, Long Ear of the Law", 380, Rarity.MYTHIC, mage.cards.b.ByrkeLongEarOfTheLaw.class));
        cards.add(new SetCardInfo("Carrot Cake", 7, Rarity.COMMON, mage.cards.c.CarrotCake.class));
        cards.add(new SetCardInfo("Early Winter", 93, Rarity.COMMON, mage.cards.e.EarlyWinter.class));
        cards.add(new SetCardInfo("Fell", 95, Rarity.UNCOMMON, mage.cards.f.Fell.class));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Head of the Homestead", 216, Rarity.COMMON, mage.cards.h.HeadOfTheHomestead.class));
        cards.add(new SetCardInfo("Hop to It", 16, Rarity.UNCOMMON, mage.cards.h.HopToIt.class));
        cards.add(new SetCardInfo("Hugs, Grisly Guardian", 218, Rarity.MYTHIC, mage.cards.h.HugsGrislyGuardian.class));
        cards.add(new SetCardInfo("Island", 266, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Lilypad Village", 255, Rarity.UNCOMMON, mage.cards.l.LilypadVillage.class));
        cards.add(new SetCardInfo("Lumra, Bellow of the Woods", 183, Rarity.MYTHIC, mage.cards.l.LumraBellowOfTheWoods.class));
        cards.add(new SetCardInfo("Lupinflower Village", 256, Rarity.UNCOMMON, mage.cards.l.LupinflowerVillage.class));
        cards.add(new SetCardInfo("Mabel, Heir to Cragflame", 224, Rarity.RARE, mage.cards.m.MabelHeirToCragflame.class));
        cards.add(new SetCardInfo("Might of the Meek", 144, Rarity.COMMON, mage.cards.m.MightOfTheMeek.class));
        cards.add(new SetCardInfo("Moonrise Cleric", 226, Rarity.COMMON, mage.cards.m.MoonriseCleric.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mudflat Village", 257, Rarity.UNCOMMON, mage.cards.m.MudflatVillage.class));
        cards.add(new SetCardInfo("Oakhollow Village", 258, Rarity.UNCOMMON, mage.cards.o.OakhollowVillage.class));
        cards.add(new SetCardInfo("Pearl of Wisdom", 64, Rarity.COMMON, mage.cards.p.PearlOfWisdom.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Pond Prophet", 229, Rarity.COMMON, mage.cards.p.PondProphet.class));
        cards.add(new SetCardInfo("Repel Calamity", 27, Rarity.UNCOMMON, mage.cards.r.RepelCalamity.class));
        cards.add(new SetCardInfo("Rockface Village", 259, Rarity.UNCOMMON, mage.cards.r.RockfaceVillage.class));
        cards.add(new SetCardInfo("Run Away Together", 67, Rarity.COMMON, mage.cards.r.RunAwayTogether.class));
        cards.add(new SetCardInfo("Salvation Swan", 28, Rarity.RARE, mage.cards.s.SalvationSwan.class));
        cards.add(new SetCardInfo("Seedglaive Mentor", 231, Rarity.UNCOMMON, mage.cards.s.SeedglaiveMentor.class));
        cards.add(new SetCardInfo("Shrike Force", 31, Rarity.UNCOMMON, mage.cards.s.ShrikeForce.class));
        cards.add(new SetCardInfo("Sunshower Druid", 195, Rarity.COMMON, mage.cards.s.SunshowerDruid.class));
        cards.add(new SetCardInfo("Swamp", 270, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Tempest Angler", 235, Rarity.COMMON, mage.cards.t.TempestAngler.class));
    }
}
