
package mage.abilities.keyword;

import java.util.Locale;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
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
        super(Zone.BATTLEFIELD, new EchoEffect(new ManaCostsImpl(manaString)), false);
        this.echoPaid = false;
        this.echoCosts.add(new ManaCostsImpl(manaString));
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
        // reset the echo paid state back, if creature enteres the battlefield
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
        }
        sb.append(" <i>(At the beginning of your upkeep, if this came under your control since the beginning of your last upkeep, sacrifice it unless you pay its echo cost.)</i>");
        return sb.toString();
    }
}

class EchoEffect extends OneShotEffect {

    protected Cost cost;
    protected DynamicValue amount;

    public EchoEffect(Cost costs) {
        super(Outcome.Sacrifice);
        this.cost = costs;
        this.amount = null;
    }

    public EchoEffect(DynamicValue amount) {
        super(Outcome.Sacrifice);
        this.amount = amount;
        this.cost = null;
    }

    public EchoEffect(final EchoEffect effect) {
        super(effect);
        this.cost = effect.cost;
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (amount != null) {
            cost = new ManaCostsImpl(Integer.toString(amount.calculate(game, source, this)));
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && source.getSourceObjectIfItStillExists(game) != null) {
            if (controller.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.ECHO_PAID, source.getSourceId(), source.getSourceId(), source.getControllerId()));
                    return true;
                }
            }
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public EchoEffect copy() {
        return new EchoEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder("sacrifice {this} unless you ");
        String costText = cost.getText();
        if (costText.toLowerCase(Locale.ENGLISH).startsWith("discard")) {
            sb.append(costText.substring(0, 1).toLowerCase(Locale.ENGLISH));
            sb.append(costText.substring(1));
        } else {
            sb.append("pay ").append(costText);
        }

        return sb.toString();

    }
}
