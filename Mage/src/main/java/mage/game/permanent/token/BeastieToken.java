package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.abilities.keyword.CantBlockAloneAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class BeastieToken extends TokenImpl {

    public BeastieToken() {
        super("Beast Token", "4/4 white Beast creature token with \"This creature can't attack or block alone.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(4);
        toughness = new MageInt(4);

        this.addAbility(new CantAttackAloneAbility());
        this.addAbility(CantBlockAloneAbility.getInstance());
    }

    private BeastieToken(final BeastieToken token) {
        super(token);
    }

    @Override
    public BeastieToken copy() {
        return new BeastieToken(this);
    }
}
