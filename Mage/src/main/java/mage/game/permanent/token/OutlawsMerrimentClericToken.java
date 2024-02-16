package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class OutlawsMerrimentClericToken extends TokenImpl {

    public OutlawsMerrimentClericToken() {
        super("Human Cleric Token", "2/1 Human Cleric with lifelink and haste");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.CLERIC);
        color.setWhite(true);
        color.setRed(true);
        power = new MageInt(2);
        toughness = new MageInt(1);

        this.addAbility(LifelinkAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private OutlawsMerrimentClericToken(final OutlawsMerrimentClericToken token) {
        super(token);
    }

    public OutlawsMerrimentClericToken copy() {
        return new OutlawsMerrimentClericToken(this);
    }
}
