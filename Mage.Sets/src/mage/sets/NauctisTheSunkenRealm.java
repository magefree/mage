package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class NauctisTheSunkenRealm extends ExpansionSet {

    private static final NauctisTheSunkenRealm instance = new NauctisTheSunkenRealm();

    public static NauctisTheSunkenRealm getInstance() {
        return instance;
    }

    private NauctisTheSunkenRealm() {
        super("Nauctis: The Sunken Realm", "NAU", ExpansionSet.buildDate(2027, 2, 5), SetType.EXPANSION);
        this.blockName = "Nauctis: The Sunken Realm"; // for sorting in GUI
        this.hasBasicLands = false; // TODO: Once spoiled, add and change

        // this.enablePlayBooster(401); // TODO: Enable once booster details are available

        cards.add(new SetCardInfo("Ring of Unity", 238, Rarity.MYTHIC, mage.cards.r.RingOfUnity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ring of Unity", 317, Rarity.MYTHIC, mage.cards.r.RingOfUnity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ring of Unity", 360, Rarity.MYTHIC, mage.cards.r.RingOfUnity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ring of Unity", 376, Rarity.MYTHIC, mage.cards.r.RingOfUnity.class, NON_FULL_USE_VARIOUS));
    }
}
