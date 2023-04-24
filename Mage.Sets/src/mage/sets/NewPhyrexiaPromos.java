package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pnph
 */
public class NewPhyrexiaPromos extends ExpansionSet {

    private static final NewPhyrexiaPromos instance = new NewPhyrexiaPromos();

    public static NewPhyrexiaPromos getInstance() {
        return instance;
    }

    private NewPhyrexiaPromos() {
        super("New Phyrexia Promos", "PNPH", ExpansionSet.buildDate(2011, 5, 12), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Myr Superion", 146, Rarity.RARE, mage.cards.m.MyrSuperion.class));
        cards.add(new SetCardInfo("Phyrexian Metamorph", "42*", Rarity.RARE, mage.cards.p.PhyrexianMetamorph.class));
        cards.add(new SetCardInfo("Priest of Urabrask", 90, Rarity.UNCOMMON, mage.cards.p.PriestOfUrabrask.class));
        cards.add(new SetCardInfo("Pristine Talisman", 151, Rarity.COMMON, mage.cards.p.PristineTalisman.class));
        cards.add(new SetCardInfo("Sheoldred, Whispering One", "73*", Rarity.MYTHIC, mage.cards.s.SheoldredWhisperingOne.class));
        cards.add(new SetCardInfo("Surgical Extraction", "74*", Rarity.RARE, mage.cards.s.SurgicalExtraction.class));
    }
}
