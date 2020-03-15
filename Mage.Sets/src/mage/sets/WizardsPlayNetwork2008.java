package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwpn
 */
public class WizardsPlayNetwork2008 extends ExpansionSet {

    private static final WizardsPlayNetwork2008 instance = new WizardsPlayNetwork2008();

    public static WizardsPlayNetwork2008 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2008() {
        super("Wizards Play Network 2008", "PWPN", ExpansionSet.buildDate(2008, 10, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Sprouting Thrinax", 21, Rarity.RARE, mage.cards.s.SproutingThrinax.class));
        cards.add(new SetCardInfo("Woolly Thoctar", 22, Rarity.RARE, mage.cards.w.WoollyThoctar.class));
     }
}
