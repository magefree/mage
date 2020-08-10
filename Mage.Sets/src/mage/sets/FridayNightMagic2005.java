package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f05
 */
public class FridayNightMagic2005 extends ExpansionSet {

    private static final FridayNightMagic2005 instance = new FridayNightMagic2005();

    public static FridayNightMagic2005 getInstance() {
        return instance;
    }

    private FridayNightMagic2005() {
        super("Friday Night Magic 2005", "F05", ExpansionSet.buildDate(2005, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Blastoderm", 4, Rarity.RARE, mage.cards.b.Blastoderm.class));
        cards.add(new SetCardInfo("Cabal Therapy", 5, Rarity.RARE, mage.cards.c.CabalTherapy.class));
        cards.add(new SetCardInfo("Circle of Protection: Red", 8, Rarity.RARE, mage.cards.c.CircleOfProtectionRed.class));
        cards.add(new SetCardInfo("Counterspell", 11, Rarity.RARE, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Duress", 10, Rarity.RARE, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Fact or Fiction", 6, Rarity.RARE, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Flametongue Kavu", 3, Rarity.RARE, mage.cards.f.FlametongueKavu.class));
        cards.add(new SetCardInfo("Icy Manipulator", 12, Rarity.RARE, mage.cards.i.IcyManipulator.class));
        cards.add(new SetCardInfo("Juggernaut", 7, Rarity.RARE, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Kird Ape", 9, Rarity.RARE, mage.cards.k.KirdApe.class));
        cards.add(new SetCardInfo("Rancor", 1, Rarity.RARE, mage.cards.r.Rancor.class));
        cards.add(new SetCardInfo("Seal of Cleansing", 2, Rarity.RARE, mage.cards.s.SealOfCleansing.class));
     }
}
