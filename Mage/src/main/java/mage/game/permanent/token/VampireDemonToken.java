package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class VampireDemonToken extends TokenImpl {

    public VampireDemonToken() {
        super("Vampire Demon Token", "4/3 white and black Vampire Demon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        subtype.add(SubType.DEMON);
        power = new MageInt(4);
        toughness = new MageInt(3);

        addAbility(FlyingAbility.getInstance());
    }

    protected VampireDemonToken(final VampireDemonToken token) {
        super(token);
    }

    @Override
    public VampireDemonToken copy() {
        return new VampireDemonToken(this);
    }
}
