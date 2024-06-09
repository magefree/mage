
package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealSourceFromYourHandCost;
import mage.abilities.effects.Effect;
import mage.constants.PhaseStep;
import mage.constants.Zone;

/**
 * 702.56. Forecast 702.56a A forecast ability is a special kind of activated
 * ability that can be activated only from a player's hand. It's written
 * "Forecast -- [Activated ability]."
 * <p>
 * 702.56b A forecast ability may be activated only during the upkeep step of
 * the card's owner and only once each turn. The controller of the forecast
 * ability reveals the card with that ability from their hand as the ability is
 * activated. That player plays with that card revealed in their hand until it
 * leaves the player's hand or until a step or phase that isn't an upkeep step
 * begins, whichever comes first.
 *
 * @author LevelX2
 */
public class ForecastAbility extends ActivatedAbilityImpl {

    private static final Condition upkeepCondition = new IsStepCondition(PhaseStep.UPKEEP, true);

    public ForecastAbility(Effect effect, Cost cost) {
        super(Zone.HAND, effect, cost);
        this.maxActivationsPerTurn = 1;
        this.condition = upkeepCondition;
        this.addCost(new RevealSourceFromYourHandCost());
    }

    protected ForecastAbility(final ForecastAbility ability) {
        super(ability);
    }

    @Override
    public ForecastAbility copy() {
        return new ForecastAbility(this);
    }

    @Override
    public String getRule() {
        return "Forecast &mdash; " + super.getRule() + " <i>(Activate only during your upkeep and only once each turn)</i>";
    }
}
