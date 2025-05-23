package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pw21
 */
public class WizardsPlayNetwork2021 extends ExpansionSet {

    private static final WizardsPlayNetwork2021 instance = new WizardsPlayNetwork2021();

    public static WizardsPlayNetwork2021 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2021() {
        super("Wizards Play Network 2021", "PW21", ExpansionSet.buildDate(2021, 6, 18), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arbor Elf", 1, Rarity.RARE, mage.cards.a.ArborElf.class));
        cards.add(new SetCardInfo("Collected Company", 2, Rarity.RARE, mage.cards.c.CollectedCompany.class));
        cards.add(new SetCardInfo("Conjurer's Closet", 6, Rarity.RARE, mage.cards.c.ConjurersCloset.class));
        cards.add(new SetCardInfo("Fabled Passage", 4, Rarity.RARE, mage.cards.f.FabledPassage.class, RETRO_ART));
        cards.add(new SetCardInfo("Mind Stone", 5, Rarity.RARE, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Wurmcoil Engine", 3, Rarity.MYTHIC, mage.cards.w.WurmcoilEngine.class));
    }
}
