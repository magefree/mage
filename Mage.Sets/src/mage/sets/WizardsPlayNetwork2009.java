package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwp09
 */
public class WizardsPlayNetwork2009 extends ExpansionSet {

    private static final WizardsPlayNetwork2009 instance = new WizardsPlayNetwork2009();

    public static WizardsPlayNetwork2009 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2009() {
        super("Wizards Play Network 2009", "PW09", ExpansionSet.buildDate(2009, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Hellspark Elemental", 25, Rarity.RARE, mage.cards.h.HellsparkElemental.class));
        cards.add(new SetCardInfo("Kor Duelist", 32, Rarity.RARE, mage.cards.k.KorDuelist.class));
        cards.add(new SetCardInfo("Marisi's Twinclaws", 26, Rarity.RARE, mage.cards.m.MarisisTwinclaws.class));
        cards.add(new SetCardInfo("Mind Control", 30, Rarity.RARE, mage.cards.m.MindControl.class));
        cards.add(new SetCardInfo("Path to Exile", 24, Rarity.RARE, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Rise from the Grave", 31, Rarity.RARE, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("Slave of Bolas", 27, Rarity.RARE, mage.cards.s.SlaveOfBolas.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 33, Rarity.RARE, mage.cards.v.VampireNighthawk.class));
     }
}
