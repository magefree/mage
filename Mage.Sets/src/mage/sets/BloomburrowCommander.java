package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class BloomburrowCommander extends ExpansionSet {

    private static final BloomburrowCommander instance = new BloomburrowCommander();

    public static BloomburrowCommander getInstance() {
        return instance;
    }

    private BloomburrowCommander() {
        super("Bloomburrow Commander", "BLC", ExpansionSet.buildDate(2024, 8, 2), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chatterfang, Squirrel General", 82, Rarity.MYTHIC, mage.cards.c.ChatterfangSquirrelGeneral.class));
        cards.add(new SetCardInfo("Jace, the Mind Sculptor", 75, Rarity.MYTHIC, mage.cards.j.JaceTheMindSculptor.class));
    }
}
