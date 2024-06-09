

package mage.abilities.effects;

import mage.constants.Outcome;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class SearchEffect extends OneShotEffect {

    protected TargetCardInLibrary target;

    public SearchEffect(TargetCardInLibrary target, Outcome outcome) {
        super(outcome);
        this.target = target;
    }

    protected SearchEffect(final SearchEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    public TargetCard getTarget() {
        return target;
    }
}
