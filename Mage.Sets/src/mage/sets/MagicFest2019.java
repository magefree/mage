package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class MagicFest2019 extends ExpansionSet {

    private static final MagicFest2019 instance = new MagicFest2019();

    public static MagicFest2019 getInstance() {
        return instance;
    }

    private MagicFest2019() {
        super("MagicFest 2019", "PF19", ExpansionSet.buildDate(2019, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Forest", 6, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 3, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Lightning Bolt", 1, Rarity.RARE, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Mountain", 5, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Plains", 2, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Sol Ring", 7, Rarity.RARE, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Swamp", 4, Rarity.LAND, mage.cards.basiclands.Swamp.class));
    }
}
