package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pmbs
 */
public class MirrodinBesiegedPromos extends ExpansionSet {

    private static final MirrodinBesiegedPromos instance = new MirrodinBesiegedPromos();

    public static MirrodinBesiegedPromos getInstance() {
        return instance;
    }

    private MirrodinBesiegedPromos() {
        super("Mirrodin Besieged Promos", "PMBS", ExpansionSet.buildDate(2011, 2, 3), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Glissa, the Traitor", "96*", Rarity.MYTHIC, mage.cards.g.GlissaTheTraitor.class));
        cards.add(new SetCardInfo("Hero of Bladehold", "8*", Rarity.MYTHIC, mage.cards.h.HeroOfBladehold.class));
        cards.add(new SetCardInfo("Mirran Crusader", "14*", Rarity.RARE, mage.cards.m.MirranCrusader.class));
        cards.add(new SetCardInfo("Thopter Assembly", "140*", Rarity.RARE, mage.cards.t.ThopterAssembly.class));
    }
}
