package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class TheHobbitCommander extends ExpansionSet {

    private static final TheHobbitCommander instance = new TheHobbitCommander();

    public static TheHobbitCommander getInstance() {
        return instance;
    }

    private TheHobbitCommander() {
        super("The Hobbit Commander", "HOC", ExpansionSet.buildDate(2026, 8, 14), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arcane Signet", 95, Rarity.MYTHIC, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Sauron, the Dark Lord", 36, Rarity.MYTHIC, mage.cards.s.SauronTheDarkLord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sauron, the Dark Lord", 76, Rarity.MYTHIC, mage.cards.s.SauronTheDarkLord.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The One Ring", 44, Rarity.MYTHIC, mage.cards.t.TheOneRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The One Ring", 84, Rarity.MYTHIC, mage.cards.t.TheOneRing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom Bombadil", 38, Rarity.MYTHIC, mage.cards.t.TomBombadil.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tom Bombadil", 78, Rarity.MYTHIC, mage.cards.t.TomBombadil.class, NON_FULL_USE_VARIOUS));
    }
}
