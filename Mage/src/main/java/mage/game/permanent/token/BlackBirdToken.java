package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author weirddan455
 */
public final class BlackBirdToken extends TokenImpl {

    public BlackBirdToken() {
        super("Bird Token", "1/1 black Bird creature token with flying and \"This creature can't block.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
        addAbility(new CantBlockAbility());
    }

    private BlackBirdToken(final BlackBirdToken token) {
        super(token);
    }

    @Override
    public BlackBirdToken copy() {
        return new BlackBirdToken(this);
    }
}
