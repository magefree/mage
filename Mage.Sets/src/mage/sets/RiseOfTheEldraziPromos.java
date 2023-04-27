package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/proe
 */
public class RiseOfTheEldraziPromos extends ExpansionSet {

    private static final RiseOfTheEldraziPromos instance = new RiseOfTheEldraziPromos();

    public static RiseOfTheEldraziPromos getInstance() {
        return instance;
    }

    private RiseOfTheEldraziPromos() {
        super("Rise of the Eldrazi Promos", "PROE", ExpansionSet.buildDate(2010, 7, 30), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Deathless Angel", 49, Rarity.RARE, mage.cards.d.DeathlessAngel.class));
        cards.add(new SetCardInfo("Emrakul, the Aeons Torn", "4*", Rarity.MYTHIC, mage.cards.e.EmrakulTheAeonsTorn.class));
        cards.add(new SetCardInfo("Guul Draz Assassin", "112*", Rarity.RARE, mage.cards.g.GuulDrazAssassin.class));
        cards.add(new SetCardInfo("Lord of Shatterskull Pass", "156*", Rarity.RARE, mage.cards.l.LordOfShatterskullPass.class));
        cards.add(new SetCardInfo("Pestilence Demon", "124*", Rarity.RARE, mage.cards.p.PestilenceDemon.class));
        cards.add(new SetCardInfo("Staggershock", 48, Rarity.RARE, mage.cards.s.Staggershock.class));
    }
}
