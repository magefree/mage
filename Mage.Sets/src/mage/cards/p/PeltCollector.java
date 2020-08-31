package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class PeltCollector extends CardImpl {

    public PeltCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature you control enters the battlefield or dies, if that creature's power is greater than Pelt Collector's, put a +1/+1 counter on Pelt Collector.
        this.addAbility(new PeltCollectorAbility());

        // As long as Pelt Collector has three or more +1/+1 counters on it, it has trample.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                TrampleAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), new SourceHasCounterCondition(CounterType.P1P1, 3),
                        "As long as {this} has three or more +1/+1 "
                        + "counters on it, it has trample."
                )
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

class PeltCollectorAbility extends TriggeredAbilityImpl {

    public PeltCollectorAbility() {
        super(Zone.BATTLEFIELD, new PeltCollectorEffect());
    }

    public PeltCollectorAbility(PeltCollectorAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) 
                || (event.getType() == GameEvent.EventType.ZONE_CHANGE 
                && ((ZoneChangeEvent) event).isDiesEvent()) ; 
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        Permanent triggeringCreature = game.getPermanentOrLKIBattlefield(event.getTargetId());
        Permanent sourceCreature = game.getPermanent(this.getSourceId());
        if (isPowerGreater(sourceCreature, triggeringCreature)
                && triggeringCreature.isCreature()
                && triggeringCreature.isControlledBy(this.getControllerId())) {
            this.getEffects().setTargetPointer(new FixedTarget(triggeringCreature, game));
            return true;
        }
        return false;
    }

    public static boolean isPowerGreater(Permanent sourceCreature, Permanent newCreature) {
        return sourceCreature != null && newCreature != null
                && newCreature.getPower().getValue()
                > sourceCreature.getPower().getValue();
    }

    @Override
    public String getRule() {
        return "Whenever another creature you control enters the battlefield "
                + "or dies, if that creature's power is greater than {this}'s, "
                + "put a +1/+1 counter on {this}.";
    }

    @Override
    public PeltCollectorAbility copy() {
        return new PeltCollectorAbility(this);
    }
}

class PeltCollectorEffect extends OneShotEffect {

    public PeltCollectorEffect() {
        super(Outcome.BoostCreature);
    }

    public PeltCollectorEffect(final PeltCollectorEffect effect) {
        super(effect);
    }

    @Override
    public PeltCollectorEffect copy() {
        return new PeltCollectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeringCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Permanent sourceCreature = game.getPermanent(source.getSourceId());
        if (!PeltCollectorAbility.isPowerGreater(sourceCreature, triggeringCreature)) {
            return false;
        }
        return new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
    }
}
