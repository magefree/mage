
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 * @author BetaSteward_at_googlemail.com
 *
 */
public class ChooseNewTargetsTargetEffect extends OneShotEffect {

    private boolean forceChange;
    private boolean onlyOneTarget;
    private FilterPermanent filterNewTarget;

    public ChooseNewTargetsTargetEffect() {
        this(false, false);
    }

    public ChooseNewTargetsTargetEffect(boolean forceChange, boolean onlyOneTarget) {
        this(forceChange, onlyOneTarget, null);
    }

    /**
     *
     * @param forceChange forces the user to choose another target (only targets
     * with maxtargets = 1 supported)
     * @param onlyOneTarget only one target can be selected for the change
     * @param filterNewTarget restriction to the new target
     */
    public ChooseNewTargetsTargetEffect(boolean forceChange, boolean onlyOneTarget, FilterPermanent filterNewTarget) {
        super(Outcome.Benefit);
        this.forceChange = forceChange;
        this.onlyOneTarget = onlyOneTarget;
        this.filterNewTarget = filterNewTarget;
    }

    public ChooseNewTargetsTargetEffect(final ChooseNewTargetsTargetEffect effect) {
        super(effect);
        this.forceChange = effect.forceChange;
        this.onlyOneTarget = effect.onlyOneTarget;
        this.filterNewTarget = effect.filterNewTarget;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        if (stackObject != null) {
            return stackObject.chooseNewTargets(game, source.getControllerId(), forceChange, onlyOneTarget, filterNewTarget);
        }
        return false;
    }

    @Override
    public ChooseNewTargetsTargetEffect copy() {
        return new ChooseNewTargetsTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (forceChange) {
            sb.append("change the target of target ");
        } else {
            sb.append("you may choose new targets for target ");
        }
        sb.append(mode.getTargets().get(0).getTargetName());
        return sb.toString();
    }
}
