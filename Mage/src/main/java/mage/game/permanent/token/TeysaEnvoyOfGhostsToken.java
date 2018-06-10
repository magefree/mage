

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class TeysaEnvoyOfGhostsToken extends TokenImpl {

    public TeysaEnvoyOfGhostsToken() {
        super("Spirit", "1/1 white and black Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.SPIRIT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    public TeysaEnvoyOfGhostsToken(final TeysaEnvoyOfGhostsToken token) {
        super(token);
    }

    public TeysaEnvoyOfGhostsToken copy() {
        return new TeysaEnvoyOfGhostsToken(this);
    }
}
