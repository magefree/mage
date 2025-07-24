package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelsSpiderMan extends ExpansionSet {

    private static final MarvelsSpiderMan instance = new MarvelsSpiderMan();

    public static MarvelsSpiderMan getInstance() {
        return instance;
    }

    private MarvelsSpiderMan() {
        super("Marvel's Spider-Man", "SPM", ExpansionSet.buildDate(2025, 9, 26), SetType.EXPANSION);
        this.blockName = "Marvel's Spider-Man"; // for sorting in GUI
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Aunt May", 3, Rarity.UNCOMMON, mage.cards.a.AuntMay.class));
        cards.add(new SetCardInfo("Daily Bugle Reporters", 6, Rarity.COMMON, mage.cards.d.DailyBugleReporters.class));
        cards.add(new SetCardInfo("Doc Ock's Henchmen", 30, Rarity.COMMON, mage.cards.d.DocOcksHenchmen.class));
        cards.add(new SetCardInfo("Eerie Gravestone", 163, Rarity.COMMON, mage.cards.e.EerieGravestone.class));
        cards.add(new SetCardInfo("Flying Octobot", 31, Rarity.UNCOMMON, mage.cards.f.FlyingOctobot.class));
        cards.add(new SetCardInfo("Forest", 198, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guy in the Chair", 102, Rarity.COMMON, mage.cards.g.GuyInTheChair.class));
        cards.add(new SetCardInfo("Island", 195, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kapow!", 103, Rarity.COMMON, mage.cards.k.Kapow.class));
        cards.add(new SetCardInfo("Kraven's Cats", 104, Rarity.COMMON, mage.cards.k.KravensCats.class));
        cards.add(new SetCardInfo("Lurking Lizards", 107, Rarity.COMMON, mage.cards.l.LurkingLizards.class));
        cards.add(new SetCardInfo("Masked Meower", 82, Rarity.COMMON, mage.cards.m.MaskedMeower.class));
        cards.add(new SetCardInfo("Merciless Enforcers", 58, Rarity.COMMON, mage.cards.m.MercilessEnforcers.class));
        cards.add(new SetCardInfo("Mountain", 197, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Origin of Spider-Man", 9, Rarity.RARE, mage.cards.o.OriginOfSpiderMan.class));
        cards.add(new SetCardInfo("Oscorp Research Team", 40, Rarity.COMMON, mage.cards.o.OscorpResearchTeam.class));
        cards.add(new SetCardInfo("Plains", 194, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Risky Research", 62, Rarity.COMMON, mage.cards.r.RiskyResearch.class));
        cards.add(new SetCardInfo("Romantic Rendezvous", 86, Rarity.COMMON, mage.cards.r.RomanticRendezvous.class));
        cards.add(new SetCardInfo("Scorpion's Sting", 65, Rarity.COMMON, mage.cards.s.ScorpionsSting.class));
        cards.add(new SetCardInfo("Scorpion, Seething Striker", 64, Rarity.UNCOMMON, mage.cards.s.ScorpionSeethingStriker.class));
        cards.add(new SetCardInfo("Scout the City", 113, Rarity.COMMON, mage.cards.s.ScoutTheCity.class));
        cards.add(new SetCardInfo("Selfless Police Captain", 12, Rarity.COMMON, mage.cards.s.SelflessPoliceCaptain.class));
        cards.add(new SetCardInfo("Shock", 88, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Spectacular Tactics", 15, Rarity.COMMON, mage.cards.s.SpectacularTactics.class));
        cards.add(new SetCardInfo("Spider-Bot", 173, Rarity.COMMON, mage.cards.s.SpiderBot.class));
        cards.add(new SetCardInfo("Spider-Byte, Web Warden", 44, Rarity.UNCOMMON, mage.cards.s.SpiderByteWebWarden.class));
        cards.add(new SetCardInfo("Spider-Gwen, Free Spirit", 90, Rarity.COMMON, mage.cards.s.SpiderGwenFreeSpirit.class));
        cards.add(new SetCardInfo("Spider-Ham, Peter Porker", 114, Rarity.RARE, mage.cards.s.SpiderHamPeterPorker.class));
        cards.add(new SetCardInfo("Spider-Rex, Daring Dino", 116, Rarity.COMMON, mage.cards.s.SpiderRexDaringDino.class));
        cards.add(new SetCardInfo("Starling, Aerial Ally", 18, Rarity.COMMON, mage.cards.s.StarlingAerialAlly.class));
        cards.add(new SetCardInfo("Swamp", 196, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Taxi Driver", 97, Rarity.COMMON, mage.cards.t.TaxiDriver.class));
        cards.add(new SetCardInfo("Thwip!", 20, Rarity.COMMON, mage.cards.t.Thwip.class));
        cards.add(new SetCardInfo("Tombstone, Career Criminal", 70, Rarity.UNCOMMON, mage.cards.t.TombstoneCareerCriminal.class));
        cards.add(new SetCardInfo("Web Up", 21, Rarity.COMMON, mage.cards.w.WebUp.class));
        cards.add(new SetCardInfo("Whoosh!", 48, Rarity.COMMON, mage.cards.w.Whoosh.class));
    }
}
