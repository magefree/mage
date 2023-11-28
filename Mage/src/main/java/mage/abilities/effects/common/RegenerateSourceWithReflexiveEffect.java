package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Susucr
 */
public class RegenerateSourceWithReflexiveEffect extends RegenerateSourceEffect {

    private final ReflexiveTriggeredAbility reflexive;
    private final boolean setReflexiveTarget;

    public RegenerateSourceWithReflexiveEffect(ReflexiveTriggeredAbility reflexive, boolean setReflexiveTarget) {
        super();
        this.reflexive = reflexive;
        this.reflexive.setTriggerPhrase("When it regenerates this way, ");
        this.reflexive.withRuleTextReplacement(true);
        this.setReflexiveTarget = setReflexiveTarget;
        this.staticText = "regenerate {this}. " + reflexive.getRule();
    }

    protected RegenerateSourceWithReflexiveEffect(final RegenerateSourceWithReflexiveEffect effect) {
        super(effect);
        this.reflexive = effect.reflexive.copy();
        this.setReflexiveTarget = effect.setReflexiveTarget;
    }

    @Override
    public RegenerateSourceWithReflexiveEffect copy() {
        return new RegenerateSourceWithReflexiveEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (super.replaceEvent(event, source, game)) {
            if (this.setReflexiveTarget) {
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
