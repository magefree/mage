package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;
import java.util.Collections;

public class SamuraiDoubleStrikeToken extends TokenImpl {

    public SamuraiDoubleStrikeToken() {
        super("Samurai Token", "2/2 white Samurai creature token with double strike.");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SAMURAI);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(DoubleStrikeAbility.getInstance());

        availableImageSetCodes = Collections.singletonList("ONE");
    }

    private SamuraiDoubleStrikeToken(final SamuraiDoubleStrikeToken token) {
        super(token);
    }

    @Override
    public SamuraiDoubleStrikeToken copy() {
        return new SamuraiDoubleStrikeToken(this);
    }
}
