package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Set;
import java.util.UUID;

/**
 * TODO: combine with Mutiny
 *
 * @author Susucr
 */
public final class BreakingOfTheFellowship extends CardImpl {

    public BreakingOfTheFellowship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Target creature an opponent controls deals damage equal to its power to another target creature that player controls.
        this.getSpellAbility().addEffect(new BreakingOfTheFellowshipEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.getSpellAbility().addTarget(new BreakingOfTheFellowshipSecondTarget());

        // The Ring tempts you.
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
    }

    private BreakingOfTheFellowship(final BreakingOfTheFellowship card) {
        super(card);
    }

    @Override
    public BreakingOfTheFellowship copy() {
        return new BreakingOfTheFellowship(this);
    }
}

class BreakingOfTheFellowshipEffect extends OneShotEffect {

    BreakingOfTheFellowshipEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature an opponent controls deals damage equal to its power to another target creature that player controls";
    }

    private BreakingOfTheFellowshipEffect(final BreakingOfTheFellowshipEffect effect) {
        super(effect);
    }

    @Override
    public BreakingOfTheFellowshipEffect copy() {
        return new BreakingOfTheFellowshipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent firstTarget = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (firstTarget != null) {
            int damage = firstTarget.getPower().getValue();
            Permanent secondTarget = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (damage > 0 && secondTarget != null) {
                secondTarget.damage(damage, firstTarget.getId(), source, game);
            }
        }
        return true;
    }
}

class BreakingOfTheFellowshipSecondTarget extends TargetPermanent {

    public BreakingOfTheFellowshipSecondTarget() {
        super(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
    }

    private BreakingOfTheFellowshipSecondTarget(final BreakingOfTheFellowshipSecondTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);

        Permanent firstTarget = game.getPermanent(source.getFirstTarget());
        if (firstTarget == null) {
            // playable or first target not yet selected
            // use all
            if (possibleTargets.size() == 1) {
                // workaround to make 1 target invalid
                possibleTargets.clear();
            }
        } else {
            // real
            // filter by same player
            possibleTargets.removeIf(id -> {
                Permanent permanent = game.getPermanent(id);
                return permanent == null || !permanent.isControlledBy(firstTarget.getControllerId());
            });
        }
        possibleTargets.removeIf(id -> firstTarget != null && firstTarget.getId().equals(id));

        return possibleTargets;
    }

    @Override
    public BreakingOfTheFellowshipSecondTarget copy() {
        return new BreakingOfTheFellowshipSecondTarget(this);
    }
}
