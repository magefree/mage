
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ppro
 * https://mtg.gamepedia.com/Promotional_cards#Pro_tour_cards
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
        /* Commented cards are Japanese-only printings.
         * Stance on non-english cards:
         * https://github.com/magefree/mage/pull/6190#issuecomment-582353697
         * https://github.com/magefree/mage/pull/6190#issuecomment-582354790
         */
        cards.add(new SetCardInfo("Aether Vial", "2020-3", Rarity.RARE, mage.cards.a.AetherVial.class));
        cards.add(new SetCardInfo("Ajani Goldmane", 2011, Rarity.MYTHIC, mage.cards.a.AjaniGoldmane.class));
        cards.add(new SetCardInfo("Arcbound Ravager", 2019, Rarity.RARE, mage.cards.a.ArcboundRavager.class));
        cards.add(new SetCardInfo("Avatar of Woe", 2010, Rarity.RARE, mage.cards.a.AvatarOfWoe.class));
        cards.add(new SetCardInfo("Cryptic Command", "2020-1", Rarity.RARE, mage.cards.c.CrypticCommand.class));
        cards.add(new SetCardInfo("Emrakul, the Aeons Torn", 2017, Rarity.MYTHIC, mage.cards.e.EmrakulTheAeonsTorn.class));
        cards.add(new SetCardInfo("Eternal Dragon", 2007, Rarity.RARE, mage.cards.e.EternalDragon.class));
        //cards.add(new SetCardInfo("Experimental Frenzy", "2019-2", Rarity.RARE, mage.cards.e.ExperimentalFrenzy.class));
        cards.add(new SetCardInfo("Lava Spike", "2022-1", Rarity.RARE, mage.cards.l.LavaSpike.class));
        cards.add(new SetCardInfo("Liliana of the Veil", 2015, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Mirari's Wake", 2008, Rarity.RARE, mage.cards.m.MirarisWake.class));
        cards.add(new SetCardInfo("Noble Hierarch", 2018, Rarity.RARE, mage.cards.n.NobleHierarch.class));
        cards.add(new SetCardInfo("Nykthos, Shrine to Nyx", "2022-3", Rarity.RARE, mage.cards.n.NykthosShrineToNyx.class));
        //cards.add(new SetCardInfo("Pteramander", "2019-1", Rarity.UNCOMMON, mage.cards.p.Pteramander.class));
        cards.add(new SetCardInfo("Snapcaster Mage", 2016, Rarity.MYTHIC, mage.cards.s.SnapcasterMage.class));
        //cards.add(new SetCardInfo("Surgical Extraction", "2020-2", Rarity.RARE, mage.cards.s.SurgicalExtraction.class));
        cards.add(new SetCardInfo("Teferi, Hero of Dominaria", "2022-2", Rarity.MYTHIC, mage.cards.t.TeferiHeroOfDominaria.class));
        cards.add(new SetCardInfo("Treva, the Renewer", 2009, Rarity.RARE, mage.cards.t.TrevaTheRenewer.class));
        //cards.add(new SetCardInfo("Vraska, Golgari Queen", "2019-3", Rarity.MYTHIC, mage.cards.v.VraskaGolgariQueen.class));
    }

}
