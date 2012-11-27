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

import mage.cards.Card;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.GameState;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayerView implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID playerId;
    private String name;
    private int life;
    private int poison;
    private int libraryCount;
    private int handCount;
    private boolean isActive;
    private boolean hasLeft;
    private ManaPoolView manaPool;
    private SimpleCardsView graveyard = new SimpleCardsView();
    private Map<UUID, PermanentView> battlefield = new LinkedHashMap<UUID, PermanentView>();
    private CardView topCard;
    private UserDataView userDataView;

    public PlayerView(Player player, GameState state, Game game) {
        this.playerId = player.getId();
        this.name = player.getName();
        this.life = player.getLife();
        this.poison = player.getCounters().getCount(CounterType.POISON);
        this.libraryCount = player.getLibrary().size();
        this.handCount = player.getHand().size();
        this.manaPool = new ManaPoolView(player.getManaPool());
        this.isActive = (player.getId().equals(state.getActivePlayerId()));
        this.hasLeft = player.hasLeft();
        for (Card card: player.getGraveyard().getCards(game)) {
            graveyard.put(card.getId(), new SimpleCardView(card.getId(), card.getExpansionSetCode(), card.getCardNumber(), card.getUsesVariousArt(), card.isFaceDown()));
        }
        for (Permanent permanent: state.getBattlefield().getAllPermanents()) {
            if (showInBattlefield(permanent, state)) {
                PermanentView view = new PermanentView(permanent, game.getCard(permanent.getId()));
                battlefield.put(view.getId(), view);
            }
        }
        this.topCard = player.isTopCardRevealed() && player.getLibrary().size() > 0 ?
                new CardView(player.getLibrary().getFromTop(game)) : null;
        if (player.getUserData() != null) {
            this.userDataView = new UserDataView(player.getUserData());
        } else {
            this.userDataView = new UserDataView(0);
        }
    }

    private boolean showInBattlefield(Permanent permanent, GameState state) {

        //show permanents controlled by player or attachments to permanents controlled by player
        if (permanent.getAttachedTo() == null)
            return permanent.getControllerId().equals(playerId);
        else {
            Permanent attachedTo = state.getPermanent(permanent.getAttachedTo());
            if (attachedTo != null)
                return attachedTo.getControllerId().equals(playerId);
            else
                return permanent.getControllerId().equals(playerId);
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

    public SimpleCardsView getGraveyard() {
        return this.graveyard;
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
}
