package mage.cards.p;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PeltCollector extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1, 3);

    public PeltCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature you control enters the battlefield or dies, if that creature's power is greater than Pelt Collector's, put a +1/+1 counter on Pelt Collector.
        this.addAbility(new PeltCollectorTriggeredAbility());

        // As long as Pelt Collector has three or more +1/+1 counters on it, it has trample.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                        TrampleAbility.getInstance(), Duration.WhileOnBattlefield
                ), condition, "As long as {this} has three or more +1/+1 counters on it, it has trample.")
        ));
    }

    public PeltCollector(final PeltCollector card) {
        super(card);
    }

    @Override
    public PeltCollector copy() {
        return new PeltCollector(this);
    }
}

class PeltCollectorTriggeredAbility extends TriggeredAbilityImpl {

    PeltCollectorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
    }

    private PeltCollectorTriggeredAbility(PeltCollectorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD)
                || (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).isDiesEvent());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())
                || !event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        Permanent triggeringCreature = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (triggeringCreature == null) {
            return false;
        }
        this.getEffects().setValue("permanentEnteredOrDied", triggeringCreature);
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent sourcePerm = getSourcePermanentIfItStillExists(game);
        if (sourcePerm == null) {
            return false;
        }
        Permanent permanent = null;
        for (Effect effect : this.getEffects()) {
            Object obj = effect.getValue("permanentEnteredOrDied");
            if (obj instanceof Permanent) {
                permanent = (Permanent) obj;
                break;
            }
        }
        return permanent != null && permanent.getPower().getValue() > sourcePerm.getPower().getValue();
    }

    @Override
    public String getRule() {
        return "Whenever another creature you control enters the battlefield "
                + "or dies, if that creature's power is greater than {this}'s, "
                + "put a +1/+1 counter on {this}.";
    }

    @Override
    public PeltCollectorTriggeredAbility copy() {
        return new PeltCollectorTriggeredAbility(this);
    }
}
