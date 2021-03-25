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
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 7.4;
        this.maxCardNumberInBooster = 275;

        cards.add(new SetCardInfo("Eager First-Year", 16, Rarity.COMMON, mage.cards.e.EagerFirstYear.class));
        cards.add(new SetCardInfo("Kasmina, Enigma Sage", 196, Rarity.MYTHIC, mage.cards.k.KasminaEnigmaSage.class));
        cards.add(new SetCardInfo("Lorehold Command", 199, Rarity.RARE, mage.cards.l.LoreholdCommand.class));
        cards.add(new SetCardInfo("Prismari Command", 214, Rarity.RARE, mage.cards.p.PrismariCommand.class));
        cards.add(new SetCardInfo("Professor Onyx", 83, Rarity.MYTHIC, mage.cards.p.ProfessorOnyx.class));
        cards.add(new SetCardInfo("Quandrix Command", 217, Rarity.RARE, mage.cards.q.QuandrixCommand.class));
        cards.add(new SetCardInfo("Silverquill Command", 232, Rarity.RARE, mage.cards.s.SilverquillCommand.class));
        cards.add(new SetCardInfo("Witherbloom Command", 248, Rarity.RARE, mage.cards.w.WitherbloomCommand.class));
    }
}
