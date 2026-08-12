package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public class Stardates extends ExpansionSet {

    private static final Stardates instance = new Stardates();

    public static Stardates getInstance() {
        return instance;
    }

    private Stardates() {
        super("Stardates", "SDS", ExpansionSet.buildDate(2026, 11, 13), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Sheoldred, the Apocalypse", 11, Rarity.MYTHIC, mage.cards.s.SheoldredTheApocalypse.class));
    }
}
