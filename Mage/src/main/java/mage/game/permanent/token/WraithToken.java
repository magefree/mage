package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author PurpleCrowbar
 */
public final class WraithToken extends TokenImpl {

    public WraithToken() {
        super("Wraith Token", "3/3 black Wraith creature token with menace");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.WRAITH);
        addAbility(new MenaceAbility());
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    public WraithToken(final WraithToken token) {
        super(token);
    }

    @Override
    public WraithToken copy() {
        return new WraithToken(this);
    }
}
