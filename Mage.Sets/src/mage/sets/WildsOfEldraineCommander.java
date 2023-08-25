package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class WildsOfEldraineCommander extends ExpansionSet {

    private static final WildsOfEldraineCommander instance = new WildsOfEldraineCommander();

    public static WildsOfEldraineCommander getInstance() {
        return instance;
    }

    private WildsOfEldraineCommander() {
        super("Wilds of Eldraine Commander", "WOC", ExpansionSet.buildDate(2023, 9, 8), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ellivere of the Wild Court", 2, Rarity.MYTHIC, mage.cards.e.EllivereOfTheWildCourt.class));
        cards.add(new SetCardInfo("Gylwain, Casting Director", 4, Rarity.MYTHIC, mage.cards.g.GylwainCastingDirector.class));
        cards.add(new SetCardInfo("Secluded Glen", 166, Rarity.RARE, mage.cards.s.SecludedGlen.class));
        cards.add(new SetCardInfo("Tegwyll, Duke of Splendor", 1, Rarity.MYTHIC, mage.cards.t.TegwyllDukeOfSplendor.class));
    }
}
