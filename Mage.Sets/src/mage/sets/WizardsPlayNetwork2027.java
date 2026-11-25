package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pw27
 *
 * @author muz
 */
public class WizardsPlayNetwork2027 extends ExpansionSet {

    private static final WizardsPlayNetwork2027 instance = new WizardsPlayNetwork2027();

    public static WizardsPlayNetwork2027 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2027() {
        super("Wizards Play Network 2027", "PW27", ExpansionSet.buildDate(2027, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Vivien Reid", 1, Rarity.MYTHIC, mage.cards.v.VivienReid.class));
        cards.add(new SetCardInfo("Broadside Barrage", 2, Rarity.RARE, mage.cards.b.BroadsideBarrage.class, RETRO_ART));
        cards.add(new SetCardInfo("Unexpected Windfall", 3, Rarity.RARE, mage.cards.u.UnexpectedWindfall.class, RETRO_ART));
        cards.add(new SetCardInfo("Gene Pollinator", 4, Rarity.RARE, mage.cards.g.GenePollinator.class, RETRO_ART));
    }
}
