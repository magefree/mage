package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/*
* @author muz
*/
public final class BlueAngelToken extends TokenImpl {

    public BlueAngelToken() {
        super("Angel Token", "3/3 blue Angel creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(FlyingAbility.getInstance());
    }

    private BlueAngelToken(final BlueAngelToken token) {
        super(token);
    }

    public BlueAngelToken copy() {
        return new BlueAngelToken(this);
    }
}
