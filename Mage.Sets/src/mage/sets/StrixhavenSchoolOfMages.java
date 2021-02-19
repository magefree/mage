package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class StrixhavenSchoolOfMages extends ExpansionSet {

    private static final StrixhavenSchoolOfMages instance = new StrixhavenSchoolOfMages();

    public static StrixhavenSchoolOfMages getInstance() {
        return instance;
    }

    private StrixhavenSchoolOfMages() {
        super("Strixhaven: School of Mages", "STX", ExpansionSet.buildDate(2021, 4, 23), SetType.EXPANSION);
        this.blockName = "Strixhaven: School of Mages";
        this.hasBoosters = false;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 7.4;
        this.maxCardNumberInBooster = 275;

        cards.add(new SetCardInfo("Prismari Command", 215, Rarity.RARE, mage.cards.p.PrismariCommand.class));
    }
}
