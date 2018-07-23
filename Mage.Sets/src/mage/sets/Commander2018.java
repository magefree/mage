package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author TheElk801
 */
public final class Commander2018 extends ExpansionSet {

    private static final Commander2018 instance = new Commander2018();

    public static Commander2018 getInstance() {
        return instance;
    }

    private Commander2018() {
        super("Commander 2018 Edition", "C18", ExpansionSet.buildDate(2018, 8, 10), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";

        cards.add(new SetCardInfo("Chaos Warp", 122, Rarity.RARE, mage.cards.c.ChaosWarp.class));
        cards.add(new SetCardInfo("Retrofitter Foundry", 57, Rarity.RARE, mage.cards.r.RetrofitterFoundry.class));
        cards.add(new SetCardInfo("Saheeli's Directive", 26, Rarity.RARE, mage.cards.s.SaheelisDirective.class));
        cards.add(new SetCardInfo("Tawnos, Urza's Apprentice", 45, Rarity.MYTHIC, mage.cards.t.TawnosUrzasApprentice.class));
        cards.add(new SetCardInfo("Thopter Spy Network", 107, Rarity.RARE, mage.cards.t.ThopterSpyNetwork.class));
    }
}
