

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class PatagiaViperSnakeToken extends TokenImpl {

    public PatagiaViperSnakeToken() {
        super("Snake Token", "1/1 green and blue Snake creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setBlue(true);
        subtype.add(SubType.SNAKE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public PatagiaViperSnakeToken(final PatagiaViperSnakeToken token) {
        super(token);
    }

    public PatagiaViperSnakeToken copy() {
        return new PatagiaViperSnakeToken(this);
    }

}
