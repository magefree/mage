package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class InsectColorlessToken extends TokenImpl {

    public InsectColorlessToken() {
        super("Insect Token", "1/1 colorless Insect creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("40K");
    }

    public InsectColorlessToken(final InsectColorlessToken token) {
        super(token);
    }

    public InsectColorlessToken copy() {
        return new InsectColorlessToken(this);
    }
}