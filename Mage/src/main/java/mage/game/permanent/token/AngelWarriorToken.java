package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

public final class AngelWarriorToken extends TokenImpl {

    public AngelWarriorToken() {
        super("Angel Warrior Token", "4/4 white Angel Warrior creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(4);
        toughness = new MageInt(4);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("ZNR");
    }

    public AngelWarriorToken(final AngelWarriorToken token) {
        super(token);
    }

    public AngelWarriorToken copy() {
        return new AngelWarriorToken(this);
    }
}
