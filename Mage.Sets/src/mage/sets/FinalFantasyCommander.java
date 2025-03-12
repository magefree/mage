package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class FinalFantasyCommander extends ExpansionSet {

    private static final FinalFantasyCommander instance = new FinalFantasyCommander();

    public static FinalFantasyCommander getInstance() {
        return instance;
    }

    private FinalFantasyCommander() {
        super("Final Fantasy Commander", "FIC", ExpansionSet.buildDate(2025, 6, 13), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 2, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 168, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 202, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cloud, Ex-SOLDIER", 221, Rarity.MYTHIC, mage.cards.c.CloudExSOLDIER.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terra, Herald of Hope", 4, Rarity.MYTHIC, mage.cards.t.TerraHeraldOfHope.class));
    }
}
