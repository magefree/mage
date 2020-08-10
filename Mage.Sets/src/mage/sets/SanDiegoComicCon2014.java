package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ps14
 */
public class SanDiegoComicCon2014 extends ExpansionSet {

    private static final SanDiegoComicCon2014 instance = new SanDiegoComicCon2014();

    public static SanDiegoComicCon2014 getInstance() {
        return instance;
    }

    private SanDiegoComicCon2014() {
        super("San Diego Comic-Con 2014", "PS14", ExpansionSet.buildDate(2014, 7, 8), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ajani Steadfast", 1, Rarity.MYTHIC, mage.cards.a.AjaniSteadfast.class));
        cards.add(new SetCardInfo("Chandra, Pyromaster", 134, Rarity.MYTHIC, mage.cards.c.ChandraPyromaster.class));
        cards.add(new SetCardInfo("Garruk, Apex Predator", 210, Rarity.MYTHIC, mage.cards.g.GarrukApexPredator.class));
        cards.add(new SetCardInfo("Jace, the Living Guildpact", 62, Rarity.MYTHIC, mage.cards.j.JaceTheLivingGuildpact.class));
        cards.add(new SetCardInfo("Liliana Vess", 103, Rarity.MYTHIC, mage.cards.l.LilianaVess.class));
        cards.add(new SetCardInfo("Nissa, Worldwaker", 187, Rarity.MYTHIC, mage.cards.n.NissaWorldwaker.class));
     }
}
