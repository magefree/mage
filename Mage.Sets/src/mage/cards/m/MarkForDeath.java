package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class MarkForDeath extends CardImpl {

    public MarkForDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Target creature an opponent controls blocks this turn if able. Untap that creature. Other creatures that player controls can't block this turn.
        this.getSpellAbility().addEffect(new MarkForDeathEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
    }

    private MarkForDeath(final MarkForDeath card) {
        super(card);
    }

    @Override
    public MarkForDeath copy() {
        return new MarkForDeath(this);
    }
}

class MarkForDeathEffect extends OneShotEffect {

    public MarkForDeathEffect() {
        super(Outcome.Damage);
        staticText = "Target creature an opponent controls blocks this turn if able. Untap that creature. Other creatures that player controls can't block this turn";
    }

    public MarkForDeathEffect(final MarkForDeathEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(target.getControllerId()));
        filter.add(Predicates.not(new CardIdPredicate(target.getId())));
        
        ContinuousEffect effect = new BlocksIfAbleTargetEffect(Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(target.getId()));
        game.addEffect(effect, source);
        
        target.untap(game);
        
        ContinuousEffect effect2 = new CantBlockAllEffect(filter, Duration.EndOfTurn);
        game.addEffect(effect2, source);
        return true;
    }

    @Override
    public MarkForDeathEffect copy() {
        return new MarkForDeathEffect(this);
    }
}
