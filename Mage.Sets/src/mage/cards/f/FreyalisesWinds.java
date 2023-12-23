package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class FreyalisesWinds extends CardImpl {

    public FreyalisesWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // Whenever a permanent becomes tapped, put a wind counter on it.
        Effect effect = new AddCountersTargetEffect(CounterType.WIND.createInstance());
        effect.setText("put a wind counter on it.");
        this.addAbility(new BecomesTappedTriggeredAbility(effect, false, new FilterPermanent("a permanent"), true));

        // If a permanent with a wind counter on it would untap during its controller's untap step, remove all wind counters from it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FreyalisesWindsReplacementEffect()));

    }

    private FreyalisesWinds(final FreyalisesWinds card) {
        super(card);
    }

    @Override
    public FreyalisesWinds copy() {
        return new FreyalisesWinds(this);
    }
}

class FreyalisesWindsReplacementEffect extends ReplacementEffectImpl {

    FreyalisesWindsReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a permanent with a wind counter on it would untap during its controller's untap step, remove all wind counters from it instead";
    }

    private FreyalisesWindsReplacementEffect(final FreyalisesWindsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public FreyalisesWindsReplacementEffect copy() {
        return new FreyalisesWindsReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanentUntapping = game.getPermanent(event.getTargetId());
        if (permanentUntapping != null) {
            permanentUntapping.getCounters(game).removeAllCounters(CounterType.WIND);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.UNTAP
                && game.getPhase().getStep().getType() == PhaseStep.UNTAP);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanentUntapping = game.getPermanent(event.getTargetId());
        return (permanentUntapping != null
                && event.getPlayerId().equals(permanentUntapping.getControllerId())
                && permanentUntapping.getCounters(game).getCount(CounterType.WIND) > 0);
    }
}
