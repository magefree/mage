package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pm15
 */
public class Magic2015Promos extends ExpansionSet {

    private static final Magic2015Promos instance = new Magic2015Promos();

    public static Magic2015Promos getInstance() {
        return instance;
    }

    private Magic2015Promos() {
        super("Magic 2015 Promos", "PM15", ExpansionSet.buildDate(2014, 7, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chief Engineer", 47, Rarity.RARE, mage.cards.c.ChiefEngineer.class));
        cards.add(new SetCardInfo("Goblin Rabblemaster", 145, Rarity.RARE, mage.cards.g.GoblinRabblemaster.class));
        cards.add(new SetCardInfo("In Garruk's Wake", 100, Rarity.RARE, mage.cards.i.InGarruksWake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("In Garruk's Wake", "100p", Rarity.RARE, mage.cards.i.InGarruksWake.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Indulgent Tormentor", 101, Rarity.RARE, mage.cards.i.IndulgentTormentor.class));
        cards.add(new SetCardInfo("Mercurial Pretender", 68, Rarity.RARE, mage.cards.m.MercurialPretender.class));
        cards.add(new SetCardInfo("Phytotitan", 191, Rarity.RARE, mage.cards.p.Phytotitan.class));
        cards.add(new SetCardInfo("Reclamation Sage", 194, Rarity.UNCOMMON, mage.cards.r.ReclamationSage.class));
        cards.add(new SetCardInfo("Resolute Archangel", 28, Rarity.RARE, mage.cards.r.ResoluteArchangel.class));
        cards.add(new SetCardInfo("Siege Dragon", 162, Rarity.RARE, mage.cards.s.SiegeDragon.class));
        cards.add(new SetCardInfo("Soul of New Phyrexia", "231p", Rarity.MYTHIC, mage.cards.s.SoulOfNewPhyrexia.class));
     }
}
