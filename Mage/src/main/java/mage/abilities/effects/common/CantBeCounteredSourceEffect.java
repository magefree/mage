

package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CantBeCounteredSourceEffect extends ContinuousRuleModifyingEffectImpl {

    public CantBeCounteredSourceEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, false, true);
        staticText = "this spell can't be countered";
    }

    public CantBeCounteredSourceEffect(final CantBeCounteredSourceEffect effect) {
        super(effect);
    }

    @Override
    public CantBeCounteredSourceEffect copy() {
        return new CantBeCounteredSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
        MageObject sourceObject = game.getObject(source);
        if (stackObject != null && sourceObject != null) {
            return sourceObject.getLogName() + " can't be countered by " + stackObject.getName();
        }
        return staticText;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null) {
            if (spell.getSourceId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

}
