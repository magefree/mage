package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class ScarsOfTheVeteran extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a white card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }
    public ScarsOfTheVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");
        

        // You may exile a white card from your hand rather than pay Scars of the Veteran's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));

        // Prevent the next 7 damage that would be dealt to any target this turn. If it’s a creature, put a +0/+1 counter on it for each 1 damage prevented this way at the beginning of the next end step.
        this.getSpellAbility().addEffect(new SacredBoonPreventDamageTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ScarsOfTheVeteran(final ScarsOfTheVeteran card) {
        super(card);
    }

    @Override
    public ScarsOfTheVeteran copy() {
        return new ScarsOfTheVeteran(this);
    }
}

class ScarsOfTheVeteranPreventDamageTargetEffect extends PreventionEffectImpl {

    private int amount = 7;

    public ScarsOfTheVeteranPreventDamageTargetEffect(Duration duration) {
        super(duration);
        staticText = "Prevent the next 7 damage that would be dealt to any target this turn. If it’s a creature, put a +0/+1 counter on it for each 1 damage prevented this way at the beginning of the next end step.";
    }

    private ScarsOfTheVeteranPreventDamageTargetEffect(final ScarsOfTheVeteranPreventDamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public ScarsOfTheVeteranPreventDamageTargetEffect copy() {
        return new ScarsOfTheVeteranPreventDamageTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
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

            // add counters at end step
            if (prevented > 0) {
                Permanent targetPermanent = game.getPermanent(source.getTargets().getFirstTarget());
                if (targetPermanent != null && targetPermanent.isCreature(game)) {
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                            new AddCountersTargetEffect(CounterType.P0P1.createInstance(prevented))
                                    .setTargetPointer(new FixedTarget(targetPermanent, game)));
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                    game.informPlayers("Scars Of The Veteran: Prevented " + prevented + " damage ");
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
