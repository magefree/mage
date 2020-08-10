package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f15
 */
public class FridayNightMagic2015 extends ExpansionSet {

    private static final FridayNightMagic2015 instance = new FridayNightMagic2015();

    public static FridayNightMagic2015 getInstance() {
        return instance;
    }

    private FridayNightMagic2015() {
        super("Friday Night Magic 2015", "F15", ExpansionSet.buildDate(2015, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abzan Beastmaster", 5, Rarity.RARE, mage.cards.a.AbzanBeastmaster.class));
        cards.add(new SetCardInfo("Anticipate", 12, Rarity.RARE, mage.cards.a.Anticipate.class));
        cards.add(new SetCardInfo("Disdainful Stroke", 2, Rarity.RARE, mage.cards.d.DisdainfulStroke.class));
        cards.add(new SetCardInfo("Frenzied Goblin", 1, Rarity.RARE, mage.cards.f.FrenziedGoblin.class));
        cards.add(new SetCardInfo("Frost Walker", 6, Rarity.RARE, mage.cards.f.FrostWalker.class));
        cards.add(new SetCardInfo("Hordeling Outburst", 3, Rarity.RARE, mage.cards.h.HordelingOutburst.class));
        cards.add(new SetCardInfo("Orator of Ojutai", 9, Rarity.RARE, mage.cards.o.OratorOfOjutai.class));
        cards.add(new SetCardInfo("Path to Exile", 7, Rarity.RARE, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Roast", 11, Rarity.RARE, mage.cards.r.Roast.class));
        cards.add(new SetCardInfo("Serum Visions", 8, Rarity.RARE, mage.cards.s.SerumVisions.class));
        cards.add(new SetCardInfo("Suspension Field", 4, Rarity.RARE, mage.cards.s.SuspensionField.class));
        cards.add(new SetCardInfo("Ultimate Price", 10, Rarity.RARE, mage.cards.u.UltimatePrice.class));
     }
}
