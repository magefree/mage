package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class OutlawsOfThunderJunctionCommander extends ExpansionSet {

    private static final OutlawsOfThunderJunctionCommander instance = new OutlawsOfThunderJunctionCommander();

    public static OutlawsOfThunderJunctionCommander getInstance() {
        return instance;
    }

    private OutlawsOfThunderJunctionCommander() {
        super("Outlaws of Thunder Junction Commander", "OTC", ExpansionSet.buildDate(2024, 4, 19), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Angel of Indemnity", 45, Rarity.RARE, mage.cards.a.AngelOfIndemnity.class));
        cards.add(new SetCardInfo("Angelic Sell-Sword", 10, Rarity.RARE, mage.cards.a.AngelicSellSword.class));
        cards.add(new SetCardInfo("Back in Town", 18, Rarity.RARE, mage.cards.b.BackInTown.class));
        cards.add(new SetCardInfo("Charred Graverobber", 19, Rarity.RARE, mage.cards.c.CharredGraverobber.class));
        cards.add(new SetCardInfo("Elemental Eruption", 27, Rarity.RARE, mage.cards.e.ElementalEruption.class));
        cards.add(new SetCardInfo("Leyline Dowser", 39, Rarity.RARE, mage.cards.l.LeylineDowser.class));
        cards.add(new SetCardInfo("Stella Lee, Wild Card", 3, Rarity.MYTHIC, mage.cards.s.StellaLeeWildCard.class));
    }
}
