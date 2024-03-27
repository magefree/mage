package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TheBigScore extends ExpansionSet {

    private static final TheBigScore instance = new TheBigScore();

    public static TheBigScore getInstance() {
        return instance;
    }

    private TheBigScore() {
        super("The Big Score", "BIG", ExpansionSet.buildDate(2024, 4, 19), SetType.SUPPLEMENTAL_STANDARD_LEGAL);
        this.blockName = "Outlaws of Thunder Junction";
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Torpor Orb", 27, Rarity.MYTHIC, mage.cards.t.TorporOrb.class));
    }
}
