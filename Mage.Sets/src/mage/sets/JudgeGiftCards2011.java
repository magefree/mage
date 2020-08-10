package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g11
 */
public class JudgeGiftCards2011 extends ExpansionSet {

    private static final JudgeGiftCards2011 instance = new JudgeGiftCards2011();

    public static JudgeGiftCards2011 getInstance() {
        return instance;
    }

    private JudgeGiftCards2011() {
        super("Judge Gift Cards 2011", "G11", ExpansionSet.buildDate(2011, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Bitterblossom", 1, Rarity.RARE, mage.cards.b.Bitterblossom.class));
        cards.add(new SetCardInfo("Dark Confidant", 6, Rarity.RARE, mage.cards.d.DarkConfidant.class));
        cards.add(new SetCardInfo("Doubling Season", 7, Rarity.RARE, mage.cards.d.DoublingSeason.class));
        cards.add(new SetCardInfo("Entomb", 4, Rarity.RARE, mage.cards.e.Entomb.class));
        cards.add(new SetCardInfo("Goblin Welder", 8, Rarity.RARE, mage.cards.g.GoblinWelder.class));
        cards.add(new SetCardInfo("Mana Crypt", 5, Rarity.RARE, mage.cards.m.ManaCrypt.class));
        cards.add(new SetCardInfo("Sword of Fire and Ice", 2, Rarity.RARE, mage.cards.s.SwordOfFireAndIce.class));
        cards.add(new SetCardInfo("Vendilion Clique", 3, Rarity.RARE, mage.cards.v.VendilionClique.class));
     }
}
