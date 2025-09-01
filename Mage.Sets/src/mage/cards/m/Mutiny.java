package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Set;
import java.util.UUID;

/**
 * TODO: combine with BreakingOfTheFellowship
 *
 * @author LevelX2
 */
public final class Mutiny extends CardImpl {

    public Mutiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Target creature an opponent controls deals damage equal to its power to another target creature that player controls.
        this.getSpellAbility().addEffect(new MutinyEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.getSpellAbility().addTarget(new MutinySecondTarget());
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterCreaturePermanent("another target creature that player controls")));
    }

    private Mutiny(final Mutiny card) {
        super(card);
    }

    @Override
    public Mutiny copy() {
        return new Mutiny(this);
    }
}

class MutinyEffect extends OneShotEffect {

    MutinyEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature an opponent controls deals damage equal to its power to another target creature that player controls";
    }

    private MutinyEffect(final MutinyEffect effect) {
        super(effect);
    }

    @Override
    public MutinyEffect copy() {
        return new MutinyEffect(this);
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

class MutinySecondTarget extends TargetPermanent {

    public MutinySecondTarget() {
        super(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
    }

    private MutinySecondTarget(final MutinySecondTarget target) {
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
    public MutinySecondTarget copy() {
        return new MutinySecondTarget(this);
    }
}
