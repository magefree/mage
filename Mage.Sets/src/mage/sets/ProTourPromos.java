
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author JayDi85
 */
public final class ProTourPromos extends ExpansionSet {

    private static final ProTourPromos instance = new ProTourPromos();

    public static ProTourPromos getInstance() {
        return instance;
    }

    private ProTourPromos() {
        super("Pro Tour Promos", "PPRO", ExpansionSet.buildDate(2007, 2, 9), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        // https://mtg.gamepedia.com/Promotional_cards#Pro_tour_cards
        cards.add(new SetCardInfo("Ajani Goldmane", 2011, Rarity.MYTHIC, mage.cards.a.AjaniGoldmane.class));
        cards.add(new SetCardInfo("Avatar of Woe", 2010, Rarity.RARE, mage.cards.a.AvatarOfWoe.class));
        cards.add(new SetCardInfo("Emrakul, the Aeons Torn", 2017, Rarity.MYTHIC, mage.cards.e.EmrakulTheAeonsTorn.class));
        cards.add(new SetCardInfo("Eternal Dragon", 2007, Rarity.RARE, mage.cards.e.EternalDragon.class));
        cards.add(new SetCardInfo("Liliana of the Veil", 2015, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Mirari's Wake", 2008, Rarity.RARE, mage.cards.m.MirarisWake.class));
        cards.add(new SetCardInfo("Noble Hierarch", 2018, Rarity.RARE, mage.cards.n.NobleHierarch.class));
        cards.add(new SetCardInfo("Snapcaster Mage", 2016, Rarity.MYTHIC, mage.cards.s.SnapcasterMage.class));
        cards.add(new SetCardInfo("Treva, the Renewer", 2009, Rarity.RARE, mage.cards.t.TrevaTheRenewer.class));
    }

}
