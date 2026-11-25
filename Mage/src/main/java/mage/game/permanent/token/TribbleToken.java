package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class TribbleToken extends TokenImpl {

    public TribbleToken() {
        super("Tribble Token", "1/1 white Tribble creature token with \"This token can't block.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.TRIBBLE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(new CantBlockAbility());
    }

    private TribbleToken(final TribbleToken token) {
        super(token);
    }

    @Override
    public TribbleToken copy() {
        return new TribbleToken(this);
    }
}
