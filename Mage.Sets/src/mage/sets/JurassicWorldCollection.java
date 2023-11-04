
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class JurassicWorldCollection extends ExpansionSet {

    private static final JurassicWorldCollection instance = new JurassicWorldCollection();

    public static JurassicWorldCollection getInstance() {
        return instance;
    }

    private JurassicWorldCollection() {
        super("Jurassic World Collection", "REX", ExpansionSet.buildDate(2023, 11, 17), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Command Tower", 26, Rarity.COMMON, mage.cards.c.CommandTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Command Tower", "26b", Rarity.COMMON, mage.cards.c.CommandTower.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 25, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", "25b", Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 22, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", "22b", Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 24, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "24b", Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 21, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", "21b", Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 23, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", "23b", Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
    }
}
