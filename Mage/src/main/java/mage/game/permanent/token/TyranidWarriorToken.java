package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class TyranidWarriorToken extends TokenImpl {

    public TyranidWarriorToken() {
        super("Tyranid Warrior Token", "3/3 green Tyranid Warrior creature token with trample");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.TYRANID);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(3);
        toughness = new MageInt(3);
        addAbility(TrampleAbility.getInstance());
    }

    public TyranidWarriorToken(final TyranidWarriorToken token) {
        super(token);
    }

    @Override
    public TyranidWarriorToken copy() {
        return new TyranidWarriorToken(this);
    }
}
