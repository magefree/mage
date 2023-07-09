package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Susucr
 */
public class SpellCastOpponentNoManaSpentTriggeredAbility extends TriggeredAbilityImpl {

    public SpellCastOpponentNoManaSpentTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        this.setTriggerPhrase("Whenever an opponent casts a spell, if no mana was spent to cast it, ");
    }

    public SpellCastOpponentNoManaSpentTriggeredAbility(final SpellCastOpponentNoManaSpentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpellCastOpponentNoManaSpentTriggeredAbility copy() {
        return new SpellCastOpponentNoManaSpentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getStackAbility().getManaCostsToPay().getUsedManaToPay().count() == 0) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }
}