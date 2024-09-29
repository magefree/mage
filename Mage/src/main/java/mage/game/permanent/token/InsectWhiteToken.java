package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class InsectWhiteToken extends TokenImpl {

    public InsectWhiteToken() {
        super("Insect Token", "2/1 white Insect creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(2);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
    }

    private InsectWhiteToken(final InsectWhiteToken token) {
        super(token);
    }

    public InsectWhiteToken copy() {
        return new InsectWhiteToken(this);
    }
}
