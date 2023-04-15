package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pbng
 */
public class BornOfTheGodsPromos extends ExpansionSet {

    private static final BornOfTheGodsPromos instance = new BornOfTheGodsPromos();

    public static BornOfTheGodsPromos getInstance() {
        return instance;
    }

    private BornOfTheGodsPromos() {
        super("Born of the Gods Promos", "PBNG", ExpansionSet.buildDate(2014, 2, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arbiter of the Ideal", "31*", Rarity.RARE, mage.cards.a.ArbiterOfTheIdeal.class));
        cards.add(new SetCardInfo("Eater of Hope", "66*", Rarity.RARE, mage.cards.e.EaterOfHope.class));
        cards.add(new SetCardInfo("Fated Conflagration", "94*", Rarity.RARE, mage.cards.f.FatedConflagration.class));
        cards.add(new SetCardInfo("Forgestoker Dragon", "98*", Rarity.RARE, mage.cards.f.ForgestokerDragon.class));
        cards.add(new SetCardInfo("Kiora's Follower", 150, Rarity.UNCOMMON, mage.cards.k.KiorasFollower.class));
        cards.add(new SetCardInfo("Nessian Wilds Ravager", "129*", Rarity.RARE, mage.cards.n.NessianWildsRavager.class));
        cards.add(new SetCardInfo("Pain Seer", 80, Rarity.RARE, mage.cards.p.PainSeer.class));
        cards.add(new SetCardInfo("Silent Sentinel", "26*", Rarity.RARE, mage.cards.s.SilentSentinel.class));
        cards.add(new SetCardInfo("Tromokratis", "55*", Rarity.RARE, mage.cards.t.Tromokratis.class));
    }
}
