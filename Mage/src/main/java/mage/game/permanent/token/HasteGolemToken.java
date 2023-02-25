package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author PurpleCrowbar
 */
public final class HasteGolemToken extends TokenImpl {

    public HasteGolemToken() {
        this(0);
    }

    public HasteGolemToken(int xValue) {
        super("Golem Token", "X/X colorless Golem artifact creature token with haste");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        this.addAbility(HasteAbility.getInstance());

        availableImageSetCodes = Arrays.asList("ONC");
    }

    public HasteGolemToken(final HasteGolemToken token) {
        super(token);
    }

    public HasteGolemToken copy() {
        return new HasteGolemToken(this);
    }
}
