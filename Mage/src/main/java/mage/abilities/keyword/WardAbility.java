package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class WardAbility extends TriggeredAbilityImpl {

    private final Cost cost;
    private final boolean showAbilityHint;

    public WardAbility(Cost cost) {
        this(cost, true);
    }

    public WardAbility(Cost cost, boolean showAbilityHint) {
        super(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(cost), false);
        this.cost = cost;
        this.showAbilityHint = showAbilityHint;
    }

    private WardAbility(final WardAbility ability) {
        super(ability);
        this.cost = ability.cost.copy();
        this.showAbilityHint = ability.showAbilityHint;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    private StackObject getTargetingObject(GameEvent event, Game game) {
        for (StackObject stackObject : game.getStack()) {
            if (stackObject.getId().equals(event.getSourceId()) || stackObject.getSourceId().equals(event.getSourceId())) {
                for (Target target : stackObject.getStackAbility().getTargets()) {
                    if (target.contains(getSourceId())) {
                        return stackObject;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!getSourceId().equals(event.getTargetId())) {
            return false;
        }
        StackObject stackObject = getTargetingObject(event, game);
        if (stackObject == null || !game.getOpponents(getControllerId()).contains(stackObject.getControllerId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(stackObject.getId(), game));
        return true;
    }

    @Override
    public WardAbility copy() {
        return new WardAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("ward");
        if (cost instanceof ManaCost) {
            sb.append(' ').append(cost.getText());
        } else {
            sb.append("&mdash;").append(CardUtil.getTextWithFirstCharUpperCase(cost.getText())).append('.');
        }

        if (showAbilityHint) {
            sb.append(" <i>(Whenever this creature becomes the target of a spell or ability an opponent controls, " +
                    "counter it unless that player ");
            if (cost instanceof ManaCost) {
                sb.append("pays ").append(cost.getText());
            } else {
                sb.append(cost.getText().replace("pay ", "pays "));
            }
            sb.append(".)</i>");
        }

        return sb.toString();
    }
}
