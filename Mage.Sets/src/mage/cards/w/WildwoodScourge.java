package mage.cards.w;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildwoodScourge extends CardImpl {

    public WildwoodScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Wildwood Scourge enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // Whenever one or more +1/+1 counters are put on another non-Hydra creature you control, put a +1/+1 counter on Wildwood Scourge.
        this.addAbility(new WildwoodScourgeTriggeredAbility());
    }

    private WildwoodScourge(final WildwoodScourge card) {
        super(card);
    }

    @Override
    public WildwoodScourge copy() {
        return new WildwoodScourge(this);
    }
}

class WildwoodScourgeTriggeredAbility extends TriggeredAbilityImpl {

    WildwoodScourgeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private WildwoodScourgeTriggeredAbility(final WildwoodScourgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WildwoodScourgeTriggeredAbility copy() {
        return new WildwoodScourgeTriggeredAbility(this);
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
            return permanent != null
                    && !event.getTargetId().equals(this.getSourceId())
                    && permanent.isCreature(game)
                    && !permanent.hasSubtype(SubType.HYDRA, game)
                    && permanent.isControlledBy(this.getControllerId());
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are put on another non-Hydra creature you control, put a +1/+1 counter on {this}.";
    }
}
