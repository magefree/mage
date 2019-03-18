
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author JayDi85
 */
public final class DuelsOfThePlaneswalkersPromos extends ExpansionSet {

    private static final DuelsOfThePlaneswalkersPromos instance = new DuelsOfThePlaneswalkersPromos();

    public static DuelsOfThePlaneswalkersPromos getInstance() {
        return instance;
    }

    private DuelsOfThePlaneswalkersPromos() {
        super("Duels of the Planeswalkers Promos", "DPAP", ExpansionSet.buildDate(2010, 6, 4), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        // all promos in one inner set (2009 - 2014)
        // cards numbers must be unqiue
        // use replacement list for scryfall download

        // 2009 - https://scryfall.com/sets/pdtp
        cards.add(new SetCardInfo("Garruk Wildspeaker", 1, Rarity.MYTHIC, mage.cards.g.GarrukWildspeaker.class));

        // 2010 - https://scryfall.com/sets/pdp10
        cards.add(new SetCardInfo("Liliana Vess", 2, Rarity.MYTHIC, mage.cards.l.LilianaVess.class));
        cards.add(new SetCardInfo("Nissa Revane", 3, Rarity.MYTHIC, mage.cards.n.NissaRevane.class));

        // 2011 - https://scryfall.com/sets/pdp11
        cards.add(new SetCardInfo("Frost Titan", 4, Rarity.MYTHIC, mage.cards.f.FrostTitan.class));
        cards.add(new SetCardInfo("Grave Titan", 5, Rarity.MYTHIC, mage.cards.g.GraveTitan.class));
        cards.add(new SetCardInfo("Inferno Titan", 6, Rarity.MYTHIC, mage.cards.i.InfernoTitan.class));

        // 2012 - https://scryfall.com/sets/pdp12
        cards.add(new SetCardInfo("Primordial Hydra", 7, Rarity.MYTHIC, mage.cards.p.PrimordialHydra.class));
        cards.add(new SetCardInfo("Serra Avatar", 8, Rarity.MYTHIC, mage.cards.s.SerraAvatar.class));
        cards.add(new SetCardInfo("Vampire Nocturnus", 9, Rarity.MYTHIC, mage.cards.v.VampireNocturnus.class));

        // 2013 - https://scryfall.com/sets/pdp13
        cards.add(new SetCardInfo("Bonescythe Sliver", 10, Rarity.RARE, mage.cards.b.BonescytheSliver.class));
        cards.add(new SetCardInfo("Ogre Battledriver", 11, Rarity.RARE, mage.cards.o.OgreBattledriver.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 12, Rarity.RARE, mage.cards.s.ScavengingOoze.class));

        // 2014 - https://scryfall.com/sets/pdp14
        cards.add(new SetCardInfo("Soul of Ravnica", 13, Rarity.MYTHIC, mage.cards.s.SoulOfRavnica.class));
        cards.add(new SetCardInfo("Soul of Zendikar", 14, Rarity.MYTHIC, mage.cards.s.SoulOfZendikar.class));
    }
}