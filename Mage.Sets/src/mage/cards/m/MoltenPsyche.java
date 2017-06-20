/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.cards.m;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MoltenPsyche extends CardImpl {

    public MoltenPsyche(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}{R}");

        // Each player shuffles the cards from his or her hand into his or her library, then draws that many cards.
        // Metalcraft - If you control three or more artifacts, Molten Psyche deals damage to each opponent equal to the number of cards that player has drawn this turn.
        this.getSpellAbility().addEffect(new MoltenPsycheEffect());
        this.getSpellAbility().addWatcher(new MoltenPsycheWatcher());
    }

    public MoltenPsyche(final MoltenPsyche card) {
        super(card);
    }

    @Override
    public MoltenPsyche copy() {
        return new MoltenPsyche(this);
    }

}

class MoltenPsycheEffect extends OneShotEffect {

    public MoltenPsycheEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles the cards from his or her hand into his or her library, then draws that many cards.\n"
                + "<i>Metalcraft</i> - If you control three or more artifacts, {this} deals damage to each opponent equal to the number of cards that player has drawn this turn.";
    }

    public MoltenPsycheEffect(final MoltenPsycheEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Integer> cardsToDraw = new LinkedHashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsInHand = player.getHand().size();
                    if (cardsInHand > 0) {
                        cardsToDraw.put(playerId, cardsInHand);
                    }
                    player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                    player.shuffleLibrary(source, game);
                }
            }

            game.applyEffects(); // so effects from creatures that were on the battlefield won't trigger from draw action

            for (UUID playerId : cardsToDraw.keySet()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.drawCards(cardsToDraw.get(playerId), game);
                    if (MetalcraftCondition.instance.apply(game, source) && !playerId.equals(source.getControllerId())) {
                        MoltenPsycheWatcher watcher = (MoltenPsycheWatcher) game.getState().getWatchers().get(MoltenPsycheWatcher.class.getSimpleName());
                        player.damage(watcher.getDraws(playerId), source.getSourceId(), game, false, true);
                    }
                }
            }
            if (MetalcraftCondition.instance.apply(game, source)) {
                MoltenPsycheWatcher watcher = (MoltenPsycheWatcher) game.getState().getWatchers().get(MoltenPsycheWatcher.class.getSimpleName());
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    if (game.isOpponent(controller, playerId)) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            player.damage(watcher.getDraws(playerId), source.getSourceId(), game, false, true);
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public MoltenPsycheEffect copy() {
        return new MoltenPsycheEffect(this);
    }

}

class MoltenPsycheWatcher extends Watcher {

    private final Map<UUID, Integer> draws = new HashMap<>();

    public MoltenPsycheWatcher() {
        super(MoltenPsycheWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public MoltenPsycheWatcher(final MoltenPsycheWatcher watcher) {
        super(watcher);
        this.draws.putAll(watcher.draws);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            int count = 1;
            if (draws.containsKey(event.getPlayerId())) {
                count += draws.get(event.getPlayerId());
            }
            draws.put(event.getPlayerId(), count);
        }
    }

    @Override
    public void reset() {
        super.reset();
        draws.clear();
    }

    public int getDraws(UUID playerId) {
        return draws.getOrDefault(playerId, 0);
    }

    @Override
    public MoltenPsycheWatcher copy() {
        return new MoltenPsycheWatcher(this);
    }

}
