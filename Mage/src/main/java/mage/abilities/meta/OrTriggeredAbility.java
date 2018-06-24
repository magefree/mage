package mage.abilities.meta;

import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

public class OrTriggeredAbility extends TriggeredAbilityImpl {

    private TriggeredAbility[] triggeredAbilities;

    private final String ruleTrigger;

    public OrTriggeredAbility(Zone zone, Effect effect, TriggeredAbility... abilities) {
        this(zone, effect, false, null, abilities);
    }

    public OrTriggeredAbility(Zone zone, Effect effect, boolean optional, String ruleTrigger, TriggeredAbility... abilities) {
        super(zone, effect, optional);
        this.triggeredAbilities = abilities;
        this.ruleTrigger = ruleTrigger;
        for (TriggeredAbility  ability : triggeredAbilities){
            //Remove useless data
            ability.getEffects().clear();
            ability.setSourceId(this.getSourceId());
            ability.setControllerId(this.getControllerId());
        }
    }

    public OrTriggeredAbility(OrTriggeredAbility ability) {
        super(ability);
        this.triggeredAbilities = ability.triggeredAbilities;
        this.ruleTrigger = ability.ruleTrigger;
    }


    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        boolean toRet = false;
        for (TriggeredAbility ability : triggeredAbilities){
            toRet = toRet || ability.checkEventType(event, game);
        }
        if (toRet){
            System.out.println("Correct event type ("+event.getType()+")");
        }
        return toRet;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean toRet = false;
        for (TriggeredAbility ability : triggeredAbilities){
            toRet = toRet || ability.checkTrigger(event, game);
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

        for (int i = 1; i < (triggeredAbilities.length - 1); i++){
            sb.append(triggeredAbilities[i].getRule().toLowerCase());
        }

        sb.append(" or ").append(triggeredAbilities[triggeredAbilities.length - 1].getRule().toLowerCase());
        return sb.toString()+super.getRule();
    }
}
