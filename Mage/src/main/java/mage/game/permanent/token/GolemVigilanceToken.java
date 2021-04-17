package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class GolemVigilanceToken extends TokenImpl {

    public GolemVigilanceToken() {
        super("Golem", "3/3 colorless Golem artifact creature token with vigilance");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(3);
        toughness = new MageInt(3);
        addAbility(VigilanceAbility.getInstance());
    }

    private GolemVigilanceToken(final GolemVigilanceToken token) {
        super(token);
    }

    public GolemVigilanceToken copy() {
        return new GolemVigilanceToken(this);
    }
}
