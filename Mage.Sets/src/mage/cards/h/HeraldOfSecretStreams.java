
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class HeraldOfSecretStreams extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures you control with +1/+1 counters on them");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public HeraldOfSecretStreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Creatures you control with +1/+1 counters on them can't be blocked.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedAllEffect(filter, Duration.WhileOnBattlefield)));
    }

    private HeraldOfSecretStreams(final HeraldOfSecretStreams card) {
        super(card);
    }

    @Override
    public HeraldOfSecretStreams copy() {
        return new HeraldOfSecretStreams(this);
    }
}
