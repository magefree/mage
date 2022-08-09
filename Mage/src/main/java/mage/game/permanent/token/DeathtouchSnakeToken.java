

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;

/**
 *
 * @author spjspj
 */
public final class DeathtouchSnakeToken extends TokenImpl {

    public DeathtouchSnakeToken() {
        super("Snake Token", "1/1 green Snake creature token with deathtouch");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SNAKE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(DeathtouchAbility.getInstance());

        setOriginalExpansionSetCode("AKH");
    }

    public DeathtouchSnakeToken(final DeathtouchSnakeToken token) {
        super(token);
    }

    public DeathtouchSnakeToken copy() {
        return new DeathtouchSnakeToken(this);
    }
}
