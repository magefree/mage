package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author fireshoes
 */
public final class ThopterColorlessToken extends TokenImpl {

    public ThopterColorlessToken() {
        super("Thopter Token", "1/1 colorless Thopter artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.THOPTER);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    public ThopterColorlessToken(final ThopterColorlessToken token) {
        super(token);
    }

    @Override
    public ThopterColorlessToken copy() {
        return new ThopterColorlessToken(this);
    }

}
