package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pss1
 */
public class BFZStandardSeries extends ExpansionSet {

    private static final BFZStandardSeries instance = new BFZStandardSeries();

    public static BFZStandardSeries getInstance() {
        return instance;
    }

    private BFZStandardSeries() {
        super("BFZ Standard Series", "PSS1", ExpansionSet.buildDate(2015, 10, 2), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Canopy Vista", 234, Rarity.RARE, mage.cards.c.CanopyVista.class));
        cards.add(new SetCardInfo("Cinder Glade", 235, Rarity.RARE, mage.cards.c.CinderGlade.class));
        cards.add(new SetCardInfo("Prairie Stream", 241, Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Smoldering Marsh", 247, Rarity.RARE, mage.cards.s.SmolderingMarsh.class));
        cards.add(new SetCardInfo("Sunken Hollow", 249, Rarity.RARE, mage.cards.s.SunkenHollow.class));
     }
}
