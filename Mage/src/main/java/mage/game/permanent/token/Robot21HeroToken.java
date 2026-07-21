package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class Robot21HeroToken extends TokenImpl {

    public Robot21HeroToken() {
        super("Robot Hero Token", "2/1 colorless Robot Hero artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ROBOT);
        subtype.add(SubType.HERO);
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private Robot21HeroToken(final Robot21HeroToken token) {
        super(token);
    }

    public Robot21HeroToken copy() {
        return new Robot21HeroToken(this);
    }
}
