package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdrc
 * https://gatherer.wizards.com/pages/card/Details.aspx?name=Nalathni+Dragon
 */
public class DragonCon extends ExpansionSet {

    private static final DragonCon instance = new DragonCon();

    public static DragonCon getInstance() {
        return instance;
    }

    private DragonCon() {
        super("Dragon Con", "PDRC", ExpansionSet.buildDate(1995, 7, 15), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Nalathni Dragon", 1, Rarity.RARE, mage.cards.n.NalathniDragon.class));
    }
}