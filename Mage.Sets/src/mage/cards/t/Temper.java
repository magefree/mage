package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Temper extends CardImpl {

    public Temper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{1}{W}");

        // Prevent the next X damage that would be dealt to target creature this turn. For each 1 damage prevented this way, put a +1/+1 counter on that creature.
        this.getSpellAbility().addEffect(new TemperPreventDamageTargetEffect(ManacostVariableValue.REGULAR, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Temper(final Temper card) {
        super(card);
    }

    @Override
    public Temper copy() {
        return new Temper(this);
    }
}

class TemperPreventDamageTargetEffect extends PreventionEffectImpl {

    private int amount;
    private final DynamicValue dVal;
    private boolean initialized;

    public TemperPreventDamageTargetEffect(DynamicValue dVal, Duration duration) {
        super(duration);
        this.initialized = false;
        this.dVal = dVal;
        staticText = "Prevent the next X damage that would be dealt to target creature this turn. For each 1 damage prevented this way, put a +1/+1 counter on that creature";
    }

    private TemperPreventDamageTargetEffect(final TemperPreventDamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.dVal = effect.dVal;
        this.initialized = effect.initialized;
    }

    @Override
    public TemperPreventDamageTargetEffect copy() {
        return new TemperPreventDamageTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (!initialized) {
            amount = dVal.calculate(game, source, this);
            initialized = true;
        }
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            int prevented = 0;
            if (event.getAmount() >= this.amount) {
                int damage = amount;
                event.setAmount(event.getAmount() - amount);
                this.used = true;
                game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
                prevented = damage;
            } else {
                int damage = event.getAmount();
                event.setAmount(0);
                amount -= damage;
                game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
                prevented = damage;
            }

            // add counters now
            if (prevented > 0) {
                Permanent targetPermanent = game.getPermanent(source.getTargets().getFirstTarget());
                if (targetPermanent != null) {
                    targetPermanent.addCounters(CounterType.P1P1.createInstance(prevented), source.getControllerId(), source, game);
                    game.informPlayers("Temper: Prevented " + prevented + " damage ");
                    game.informPlayers("Temper: Adding " + prevented + " +1/+1 counters to " + targetPermanent.getName());
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            return source.getTargets().getFirstTarget().equals(event.getTargetId());
        }
        return false;
    }

}
