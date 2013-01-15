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

package mage.game;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants.MultiplayerAttackOption;
import mage.Constants.PhaseStep;
import mage.Constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.turn.TurnMod;

public class CommanderDuel extends GameImpl<CommanderDuel> {

    public CommanderDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range) {
        super(attackOption, range);
    }

    public CommanderDuel(final CommanderDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new CommanderDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

	// MTG Rules 20121001
	// 903.7. Once the starting player has been determined, each player sets his or her life total to 40 and
	// draws a hand of seven cards.
    @Override
    public int getLife() {
        return 40;
    }

    @Override
    protected void init(UUID choosingPlayerId, GameOptions gameOptions) {
        super.init(choosingPlayerId, gameOptions);
        state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
    }

    @Override
    public void quit(UUID playerId) {
        super.quit(playerId);
        end();
    }

    @Override
    public Set<UUID> getOpponents(UUID playerId) {
        Set<UUID> opponents = new HashSet<UUID>();
        for (UUID opponentId: this.getPlayer(playerId).getInRange()) {
            if (!opponentId.equals(playerId))
                opponents.add(opponentId);
        }
        return opponents;
    }

    @Override
    public void leave(UUID playerId) {

    }

    @Override
    public CommanderDuel copy() {
        return new CommanderDuel(this);
    }

}
