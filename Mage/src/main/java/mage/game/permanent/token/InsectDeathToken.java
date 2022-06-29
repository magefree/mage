package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class InsectDeathToken extends TokenImpl {

    public InsectDeathToken() {
        super("Insect Token", "1/1 green Insect creature token with flying and deathtouch");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
        addAbility(DeathtouchAbility.getInstance());

        availableImageSetCodes = Arrays.asList("M15", "C20", "C21", "CLB");
    }

    public InsectDeathToken(final InsectDeathToken token) {
        super(token);
    }

    public InsectDeathToken copy() {
        return new InsectDeathToken(this);
    }
}
