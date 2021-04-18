
package mage.abilities.mana;

import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.mana.AddConditionalColorlessManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.constants.Zone;
import mage.game.Game;

public class ActivateIfConditionManaAbility extends ActivatedManaAbilityImpl {

    public ActivateIfConditionManaAbility(Zone zone, BasicManaEffect effect, Cost cost, Condition condition) {
        super(zone, effect, cost);
        this.netMana.add(effect.getManaTemplate());
        this.condition = condition;
    }

    public ActivateIfConditionManaAbility(Zone zone, AddConditionalColorlessManaEffect effect, Cost cost, Condition condition) {
        super(zone, effect, cost);
        this.netMana.add(effect.getMana());
        this.condition = condition;
    }

    public ActivateIfConditionManaAbility(ActivateIfConditionManaAbility ability) {
        super(ability);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        return super.activate(game, noMana);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only if " + condition.toString() + '.';
    }

    @Override
    public ActivateIfConditionManaAbility copy() {
        return new ActivateIfConditionManaAbility(this);
    }

}
