package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class MoogleToken extends TokenImpl {

    public MoogleToken() {
        super("Moogle Token", "1/2 white Moogle creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.MOOGLE);
        power = new MageInt(1);
        toughness = new MageInt(2);

        addAbility(LifelinkAbility.getInstance());
    }

    private MoogleToken(final MoogleToken token) {
        super(token);
    }

    @Override
    public MoogleToken copy() {
        return new MoogleToken(this);
    }
}
