package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class InsectWarriorToken extends TokenImpl {

    public InsectWarriorToken() {
        super("Insect Warrior Token", "1/1 black Insect Warrior creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.INSECT);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    private InsectWarriorToken(final InsectWarriorToken token) {
        super(token);
    }

    public InsectWarriorToken copy() {
        return new InsectWarriorToken(this);
    }
}
