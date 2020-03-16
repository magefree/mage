package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/cp1
 */
public class Magic2015ClashPack extends ExpansionSet {

    private static final Magic2015ClashPack instance = new Magic2015ClashPack();

    public static Magic2015ClashPack getInstance() {
        return instance;
    }

    private Magic2015ClashPack() {
        super("Magic 2015 Clash Pack", "CP1", ExpansionSet.buildDate(2014, 7, 18), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Fated Intervention", 2, Rarity.RARE, mage.cards.f.FatedIntervention.class));
        cards.add(new SetCardInfo("Font of Fertility", 3, Rarity.COMMON, mage.cards.f.FontOfFertility.class));
        cards.add(new SetCardInfo("Hydra Broodmaster", 4, Rarity.RARE, mage.cards.h.HydraBroodmaster.class));
        cards.add(new SetCardInfo("Prognostic Sphinx", 1, Rarity.RARE, mage.cards.p.PrognosticSphinx.class));
        cards.add(new SetCardInfo("Prophet of Kruphix", 5, Rarity.RARE, mage.cards.p.ProphetOfKruphix.class));
        cards.add(new SetCardInfo("Temple of Mystery", 6, Rarity.RARE, mage.cards.t.TempleOfMystery.class));
     }
}
