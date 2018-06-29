
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealSourceFromYourHandCost;
import mage.abilities.effects.Effect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;

/**
 * 702.56. Forecast 702.56a A forecast ability is a special kind of activated
 * ability that can be activated only from a player's hand. It's written
 * "Forecast -- [Activated ability]."
 *
 * 702.56b A forecast ability may be activated only during the upkeep step of
 * the card's owner and only once each turn. The controller of the forecast
 * ability reveals the card with that ability from their hand as the ability is
 * activated. That player plays with that card revealed in their hand until it
 * leaves the player's hand or until a step or phase that isn't an upkeep step
 * begins, whichever comes first.
 *
 * @author LevelX2
 *
 */
public class ForecastAbility extends LimitedTimesPerTurnActivatedAbility {

    public ForecastAbility(Effect effect, Cost cost) {
        super(Zone.HAND, effect, cost);
        this.addCost(new RevealSourceFromYourHandCost());
    }

    public ForecastAbility(final ForecastAbility ability) {
        super(ability);
    }

    @Override
    public ForecastAbility copy() {
        return new ForecastAbility(this);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // May be activated only during the upkeep step of the card's owner
        // Because it can only be activated from a players hand it should be ok to check here with controllerId instead of card.getOwnerId().
        if (!game.isActivePlayer(controllerId) || PhaseStep.UPKEEP != game.getStep().getType()) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("<i>Forecast</i> &mdash; ");
        sb.append(super.getRule()).append(" <i>Activate this ability only during your upkeep.</i>");
        return sb.toString();
    }
}
