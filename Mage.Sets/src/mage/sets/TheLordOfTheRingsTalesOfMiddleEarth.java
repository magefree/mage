package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class TheLordOfTheRingsTalesOfMiddleEarth extends ExpansionSet {

    private static final TheLordOfTheRingsTalesOfMiddleEarth instance = new TheLordOfTheRingsTalesOfMiddleEarth();

    public static TheLordOfTheRingsTalesOfMiddleEarth getInstance() {
        return instance;
    }

    private TheLordOfTheRingsTalesOfMiddleEarth() {
        super("The Lord of the Rings: Tales of Middle-earth", "LTR", ExpansionSet.buildDate(2023, 6, 23), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "The Lord of the Rings: Tales of Middle-earth";
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Aragorn and Arwen, Wed", 287, Rarity.MYTHIC, mage.cards.a.AragornAndArwenWed.class));
        cards.add(new SetCardInfo("Bilbo, Retired Burglar", 403, Rarity.UNCOMMON, mage.cards.b.BilboRetiredBurglar.class));
        cards.add(new SetCardInfo("Call of the Ring", 79, Rarity.RARE, mage.cards.c.CallOfTheRing.class));
        cards.add(new SetCardInfo("Forest", 280, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Frodo Baggins", 404, Rarity.UNCOMMON, mage.cards.f.FrodoBaggins.class));
        cards.add(new SetCardInfo("Frodo, Sauron's Bane", 18, Rarity.RARE, mage.cards.f.FrodoSauronsBane.class));
        cards.add(new SetCardInfo("Gandalf the Grey", 207, Rarity.RARE, mage.cards.g.GandalfTheGrey.class));
        cards.add(new SetCardInfo("Gollum, Patient Plotter", 84, Rarity.UNCOMMON, mage.cards.g.GollumPatientPlotter.class));
        cards.add(new SetCardInfo("Island", 274, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Lobelia Sackville-Baggins", 399, Rarity.RARE, mage.cards.l.LobeliaSackvilleBaggins.class));
        cards.add(new SetCardInfo("Mount Doom", 258, Rarity.MYTHIC, mage.cards.m.MountDoom.class));
        cards.add(new SetCardInfo("Mountain", 278, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 272, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Reprieve", 26, Rarity.UNCOMMON, mage.cards.r.Reprieve.class));
        cards.add(new SetCardInfo("Samwise the Stouthearted", 28, Rarity.UNCOMMON, mage.cards.s.SamwiseTheStouthearted.class));
        cards.add(new SetCardInfo("Sauron, the Lidless Eye", 288, Rarity.MYTHIC, mage.cards.s.SauronTheLidlessEye.class));
        cards.add(new SetCardInfo("Sauron, the Necromancer", 106, Rarity.RARE, mage.cards.s.SauronTheNecromancer.class));
        cards.add(new SetCardInfo("Swamp", 276, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("The One Ring", 246, Rarity.MYTHIC, mage.cards.t.TheOneRing.class));
        cards.add(new SetCardInfo("The Shire", 260, Rarity.RARE, mage.cards.t.TheShire.class));
        cards.add(new SetCardInfo("Tom Bombadil", 234, Rarity.MYTHIC, mage.cards.t.TomBombadil.class));
        cards.add(new SetCardInfo("Trailblazer's Boots", 398, Rarity.RARE, mage.cards.t.TrailblazersBoots.class));
        cards.add(new SetCardInfo("Wizard's Rockets", 400, Rarity.COMMON, mage.cards.w.WizardsRockets.class));
        cards.add(new SetCardInfo("You Cannot Pass!", 38, Rarity.UNCOMMON, mage.cards.y.YouCannotPass.class));
    }
}
