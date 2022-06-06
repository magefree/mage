package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

public final class BatToken extends TokenImpl {

    public BatToken() {
        super("Bat Token", "1/1 black Bat creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.BAT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("C17", "GVL", "GPT", "MMA", "M19", "MID", "VOC", "GK2");
    }

    public BatToken(final BatToken token) {
        super(token);
    }

    public BatToken copy() {
        return new BatToken(this);
    }
}
