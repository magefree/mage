package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class StarTrekCommander extends ExpansionSet {

    private static final StarTrekCommander instance = new StarTrekCommander();

    public static StarTrekCommander getInstance() {
        return instance;
    }

    private StarTrekCommander() {
        super("Star Trek Commander", "TRC", ExpansionSet.buildDate(2026, 11, 13), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Benjamin Sisko, Besieged", 200, Rarity.MYTHIC, mage.cards.b.BenjaminSiskoBesieged.class));
        cards.add(new SetCardInfo("Bio-Asset Allocator", 196, Rarity.RARE, mage.cards.b.BioAssetAllocator.class));
        cards.add(new SetCardInfo("Defense Force Aggressor", 161, Rarity.COMMON, mage.cards.d.DefenseForceAggressor.class));
        cards.add(new SetCardInfo("Kirk, Enterprising Captain", 198, Rarity.MYTHIC, mage.cards.k.KirkEnterprisingCaptain.class));
        cards.add(new SetCardInfo("Klingon Strike Force", 199, Rarity.RARE, mage.cards.k.KlingonStrikeForce.class));
        cards.add(new SetCardInfo("Operations Officer", 192, Rarity.RARE, mage.cards.o.OperationsOfficer.class));
        cards.add(new SetCardInfo("Picard, Steadfast Captain", 193, Rarity.MYTHIC, mage.cards.p.PicardSteadfastCaptain.class));
        cards.add(new SetCardInfo("Sickbay Orderly", 138, Rarity.COMMON, mage.cards.s.SickbayOrderly.class));
        cards.add(new SetCardInfo("Spock, Logical Choice", 194, Rarity.MYTHIC, mage.cards.s.SpockLogicalChoice.class));
        cards.add(new SetCardInfo("Starfleet Crew", 139, Rarity.COMMON, mage.cards.s.StarfleetCrew.class));
        cards.add(new SetCardInfo("Talarian Hook Spider", 175, Rarity.COMMON, mage.cards.t.TalarianHookSpider.class));
        cards.add(new SetCardInfo("Tenacious Tosk", 201, Rarity.RARE, mage.cards.t.TenaciousTosk.class));
        cards.add(new SetCardInfo("Trelane, Squire of Gothos", 195, Rarity.RARE, mage.cards.t.TrelaneSquireOfGothos.class));
        cards.add(new SetCardInfo("Warship Flight Crew", 168, Rarity.COMMON, mage.cards.w.WarshipFlightCrew.class));
        cards.add(new SetCardInfo("Worf, Chief Tactical Officer", 141, Rarity.UNCOMMON, mage.cards.w.WorfChiefTacticalOfficer.class));
    }
}
