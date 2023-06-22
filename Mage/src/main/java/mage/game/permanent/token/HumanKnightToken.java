package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class HumanKnightToken extends TokenImpl {

    public HumanKnightToken() {
        super("Human Knight Token", "2/2 red Human Knight creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(TrampleAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    public HumanKnightToken(final HumanKnightToken token) {
        super(token);
    }

    @Override
    public HumanKnightToken copy() {
        return new HumanKnightToken(this);
    }
}
