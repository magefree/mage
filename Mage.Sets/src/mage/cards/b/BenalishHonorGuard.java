
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class BenalishHonorGuard extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("legendary creature you control");
    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public BenalishHonorGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Benalish Honor Guard gets +1/+0 for each legendary creature you control.
        PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(count, StaticValue.get(0), Duration.WhileOnBattlefield)));
    }

    private BenalishHonorGuard(final BenalishHonorGuard card) {
        super(card);
    }

    @Override
    public BenalishHonorGuard copy() {
        return new BenalishHonorGuard(this);
    }
}
