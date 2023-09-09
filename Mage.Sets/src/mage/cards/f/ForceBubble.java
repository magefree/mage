
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000 & L_J
 */
public final class ForceBubble extends CardImpl {

    public ForceBubble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // If damage would be dealt to you, put that many depletion counters on Force Bubble instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ForceBubbleReplacementEffect()));

        // When there are four or more depletion counters on Force Bubble, sacrifice it.
        this.addAbility(new ForceBubbleStateTriggeredAbility());

        // At the beginning of each end step, remove all depletion counters from Force Bubble.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new RemoveAllCountersSourceEffect(CounterType.DEPLETION), TargetController.ANY, false));
    }

    private ForceBubble(final ForceBubble card) {
        super(card);
    }

    @Override
    public ForceBubble copy() {
        return new ForceBubble(this);
    }
}

class ForceBubbleReplacementEffect extends ReplacementEffectImpl {

    ForceBubbleReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        staticText = "If damage would be dealt to you, put that many depletion counters on {this} instead";
    }

    private ForceBubbleReplacementEffect(final ForceBubbleReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        new AddCountersSourceEffect(CounterType.DEPLETION.createInstance(damageEvent.getAmount()), true).apply(game, source);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getControllerId());
    }

    @Override
    public ForceBubbleReplacementEffect copy() {
        return new ForceBubbleReplacementEffect(this);
    }
}

class ForceBubbleStateTriggeredAbility extends StateTriggeredAbility {

    public ForceBubbleStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    private ForceBubbleStateTriggeredAbility(final ForceBubbleStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ForceBubbleStateTriggeredAbility copy() {
        return new ForceBubbleStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.DEPLETION) >= 4;
    }

    @Override
    public String getRule() {
        return "When there are four or more depletion counters on {this}, sacrifice it.";
    }
}
