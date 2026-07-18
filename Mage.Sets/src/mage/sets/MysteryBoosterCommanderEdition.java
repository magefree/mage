package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */

public final class MysteryBoosterCommanderEdition extends ExpansionSet {

    private static final MysteryBoosterCommanderEdition instance = new MysteryBoosterCommanderEdition();

    public static MysteryBoosterCommanderEdition getInstance() {
        return instance;
    }

    private MysteryBoosterCommanderEdition() {
        super("Mystery Booster: Commander Edition", "MBC", ExpansionSet.buildDate(2027, 10, 1), SetType.SUPPLEMENTAL);
        this.blockName = "Mystery Booster Commander Edition";

        // this.enableSetBooster(Integer.MAX_VALUE);

        cards.add(new SetCardInfo("Joven and Chandler", 24, Rarity.RARE, mage.cards.j.JovenAndChandler.class));
        // cards.add(new SetCardInfo("Tolabow, Loch Rascal", 12, Rarity.RARE, mage.cards.t.TolabowLochRascal.class));
    }
}
