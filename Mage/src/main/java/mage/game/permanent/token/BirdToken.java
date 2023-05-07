package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class BirdToken extends TokenImpl {

    public BirdToken() {
        super("Bird Token", "1/1 white Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    public BirdToken(final BirdToken token) {
        super(token);
    }

    @Override
    public BirdToken copy() {
        return new BirdToken(this);
    }
}
