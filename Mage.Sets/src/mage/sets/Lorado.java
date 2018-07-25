package mage.sets;


import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author EikePace
 */
public final class Lorado extends ExpansionSet {

    private static final Lorado instance = new Lorado();

    public static Lorado getInstance() {
        return instance;
    }

    private Lorado() {
        super("Lorado", "LDO", ExpansionSet.buildDate(2018, 4, 16), SetType.CUSTOM_SET);
        this.blockName = "Lorado";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Angelic Regulator", 1, Rarity.RARE, mage.cards.a.AngelicRegulator.class));
        cards.add(new SetCardInfo("Arrest", 2, Rarity.UNCOMMON, mage.cards.a.Arrest.class));
        cards.add(new SetCardInfo("Back in the Saddle", 3, Rarity.UNCOMMON, mage.cards.b.BackInTheSaddle.class));
        cards.add(new SetCardInfo("Cattle Drive", 4, Rarity.COMMON, mage.cards.c.CattleDrive.class));
        cards.add(new SetCardInfo("Drag Behind", 5, Rarity.RARE, mage.cards.d.DragBehind.class));
        cards.add(new SetCardInfo("Empty Night", 6, Rarity.RARE, mage.cards.e.EmptyNight.class));
    }


}