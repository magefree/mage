package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
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

    DontMoveEffect() {
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
        game.addDelayedTriggeredAbility(new DontMoveAbility(game.getTurnNum()), source);
        return true;
    }
}

// Instead of using Duration.UntilYourNextTurn, since currently DelayedTriggeredAbility does not support checking for
// this, instead this subclass will manually check for the end of this trigger's life by tracking the turn number
// and ending when next the game circles back to the casting player, after the turn number has changed.
// This workaround was taken directly from the diff helpfully provided by michaelstephendavies in issue #2078:
// https://github.com/magefree/mage/issues/2078
class DontMoveAbility extends DelayedTriggeredAbility {

    private final int startingTurn;

    public DontMoveAbility(int startingTurn) {
        super(new DestroyTargetEffect(), Duration.Custom, false);
        this.startingTurn = startingTurn;
    }

    private DontMoveAbility(final DontMoveAbility ability) {
        super(ability);
        this.startingTurn = ability.startingTurn;
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
        if (permanent == null || !permanent.isCreature(game)){
            return false;
        }
        this.getAllEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }

    @Override
    public boolean isInactive(Game game) {
        return game.getActivePlayerId().equals(getControllerId()) && game.getTurnNum() != startingTurn;
    }
}
