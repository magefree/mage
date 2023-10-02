package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pw11
 */
public class WizardsPlayNetwork2011 extends ExpansionSet {

    private static final WizardsPlayNetwork2011 instance = new WizardsPlayNetwork2011();

    public static WizardsPlayNetwork2011 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2011() {
        super("Wizards Play Network 2011", "PW11", ExpansionSet.buildDate(2011, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bloodcrazed Neonate", 83, Rarity.RARE, mage.cards.b.BloodcrazedNeonate.class));
        cards.add(new SetCardInfo("Boneyard Wurm", 84, Rarity.RARE, mage.cards.b.BoneyardWurm.class));
        cards.add(new SetCardInfo("Curse of the Bloody Tome", 80, Rarity.RARE, mage.cards.c.CurseOfTheBloodyTome.class));
     }
}
