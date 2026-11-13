package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class NauctisTheSunkenRealmCommander extends ExpansionSet {

    private static final NauctisTheSunkenRealmCommander instance = new NauctisTheSunkenRealmCommander();

    public static NauctisTheSunkenRealmCommander getInstance() {
        return instance;
    }

    private NauctisTheSunkenRealmCommander() {
        super("Nauctis: The Sunken Realm Commander", "NAC", ExpansionSet.buildDate(2027, 2, 5), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Crucible of Worlds", 345, Rarity.MYTHIC, mage.cards.c.CrucibleOfWorlds.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crucible of Worlds", 365, Rarity.MYTHIC, mage.cards.c.CrucibleOfWorlds.class, NON_FULL_USE_VARIOUS));
    }
}
