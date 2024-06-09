package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class GolemTrampleToken extends TokenImpl {

    public GolemTrampleToken() {
        super("Golem Token", "3/3 colorless Golem artifact creature token with trample");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(TrampleAbility.getInstance());
    }

    private GolemTrampleToken(final GolemTrampleToken token) {
        super(token);
    }

    public GolemTrampleToken copy() {
        return new GolemTrampleToken(this);
    }
}
