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
import mage.abilities.Ability;
import mage.abilities.common.EmptyEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.CommanderReplacementEffect;
import mage.abilities.effects.common.cost.CommanderCostModification;
import mage.cards.Card;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.game.match.MatchType;
import mage.game.turn.TurnMod;
import mage.players.Player;

public class CommanderDuel extends GameImpl<CommanderDuel> {

    public CommanderDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans) {
        super(attackOption, range, freeMulligans);
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
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new EmptyEffect("Commander effects"));
        //Move commender to commande zone
        for (UUID playerId: state.getPlayerList(startingPlayerId)) {
            Player player = getPlayer(playerId);
            if(player != null){
                if(player.getSideboard().size() > 0){
                    Card commander =  getCard((UUID)player.getSideboard().toArray()[0]);
                    if(commander != null){
                        commander.moveToZone(Zone.COMMAND, null, this, true);
                        ability.addEffect(new CommanderReplacementEffect(commander.getId()));
                        ability.addEffect(new CommanderCostModification(commander.getId()));
                        getState().setValue(commander + "_castCount", new Integer(0));
                    }
                }
            }
            
        }
        this.getState().addAbility(ability, null, null);
        state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
    }

    @Override
    public void quit(UUID playerId) {
        super.quit(playerId);
    }

    @Override
    public Set<UUID> getOpponents(UUID playerId) {
        Set<UUID> opponents = new HashSet<UUID>();
        for (UUID opponentId: this.getPlayer(playerId).getInRange()) {
            if (!opponentId.equals(playerId)) {
                opponents.add(opponentId);
            }
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
