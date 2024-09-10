package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class DemonToken extends TokenImpl {

    public DemonToken() {
        super("Demon Token", "5/5 black Demon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
    }

    private DemonToken(final DemonToken token) {
        super(token);
    }

    @Override
    public DemonToken copy() {
        return new DemonToken(this);
    }
}
