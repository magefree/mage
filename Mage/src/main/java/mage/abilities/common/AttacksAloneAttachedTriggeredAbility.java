package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.AttachmentType;
import mage.constants.SetTargetPointer;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author awjackson
 */
public class AttacksAloneAttachedTriggeredAbility extends AttacksAttachedTriggeredAbility {

    public AttacksAloneAttachedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttacksAloneAttachedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, AttachmentType.EQUIPMENT, optional);
    }

    public AttacksAloneAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean optional) {
        this(effect, attachmentType, optional, SetTargetPointer.NONE);
    }

    public AttacksAloneAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean optional, SetTargetPointer setTargetPointer) {
        super(effect, attachmentType, optional, setTargetPointer);
        setTriggerPhrase("Whenever " + attachmentType.verb().toLowerCase() + " creature attacks alone, ");
    }

    protected AttacksAloneAttachedTriggeredAbility(final AttacksAloneAttachedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AttacksAloneAttachedTriggeredAbility copy() {
        return new AttacksAloneAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().attacksAlone() && super.checkTrigger(event, game);
    }
}
