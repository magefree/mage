package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/prcq
 */
public class RegionalChampionshipQualifiers2022 extends ExpansionSet {

    private static final RegionalChampionshipQualifiers2022 instance = new RegionalChampionshipQualifiers2022();

    public static RegionalChampionshipQualifiers2022 getInstance() {
        return instance;
    }

    private RegionalChampionshipQualifiers2022() {
        super("Regional Championship Qualifiers 2022", "PRCQ", ExpansionSet.buildDate(2022, 10, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Gideon, Ally of Zendikar", 1, Rarity.MYTHIC, mage.cards.g.GideonAllyOfZendikar.class));
        cards.add(new SetCardInfo("Selfless Spirit", 2, Rarity.RARE, mage.cards.s.SelflessSpirit.class));
        cards.add(new SetCardInfo("Thraben Inspector", 3, Rarity.RARE, mage.cards.t.ThrabenInspector.class));
    }
}
