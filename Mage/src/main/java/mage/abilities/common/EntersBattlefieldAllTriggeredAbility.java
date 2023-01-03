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
 * @author LevelX2
 */
public class EntersBattlefieldAllTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected String rule;
    protected boolean controlledText;
    protected SetTargetPointer setTargetPointer;
    protected final boolean thisOrAnother;

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
        this(zone, effect, filter, optional, setTargetPointer, rule, controlledText, false);
    }

    protected EntersBattlefieldAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, String rule, boolean controlledText, boolean thisOrAnother) {
        super(zone, effect, optional);
        this.filter = filter;
        this.rule = rule;
        this.controlledText = controlledText;
        this.setTargetPointer = setTargetPointer;
        this.thisOrAnother = thisOrAnother;
        setTriggerPhrase(generateTriggerPhrase());
    }

    public EntersBattlefieldAllTriggeredAbility(final EntersBattlefieldAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.rule = ability.rule;
        this.controlledText = ability.controlledText;
        this.setTargetPointer = ability.setTargetPointer;
        this.thisOrAnother = ability.thisOrAnother;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if (!filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setValue("permanentEnteringBattlefield", permanent);
        this.getEffects().setValue("permanentEnteringControllerId", permanent.getControllerId());
        switch (setTargetPointer) {
            case PLAYER:
                this.getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                return true;
            case PERMANENT:
                this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
            default:
                return true;
        }
    }

    @Override
    public String getRule() {
        if (rule != null && !rule.isEmpty()) {
            return rule;
        }
        return super.getRule();
    }

    protected String generateTriggerPhrase() {
        StringBuilder sb = new StringBuilder("Whenever ");
        if (thisOrAnother) {
            sb.append("{this} or another ");
        }
        sb.append(filter.getMessage());
        if (filter.getMessage().startsWith("one or more")) {
            sb.append(" enter the battlefield");
        } else {
            sb.append(" enters the battlefield");
        }
        if (controlledText) {
            sb.append(" under your control, ");
        } else {
            sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public EntersBattlefieldAllTriggeredAbility copy() {
        return new EntersBattlefieldAllTriggeredAbility(this);
    }
}
