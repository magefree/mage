// @author jeffwadsworth

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AttachmentType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

public class AttacksOrBlocksAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final AttachmentType attachmentType;

    public AttacksOrBlocksAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType) {
        super(Zone.BATTLEFIELD, effect);
        this.attachmentType = attachmentType;
        setTriggerPhrase("Whenever " + attachmentType.verb().toLowerCase() + " creature attacks or blocks, ");
    }

    public AttacksOrBlocksAttachedTriggeredAbility(final AttacksOrBlocksAttachedTriggeredAbility ability) {
        super(ability);
        this.attachmentType = ability.attachmentType;
    }

    @Override
    public AttacksOrBlocksAttachedTriggeredAbility copy() {
        return new AttacksOrBlocksAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                || event.getType() == GameEvent.EventType.CREATURE_BLOCKS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = getSourcePermanentOrLKI(game);
        if (enchantment == null) {
            return false;
        }
        UUID idToCheck = (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) ? event.getSourceId() : event.getTargetId();
        return idToCheck.equals(enchantment.getAttachedTo());
    }
}
