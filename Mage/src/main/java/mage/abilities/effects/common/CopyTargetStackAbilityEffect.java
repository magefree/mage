package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackAbility;

public class CopyTargetStackAbilityEffect extends OneShotEffect {

    /**
     * Copy target (activated/triggered) ability on the stack, choosing new targets for the copy
     */
    public CopyTargetStackAbilityEffect() {
        super(Outcome.Copy);
    }

    public CopyTargetStackAbilityEffect(final CopyTargetStackAbilityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackAbility == null) {
            return false;
        }
        stackAbility.createCopyOnStack(game, source, source.getControllerId(), true);
        return true;
    }

    @Override
    public CopyTargetStackAbilityEffect copy() {
        return new CopyTargetStackAbilityEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("copy ");
        if (!mode.getTargets().isEmpty()) {
            sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        }
        sb.append(". You may choose new targets for the copy");
        return sb.toString();
    }

}
