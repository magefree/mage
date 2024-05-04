package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ModernHorizons3Commander extends ExpansionSet {

    private static final ModernHorizons3Commander instance = new ModernHorizons3Commander();

    public static ModernHorizons3Commander getInstance() {
        return instance;
    }

    private ModernHorizons3Commander() {
        super("Modern Horizons 3 Commander", "M3C", ExpansionSet.buildDate(2024, 6, 7), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Final Act", 52, Rarity.RARE, mage.cards.f.FinalAct.class));
        cards.add(new SetCardInfo("March from Velis Vel", 48, Rarity.RARE, mage.cards.m.MarchFromVelisVel.class));
        cards.add(new SetCardInfo("Siege-Gang Lieutenant", 61, Rarity.RARE, mage.cards.s.SiegeGangLieutenant.class));
    }
}
