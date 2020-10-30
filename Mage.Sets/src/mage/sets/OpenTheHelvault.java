package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/phel
 */
public class OpenTheHelvault extends ExpansionSet {

    private static final OpenTheHelvault instance = new OpenTheHelvault();

    public static OpenTheHelvault getInstance() {
        return instance;
    }

    private OpenTheHelvault() {
        super("Open the Helvault", "PHEL", ExpansionSet.buildDate(2012, 4, 28), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Avacyn, Angel of Hope", 6, Rarity.MYTHIC, mage.cards.a.AvacynAngelOfHope.class));
        cards.add(new SetCardInfo("Bruna, Light of Alabaster", 208, Rarity.MYTHIC, mage.cards.b.BrunaLightOfAlabaster.class));
        cards.add(new SetCardInfo("Gisela, Blade of Goldnight", 209, Rarity.MYTHIC, mage.cards.g.GiselaBladeOfGoldnight.class));
        cards.add(new SetCardInfo("Griselbrand", 106, Rarity.MYTHIC, mage.cards.g.Griselbrand.class));
        cards.add(new SetCardInfo("Sigarda, Host of Herons", 210, Rarity.MYTHIC, mage.cards.s.SigardaHostOfHerons.class));
     }
}
