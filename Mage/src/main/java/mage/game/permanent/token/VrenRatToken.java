package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 * @author PurpleCrowbar
 */
public final class VrenRatToken extends TokenImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("other Rat you control");

    static {
        filter.add(SubType.RAT.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public VrenRatToken() {
        super("Rat Token", "1/1 black Rat creature tokens with \"This creature gets +1/+1 for each other Rat you control\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.RAT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter),
                        new PermanentsOnBattlefieldCount(filter), Duration.WhileOnBattlefield
                )
        ));
    }

    private VrenRatToken(final VrenRatToken token) {
        super(token);
    }

    @Override
    public VrenRatToken copy() {
        return new VrenRatToken(this);
    }
}
