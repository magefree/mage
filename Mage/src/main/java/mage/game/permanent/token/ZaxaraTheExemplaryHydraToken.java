package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author AsterAether
 */
public class ZaxaraTheExemplaryHydraToken extends TokenImpl {

    public ZaxaraTheExemplaryHydraToken() {
        super("Hydra", "0/0 green Hydra creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.HYDRA);
        power = new MageInt(0);
        toughness = new MageInt(0);

        availableImageSetCodes = Arrays.asList("C20");
    }

    private ZaxaraTheExemplaryHydraToken(final ZaxaraTheExemplaryHydraToken token) {
        super(token);
    }

    @Override
    public ZaxaraTheExemplaryHydraToken copy() {
        return new ZaxaraTheExemplaryHydraToken(this);
    }
}