
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Saga
 */
public class EntersBattlefieldOrAttacksAllTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected String rule;
    protected boolean controlledText;
    protected SetTargetPointer setTargetPointer;

    /**
     * zone = BATTLEFIELD optional = false
     *
     * @param effect
     * @param filter
     */
    public EntersBattlefieldOrAttacksAllTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, false);
    }

    public EntersBattlefieldOrAttacksAllTriggeredAbility(Effect effect, FilterPermanent filter, String rule) {
        this(Zone.BATTLEFIELD, effect, filter, false, rule);
    }

    public EntersBattlefieldOrAttacksAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE, null, false);
    }

    public EntersBattlefieldOrAttacksAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, String rule) {
        this(zone, effect, filter, optional, rule, false);
    }

    public EntersBattlefieldOrAttacksAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, String rule, boolean controlledText) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE, rule, controlledText);
    }

    public EntersBattlefieldOrAttacksAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, String rule) {
        this(zone, effect, filter, optional, setTargetPointer, rule, false);
    }

    public EntersBattlefieldOrAttacksAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, String rule, boolean controlledText) {
        super(zone, effect, optional);
        this.filter = filter;
        this.rule = rule;
        this.controlledText = controlledText;
        this.setTargetPointer = setTargetPointer;
        this.setTriggerPhrase(generateTriggerPhrase());
    }

    public EntersBattlefieldOrAttacksAllTriggeredAbility(final EntersBattlefieldOrAttacksAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.rule = ability.rule;
        this.controlledText = ability.controlledText;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && filter.match(permanent, getControllerId(), this, game)) {
            if (setTargetPointer != SetTargetPointer.NONE) {
                for (Effect effect : this.getEffects()) {
                    switch (setTargetPointer) {
                        case PERMANENT:
                            effect.setTargetPointer(new FixedTarget(permanent, game));
                            break;
                        case PLAYER:
                            effect.setTargetPointer(new FixedTarget(permanent.getControllerId()));
                            break;
                    }

                }
            }
            return true;
        }

        Permanent attacker = game.getPermanent(event.getSourceId());
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                && filter.match(attacker, getControllerId(), this, game)) {
            if (setTargetPointer != SetTargetPointer.NONE) {
                for (Effect effect : this.getEffects()) {
                    switch (setTargetPointer) {
                        case PERMANENT:
                            effect.setTargetPointer(new FixedTarget(attacker.getId(), game));
                            break;
                        case PLAYER:
                            UUID playerId = controlledText ? attacker.getControllerId() : game.getCombat().getDefendingPlayerId(attacker.getId(), game);
                            if (playerId != null) {
                                effect.setTargetPointer(new FixedTarget(playerId));
                            }
                            break;
                    }

                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        if (rule != null && !rule.isEmpty()) {
            return rule;
        }
        return super.getRule();
    }

    private String generateTriggerPhrase() {
        StringBuilder sb = new StringBuilder("Whenever ").append(filter.getMessage());
        sb.append(" enters the battlefield ");
        if (controlledText) {
            sb.append("under your control, ");
        } else {
            sb.append("or attacks, ");
        }
        return sb.toString();
    }

    @Override
    public EntersBattlefieldOrAttacksAllTriggeredAbility copy() {
        return new EntersBattlefieldOrAttacksAllTriggeredAbility(this);
    }
}
