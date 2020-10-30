package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/j12
 */
public class JudgeGiftCards2012 extends ExpansionSet {

    private static final JudgeGiftCards2012 instance = new JudgeGiftCards2012();

    public static JudgeGiftCards2012 getInstance() {
        return instance;
    }

    private JudgeGiftCards2012() {
        super("Judge Gift Cards 2012", "J12", ExpansionSet.buildDate(2012, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Command Tower", 8, Rarity.RARE, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Flusterstorm", 2, Rarity.RARE, mage.cards.f.Flusterstorm.class));
        cards.add(new SetCardInfo("Karakas", 6, Rarity.RARE, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Karmic Guide", 4, Rarity.RARE, mage.cards.k.KarmicGuide.class));
        cards.add(new SetCardInfo("Noble Hierarch", 3, Rarity.RARE, mage.cards.n.NobleHierarch.class));
        cards.add(new SetCardInfo("Sneak Attack", 5, Rarity.RARE, mage.cards.s.SneakAttack.class));
        cards.add(new SetCardInfo("Sword of Light and Shadow", 7, Rarity.RARE, mage.cards.s.SwordOfLightAndShadow.class));
        cards.add(new SetCardInfo("Xiahou Dun, the One-Eyed", 1, Rarity.RARE, mage.cards.x.XiahouDunTheOneEyed.class));
     }
}
