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

        // this.enablePlayBooster(198); TODO: Enable later

        cards.add(new SetCardInfo("An Unexpected Party", 29, Rarity.RARE, mage.cards.a.AnUnexpectedParty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("An Unexpected Party", 289, Rarity.RARE, mage.cards.a.AnUnexpectedParty.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bilbo, Luckwearer", 32, Rarity.UNCOMMON, mage.cards.b.BilboLuckwearer.class));
        cards.add(new SetCardInfo("Bilbo, Thief in the Night", 219, Rarity.MYTHIC, mage.cards.b.BilboThiefInTheNight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bilbo, Thief in the Night", 255, Rarity.MYTHIC, mage.cards.b.BilboThiefInTheNight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bilbo, Thief in the Night", 33, Rarity.MYTHIC, mage.cards.b.BilboThiefInTheNight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 198, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART));
        cards.add(new SetCardInfo("Island", 195, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART));
        cards.add(new SetCardInfo("Mountain", 197, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART));
        cards.add(new SetCardInfo("My Precious", 176, Rarity.RARE, mage.cards.m.MyPrecious.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("My Precious", 235, Rarity.RARE, mage.cards.m.MyPrecious.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("My Precious", 271, Rarity.RARE, mage.cards.m.MyPrecious.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 194, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 313, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 314, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 315, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 316, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 317, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 318, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 319, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 320, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Riddles in the Dark", 292, Rarity.RARE, mage.cards.r.RiddlesInTheDark.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Riddles in the Dark", 53, Rarity.RARE, mage.cards.r.RiddlesInTheDark.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smaug the Magnificent", 110, Rarity.MYTHIC, mage.cards.s.SmaugTheMagnificent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smaug the Magnificent", 229, Rarity.MYTHIC, mage.cards.s.SmaugTheMagnificent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Smaug the Magnificent", 249, Rarity.MYTHIC, mage.cards.s.SmaugTheMagnificent.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Smaug the Magnificent", 265, Rarity.MYTHIC, mage.cards.s.SmaugTheMagnificent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 196, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART));
        cards.add(new SetCardInfo("The Arkenstone", 170, Rarity.MYTHIC, mage.cards.t.TheArkenstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Arkenstone", 234, Rarity.MYTHIC, mage.cards.t.TheArkenstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Arkenstone", 247, Rarity.MYTHIC, mage.cards.t.TheArkenstone.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("The Arkenstone", 270, Rarity.MYTHIC, mage.cards.t.TheArkenstone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Arkenstone", 283, Rarity.MYTHIC, mage.cards.t.TheArkenstone.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom, Bert, and William", 169, Rarity.RARE, mage.cards.t.TomBertAndWilliam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom, Bert, and William", 312, Rarity.RARE, mage.cards.t.TomBertAndWilliam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wood Elves", 142, Rarity.COMMON, mage.cards.w.WoodElves.class));
    }
}
