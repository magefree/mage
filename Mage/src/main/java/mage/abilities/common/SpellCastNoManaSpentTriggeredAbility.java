package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author grimreap124
 */
public class SpellCastNoManaSpentTriggeredAbility extends TriggeredAbilityImpl {

    public SpellCastNoManaSpentTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        this.setTriggerPhrase("Whenever a player casts a spell, if no mana was spent to cast it, ");
    }

    protected SpellCastNoManaSpentTriggeredAbility(final SpellCastNoManaSpentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpellCastNoManaSpentTriggeredAbility copy() {
        return new SpellCastNoManaSpentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.getStackAbility().getManaCostsToPay().getUsedManaToPay().count() == 0) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }
}