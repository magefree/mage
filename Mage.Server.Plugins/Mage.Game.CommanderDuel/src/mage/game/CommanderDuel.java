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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EmptyEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.CommanderReplacementEffect;
import mage.abilities.effects.common.cost.CommanderCostModification;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.MultiplayerAttackOption;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.match.MatchType;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.Watcher;
import mage.watchers.common.CommanderCombatDamageWatcher;

public class CommanderDuel extends GameImpl<CommanderDuel> {

    private final Map<UUID, Cards> mulliganedCards = new HashMap<UUID, Cards>();
    
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
            if (player != null){
                if (player.getSideboard().size() > 0){
                    Card commander =  getCard((UUID)player.getSideboard().toArray()[0]);
                    if (commander != null) {
                        player.setCommanderId(commander.getId());
                        commander.moveToZone(Zone.COMMAND, null, this, true);
                        ability.addEffect(new CommanderReplacementEffect(commander.getId()));
                        ability.addEffect(new CommanderCostModification(commander.getId()));
                        getState().setValue(commander.getId() + "_castCount", new Integer(0));
                        getState().getWatchers().add(new CommanderCombatDamageWatcher(commander.getId()));
                    }
                }
            }
            
        }
        this.getState().addAbility(ability, this.getId(), null);
        state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
    }
    
    
    //20130711
    /*903.8. The Commander variant uses an alternate mulligan rule. 
     * Each time a player takes a mulligan, rather than shuffling his or her entire hand of cards into his or her library, that player exiles any number of cards from his or her hand face down. 
     * Then the player draws a number of cards equal to one less than the number of cards he or she exiled this way. 
     * That player may look at all cards exiled this way while taking mulligans. 
     * Once a player keeps an opening hand, that player shuffles all cards he or she exiled this way into his or her library.
     * */
    //TODO implement may look at exile cards
    @Override
    public void mulligan(UUID playerId) {
        Player player = getPlayer(playerId);
        TargetCardInHand target = new TargetCardInHand(1, player.getHand().size(), new FilterCard("card to mulligan"));
        target.setNotTarget(true);
        //target.setRequired(true);
        if(player.choose(Outcome.Exile, player.getHand(), target, this)){
            int numCards = target.getTargets().size();
            for(UUID uuid : target.getTargets()){
                Card card = player.getHand().get(uuid, this);
                if(card != null){
                    if(!mulliganedCards.containsKey(playerId)){
                        mulliganedCards.put(playerId, new CardsImpl());
                    }
                    card.setFaceDown(true);
                    card.moveToExile(null, "", null, this);
                    mulliganedCards.get(playerId).add(card);
                }
            }
            int deduction = 1;
            if (freeMulligans > 0) {
                if (usedFreeMulligans != null && usedFreeMulligans.containsKey(player.getId())) {
                    int used = usedFreeMulligans.get(player.getId()).intValue();
                    if (used < freeMulligans ) {
                        deduction = 0;
                        usedFreeMulligans.put(player.getId(), new Integer(used+1));
                    }
                } else {
                    deduction = 0;{

                }
                    usedFreeMulligans.put(player.getId(), new Integer(1));
                }
            }
            player.drawCards(numCards - deduction, this);
            fireInformEvent(new StringBuilder(player.getName())
                    .append(" mulligans ")
                    .append(numCards)
                    .append(numCards == 1? " card":" cards")
                    .append(deduction == 0 ? " for free and draws ":" down to ")
                    .append(Integer.toString(player.getHand().size()))
                    .append(player.getHand().size() <= 1? " card":" cards").toString());
        }        
    }
    
    @Override
    public void endMulligan(UUID playerId){
        //return cards to
        Player player = getPlayer(playerId);
        if(player != null && mulliganedCards.containsKey(playerId)){
            for(Card card : mulliganedCards.get(playerId).getCards(this)){
                if(card != null){
                    card.setFaceDown(false);
                    card.moveToZone(Zone.LIBRARY, null, this, false);
                }
            }
            if(mulliganedCards.get(playerId).size() > 0){
                player.shuffleLibrary(this);
            }
        }
    }

    /* 20130711
     *903.14a A player that’s been dealt 21 or more combat damage by the same commander
     * over the course of the game loses the game. (This is a state-based action. See rule 704.) 
     *
     */
    @Override
    protected boolean checkStateBasedActions() {
        for(Watcher watcher : getState().getWatchers().values()){
            if(watcher instanceof CommanderCombatDamageWatcher){
                CommanderCombatDamageWatcher damageWatcher = (CommanderCombatDamageWatcher)watcher;
                for(UUID playerUUID : damageWatcher.getDamageToPlayer().keySet()){
                    Player player = getPlayer(playerUUID);
                    if(player != null && damageWatcher.getDamageToPlayer().get(playerUUID) >= 21){
                        player.lost(this);
                    }
                }
            }
        }
        return super.checkStateBasedActions();
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
        super.leave(playerId);
    }

    @Override
    public CommanderDuel copy() {
        return new CommanderDuel(this);
    }


}
