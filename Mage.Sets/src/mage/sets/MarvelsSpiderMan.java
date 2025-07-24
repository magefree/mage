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
        cards.add(new SetCardInfo("Flying Octobot", 31, Rarity.UNCOMMON, mage.cards.f.FlyingOctobot.class));
        cards.add(new SetCardInfo("Forest", 198, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 195, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Merciless Enforcers", 58, Rarity.COMMON, mage.cards.m.MercilessEnforcers.class));
        cards.add(new SetCardInfo("Mountain", 197, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Origin of Spider-Man", 9, Rarity.RARE, mage.cards.o.OriginOfSpiderMan.class));
        cards.add(new SetCardInfo("Oscorp Research Team", 40, Rarity.COMMON, mage.cards.o.OscorpResearchTeam.class));
        cards.add(new SetCardInfo("Plains", 194, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Risky Research", 62, Rarity.COMMON, mage.cards.r.RiskyResearch.class));
        cards.add(new SetCardInfo("Scorpion, Seething Striker", 64, Rarity.UNCOMMON, mage.cards.s.ScorpionSeethingStriker.class));
        cards.add(new SetCardInfo("Selfless Police Captain", 12, Rarity.COMMON, mage.cards.s.SelflessPoliceCaptain.class));
        cards.add(new SetCardInfo("Shock", 88, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Spectacular Tactics", 15, Rarity.COMMON, mage.cards.s.SpectacularTactics.class));
        cards.add(new SetCardInfo("Spider-Byte, Web Warden", 44, Rarity.UNCOMMON, mage.cards.s.SpiderByteWebWarden.class));
        cards.add(new SetCardInfo("Starling, Aerial Ally", 18, Rarity.COMMON, mage.cards.s.StarlingAerialAlly.class));
        cards.add(new SetCardInfo("Swamp", 196, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Web Up", 21, Rarity.COMMON, mage.cards.w.WebUp.class));
        cards.add(new SetCardInfo("Whoosh!", 48, Rarity.COMMON, mage.cards.w.Whoosh.class));
    }
}
