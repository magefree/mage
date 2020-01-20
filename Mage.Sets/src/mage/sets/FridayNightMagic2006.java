package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f06
 */
public class FridayNightMagic2006 extends ExpansionSet {

    private static final FridayNightMagic2006 instance = new FridayNightMagic2006();

    public static FridayNightMagic2006 getInstance() {
        return instance;
    }

    private FridayNightMagic2006() {
        super("Friday Night Magic 2006", "F06", ExpansionSet.buildDate(2006, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Armadillo Cloak", 2, Rarity.RARE, mage.cards.a.ArmadilloCloak.class));
        cards.add(new SetCardInfo("Arrogant Wurm", 10, Rarity.RARE, mage.cards.a.ArrogantWurm.class));
        cards.add(new SetCardInfo("Astral Slide", 9, Rarity.RARE, mage.cards.a.AstralSlide.class));
        cards.add(new SetCardInfo("Chainer's Edict", 7, Rarity.RARE, mage.cards.c.ChainersEdict.class));
        cards.add(new SetCardInfo("Circular Logic", 8, Rarity.RARE, mage.cards.c.CircularLogic.class));
        cards.add(new SetCardInfo("Elves of Deep Shadow", 1, Rarity.RARE, mage.cards.e.ElvesOfDeepShadow.class));
        cards.add(new SetCardInfo("Fire // Ice", "12a", Rarity.RARE, mage.cards.f.FireIce.class));
        cards.add(new SetCardInfo("Goblin Warchief", 5, Rarity.RARE, mage.cards.g.GoblinWarchief.class));
        cards.add(new SetCardInfo("Life // Death", "11a", Rarity.RARE, mage.cards.l.LifeDeath.class));
        cards.add(new SetCardInfo("Lobotomy", 4, Rarity.RARE, mage.cards.l.Lobotomy.class));
        cards.add(new SetCardInfo("Terminate", 3, Rarity.RARE, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Wild Mongrel", 6, Rarity.RARE, mage.cards.w.WildMongrel.class));
     }
}
