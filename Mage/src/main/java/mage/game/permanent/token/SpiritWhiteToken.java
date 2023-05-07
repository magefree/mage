package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author nantuko
 */
public final class SpiritWhiteToken extends TokenImpl {

    public SpiritWhiteToken() {
        super("Spirit Token", "1/1 white Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    public SpiritWhiteToken(final SpiritWhiteToken token) {
        super(token);
    }

    @Override
    public SpiritWhiteToken copy() {
        return new SpiritWhiteToken(this);
    }
}
