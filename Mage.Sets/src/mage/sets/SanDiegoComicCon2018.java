package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ps18
 */
public class SanDiegoComicCon2018 extends ExpansionSet {

    private static final SanDiegoComicCon2018 instance = new SanDiegoComicCon2018();

    public static SanDiegoComicCon2018 getInstance() {
        return instance;
    }

    private SanDiegoComicCon2018() {
        super("San Diego Comic-Con 2018", "PS18", ExpansionSet.buildDate(2018, 7, 19), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chandra, Torch of Defiance", 110, Rarity.MYTHIC, mage.cards.c.ChandraTorchOfDefiance.class));
        cards.add(new SetCardInfo("Gideon of the Trials", 14, Rarity.MYTHIC, mage.cards.g.GideonOfTheTrials.class));
        cards.add(new SetCardInfo("Jace, Cunning Castaway", 60, Rarity.MYTHIC, mage.cards.j.JaceCunningCastaway.class));
        cards.add(new SetCardInfo("Liliana, Untouched by Death", 106, Rarity.MYTHIC, mage.cards.l.LilianaUntouchedByDeath.class));
        cards.add(new SetCardInfo("Nissa, Vital Force", 163, Rarity.MYTHIC, mage.cards.n.NissaVitalForce.class));
     }
}
