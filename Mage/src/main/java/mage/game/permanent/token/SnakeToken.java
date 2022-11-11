package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class SnakeToken extends TokenImpl {

    public SnakeToken() {
        super("Snake Token", "1/1 green Snake creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SNAKE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("6ED", "C15", "C19", "CHK", "KTK", "MM2", "MMQ", "SOK",
                "VIS", "ZEN", "C20", "MIC", "DMC");
    }

    public SnakeToken(final SnakeToken token) {
        super(token);
    }

    public SnakeToken copy() {
        return new SnakeToken(this);
    }
}