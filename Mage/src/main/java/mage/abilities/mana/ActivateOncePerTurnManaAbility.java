
package mage.abilities.mana;

import mage.Mana;
import mage.abilities.costs.Cost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class ActivateOncePerTurnManaAbility extends ActivatedManaAbilityImpl {

    public ActivateOncePerTurnManaAbility(Zone zone, BasicManaEffect effect, Cost cost) {
        super(zone, effect, cost);
        this.netMana.add(effect.getManaTemplate());
        this.maxActivationsPerTurn = 1;
    }

    public ActivateOncePerTurnManaAbility(Zone zone, AddManaOfAnyColorEffect effect, Cost cost) {
        super(zone, effect, cost);
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, effect.getAmount(), 0));
        this.maxActivationsPerTurn = 1;
    }

    public ActivateOncePerTurnManaAbility(ActivateOncePerTurnManaAbility ability) {
        super(ability);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (canActivate(this.controllerId, game).canActivate()) {
            return super.activate(game, noMana);
        }
        return false;
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only once each turn.";
    }

    @Override
    public ActivateOncePerTurnManaAbility copy() {
        return new ActivateOncePerTurnManaAbility(this);
    }

}
