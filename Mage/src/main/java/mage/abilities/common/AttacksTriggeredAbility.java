
package mage.abilities.common;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AttacksTriggeredAbility extends TriggeredAbilityImpl {

    protected SetTargetPointer setTargetPointer;
    protected String text;

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
        if (game.getCombat().getAttackers().contains(this.getSourceId())) {
            switch (setTargetPointer) {
                case PLAYER:
                    UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
                    if (defendingPlayerId != null) {
                        for (Effect effect : getEffects()) {
                            effect.setTargetPointer(new FixedTarget(defendingPlayerId));
                        }
                    }
                    break;

            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        if (text == null || text.isEmpty()) {
            return "Whenever {this} attacks, " + super.getRule();
        }
        return text;
    }

    @Override
    public AttacksTriggeredAbility copy() {
        return new AttacksTriggeredAbility(this);
    }

}
