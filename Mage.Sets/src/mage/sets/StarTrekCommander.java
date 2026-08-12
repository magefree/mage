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

        cards.add(new SetCardInfo("Badgey, Malicious Glitch", 152, Rarity.UNCOMMON, mage.cards.b.BadgeyMaliciousGlitch.class));
        cards.add(new SetCardInfo("Benjamin Sisko, Besieged", 200, Rarity.MYTHIC, mage.cards.b.BenjaminSiskoBesieged.class));
        cards.add(new SetCardInfo("Bio-Asset Allocator", 196, Rarity.RARE, mage.cards.b.BioAssetAllocator.class));
        cards.add(new SetCardInfo("Ceti Eel", 153, Rarity.COMMON, mage.cards.c.CetiEel.class));
        cards.add(new SetCardInfo("Cryogenic Stasis", 142, Rarity.COMMON, mage.cards.c.CryogenicStasis.class));
        cards.add(new SetCardInfo("Defense Force Aggressor", 161, Rarity.COMMON, mage.cards.d.DefenseForceAggressor.class));
        cards.add(new SetCardInfo("Direct Hit", 162, Rarity.COMMON, mage.cards.d.DirectHit.class));
        cards.add(new SetCardInfo("Disruptor Pistol", 177, Rarity.COMMON, mage.cards.d.DisruptorPistol.class));
        cards.add(new SetCardInfo("Evasive Maneuvers", 132, Rarity.COMMON, mage.cards.e.EvasiveManeuvers.class));
        cards.add(new SetCardInfo("Exocomp", 145, Rarity.COMMON, mage.cards.e.Exocomp.class));
        cards.add(new SetCardInfo("Gin'tak Charge", 163, Rarity.COMMON, mage.cards.g.GintakCharge.class));
        cards.add(new SetCardInfo("Gorn Captain", 172, Rarity.COMMON, mage.cards.g.GornCaptain.class));
        cards.add(new SetCardInfo("Gumato", 173, Rarity.UNCOMMON, mage.cards.g.Gumato.class));
        cards.add(new SetCardInfo("Head of Security", 133, Rarity.COMMON, mage.cards.h.HeadOfSecurity.class));
        cards.add(new SetCardInfo("Kirk, Enterprising Captain", 198, Rarity.MYTHIC, mage.cards.k.KirkEnterprisingCaptain.class));
        cards.add(new SetCardInfo("Klingon Strike Force", 199, Rarity.RARE, mage.cards.k.KlingonStrikeForce.class));
        cards.add(new SetCardInfo("Kolinahr Priest", 146, Rarity.COMMON, mage.cards.k.KolinahrPriest.class));
        cards.add(new SetCardInfo("Kruge, Genesis Seeker", 165, Rarity.UNCOMMON, mage.cards.k.KrugeGenesisSeeker.class));
        cards.add(new SetCardInfo("La Forge, Perceptive Engineer", 147, Rarity.UNCOMMON, mage.cards.l.LaForgePerceptiveEngineer.class));
        cards.add(new SetCardInfo("Last Gasp", 580, Rarity.COMMON, mage.cards.l.LastGasp.class));
        cards.add(new SetCardInfo("Mek'leth Berserker", 166, Rarity.COMMON, mage.cards.m.MeklethBerserker.class));
        cards.add(new SetCardInfo("Operations Officer", 192, Rarity.RARE, mage.cards.o.OperationsOfficer.class));
        cards.add(new SetCardInfo("Pelia, Immortal Innovator", 148, Rarity.UNCOMMON, mage.cards.p.PeliaImmortalInnovator.class));
        cards.add(new SetCardInfo("Picard, Steadfast Captain", 193, Rarity.MYTHIC, mage.cards.p.PicardSteadfastCaptain.class));
        cards.add(new SetCardInfo("Refute", 579, Rarity.COMMON, mage.cards.r.Refute.class));
        cards.add(new SetCardInfo("Run Amok", 582, Rarity.COMMON, mage.cards.r.RunAmok.class));
        cards.add(new SetCardInfo("Shuttle Crew", 137, Rarity.UNCOMMON, mage.cards.s.ShuttleCrew.class));
        cards.add(new SetCardInfo("Sickbay Orderly", 138, Rarity.COMMON, mage.cards.s.SickbayOrderly.class));
        cards.add(new SetCardInfo("Spock, Logical Choice", 194, Rarity.MYTHIC, mage.cards.s.SpockLogicalChoice.class));
        cards.add(new SetCardInfo("Starfleet Crew", 139, Rarity.COMMON, mage.cards.s.StarfleetCrew.class));
        cards.add(new SetCardInfo("T'Pol, Vulcan Representative", 151, Rarity.UNCOMMON, mage.cards.t.TPolVulcanRepresentative.class));
        cards.add(new SetCardInfo("Talarian Hook Spider", 175, Rarity.COMMON, mage.cards.t.TalarianHookSpider.class));
        cards.add(new SetCardInfo("Tenacious Tosk", 201, Rarity.RARE, mage.cards.t.TenaciousTosk.class));
        cards.add(new SetCardInfo("Trelane, Squire of Gothos", 195, Rarity.RARE, mage.cards.t.TrelaneSquireOfGothos.class));
        cards.add(new SetCardInfo("Warship Flight Crew", 168, Rarity.COMMON, mage.cards.w.WarshipFlightCrew.class));
        cards.add(new SetCardInfo("Will Riker, Assuming Command", 140, Rarity.UNCOMMON, mage.cards.w.WillRikerAssumingCommand.class));
        cards.add(new SetCardInfo("Worf, Chief Tactical Officer", 141, Rarity.UNCOMMON, mage.cards.w.WorfChiefTacticalOfficer.class));
    }
}
