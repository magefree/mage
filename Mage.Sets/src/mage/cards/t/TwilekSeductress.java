
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Styxo
 */
public final class TwilekSeductress extends CardImpl {

    public TwilekSeductress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.TWILEK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {this} must be blocked it if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // Whenever {this} becomes blocked by a creature, put a bounty counter on that creature
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), false));
    }

    private TwilekSeductress(final TwilekSeductress card) {
        super(card);
    }

    @Override
    public TwilekSeductress copy() {
        return new TwilekSeductress(this);
    }
}
