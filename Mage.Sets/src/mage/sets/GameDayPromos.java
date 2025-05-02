package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/gdy
 */
public final class GameDayPromos extends ExpansionSet {

    private static final GameDayPromos instance = new GameDayPromos();

    public static GameDayPromos getInstance() {
        return instance;
    }

    private GameDayPromos() {
        super("Game Day Promos", "GDY", ExpansionSet.buildDate(2022, 4, 8), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("All-Seeing Arbiter", 3, Rarity.MYTHIC, mage.cards.a.AllSeeingArbiter.class));
        cards.add(new SetCardInfo("Braids, Arisen Nightmare", 8, Rarity.RARE, mage.cards.b.BraidsArisenNightmare.class));
        cards.add(new SetCardInfo("Power Word Kill", 1, Rarity.RARE, mage.cards.p.PowerWordKill.class));
        cards.add(new SetCardInfo("Recruitment Officer", 7, Rarity.RARE, mage.cards.r.RecruitmentOfficer.class));
        cards.add(new SetCardInfo("Shivan Devastator", 6, Rarity.MYTHIC, mage.cards.s.ShivanDevastator.class));
        cards.add(new SetCardInfo("Skyclave Apparition", 2, Rarity.RARE, mage.cards.s.SkyclaveApparition.class));
        cards.add(new SetCardInfo("Surge Engine", 9, Rarity.MYTHIC, mage.cards.s.SurgeEngine.class));
        cards.add(new SetCardInfo("Touch the Spirit Realm", 4, Rarity.RARE, mage.cards.t.TouchTheSpiritRealm.class));
        cards.add(new SetCardInfo("Workshop Warchief", 5, Rarity.RARE, mage.cards.w.WorkshopWarchief.class));
    }
}
