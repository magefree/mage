package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class LordOfTheRingsTalesOfMiddleEarthCommander extends ExpansionSet {

    private static final LordOfTheRingsTalesOfMiddleEarthCommander instance = new LordOfTheRingsTalesOfMiddleEarthCommander();

    public static LordOfTheRingsTalesOfMiddleEarthCommander getInstance() {
        return instance;
    }

    private LordOfTheRingsTalesOfMiddleEarthCommander() {
        super("Lord of the Rings: Tales of Middle-Earth Commander", "LTC", ExpansionSet.buildDate(2023, 6, 23), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bridge of Khazad-dum", 350, Rarity.MYTHIC, mage.cards.e.EnsnaringBridge.class));
        cards.add(new SetCardInfo("Sol Ring", 284, Rarity.UNCOMMON, mage.cards.s.SolRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sol Ring", 408, Rarity.MYTHIC, mage.cards.s.SolRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sol Ring", 409, Rarity.MYTHIC, mage.cards.s.SolRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sol Ring", 410, Rarity.MYTHIC, mage.cards.s.SolRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Party Tree", 348, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class));
        cards.add(new SetCardInfo("Valley of Gorgoroth", 376, Rarity.MYTHIC, mage.cards.w.Wasteland.class));
    }
}
