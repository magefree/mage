package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Collection;

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
    public int getMaxActivationsPerGame(Game game) {
        return (int)game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        getControllerId(), this, game
                ).stream()
                .map(p -> p.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(PowerUpAbility.PowerUpMoreAbility.class::isInstance)
                .count() + 1;
    }

    @Override
    public String getRule() {
        return "Power-up &mdash; " + super.getRule();
    }

    private static final class PowerUpMoreAbility extends SimpleStaticAbility {

        private PowerUpMoreAbility() {
            super(new InfoEffect("Each power-up ability of permanents you control can be activated an additional time."));
        }

        private PowerUpMoreAbility(final PowerUpMoreAbility ability) {
            super(ability);
        }

        @Override
        public PowerUpMoreAbility copy() {
            return new PowerUpMoreAbility(this);
        }
    }

    public static PowerUpMoreAbility makePowerUpMoreAbility() {
        return new PowerUpMoreAbility();
    }
}
