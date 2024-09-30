package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Spider22Token extends TokenImpl {

    public Spider22Token() {
        super("Spider Token", "2/2 green Spider creature token with reach");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SPIDER);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(ReachAbility.getInstance());
    }

    private Spider22Token(final Spider22Token token) {
        super(token);
    }

    public Spider22Token copy() {
        return new Spider22Token(this);
    }
}
