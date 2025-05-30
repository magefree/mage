package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class InsectBlackGreenFlyingToken extends TokenImpl {

    public InsectBlackGreenFlyingToken() {
        super("Insect Token", "1/1 black and green Insect creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private InsectBlackGreenFlyingToken(final InsectBlackGreenFlyingToken token) {
        super(token);
    }

    public InsectBlackGreenFlyingToken copy() {
        return new InsectBlackGreenFlyingToken(this);
    }
}
