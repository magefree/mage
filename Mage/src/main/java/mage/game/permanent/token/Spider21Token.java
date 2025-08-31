package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Spider21Token extends TokenImpl {

    public Spider21Token() {
        super("Spider Token", "2/1 green Spider creature token with reach");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SPIDER);
        power = new MageInt(2);
        toughness = new MageInt(1);

        this.addAbility(ReachAbility.getInstance());
    }

    private Spider21Token(final Spider21Token token) {
        super(token);
    }

    public Spider21Token copy() {
        return new Spider21Token(this);
    }
}
