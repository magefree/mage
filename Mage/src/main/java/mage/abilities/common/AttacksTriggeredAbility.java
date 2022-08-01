package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AttacksTriggeredAbility extends TriggeredAbilityImpl {

    protected final String text;
    protected final SetTargetPointer setTargetPointer;

    public AttacksTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttacksTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, null);
    }

    public AttacksTriggeredAbility(Effect effect, boolean optional, String text) {
        this(effect, optional, text, SetTargetPointer.NONE);
    }

    public AttacksTriggeredAbility(Effect effect, boolean optional, String text, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.text = text;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever {this} attacks, ");
    }

    public AttacksTriggeredAbility(final AttacksTriggeredAbility ability) {
        super(ability);
        this.text = ability.text;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getCombat().getAttackers().contains(this.getSourceId())) {
            return false;
        }
        if (setTargetPointer == SetTargetPointer.PLAYER) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
            if (defendingPlayerId != null) {
                getEffects().setTargetPointer(new FixedTarget(defendingPlayerId));
            }
        }
        return true;
    }

    @Override
    public String getRule() {
        if (text == null || text.isEmpty()) {
            return super.getRule();
        }
        return text;
    }

    @Override
    public AttacksTriggeredAbility copy() {
        return new AttacksTriggeredAbility(this);
    }
}
