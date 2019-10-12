package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class GameNight2019 extends ExpansionSet {

    private static final GameNight2019 instance = new GameNight2019();

    public static GameNight2019 getInstance() {
        return instance;
    }

    private GameNight2019() {
        super("Game Night 2019", "GN2", ExpansionSet.buildDate(2019, 11, 15), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false; // TODO: change when spoiled

        cards.add(new SetCardInfo("Calculating Lich", 3, Rarity.MYTHIC, mage.cards.c.CalculatingLich.class));
        cards.add(new SetCardInfo("Earthshaker Giant", 5, Rarity.MYTHIC, mage.cards.e.EarthshakerGiant.class));
        cards.add(new SetCardInfo("Fiendish Duo", 4, Rarity.MYTHIC, mage.cards.f.FiendishDuo.class));
        cards.add(new SetCardInfo("Highcliff Felidar", 1, Rarity.MYTHIC, mage.cards.h.HighcliffFelidar.class));
        cards.add(new SetCardInfo("Sphinx of Enlightenment", 2, Rarity.MYTHIC, mage.cards.s.SphinxOfEnlightenment.class));
    }
}
