package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ChooseNewTargetsTargetEffect extends OneShotEffect {

    private final boolean forceChange;
    private final boolean onlyOneTarget;

    public ChooseNewTargetsTargetEffect() {
        this(false, false);
    }

    /**
     * @param forceChange     forces the user to choose another target (only targets
     *                        with maxtargets = 1 supported)
     * @param onlyOneTarget   only one target can be selected for the change
     * @param filterNewTarget restriction to the new target
     */
    public ChooseNewTargetsTargetEffect(boolean forceChange, boolean onlyOneTarget) {
        super(Outcome.Benefit);
        this.forceChange = forceChange;
        this.onlyOneTarget = onlyOneTarget;
    }

    protected ChooseNewTargetsTargetEffect(final ChooseNewTargetsTargetEffect effect) {
        super(effect);
        this.forceChange = effect.forceChange;
        this.onlyOneTarget = effect.onlyOneTarget;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        if (stackObject != null) {
            return stackObject.chooseNewTargets(game, source.getControllerId(), forceChange, onlyOneTarget, null);
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
            sb.append("change the target of ");
        } else {
            sb.append("you may choose new targets for ");
        }
        sb.append(getTargetPointer().describeTargets(mode.getTargets(), "it"));
        return sb.toString();
    }
}
