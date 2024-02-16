
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author L_J
 */
public final class RatColony extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other Rat you control");

    static {
        filter.add(SubType.RAT.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public RatColony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Rat Colony gets +1/+0 for each other Rat you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter),
                        StaticValue.get(0), Duration.WhileOnBattlefield)));

        // A deck can have any number of cards named Rat Colony.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("A deck can have any number of cards named Rat Colony.")));
    }

    private RatColony(final RatColony card) {
        super(card);
    }

    @Override
    public RatColony copy() {
        return new RatColony(this);
    }
}
