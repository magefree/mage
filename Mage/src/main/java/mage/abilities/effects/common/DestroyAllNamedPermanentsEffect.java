package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SharesNamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DestroyAllNamedPermanentsEffect extends OneShotEffect {

    public DestroyAllNamedPermanentsEffect() {
        super(Outcome.DestroyPermanent);
    }

    protected DestroyAllNamedPermanentsEffect(final DestroyAllNamedPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public DestroyAllNamedPermanentsEffect copy() {
        return new DestroyAllNamedPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetPermanent == null) {
            return false;
        }
        FilterPermanent filter = new FilterPermanent();
        filter.add(Predicates.or(
                new SharesNamePredicate(targetPermanent),
                new PermanentIdPredicate(targetPermanent.getId())
        ));
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            perm.destroy(source, game, false);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Destroy " + getTargetPointer().describeTargets(mode.getTargets(), "that permanent")
                + " and all other permanents with the same name as that permanent";
    }

}
