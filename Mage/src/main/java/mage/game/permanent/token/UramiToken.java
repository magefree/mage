

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SuperType;

/**
 *
 * @author spjspj
 */
public final class UramiToken extends TokenImpl {

    public UramiToken() {
        super("Urami", "Urami, a legendary 5/5 black Demon Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DEMON);
        subtype.add(SubType.SPIRIT);
        this.supertype.add(SuperType.LEGENDARY);

        color.setBlack(true);
        power = new MageInt(5);
        toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
    }

    public UramiToken(final UramiToken token) {
        super(token);
    }

    public UramiToken copy() {
        return new UramiToken(this);
    }
}
