package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class VampireKnightToken extends TokenImpl {

    public VampireKnightToken() {
        super("Vampire Knight Token", "1/1 black Vampire Knight creature token with lifelink");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.VAMPIRE);
        subtype.add(SubType.KNIGHT);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(LifelinkAbility.getInstance());
    }

    public VampireKnightToken(final VampireKnightToken token) {
        super(token);
    }

    public VampireKnightToken copy() {
        return new VampireKnightToken(this);
    }
}
