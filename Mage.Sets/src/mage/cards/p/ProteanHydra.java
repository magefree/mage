
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ProteanHydra extends CardImpl {

    public ProteanHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{X}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Protean Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // If damage would be dealt to Protean Hydra, prevent that damage and remove that many +1/+1 counters from it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ProteanHydraEffect2()));

        // Whenever a +1/+1 counter is removed from Protean Hydra, put two +1/+1 counters on it at the beginning of the next end step.
        this.addAbility(new ProteanHydraAbility());

    }

    public ProteanHydra(final ProteanHydra card) {
        super(card);
    }

    @Override
    public ProteanHydra copy() {
        return new ProteanHydra(this);
    }

    static class ProteanHydraEffect2 extends PreventionEffectImpl {

        public ProteanHydraEffect2() {
            super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
            staticText = "If damage would be dealt to {this}, prevent that damage and remove that many +1/+1 counters from it";
        }

        public ProteanHydraEffect2(final ProteanHydraEffect2 effect) {
            super(effect);
        }

        @Override
        public ProteanHydraEffect2 copy() {
            return new ProteanHydraEffect2(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return true;
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            int damage = event.getAmount();
            preventDamageAction(event, source, game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.removeCounters(CounterType.P1P1.createInstance(damage), game); //MTG ruling Protean Hydra loses counters even if the damage isn't prevented
            }
            return false;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            if (super.applies(event, source, game)) {
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
            return false;
        }

    }

    class ProteanHydraAbility extends TriggeredAbilityImpl {

        public ProteanHydraAbility() {
            super(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(new ProteanHydraDelayedTriggeredAbility()), false);
        }

        public ProteanHydraAbility(final ProteanHydraAbility ability) {
            super(ability);
        }

        @Override
        public ProteanHydraAbility copy() {
            return new ProteanHydraAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == EventType.COUNTER_REMOVED;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getData().equals("+1/+1") && event.getTargetId().equals(this.getSourceId())) {
                return true;
            }
            return false;
        }

        @Override
        public String getRule() {
            return "Whenever a +1/+1 counter is removed from {this}, put two +1/+1 counters on it at the beginning of the next end step.";
        }

    }

    static class ProteanHydraDelayedTriggeredAbility extends DelayedTriggeredAbility {

        public ProteanHydraDelayedTriggeredAbility() {
            super(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)));
        }

        public ProteanHydraDelayedTriggeredAbility(final ProteanHydraDelayedTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public ProteanHydraDelayedTriggeredAbility copy() {
            return new ProteanHydraDelayedTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == EventType.END_TURN_STEP_PRE;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return true;
        }

        @Override
        public String getRule() {
            return "Put two +1/+1 counters on {this} at the beginning of the next end step";
        }

    }
}
