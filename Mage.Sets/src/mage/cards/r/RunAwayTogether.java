package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RunAwayTogether extends CardImpl {

    public RunAwayTogether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose two target creatures controlled by different players. Return those creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect(true));
        this.getSpellAbility().addTarget(new RunAwayTogetherTarget());
    }

    private RunAwayTogether(final RunAwayTogether card) {
        super(card);
    }

    @Override
    public RunAwayTogether copy() {
        return new RunAwayTogether(this);
    }
}

class RunAwayTogetherTarget extends TargetCreaturePermanent {

    RunAwayTogetherTarget() {
        super(2, 2, StaticFilters.FILTER_PERMANENT_CREATURE, false);
    }

    private RunAwayTogetherTarget(final RunAwayTogetherTarget target) {
        super(target);
    }

    @Override
    public RunAwayTogetherTarget copy() {
        return new RunAwayTogetherTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Permanent creature = game.getPermanent(id);
        if (creature == null) {
            return false;
        }
        return !this.getTargets()
                .stream()
                .map(game::getPermanent)
                .anyMatch(permanent -> permanent != null
                        && !creature.getId().equals(permanent.getId())
                        && !creature.isControlledBy(permanent.getControllerId())
                );
    }
}
// give carly rae jepsen a sword