package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Warhammer40000 extends ExpansionSet {

    private static final Warhammer40000 instance = new Warhammer40000();

    public static Warhammer40000 getInstance() {
        return instance;
    }

    private Warhammer40000() {
        super("Warhammer 40,000", "40K", ExpansionSet.buildDate(2022, 4, 29), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Blood for the Blood God!", 108, Rarity.COMMON, mage.cards.b.BloodForTheBloodGod.class));
        cards.add(new SetCardInfo("Fabricate", 181, Rarity.RARE, mage.cards.f.Fabricate.class));
    }
}
