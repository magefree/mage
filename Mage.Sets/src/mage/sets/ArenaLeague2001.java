package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pal01
 */
public class ArenaLeague2001 extends ExpansionSet {

    private static final ArenaLeague2001 instance = new ArenaLeague2001();

    public static ArenaLeague2001 getInstance() {
        return instance;
    }

    private ArenaLeague2001() {
        super("Arena League 2001", "PAL01", ExpansionSet.buildDate(2001, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Creeping Mold", 2, Rarity.RARE, mage.cards.c.CreepingMold.class));
        cards.add(new SetCardInfo("Diabolic Edict", 10, Rarity.RARE, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Dismiss", 4, Rarity.RARE, mage.cards.d.Dismiss.class));
        cards.add(new SetCardInfo("Empyrial Armor", 8, Rarity.RARE, mage.cards.e.EmpyrialArmor.class));
        cards.add(new SetCardInfo("Fling", 6, Rarity.RARE, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Forest", 1, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 11, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Blessing", 12, Rarity.RARE, mage.cards.g.GaeasBlessing.class));
        cards.add(new SetCardInfo("Island", 3, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mountain", 5, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Plains", 7, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Swamp", 9, Rarity.LAND, mage.cards.basiclands.Swamp.class));
     }
}
