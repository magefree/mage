package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class LeviathanToken extends TokenImpl {

    public LeviathanToken() {
        super("Leviathan Token", "6/5 blue Leviathan creature token with hexproof");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.LEVIATHAN);
        color.setBlue(true);
        power = new MageInt(6);
        toughness = new MageInt(5);

        addAbility(HexproofAbility.getInstance());
    }

    private LeviathanToken(final LeviathanToken token) {
        super(token);
    }

    @Override
    public LeviathanToken copy() {
        return new LeviathanToken(this);
    }
}
