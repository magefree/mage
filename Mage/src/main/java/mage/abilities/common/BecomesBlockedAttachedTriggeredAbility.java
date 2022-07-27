package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public class BecomesBlockedAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final SetTargetPointer setTargetPointer;

    public BecomesBlockedAttachedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, SetTargetPointer.NONE);
    }

    public BecomesBlockedAttachedTriggeredAbility(Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
    }

    public BecomesBlockedAttachedTriggeredAbility(final BecomesBlockedAttachedTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = getSourcePermanentOrLKI(game);
        UUID blockedId = event.getTargetId();
        if (enchantment == null || !blockedId.equals(enchantment.getAttachedTo())) {
            return false;
        }
        switch (setTargetPointer) {
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTarget(blockedId, game));
                break;
            case PLAYER:
                UUID playerId = game.getCombat().getDefendingPlayerId(blockedId, game);
                if (playerId != null) {
                    getEffects().setTargetPointer(new FixedTarget(playerId));
                }
                break;
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever enchanted creature becomes blocked, ";
    }

    @Override
    public BecomesBlockedAttachedTriggeredAbility copy() {
        return new BecomesBlockedAttachedTriggeredAbility(this);
    }
}
