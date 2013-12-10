/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.common.ActivateOncePerTurnActivatedAbility;
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
 * ability reveals the card with that ability from his or her hand as the
 * ability is activated. That player plays with that card revealed in his or her
 * hand until it leaves the player's hand or until a step or phase that isn't an
 * upkeep step begins, whichever comes first.
 *
 * @author LevelX2
 *
 */
public class ForecastAbility extends ActivateOncePerTurnActivatedAbility {

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
    public boolean canActivate(UUID playerId, Game game) {
        // May be activated only during the upkeep step of the card's owner
        if (!game.getActivePlayerId().equals(controllerId) || !game.getStep().getType().equals(PhaseStep.UPKEEP)) {
            return false;
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("<i>Forecast</i> - ");
        sb.append(super.getRule()).append(" <i>Activate this ability only during your upkeep.</i>");
        return sb.toString();
    }
}
