package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
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
    private final DynamicValue genericMana;

    private final boolean showAbilityHint;
    private final String whereXIs;

    public WardAbility(Cost cost) {
        this(cost, true);
    }

    public WardAbility(Cost cost, boolean showAbilityHint) {
        super(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(cost), false);
        this.cost = cost;
        this.genericMana = null;
        this.showAbilityHint = showAbilityHint;
        this.whereXIs = null;
    }

    public WardAbility(DynamicValue genericMana) {
        this(genericMana, null);
    }

    public WardAbility(DynamicValue genericMana, String whereXIs) {
        super(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(genericMana), false);
        this.genericMana = genericMana;
        this.whereXIs = whereXIs;
        this.cost = null;
        this.showAbilityHint = false;
    }

    private WardAbility(final WardAbility ability) {
        super(ability);
        if (ability.cost != null) {
            this.cost = ability.cost.copy();
            this.genericMana = null;
        } else {
            this.genericMana = ability.genericMana.copy();
            this.cost = null;
        }
        this.showAbilityHint = ability.showAbilityHint;
        this.whereXIs = ability.whereXIs;
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
        if (cost != null) {
            if (cost instanceof ManaCost) {
                sb.append(' ').append(cost.getText());
            } else {
                sb.append("&mdash;").append(CardUtil.getTextWithFirstCharUpperCase(cost.getText())).append('.');
            }
        } else {
            sb.append(" {X}");
            if (whereXIs != null) {
                sb.append(", where X is ").append(whereXIs).append('.');
            }
        }

        if (showAbilityHint) {
            sb.append(" <i>(Whenever this creature becomes the target of a spell or ability an opponent controls, " +
                    "counter it unless that player ");
            if (cost != null) {
                if (cost instanceof ManaCost) {
                    sb.append("pays ").append(cost.getText());
                } else {
                    sb.append(cost.getText().replace("pay ", "pays "));
                }
                sb.append(".)</i>");
            } else {
                sb.append("pays {X}");
                if (whereXIs != null) {
                    sb.append(whereXIs);
                }
                sb.append(".)</i>");
            }
        }

        return sb.toString();
    }
}
