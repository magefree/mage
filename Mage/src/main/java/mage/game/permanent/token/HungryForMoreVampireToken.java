package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class HungryForMoreVampireToken extends TokenImpl {

    public HungryForMoreVampireToken() {
        super("Vampire Token", "3/1 black and red Vampire creature token with trample, lifelink, and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(3);
        toughness = new MageInt(1);
        addAbility(TrampleAbility.getInstance());
        addAbility(LifelinkAbility.getInstance());
        addAbility(HasteAbility.getInstance());

        availableImageSetCodes.addAll(Arrays.asList("MID"));
    }

    public HungryForMoreVampireToken(final HungryForMoreVampireToken token) {
        super(token);
    }

    public HungryForMoreVampireToken copy() {
        return new HungryForMoreVampireToken(this);
    }
}
