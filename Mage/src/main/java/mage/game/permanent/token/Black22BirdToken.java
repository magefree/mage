package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class Black22BirdToken extends TokenImpl {

    public Black22BirdToken() {
        super("Bird Token", "2/2 black Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(2);
        toughness = new MageInt(2);

        addAbility(FlyingAbility.getInstance());
    }

    private Black22BirdToken(final Black22BirdToken token) {
        super(token);
    }

    @Override
    public Black22BirdToken copy() {
        return new Black22BirdToken(this);
    }
}
