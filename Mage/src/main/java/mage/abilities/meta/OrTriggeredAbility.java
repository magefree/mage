package mage.abilities.meta;

import mage.MageObject;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A triggered ability that combines several others and triggers whenever one or more of them would. The abilities
 * passed in should have null as their effect, and should have their own targets set if necessary. All other information
 * will be passed in from changes to this Ability. Note: this does NOT work with abilities that have intervening if clauses.
 * @author noahg
 */
public class OrTriggeredAbility extends TriggeredAbilityImpl {

    private final String ruleTrigger;
    private TriggeredAbility[] triggeredAbilities;
    private List<Integer> triggeringAbilities;

    public OrTriggeredAbility(Zone zone, Effect effect, TriggeredAbility... abilities) {
        this(zone, effect, false, null, abilities);
    }

    public OrTriggeredAbility(Zone zone, Effect effect, boolean optional, String ruleTrigger, TriggeredAbility... abilities) {
        super(zone, effect, optional);
        this.triggeredAbilities = abilities;
        this.ruleTrigger = ruleTrigger;
        this.triggeringAbilities = new ArrayList<>();
        for (TriggeredAbility ability : triggeredAbilities) {
            //Remove useless data
            ability.getEffects().clear();
        }
    }

    public OrTriggeredAbility(OrTriggeredAbility ability) {
        super(ability);
        this.triggeredAbilities = new TriggeredAbility[ability.triggeredAbilities.length];
        for (int i = 0; i < this.triggeredAbilities.length; i++){
            this.triggeredAbilities[i] = ability.triggeredAbilities[i].copy();
        }
        this.triggeringAbilities = new ArrayList<>(ability.triggeringAbilities);
        this.ruleTrigger = ability.ruleTrigger;
    }


    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        for (TriggeredAbility ability : triggeredAbilities) {
            if (ability.checkEventType(event, game)){
                System.out.println("Correct event type (" + event.getType() + ")");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean toRet = false;
        for (int i = 0; i < triggeredAbilities.length; i++) {
            TriggeredAbility ability = triggeredAbilities[i];
            if (ability.checkEventType(event, game) && ability.checkTrigger(event, game)) {
                System.out.println("Triggered from " + ability.getRule());
                triggeringAbilities.add(i);
                toRet = true;
            }
            System.out.println("Checked " + ability.getRule());
        }
        return toRet;
    }

    @Override
    public OrTriggeredAbility copy() {
        return new OrTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        if (ruleTrigger != null && !ruleTrigger.isEmpty()) {
            return ruleTrigger + super.getRule();
        }
        StringBuilder sb = new StringBuilder();
        if (triggeredAbilities[0].getRule().length() > 0) {
            sb.append(triggeredAbilities[0].getRule().substring(0, 1).toUpperCase())
                    .append(triggeredAbilities[0].getRule().substring(1).toLowerCase());
        }

        for (int i = 1; i < (triggeredAbilities.length - 1); i++) {
            sb.append(triggeredAbilities[i].getRule().toLowerCase());
        }

        sb.append(" or ").append(triggeredAbilities[triggeredAbilities.length - 1].getRule().toLowerCase());
        return sb.toString() + super.getRule();
    }


    @Override
    public void setControllerId(UUID controllerId) {
        super.setControllerId(controllerId);
        for (TriggeredAbility ability : triggeredAbilities) {
            ability.setControllerId(controllerId);
        }
    }

    @Override
    public void setSourceId(UUID sourceId) {
        super.setSourceId(sourceId);
        for (TriggeredAbility ability : triggeredAbilities) {
            ability.setSourceId(sourceId);
        }
    }

    @Override
    public void addWatcher(Watcher watcher) {
        super.addWatcher(watcher);
        for (TriggeredAbility ability : triggeredAbilities) {
            ability.addWatcher(watcher);
        }
    }

    @Override
    public void setSourceObject(MageObject sourceObject, Game game) {
        super.setSourceObject(sourceObject, game);
        for (TriggeredAbility ability : triggeredAbilities) {
            ability.setSourceObject(sourceObject, game);
        }
    }
}
