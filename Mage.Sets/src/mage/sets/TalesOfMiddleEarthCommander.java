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
        cards.add(new SetCardInfo("Banishing Light", 161, Rarity.UNCOMMON, mage.cards.b.BanishingLight.class));
        cards.add(new SetCardInfo("Bojuka Bog", 358, Rarity.MYTHIC, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Boseiju, Who Shelters All", 359, Rarity.MYTHIC, mage.cards.b.BoseijuWhoSheltersAll.class));
        cards.add(new SetCardInfo("Cabal Coffers", 360, Rarity.MYTHIC, mage.cards.c.CabalCoffers.class));
        cards.add(new SetCardInfo("Castle Ardenvale", 361, Rarity.MYTHIC, mage.cards.c.CastleArdenvale.class));
        cards.add(new SetCardInfo("Cavern of Souls", 362, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Cloudstone Curio", 349, Rarity.MYTHIC, mage.cards.c.CloudstoneCurio.class));
        cards.add(new SetCardInfo("Deserted Temple", 363, Rarity.MYTHIC, mage.cards.d.DesertedTemple.class));
        cards.add(new SetCardInfo("Ensnaring Bridge", 350, Rarity.MYTHIC, mage.cards.e.EnsnaringBridge.class));
        cards.add(new SetCardInfo("Evolving Wilds", 306, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Field of Ruin", 308, Rarity.UNCOMMON, mage.cards.f.FieldOfRuin.class));
        cards.add(new SetCardInfo("Gemstone Caverns", 364, Rarity.MYTHIC, mage.cards.g.GemstoneCaverns.class));
        cards.add(new SetCardInfo("Graypelt Refuge", 316, Rarity.UNCOMMON, mage.cards.g.GraypeltRefuge.class));
        cards.add(new SetCardInfo("Homeward Path", 365, Rarity.MYTHIC, mage.cards.h.HomewardPath.class));
        cards.add(new SetCardInfo("Horizon Canopy", 366, Rarity.MYTHIC, mage.cards.h.HorizonCanopy.class));
        cards.add(new SetCardInfo("Karakas", 367, Rarity.MYTHIC, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Kor Haven", 368, Rarity.MYTHIC, mage.cards.k.KorHaven.class));
        cards.add(new SetCardInfo("Minamo, School at Water's Edge", 369, Rarity.MYTHIC, mage.cards.m.MinamoSchoolAtWatersEdge.class));
        cards.add(new SetCardInfo("Mind Stone", 282, Rarity.UNCOMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mouth of Ronom", 370, Rarity.MYTHIC, mage.cards.m.MouthOfRonom.class));
        cards.add(new SetCardInfo("Oboro, Palace in the Clouds", 371, Rarity.MYTHIC, mage.cards.o.OboroPalaceInTheClouds.class));
        cards.add(new SetCardInfo("Pillar of the Paruns", 372, Rarity.MYTHIC, mage.cards.p.PillarOfTheParuns.class));
        cards.add(new SetCardInfo("Radagast, Wizard of Wilds", 66, Rarity.RARE, mage.cards.r.RadagastWizardOfWilds.class));
        cards.add(new SetCardInfo("Reflecting Pool", 373, Rarity.MYTHIC, mage.cards.r.ReflectingPool.class));
        cards.add(new SetCardInfo("Rings of Brighthearth", 352, Rarity.MYTHIC, mage.cards.r.RingsOfBrighthearth.class));
        cards.add(new SetCardInfo("Sam, Loyal Attendant", 7, Rarity.MYTHIC, mage.cards.s.SamLoyalAttendant.class));
        cards.add(new SetCardInfo("Sauron, Lord of the Rings", 4, Rarity.MYTHIC, mage.cards.s.SauronLordOfTheRings.class));
        cards.add(new SetCardInfo("Shadowspear", 353, Rarity.MYTHIC, mage.cards.s.Shadowspear.class));
        cards.add(new SetCardInfo("Shinka, the Bloodsoaked Keep", 374, Rarity.MYTHIC, mage.cards.s.ShinkaTheBloodsoakedKeep.class));
        cards.add(new SetCardInfo("Sol Ring", 284, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Sword of Hearth and Home", 354, Rarity.MYTHIC, mage.cards.s.SwordOfHearthAndHome.class));
        cards.add(new SetCardInfo("Sword of the Animist", 355, Rarity.MYTHIC, mage.cards.s.SwordOfTheAnimist.class));
        cards.add(new SetCardInfo("The Great Henge", 348, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class));
        cards.add(new SetCardInfo("The Ozolith", 351, Rarity.MYTHIC, mage.cards.t.TheOzolith.class));
        cards.add(new SetCardInfo("Thorn of Amethyst", 356, Rarity.MYTHIC, mage.cards.t.ThornOfAmethyst.class));
        cards.add(new SetCardInfo("Urborg, Tomb of Yawgmoth", 375, Rarity.MYTHIC, mage.cards.u.UrborgTombOfYawgmoth.class));
        cards.add(new SetCardInfo("Wasteland", 376, Rarity.MYTHIC, mage.cards.w.Wasteland.class));
        cards.add(new SetCardInfo("Yavimaya, Cradle of Growth", 377, Rarity.MYTHIC, mage.cards.y.YavimayaCradleOfGrowth.class));
    }
}
