package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class BlueBirdToken extends TokenImpl {

    public BlueBirdToken() {
        super("Bird Token", "1/1 blue Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("EVE", "INV", "KHM");
    }

    public BlueBirdToken(final BlueBirdToken token) {
        super(token);
    }

    public BlueBirdToken copy() {
        return new BlueBirdToken(this);
    }
}
