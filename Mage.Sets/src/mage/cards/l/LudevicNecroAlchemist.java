/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author spjspj
 */
public class LudevicNecroAlchemist extends CardImpl {

    public LudevicNecroAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // At the beginning of each player's end step, that player may draw a card if a player other than you lost life this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new LudevicNecroAlchemistEffect(), TargetController.ANY, new LudevicNecroAlchemistCondition(), false));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    public LudevicNecroAlchemist(final LudevicNecroAlchemist card) {
        super(card);
    }

    @Override
    public LudevicNecroAlchemist copy() {
        return new LudevicNecroAlchemist(this);
    }
}

class LudevicNecroAlchemistCondition implements Condition {
    
    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = (PlayerLostLifeWatcher) game.getState().getWatchers().get(PlayerLostLifeWatcher.class.getSimpleName());
        UUID player = game.getActivePlayerId();
        PlayerList playerList = game.getState().getPlayerList().copy();
        Player currentPlayer = null;
        UUID sourcePlayerId = source.getControllerId();
        Player firstPlayer = null;
        if (playerList != null) {
            firstPlayer = playerList.getCurrent(game);
            currentPlayer = playerList.getNext(game);
        }

        while (watcher != null && currentPlayer != null) {
            if (currentPlayer != null && !Objects.equals(currentPlayer.getId(), sourcePlayerId) && watcher.getLiveLost(currentPlayer.getId()) > 0) {
                return true;
            }
            if (Objects.equals(currentPlayer, firstPlayer)) {
                return false;
            }
            currentPlayer = playerList.getNext(game);
        }
        return false;
    }

    public String toString() {
        return "if a player other than you lost life this turn";
    }
}

class LudevicNecroAlchemistEffect extends OneShotEffect {

    public LudevicNecroAlchemistEffect() {
        super(Outcome.DrawCard);
        staticText = "that player may draw a card if a player other than you lost life this turn";
    }

    public LudevicNecroAlchemistEffect(final LudevicNecroAlchemistEffect effect) {
        super(effect);
    }

    @Override
    public LudevicNecroAlchemistEffect copy() {
        return new LudevicNecroAlchemistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = game.getActivePlayerId();
        if (playerId != null) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.chooseUse(outcome, "Draw a card?", source, game)) {
                player.drawCards(1, game);
                return true;
            }
        }
        return false;
    }
}
