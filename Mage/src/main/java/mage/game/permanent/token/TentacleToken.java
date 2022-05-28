package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class TentacleToken extends TokenImpl {

    public TentacleToken() {
        super("Tentacle Token", "1/1 blue Tentacle creature token");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.TENTACLE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("THB", "NCC");
    }

    private TentacleToken(final TentacleToken token) {
        super(token);
    }

    public TentacleToken copy() {
        return new TentacleToken(this);
    }
}
