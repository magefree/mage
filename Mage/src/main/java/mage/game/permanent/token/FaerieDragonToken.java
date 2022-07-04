package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 *
 * @author weirddan455
 */
public class FaerieDragonToken extends TokenImpl {

    public FaerieDragonToken() {
        super("Faerie Dragon Token", "1/1 blue Faerie Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.FAERIE);
        subtype.add(SubType.DRAGON);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AFR", "CLB");
    }

    private FaerieDragonToken(final FaerieDragonToken token) {
        super(token);
    }

    @Override
    public FaerieDragonToken copy() {
        return new FaerieDragonToken(this);
    }
}
