package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.AsThoughEffectType;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class ExhaustAbility extends ActivatedAbilityImpl {

    public ExhaustAbility(Effect effect, Cost cost) {
        super(Zone.BATTLEFIELD, effect, cost);
    }

    private ExhaustAbility(final ExhaustAbility ability) {
        super(ability);
        this.maxActivationsPerGame = 1;
    }

    @Override
    public ExhaustAbility copy() {
        return new ExhaustAbility(this);
    }

    @Override
    public boolean hasMoreActivationsThisTurn(Game game) {
        ActivationInfo info = getActivationInfo(game);
        if (info != null && info.totalActivations >= maxActivationsPerGame) {
            boolean canActivate = !game.getContinuousEffects()
                .asThough(sourceId, AsThoughEffectType.ALLOW_EXHAUST_PER_TURN, this, controllerId, game)
                .isEmpty();
            if (canActivate) {
                return true;
            }
        }
        return super.hasMoreActivationsThisTurn(game);
    }

    @Override
    public String getRule() {
        return "Exhaust &mdash; " + super.getRule() + " <i>(Activate each exhaust ability only once.)</i>";
    }
}
