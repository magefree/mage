
package mage.abilities.common;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class EntersBattlefieldAllTriggeredAbility extends TriggeredAbilityImpl {

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
    public EntersBattlefieldAllTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, false);
    }

    public EntersBattlefieldAllTriggeredAbility(Effect effect, FilterPermanent filter, String rule) {
        this(Zone.BATTLEFIELD, effect, filter, false, rule);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE, null, false);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, String rule) {
        this(zone, effect, filter, optional, rule, false);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, String rule, boolean controlledText) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE, rule, controlledText);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, String rule) {
        this(zone, effect, filter, optional, setTargetPointer, rule, false);
    }

    public EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, String rule, boolean controlledText) {
        super(zone, effect, optional);
        this.filter = filter;
        this.rule = rule;
        this.controlledText = controlledText;
        this.setTargetPointer = setTargetPointer;
    }

    public EntersBattlefieldAllTriggeredAbility(final EntersBattlefieldAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.rule = ability.rule;
        this.controlledText = ability.controlledText;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("permanentEnteringBattlefield", permanent);
            }
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
        return false;
    }

    @Override
    public String getRule() {
        if (rule != null && !rule.isEmpty()) {
            return rule;
        }
        StringBuilder sb = new StringBuilder("Whenever ").append(filter.getMessage());
        sb.append(" enters the battlefield");
        if (controlledText) {
            sb.append(" under your control, ");
        } else {
            sb.append(", ");
        }
        sb.append(super.getRule());
        return sb.toString();
    }

    @Override
    public EntersBattlefieldAllTriggeredAbility copy() {
        return new EntersBattlefieldAllTriggeredAbility(this);
    }
}
