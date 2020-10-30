package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ps17
 */
public class SanDiegoComicCon2017 extends ExpansionSet {

    private static final SanDiegoComicCon2017 instance = new SanDiegoComicCon2017();

    public static SanDiegoComicCon2017 getInstance() {
        return instance;
    }

    private SanDiegoComicCon2017() {
        super("San Diego Comic-Con 2017", "PS17", ExpansionSet.buildDate(2017, 7, 20), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chandra, Torch of Defiance", 110, Rarity.MYTHIC, mage.cards.c.ChandraTorchOfDefiance.class));
        cards.add(new SetCardInfo("Gideon of the Trials", 14, Rarity.MYTHIC, mage.cards.g.GideonOfTheTrials.class));
        cards.add(new SetCardInfo("Jace, Unraveler of Secrets", 69, Rarity.MYTHIC, mage.cards.j.JaceUnravelerOfSecrets.class));
        cards.add(new SetCardInfo("Liliana, Death's Majesty", 97, Rarity.MYTHIC, mage.cards.l.LilianaDeathsMajesty.class));
        cards.add(new SetCardInfo("Nicol Bolas, God-Pharaoh", 140, Rarity.MYTHIC, mage.cards.n.NicolBolasGodPharaoh.class));
        cards.add(new SetCardInfo("Nissa, Steward of Elements", 204, Rarity.MYTHIC, mage.cards.n.NissaStewardOfElements.class));
     }
}
