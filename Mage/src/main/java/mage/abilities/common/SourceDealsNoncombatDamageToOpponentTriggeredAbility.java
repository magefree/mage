package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author notgreat
 */
public class SourceDealsNoncombatDamageToOpponentTriggeredAbility extends TriggeredAbilityImpl {
    SetTargetPointer setTargetPointer;

    public SourceDealsNoncombatDamageToOpponentTriggeredAbility(Effect effect) {
        this(effect, SetTargetPointer.NONE);
    }
    public SourceDealsNoncombatDamageToOpponentTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        this(effect, false, setTargetPointer);
    }

    public SourceDealsNoncombatDamageToOpponentTriggeredAbility(Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever a source you control deals noncombat damage to an opponent, ");
    }

    protected SourceDealsNoncombatDamageToOpponentTriggeredAbility(final SourceDealsNoncombatDamageToOpponentTriggeredAbility ability) {
        super(ability);
        setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public SourceDealsNoncombatDamageToOpponentTriggeredAbility copy() {
        return new SourceDealsNoncombatDamageToOpponentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getTargetId());
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (opponent == null || !game.isOpponent(opponent, getControllerId())
                || !isControlledBy(game.getControllerId(event.getSourceId()))
                || damageEvent.isCombatDamage()) {
            return false;
        }
        int damageAmount = event.getAmount();
        if (damageAmount < 1) {
            return false;
        }
        this.getEffects().setValue("damage", damageAmount);
        if (setTargetPointer == SetTargetPointer.PLAYER) {
            getEffects().setTargetPointer(new FixedTarget(event.getSourceId()));
        }
        return true;
    }
}
