package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/plpa
 */
public class LaunchParties extends ExpansionSet {

    private static final LaunchParties instance = new LaunchParties();

    public static LaunchParties getInstance() {
        return instance;
    }

    private LaunchParties() {
        super("Launch Parties", "PLPA", ExpansionSet.buildDate(2014, 6, 6), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Earwig Squad", 1, Rarity.RARE, mage.cards.e.EarwigSquad.class));
        cards.add(new SetCardInfo("Figure of Destiny", 3, Rarity.RARE, mage.cards.f.FigureOfDestiny.class));
        cards.add(new SetCardInfo("Knight of New Alara", 6, Rarity.RARE, mage.cards.k.KnightOfNewAlara.class));
        cards.add(new SetCardInfo("Magister of Worth", 7, Rarity.RARE, mage.cards.m.MagisterOfWorth.class));
        cards.add(new SetCardInfo("Obelisk of Alara", 5, Rarity.RARE, mage.cards.o.ObeliskOfAlara.class));
        cards.add(new SetCardInfo("Vexing Shusher", 2, Rarity.RARE, mage.cards.v.VexingShusher.class));
     }
}
