
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class EnduringScalelord extends CardImpl {

    public EnduringScalelord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{W}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more +1/+1 counters are put on another creature you control, you may put a +1/+1 counter on Enduring Scaleguard.
        this.addAbility(new EnduringScalelordTriggeredAbility());

    }

    private EnduringScalelord(final EnduringScalelord card) {
        super(card);
    }

    @Override
    public EnduringScalelord copy() {
        return new EnduringScalelord(this);
    }
}

class EnduringScalelordTriggeredAbility extends TriggeredAbilityImpl {

    EnduringScalelordTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
    }

    private EnduringScalelordTriggeredAbility(final EnduringScalelordTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EnduringScalelordTriggeredAbility copy() {
        return new EnduringScalelordTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.P1P1.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            return (permanent != null
                    && !event.getTargetId().equals(this.getSourceId())
                    && permanent.isCreature(game)
                    && permanent.isControlledBy(this.getControllerId()));
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are put on another creature you control, you may put a +1/+1 counter on {this}.";
    }
}
