package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class TheVoidToken extends TokenImpl {

    public TheVoidToken() {
        super("The Void", "The Void, a legendary 5/5 black Horror Villain creature token with flying, indestructible, and \"The Void attacks each combat if able.\"");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HORROR);
        subtype.add(SubType.VILLAIN);

        color.setBlack(true);
        power = new MageInt(5);
        toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(IndestructibleAbility.getInstance());
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private TheVoidToken(final TheVoidToken token) {
        super(token);
    }

    public TheVoidToken copy() {
        return new TheVoidToken(this);
    }
}
