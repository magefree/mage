package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class GolemFlyingToken extends TokenImpl {

    public GolemFlyingToken() {
        super("Golem Token", "3/3 colorless Golem artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(FlyingAbility.getInstance());
    }

    private GolemFlyingToken(final GolemFlyingToken token) {
        super(token);
    }

    public GolemFlyingToken copy() {
        return new GolemFlyingToken(this);
    }
}
