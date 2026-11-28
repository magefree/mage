package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pr23
 *
 * @author DarioBarrabinoDev
 */
public final class RegionalChampionshipQualifiers2023 extends ExpansionSet {

    private static final RegionalChampionshipQualifiers2023 instance = new RegionalChampionshipQualifiers2023();

    public static RegionalChampionshipQualifiers2023 getInstance() {
        return instance;
    }

    private RegionalChampionshipQualifiers2023() {
        super("Regional Championship Qualifiers 2023", "PR23", ExpansionSet.buildDate(2023, 1, 7), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Mystical Dispute", "1", Rarity.RARE, mage.cards.m.MysticalDispute.class));
        cards.add(new SetCardInfo("Snapcaster Mage", "2", Rarity.MYTHIC, mage.cards.s.SnapcasterMage.class));
        cards.add(new SetCardInfo("Thing in the Ice", "3", Rarity.RARE, mage.cards.t.ThingInTheIce.class));
    }
}
