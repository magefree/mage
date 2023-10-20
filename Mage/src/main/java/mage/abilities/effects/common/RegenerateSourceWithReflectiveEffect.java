package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Susucr
 */
public class RegenerateSourceWithReflectiveEffect extends RegenerateSourceEffect {

    private final ReflexiveTriggeredAbility reflexive;
    private final boolean setReflectiveTarget;

    public RegenerateSourceWithReflectiveEffect(ReflexiveTriggeredAbility reflexive, boolean setReflectiveTarget) {
        super();
        this.reflexive = reflexive;
        this.reflexive.setTriggerPhrase("When it regenerates this way, ");
        this.setReflectiveTarget = setReflectiveTarget;
        this.staticText = "regenerate {this}. " + reflexive.getRule();
    }

    protected RegenerateSourceWithReflectiveEffect(final RegenerateSourceWithReflectiveEffect effect) {
        super(effect);
        this.reflexive = effect.reflexive.copy();
        this.setReflectiveTarget = effect.setReflectiveTarget;
    }

    @Override
    public RegenerateSourceWithReflectiveEffect copy() {
        return new RegenerateSourceWithReflectiveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (super.apply(game, source)) {
            if (this.setReflectiveTarget) {
                reflexive.getEffects().setTargetPointer(
                        new FixedTarget(targetPointer.getFirst(game, source), game)
                );
            }
            game.fireReflexiveTriggeredAbility(reflexive, source);
            return true;
        }
        return false;
    }

}