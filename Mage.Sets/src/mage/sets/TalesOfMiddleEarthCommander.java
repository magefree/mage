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

        cards.add(new SetCardInfo("Ancient Tomb", 357, Rarity.MYTHIC, mage.cards.a.AncientTomb.class));
        cards.add(new SetCardInfo("Bojuka Bog", 358, Rarity.MYTHIC, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Boseiju, Who Shelters All", 359, Rarity.MYTHIC, mage.cards.b.BoseijuWhoSheltersAll.class));
        cards.add(new SetCardInfo("Cavern of Souls", 362, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Cloudstone Curio", 349, Rarity.MYTHIC, mage.cards.c.CloudstoneCurio.class));
        cards.add(new SetCardInfo("Deserted Temple", 363, Rarity.RARE, mage.cards.d.DesertedTemple.class));
        cards.add(new SetCardInfo("Ensnaring Bridge", 350, Rarity.MYTHIC, mage.cards.e.EnsnaringBridge.class));
        cards.add(new SetCardInfo("Homeward Path", 365, Rarity.MYTHIC, mage.cards.h.HomewardPath.class));
        cards.add(new SetCardInfo("Minamo, School at Water's Edge", 369, Rarity.MYTHIC, mage.cards.m.MinamoSchoolAtWatersEdge.class));
        cards.add(new SetCardInfo("Radagast, Wizard of Wilds", 66, Rarity.RARE, mage.cards.r.RadagastWizardOfWilds.class));
        cards.add(new SetCardInfo("Rings of Brighthearth", 352, Rarity.MYTHIC, mage.cards.r.RingsOfBrighthearth.class));
        cards.add(new SetCardInfo("Sam, Loyal Attendant", 7, Rarity.MYTHIC, mage.cards.s.SamLoyalAttendant.class));
        cards.add(new SetCardInfo("Sauron, Lord of the Rings", 4, Rarity.MYTHIC, mage.cards.s.SauronLordOfTheRings.class));
        cards.add(new SetCardInfo("Shadowspear", 353, Rarity.MYTHIC, mage.cards.s.Shadowspear.class));
        cards.add(new SetCardInfo("Sol Ring", 284, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("The Great Henge", 348, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class));
        cards.add(new SetCardInfo("The Ozolith", 351, Rarity.MYTHIC, mage.cards.t.TheOzolith.class));
        cards.add(new SetCardInfo("Wasteland", 376, Rarity.MYTHIC, mage.cards.w.Wasteland.class));
        cards.add(new SetCardInfo("Yavimaya, Cradle of Growth", 337, Rarity.MYTHIC, mage.cards.y.YavimayaCradleOfGrowth.class));
    }
}
