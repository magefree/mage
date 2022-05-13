package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class OutlawsMerrimentWarriorToken extends TokenImpl {

    public OutlawsMerrimentWarriorToken() {
        super("Human Warrior Token", "3/1 Human Warrior with trample and haste");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.WARRIOR);
        color.setWhite(true);
        color.setRed(true);
        power = new MageInt(3);
        toughness = new MageInt(1);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private OutlawsMerrimentWarriorToken(final OutlawsMerrimentWarriorToken token) {
        super(token);
    }

    public OutlawsMerrimentWarriorToken copy() {
        return new OutlawsMerrimentWarriorToken(this);
    }
}
