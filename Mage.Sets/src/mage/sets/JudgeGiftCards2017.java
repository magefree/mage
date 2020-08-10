package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/j17
 */
public class JudgeGiftCards2017 extends ExpansionSet {

    private static final JudgeGiftCards2017 instance = new JudgeGiftCards2017();

    public static JudgeGiftCards2017 getInstance() {
        return instance;
    }

    private JudgeGiftCards2017() {
        super("Judge Gift Cards 2017", "J17", ExpansionSet.buildDate(2017, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Avacyn, Angel of Hope", 1, Rarity.MYTHIC, mage.cards.a.AvacynAngelOfHope.class));
        cards.add(new SetCardInfo("Capture of Jingzhou", 2, Rarity.RARE, mage.cards.c.CaptureOfJingzhou.class));
        cards.add(new SetCardInfo("Doran, the Siege Tower", 5, Rarity.MYTHIC, mage.cards.d.DoranTheSiegeTower.class));
        cards.add(new SetCardInfo("Gaddock Teeg", 3, Rarity.RARE, mage.cards.g.GaddockTeeg.class));
        cards.add(new SetCardInfo("Homeward Path", 4, Rarity.RARE, mage.cards.h.HomewardPath.class));
        cards.add(new SetCardInfo("Pendelhaven", 8, Rarity.RARE, mage.cards.p.Pendelhaven.class));
        cards.add(new SetCardInfo("Prismatic Geoscope", 6, Rarity.RARE, mage.cards.p.PrismaticGeoscope.class));
        // Card not implemented
        // cards.add(new SetCardInfo("Rules Lawyer", 9, Rarity.RARE, mage.cards.r.RulesLawyer.class));
        cards.add(new SetCardInfo("Spellskite", 7, Rarity.RARE, mage.cards.s.Spellskite.class));
     }
}
