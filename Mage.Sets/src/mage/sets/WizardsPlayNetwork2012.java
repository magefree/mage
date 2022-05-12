package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwp12
 */
public class WizardsPlayNetwork2012 extends ExpansionSet {

    private static final WizardsPlayNetwork2012 instance = new WizardsPlayNetwork2012();

    public static WizardsPlayNetwork2012 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2012() {
        super("Wizards Play Network 2012", "PW12", ExpansionSet.buildDate(2012, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Curse of Thirst", 81, Rarity.RARE, mage.cards.c.CurseOfThirst.class));
        cards.add(new SetCardInfo("Gather the Townsfolk", 79, Rarity.RARE, mage.cards.g.GatherTheTownsfolk.class));
        cards.add(new SetCardInfo("Nearheath Stalker", 82, Rarity.RARE, mage.cards.n.NearheathStalker.class));
     }
}
