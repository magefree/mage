package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public class ThopterColorlessToken2 extends TokenImpl {

    public ThopterColorlessToken2() {
        super("Thopter Token", "0/0 colorless Thopter artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.THOPTER);
        power = new MageInt(0);
        toughness = new MageInt(0);

        addAbility(FlyingAbility.getInstance());
    }

    private ThopterColorlessToken2(final ThopterColorlessToken2 token) {
        super(token);
    }

    @Override
    public ThopterColorlessToken2 copy() {
        return new ThopterColorlessToken2(this);
    }
}
