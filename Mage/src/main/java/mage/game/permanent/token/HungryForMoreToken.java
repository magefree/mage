package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class HungryForMoreToken extends TokenImpl {

    public HungryForMoreToken() {
        super("Vampire", "3/1 black and red Vampire creature token with trample, lifelink, and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(3);
        toughness = new MageInt(1);
        addAbility(TrampleAbility.getInstance());
        addAbility(LifelinkAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    public HungryForMoreToken(final HungryForMoreToken token) {
        super(token);
    }

    public HungryForMoreToken copy() {
        return new HungryForMoreToken(this);
    }
}
