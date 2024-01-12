package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.delayed.ManaSpentDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jimga150
 */
public final class DontMove extends CardImpl {

    // Based on Guan Yu's 1,000-Li March and Fire Giant's Fury
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creatures");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public DontMove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        

        // Destroy all tapped creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, false));

        // Until your next turn, whenever a creature becomes tapped, destroy it.
        this.getSpellAbility().addEffect(new DontMoveEffect());

        // Don't Move won't affect a creature that enters the battlefield tapped.
    }

    private DontMove(final DontMove card) {
        super(card);
    }

    @Override
    public DontMove copy() {
        return new DontMove(this);
    }
}

class DontMoveEffect extends OneShotEffect {

    public DontMoveEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Until your next turn, whenever a creature becomes tapped, destroy it.";
    }

    private DontMoveEffect(final DontMoveEffect effect) {
        super(effect);
    }

    @Override
    public DontMoveEffect copy() {
        return new DontMoveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new DontMoveAbility(), source);
        return true;
    }
}

class DontMoveAbility extends DelayedTriggeredAbility {

    public DontMoveAbility() {
        super(new DestroyTargetEffect(), Duration.UntilYourNextTurn, false);
    }

    private DontMoveAbility(final DontMoveAbility ability) {
        super(ability);
    }

    @Override
    public DelayedTriggeredAbility copy() {
        return new DontMoveAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isCreature()){
            return false;
        }
        this.getAllEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }
}