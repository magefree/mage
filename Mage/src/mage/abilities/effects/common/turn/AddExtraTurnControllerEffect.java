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
package mage.abilities.effects.common.turn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 * @author noxx
 */
public class AddExtraTurnControllerEffect extends OneShotEffect {

    private final boolean loseGameAtEnd;

    public AddExtraTurnControllerEffect() {
        this(false);
    }

    public AddExtraTurnControllerEffect(boolean loseGameAtEnd) {
        super(loseGameAtEnd ? Outcome.AIDontUseIt : Outcome.ExtraTurn);
        this.loseGameAtEnd = loseGameAtEnd;
        staticText = "Take an extra turn after this one";
        if(loseGameAtEnd) {
            staticText += ". At the beginning of that turn's end step, you lose the game";
        }
    }

    public AddExtraTurnControllerEffect(final AddExtraTurnControllerEffect effect) {
        super(effect);
        this.loseGameAtEnd = effect.loseGameAtEnd;
    }

    @Override
    public AddExtraTurnControllerEffect copy() {
        return new AddExtraTurnControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TurnMod extraTurn = new TurnMod(player.getId(), false);
            game.getState().getTurnMods().add(extraTurn);
            if(loseGameAtEnd) {
                LoseGameDelayedTriggeredAbility delayedTriggeredAbility = new LoseGameDelayedTriggeredAbility();
                delayedTriggeredAbility.setSourceId(source.getSourceId());
                delayedTriggeredAbility.setControllerId(source.getControllerId());
                delayedTriggeredAbility.setConnectedTurnMod(extraTurn.getId());
                game.addDelayedTriggeredAbility(delayedTriggeredAbility);
            }
        }
        return true;
    }

}

class LoseGameDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID connectedTurnMod;

    public LoseGameDelayedTriggeredAbility() {
        super(new LoseGameSourceControllerEffect(), Duration.EndOfGame);
    }

    public LoseGameDelayedTriggeredAbility(final LoseGameDelayedTriggeredAbility ability) {
        super(ability);
        this.connectedTurnMod = ability.connectedTurnMod;
    }

    @Override
    public LoseGameDelayedTriggeredAbility copy() {
        return new LoseGameDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return connectedTurnMod != null && connectedTurnMod.equals(game.getState().getTurnId());
    }

    public void setConnectedTurnMod(UUID connectedTurnMod) {
        this.connectedTurnMod = connectedTurnMod;
    }

    @Override
    public String getRule() {
        return "At the beginning of that turn's end step, you lose the game";
    }
}
