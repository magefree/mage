package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

public final class TalesOfMiddleEarthCommander extends ExpansionSet {

    private static final TalesOfMiddleEarthCommander instance = new TalesOfMiddleEarthCommander();

    public static TalesOfMiddleEarthCommander getInstance() {
        return instance;
    }

    private TalesOfMiddleEarthCommander() {
        super("Tales of Middle-earth Commander", "LTC", ExpansionSet.buildDate(2023, 6, 23), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ensnaring Bridge", 350, Rarity.MYTHIC, mage.cards.e.EnsnaringBridge.class));
        cards.add(new SetCardInfo("Radagast, Wizard of Wilds", 66, Rarity.RARE, mage.cards.r.RadagastWizardOfWilds.class));
        cards.add(new SetCardInfo("Sam, Loyal Attendant", 7, Rarity.MYTHIC, mage.cards.s.SamLoyalAttendant.class));
        cards.add(new SetCardInfo("Sol Ring", 284, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("The Great Henge", 348, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class));
        cards.add(new SetCardInfo("Wasteland", 376, Rarity.MYTHIC, mage.cards.w.Wasteland.class));
    }
}
