// @author jeffwadsworth

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AttachmentType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class AttacksOrBlocksAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final AttachmentType attachmentType;

    public AttacksOrBlocksAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType) {
        super(Zone.BATTLEFIELD, effect);
        this.attachmentType = attachmentType;
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
                || event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = getSourcePermanentOrLKI(game);
        return enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo());
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + attachmentType.verb().toLowerCase() + " creature attacks or blocks, ";
    }
}
