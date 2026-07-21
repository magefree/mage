package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class Robot11HeroToken extends TokenImpl {

    public Robot11HeroToken() {
        super("Robot Hero Token", "1/1 colorless Robot Hero artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT);
        subtype.add(SubType.HERO);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private Robot11HeroToken(final Robot11HeroToken token) {
        super(token);
    }

    public Robot11HeroToken copy() {
        return new Robot11HeroToken(this);
    }
}
