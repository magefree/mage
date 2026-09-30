package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class KlingonWarriorToken extends TokenImpl {

    public KlingonWarriorToken() {
        super("Klingon Warrior Token", "2/1 red Klingon Warrior creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.KLINGON);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }

    private KlingonWarriorToken(final KlingonWarriorToken token) {
        super(token);
    }

    public KlingonWarriorToken copy() {
        return new KlingonWarriorToken(this);
    }
}
