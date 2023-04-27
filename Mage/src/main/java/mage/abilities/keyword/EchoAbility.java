package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.keyword.EchoEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Backfir3
 */
public class EchoAbility extends TriggeredAbilityImpl {

    protected UUID lastController;
    protected boolean echoPaid;
    protected Costs<Cost> echoCosts = new CostsImpl<>();
    private boolean manaEcho = true;
    private DynamicValue amount;
    private String rule;

    public EchoAbility(String manaString) {
        super(Zone.BATTLEFIELD, new EchoEffect(new ManaCostsImpl<>(manaString)), false);
        this.echoPaid = false;
        this.echoCosts.add(new ManaCostsImpl<>(manaString));
        this.lastController = null;
        this.rule = null;
    }

    public EchoAbility(DynamicValue amount, String rule) {
        super(Zone.BATTLEFIELD, new EchoEffect(amount), false);
        this.amount = amount;
        this.echoPaid = false;
        this.echoCosts.add(costs);
        this.lastController = null;
        this.manaEcho = true;
        this.rule = rule;
    }

    public EchoAbility(Cost echoCost) {
        super(Zone.BATTLEFIELD, new EchoEffect(echoCost), false);
        this.echoPaid = false;
        this.echoCosts.add(echoCost);
        this.manaEcho = false;
        this.lastController = null;
        this.rule = null;
    }

    public EchoAbility(final EchoAbility ability) {
        super(ability);
        this.echoPaid = ability.echoPaid;
        this.echoCosts = ability.echoCosts.copy();
        this.manaEcho = ability.manaEcho;
        this.lastController = ability.lastController;
        this.amount = ability.amount;
        this.rule = ability.rule;
    }

    @Override
    public EchoAbility copy() {
        return new EchoAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // reset the echo paid state back, if creature enters the battlefield
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && event.getTargetId().equals(this.getSourceId())) {

            this.echoPaid = false;
        }
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            if (lastController != null) {
                if (!lastController.equals(this.controllerId)) {
                    this.echoPaid = false;
                }
            }
            // remember the last controller
            lastController = this.getControllerId();
            // if echo not paid yet, controller has to pay
            if (event.getPlayerId().equals(this.controllerId)
                    && lastController.equals(this.controllerId)
                    && !this.echoPaid) {
                this.echoPaid = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder();
        if (rule != null) {
            sb.append(rule);
        } else {
            sb = new StringBuilder("Echo");
            if (manaEcho) {
                sb.append(' ');
            } else {
                sb.append("&mdash;");
            }
            if (echoCosts != null) {
                sb.append(echoCosts.getText());
            }
            if (!manaEcho) {
                sb.append('.');
            }
        }
        sb.append(" <i>(At the beginning of your upkeep, if this came under your control since the beginning of your last upkeep, sacrifice it unless you pay its echo cost.)</i>");
        return sb.toString();
    }
}

