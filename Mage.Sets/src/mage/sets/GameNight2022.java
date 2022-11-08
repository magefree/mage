package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/gn3
 *
 * @author TheElk801
 */
public class GameNight2022 extends ExpansionSet {

    private static final GameNight2022 instance = new GameNight2022();

    public static GameNight2022 getInstance() {
        return instance;
    }

    private GameNight2022() {
        super("Game Night 2022", "GN3", ExpansionSet.buildDate(2019, 10, 14), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false; //temporary

        cards.add(new SetCardInfo("Imaryll, Elfhame Elite", 5, Rarity.MYTHIC, mage.cards.i.ImaryllElfhameElite.class));
        cards.add(new SetCardInfo("Nogi, Draco-Zealot", 4, Rarity.MYTHIC, mage.cards.n.NogiDracoZealot.class));
        cards.add(new SetCardInfo("Zamriel, Seraph of Steel", 1, Rarity.MYTHIC, mage.cards.z.ZamrielSeraphOfSteel.class));
    }
}
