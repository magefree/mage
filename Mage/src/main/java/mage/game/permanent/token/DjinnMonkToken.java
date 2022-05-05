

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class DjinnMonkToken extends TokenImpl {

    public DjinnMonkToken() {
        super("Djinn Monk Token", "2/2 blue Djinn Monk creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.DJINN);
        subtype.add(SubType.MONK);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FlyingAbility.getInstance());
    }

    public DjinnMonkToken(final DjinnMonkToken token) {
        super(token);
    }

    public DjinnMonkToken copy() {
        return new DjinnMonkToken(this);
    }
}
