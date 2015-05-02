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

package mage.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.GameState;
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayerView implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID playerId;
    private final String name;
    private final int life;
    private final int poison;
    private final int libraryCount;
    private final int handCount;
    private final boolean isActive;
    private final boolean hasPriority;
    private final boolean timerActive;
    private final boolean hasLeft;
    private final ManaPoolView manaPool;
    private final CardsView graveyard = new CardsView();
    private final CardsView exile = new CardsView();
    private final Map<UUID, PermanentView> battlefield = new LinkedHashMap<>();
    private final CardView topCard;
    private final UserDataView userDataView;
    private final List<CommandObjectView> commandList = new ArrayList<>();
    private final List<UUID> attachments = new ArrayList<>();
    private final int statesSavedSize;
    private final int priorityTimeLeft;
    private final boolean passedTurn; // F4
    private final boolean passedUntilEndOfTurn; // F5
    private final boolean passedUntilNextMain; // F6
    private final boolean passedUntilStackResolved; // F8
    private final boolean passedAllTurns; // F9

    public PlayerView(Player player, GameState state, Game game, UUID createdForPlayerId, UUID watcherUserId) {
        this.playerId = player.getId();
        this.name = player.getName();
        this.life = player.getLife();
        this.poison = player.getCounters().getCount(CounterType.POISON);
        this.libraryCount = player.getLibrary().size();
        this.handCount = player.getHand().size();
        this.manaPool = new ManaPoolView(player.getManaPool());
        this.isActive = (player.getId().equals(state.getActivePlayerId()));
        this.hasPriority = player.getId().equals(state.getPriorityPlayerId());
        this.priorityTimeLeft = player.getPriorityTimeLeft();
        this.timerActive = (this.hasPriority && player.isGameUnderControl()) ||
                (player.getPlayersUnderYourControl().contains(state.getPriorityPlayerId()));

        this.hasLeft = player.hasLeft();
        for (Card card: player.getGraveyard().getCards(game)) {
            graveyard.put(card.getId(), new CardView(card));
        }
        for (ExileZone exileZone : game.getExile().getExileZones()) {
            for (Card card : exileZone.getCards(game)) {
                if (player.getId().equals(card.getOwnerId())) {
                    exile.put(card.getId(), new CardView(card, game, card.getId(), false)); // unnown if it's allowed to look under a face down card
                }                
            }
        }
        for (Permanent permanent: state.getBattlefield().getAllPermanents()) {
            if (showInBattlefield(permanent, state)) {
                PermanentView view = new PermanentView(permanent, game.getCard(permanent.getId()), createdForPlayerId, game);
                battlefield.put(view.getId(), view);
            }
        }
        this.topCard = player.isTopCardRevealed() && player.getLibrary().size() > 0 ?
                new CardView(player.getLibrary().getFromTop(game)) : null;
        if (player.getUserData() != null) {
            this.userDataView = new UserDataView(player.getUserData());
        } else {
            this.userDataView = new UserDataView(0, false, false, null);
        }
        
        for (CommandObject commandObject : game.getState().getCommand()) {
            if (commandObject instanceof Emblem) {
                Emblem emblem = (Emblem) commandObject;
                if (emblem.getControllerId().equals(this.playerId)) {
                    Card sourceCard = game.getCard(((CommandObject)emblem).getSourceId());
                    if (sourceCard != null) {
                        commandList.add(new EmblemView(emblem, sourceCard));
                    }
                }
            }
            else if(commandObject instanceof Commander){
                Commander commander = (Commander)commandObject;
                if(commander.getControllerId().equals(this.playerId)){
                    Card sourceCard = game.getCard(commander.getSourceId());
                    if(sourceCard != null){
                        commandList.add(new CommanderView(commander, sourceCard, game));
                    }
                }
            }
        }

        if (player.getAttachments() != null) {
            attachments.addAll(player.getAttachments());
        }

        this.statesSavedSize = player.getStoredBookmark();

        this.passedTurn = player.getPassedTurn();
        this.passedUntilEndOfTurn = player.getPassedUntilEndOfTurn();
        this.passedUntilNextMain = player.getPassedUntilNextMain();
        this.passedAllTurns = player.getPassedAllTurns();
        this.passedUntilStackResolved = player.getPassedUntilStackResolved();
    }

    private boolean showInBattlefield(Permanent permanent, GameState state) {

        //show permanents controlled by player or attachments to permanents controlled by player
        if (permanent.getAttachedTo() == null) {
            return permanent.getControllerId().equals(playerId);
        }
        else {
            Permanent attachedTo = state.getPermanent(permanent.getAttachedTo());
            if (attachedTo != null) {
                return attachedTo.getControllerId().equals(playerId);
            }
            else {
                return permanent.getControllerId().equals(playerId);
            }
        }
    }

    public int getLife() {
        return this.life;
    }

    public int getPoison() {
        return this.poison;
    }

    public int getLibraryCount() {
        return this.libraryCount;
    }

    public int getHandCount() {
        return this.handCount;
    }

    public ManaPoolView getManaPool() {
        return this.manaPool;
    }

    public CardsView getGraveyard() {
        return this.graveyard;
    }

    public CardsView getExile() {
        return exile;
    }

    public Map<UUID, PermanentView> getBattlefield() {
        return this.battlefield;
    }

    public UUID getPlayerId() {
        return this.playerId;
    }

    public String getName() {
        return this.name;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public boolean hasLeft() {
        return this.hasLeft;
    }

    public CardView getTopCard() {
        return this.topCard;
    }

    public UserDataView getUserData() {
        return this.userDataView;
    }

    public List<CommandObjectView> getCommadObjectList() {
        return commandList;
    }

    public List<UUID> getAttachments() {
        return attachments;
    }

    public boolean hasAttachments() {
        return attachments != null && attachments.size() > 0;
    }

    public int getStatesSavedSize() {
        return statesSavedSize;
    }

    public int getPriorityTimeLeft() {
        return priorityTimeLeft;
    }

    public boolean hasPriority() {
        return hasPriority;
    }

    public boolean isTimerActive() {
        return timerActive;
    }

    public boolean isPassedTurn() {
        return passedTurn;
    }

    public boolean isPassedUntilEndOfTurn() {
        return passedUntilEndOfTurn;
    }

    public boolean isPassedUntilNextMain() {
        return passedUntilNextMain;
    }

    public boolean isPassedAllTurns() {
        return passedAllTurns;
    }

    public boolean isPassedUntilStackResolved() {
        return passedUntilStackResolved;
    }
}
