package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pdka
 */
public class DarkAscensionPromos extends ExpansionSet {

    private static final DarkAscensionPromos instance = new DarkAscensionPromos();

    public static DarkAscensionPromos getInstance() {
        return instance;
    }

    private DarkAscensionPromos() {
        super("Dark Ascension Promos", "PDKA", ExpansionSet.buildDate(2012, 1, 28), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Archdemon of Greed", "71*", Rarity.RARE, mage.cards.a.ArchdemonOfGreed.class));
        cards.add(new SetCardInfo("Gravecrawler", "64*", Rarity.RARE, mage.cards.g.Gravecrawler.class));
        cards.add(new SetCardInfo("Mondronen Shaman", "98*", Rarity.RARE, mage.cards.m.MondronenShaman.class));
        cards.add(new SetCardInfo("Ravenous Demon", "71*", Rarity.RARE, mage.cards.r.RavenousDemon.class));
        cards.add(new SetCardInfo("Strangleroot Geist", 127, Rarity.UNCOMMON, mage.cards.s.StranglerootGeist.class));
        cards.add(new SetCardInfo("Tovolar's Magehunter", "98*", Rarity.RARE, mage.cards.t.TovolarsMagehunter.class));
        cards.add(new SetCardInfo("Zombie Apocalypse", 80, Rarity.RARE, mage.cards.z.ZombieApocalypse.class));
    }
}
