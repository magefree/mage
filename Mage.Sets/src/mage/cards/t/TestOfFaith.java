
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TestOfFaith extends CardImpl {

    public TestOfFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Prevent the next 3 damage that would be dealt to target creature this turn. For each 1 damage prevented this way, put a +1/+1 counter on that creature.
        this.getSpellAbility().addEffect(new TestOfFaithPreventDamageTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TestOfFaith(final TestOfFaith card) {
        super(card);
    }

    @Override
    public TestOfFaith copy() {
        return new TestOfFaith(this);
    }
}

class TestOfFaithPreventDamageTargetEffect extends PreventionEffectImpl {

    private int amount = 3;

    public TestOfFaithPreventDamageTargetEffect(Duration duration) {
        super(duration);
        staticText = "Prevent the next 3 damage that would be dealt to target creature this turn. For each 1 damage prevented this way, put a +1/+1 counter on that creature";
    }

    public TestOfFaithPreventDamageTargetEffect(final TestOfFaithPreventDamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public TestOfFaithPreventDamageTargetEffect copy() {
        return new TestOfFaithPreventDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int prevented = 0;
            if (event.getAmount() >= this.amount) {
                int damage = amount;
                event.setAmount(event.getAmount() - amount);
                this.used = true;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
                prevented = damage;
            } else {
                int damage = event.getAmount();
                event.setAmount(0);
                amount -= damage;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
                prevented = damage;
            }

            // add counters now
            if (prevented > 0) {
                Permanent targetPermanent = game.getPermanent(source.getTargets().getFirstTarget());
                if (targetPermanent != null) {
                    targetPermanent.addCounters(CounterType.P1P1.createInstance(prevented), source, game);
                    game.informPlayers(new StringBuilder("Test of Faith: Prevented ").append(prevented).append(" damage ").toString());
                    game.informPlayers("Test of Faith: Adding " + prevented + " +1/+1 counters to " + targetPermanent.getName());
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            if (source.getTargets().getFirstTarget().equals(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }

}
