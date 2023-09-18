package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AttachmentType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * "When enchanted/equipped creature attacks " triggered ability
 *
 * @author LevelX2
 */
public class AttacksAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final AttachmentType attachmentType;
    private final SetTargetPointer setTargetPointer;

    public AttacksAttachedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttacksAttachedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, AttachmentType.EQUIPMENT, optional);
    }

    public AttacksAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean optional) {
        this(effect, attachmentType, optional, SetTargetPointer.NONE);
    }

    public AttacksAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean optional, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.attachmentType = attachmentType;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + attachmentType.verb().toLowerCase() + " creature attacks, ");
    }

    protected AttacksAttachedTriggeredAbility(final AttacksAttachedTriggeredAbility ability) {
        super(ability);
        this.attachmentType = ability.attachmentType;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public AttacksAttachedTriggeredAbility copy() {
        return new AttacksAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attachment = getSourcePermanentOrLKI(game);
        UUID attackerId = event.getSourceId();
        if (attachment == null || !attackerId.equals(attachment.getAttachedTo())) {
            return false;
        }
        switch (setTargetPointer) {
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTarget(attackerId, game));
                break;
            case PLAYER:
                getEffects().setTargetPointer(new FixedTarget(game.getCombat().getDefendingPlayerId(attackerId, game)));
                break;
        }
        return true;
    }
}
