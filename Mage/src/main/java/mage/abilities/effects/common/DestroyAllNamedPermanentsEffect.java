package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

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
        if (CardUtil.haveEmptyName(targetPermanent)) {
            filter.add(new PermanentIdPredicate(targetPermanent.getId()));  // if no name (face down creature) only the creature itself is selected
        } else {
            filter.add(new NamePredicate(targetPermanent.getName()));
        }
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            perm.destroy(source, game, false);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Destroy target " + mode.getTargets().get(0).getTargetName() + " and all other permanents with the same name as that permanent";
    }

}
