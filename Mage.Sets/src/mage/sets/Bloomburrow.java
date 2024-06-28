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

        cards.add(new SetCardInfo("Bria, Riptide Rogue", 379, Rarity.MYTHIC, mage.cards.b.BriaRiptideRogue.class));
        cards.add(new SetCardInfo("Byrke, Long Ear of the Law", 380, Rarity.MYTHIC, mage.cards.b.ByrkeLongEarOfTheLaw.class));
        cards.add(new SetCardInfo("Lilypad Village", 255, Rarity.UNCOMMON, mage.cards.l.LilypadVillage.class));
        cards.add(new SetCardInfo("Lumra, Bellow of the Woods", 183, Rarity.MYTHIC, mage.cards.l.LumraBellowOfTheWoods.class));
        cards.add(new SetCardInfo("Lupinflower Village", 256, Rarity.UNCOMMON, mage.cards.l.LupinflowerVillage.class));
        cards.add(new SetCardInfo("Mabel, Heir to Cragflame", 224, Rarity.RARE, mage.cards.m.MabelHeirToCragflame.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
    }
}
