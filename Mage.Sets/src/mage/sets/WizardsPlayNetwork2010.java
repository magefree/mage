package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwp10
 */
public class WizardsPlayNetwork2010 extends ExpansionSet {

    private static final WizardsPlayNetwork2010 instance = new WizardsPlayNetwork2010();

    public static WizardsPlayNetwork2010 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2010() {
        super("Wizards Play Network 2010", "PW10", ExpansionSet.buildDate(2010, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Curse of Wizardry", 47, Rarity.RARE, mage.cards.c.CurseOfWizardry.class));
        cards.add(new SetCardInfo("Fling", 50, Rarity.RARE, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Golem's Heart", 60, Rarity.RARE, mage.cards.g.GolemsHeart.class));
        cards.add(new SetCardInfo("Kor Firewalker", 36, Rarity.RARE, mage.cards.k.KorFirewalker.class));
        cards.add(new SetCardInfo("Leatherback Baloth", 37, Rarity.RARE, mage.cards.l.LeatherbackBaloth.class));
        cards.add(new SetCardInfo("Pathrazer of Ulamog", 46, Rarity.RARE, mage.cards.p.PathrazerOfUlamog.class));
        cards.add(new SetCardInfo("Plague Stinger", 59, Rarity.RARE, mage.cards.p.PlagueStinger.class));
        cards.add(new SetCardInfo("Skinrender", 63, Rarity.RARE, mage.cards.s.Skinrender.class));
        cards.add(new SetCardInfo("Sylvan Ranger", 51, Rarity.RARE, mage.cards.s.SylvanRanger.class));
        cards.add(new SetCardInfo("Syphon Mind", 40, Rarity.RARE, mage.cards.s.SyphonMind.class));
     }
}
