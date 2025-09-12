package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 * @author PurpleCrowbar
 */
public final class VrenRatToken extends TokenImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.RAT, "other Rat you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public VrenRatToken() {
        super("Rat Token", "1/1 black Rat creature token with \"This token gets +1/+1 for each other Rat you control.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.RAT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)));
    }

    private VrenRatToken(final VrenRatToken token) {
        super(token);
    }

    @Override
    public VrenRatToken copy() {
        return new VrenRatToken(this);
    }
}
