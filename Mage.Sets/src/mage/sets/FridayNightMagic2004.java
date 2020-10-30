package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f04
 */
public class FridayNightMagic2004 extends ExpansionSet {

    private static final FridayNightMagic2004 instance = new FridayNightMagic2004();

    public static FridayNightMagic2004 getInstance() {
        return instance;
    }

    private FridayNightMagic2004() {
        super("Friday Night Magic 2004", "F04", ExpansionSet.buildDate(2004, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Accumulated Knowledge", 8, Rarity.RARE, mage.cards.a.AccumulatedKnowledge.class));
        cards.add(new SetCardInfo("Avalanche Riders", 9, Rarity.RARE, mage.cards.a.AvalancheRiders.class));
        cards.add(new SetCardInfo("Brainstorm", 12, Rarity.RARE, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Carrion Feeder", 6, Rarity.RARE, mage.cards.c.CarrionFeeder.class));
        cards.add(new SetCardInfo("Krosan Warchief", 4, Rarity.RARE, mage.cards.k.KrosanWarchief.class));
        cards.add(new SetCardInfo("Lightning Rift", 5, Rarity.RARE, mage.cards.l.LightningRift.class));
        cards.add(new SetCardInfo("Mother of Runes", 11, Rarity.RARE, mage.cards.m.MotherOfRunes.class));
        cards.add(new SetCardInfo("Reanimate", 10, Rarity.RARE, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Silver Knight", 3, Rarity.RARE, mage.cards.s.SilverKnight.class));
        cards.add(new SetCardInfo("Slice and Dice", 2, Rarity.RARE, mage.cards.s.SliceAndDice.class));
        cards.add(new SetCardInfo("Treetop Village", 7, Rarity.RARE, mage.cards.t.TreetopVillage.class));
        cards.add(new SetCardInfo("Willbender", 1, Rarity.RARE, mage.cards.w.Willbender.class));
     }
}
