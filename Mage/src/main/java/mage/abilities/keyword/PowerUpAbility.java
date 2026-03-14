package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class PowerUpAbility extends ActivatedAbilityImpl {

    private enum PowerUpAbilityAdjuster implements CostAdjuster {
        instance;

        @Override
        public void reduceCost(Ability ability, Game game) {
            Permanent permanent = ability.getSourcePermanentIfItStillExists(game);
            if (permanent != null && permanent.getTurnsOnBattlefield() == 0) {
                CardUtil.adjustCost(ability, permanent.getManaCost(), false);
            }
        }
    }

    public PowerUpAbility(Effect effect, Cost cost) {
        this(Zone.BATTLEFIELD, effect, cost);
        this.maxActivationsPerGame = 1;
        this.setCostAdjuster(PowerUpAbilityAdjuster.instance);
    }

    public PowerUpAbility(Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
    }

    private PowerUpAbility(final PowerUpAbility ability) {
        super(ability);
    }

    @Override
    public PowerUpAbility copy() {
        return new PowerUpAbility(this);
    }

    @Override
    public String getRule() {
        return "Power-up &mdash; " + super.getRule();
    }
}
