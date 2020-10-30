package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f08
 */
public class FridayNightMagic2008 extends ExpansionSet {

    private static final FridayNightMagic2008 instance = new FridayNightMagic2008();

    public static FridayNightMagic2008 getInstance() {
        return instance;
    }

    private FridayNightMagic2008() {
        super("Friday Night Magic 2008", "F08", ExpansionSet.buildDate(2008, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Desert", 8, Rarity.RARE, mage.cards.d.Desert.class));
        cards.add(new SetCardInfo("Eternal Witness", 3, Rarity.RARE, mage.cards.e.EternalWitness.class));
        cards.add(new SetCardInfo("Isochron Scepter", 11, Rarity.RARE, mage.cards.i.IsochronScepter.class));
        cards.add(new SetCardInfo("Pendelhaven", 5, Rarity.RARE, mage.cards.p.Pendelhaven.class));
        cards.add(new SetCardInfo("Remand", 1, Rarity.RARE, mage.cards.r.Remand.class));
        cards.add(new SetCardInfo("Resurrection", 6, Rarity.RARE, mage.cards.r.Resurrection.class));
        cards.add(new SetCardInfo("Serrated Arrows", 10, Rarity.RARE, mage.cards.s.SerratedArrows.class));
        cards.add(new SetCardInfo("Shrapnel Blast", 12, Rarity.RARE, mage.cards.s.ShrapnelBlast.class));
        cards.add(new SetCardInfo("Tendrils of Agony", 4, Rarity.RARE, mage.cards.t.TendrilsOfAgony.class));
        cards.add(new SetCardInfo("Thirst for Knowledge", 9, Rarity.RARE, mage.cards.t.ThirstForKnowledge.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 2, Rarity.RARE, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Wall of Roots", 7, Rarity.RARE, mage.cards.w.WallOfRoots.class));
     }
}
