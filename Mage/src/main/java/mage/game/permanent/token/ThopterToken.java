package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class ThopterToken extends TokenImpl {

    public ThopterToken() {
        super("Thopter Token", "1/1 blue Thopter artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.THOPTER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    protected ThopterToken(final ThopterToken token) {
        super(token);
    }

    public ThopterToken copy() {
        return new ThopterToken(this);
    }
}
