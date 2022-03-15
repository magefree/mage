

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class WingmateRocToken extends TokenImpl {

    public WingmateRocToken() {
        super("Bird Token", "3/4 white Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BIRD);
        color.setWhite(true);
        power = new MageInt(3);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    public WingmateRocToken(final WingmateRocToken token) {
        super(token);
    }

    public WingmateRocToken copy() {
        return new WingmateRocToken(this);
    }
}
