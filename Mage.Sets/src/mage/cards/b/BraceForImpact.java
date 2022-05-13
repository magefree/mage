package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
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
public final class BraceForImpact extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("multicolored creature");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public BraceForImpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Prevent all damage that would be dealt to target multicolored creature this turn. For each 1 damage prevented this way, put a +1/+1 counter on that creature.
        this.getSpellAbility().addEffect(new BraceForImpactPreventDamageTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private BraceForImpact(final BraceForImpact card) {
        super(card);
    }

    @Override
    public BraceForImpact copy() {
        return new BraceForImpact(this);
    }
}

class BraceForImpactPreventDamageTargetEffect extends PreventionEffectImpl {

    public BraceForImpactPreventDamageTargetEffect(Duration duration) {
        super(duration);
        staticText = "Prevent all damage that would be dealt to target multicolored creature this turn. For each 1 damage prevented this way, put a +1/+1 counter on that creature";
    }

    public BraceForImpactPreventDamageTargetEffect(final BraceForImpactPreventDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public BraceForImpactPreventDamageTargetEffect copy() {
        return new BraceForImpactPreventDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (game.replaceEvent(preventEvent)) { return false; }
        int prevented;
        int damage = event.getAmount();
        event.setAmount(0);
        game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
        prevented = damage;

        // add counters now
        if (prevented > 0) {
            Permanent targetPermanent = game.getPermanent(source.getTargets().getFirstTarget());
            if (targetPermanent != null) {
                targetPermanent.addCounters(CounterType.P1P1.createInstance(prevented), source.getControllerId(), source, game);
                game.informPlayers("Brace for Impact: Prevented " + prevented + " damage ");
                game.informPlayers("Brace for Impact: Adding " + prevented + " +1/+1 counters to " + targetPermanent.getName());
            }
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            return source.getTargets().getFirstTarget().equals(event.getTargetId());
        }
        return false;
    }

}
