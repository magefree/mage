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
package mage.cards.t;

import java.util.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public class TheFallen extends CardImpl {

    public TheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, The Fallen deals 1 damage to each opponent it has dealt damage to this game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TheFallenEffect(), TargetController.YOU, false), new TheFallenWatcher());
    }

    public TheFallen(final TheFallen card) {
        super(card);
    }

    @Override
    public TheFallen copy() {
        return new TheFallen(this);
    }
}

class TheFallenEffect extends OneShotEffect {

    public TheFallenEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to each opponent it has dealt damage to this game";
    }

    public TheFallenEffect(final TheFallenEffect effect) {
        super(effect);
    }

    @Override
    public TheFallenEffect copy() {
        return new TheFallenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TheFallenWatcher watcher = (TheFallenWatcher) game.getState().getWatchers().get(TheFallenWatcher.class.getSimpleName());
        if (watcher != null && watcher.getPlayerDealtDamageThisGame(source.getSourceId()) != null) {
            for (UUID playerId : watcher.getPlayerDealtDamageThisGame(source.getSourceId())) {
                if (!source.getControllerId().equals(playerId)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.damage(1, source.getSourceId(), game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class TheFallenWatcher extends Watcher {

    private Map<UUID, Set<UUID>> playersDealtDamageThisGame = new HashMap<>(); // Map<creatureId, Set<playerId>>

    public TheFallenWatcher() {
        super(TheFallenWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public TheFallenWatcher(final TheFallenWatcher watcher) {
        super(watcher);
        playersDealtDamageThisGame = new HashMap<>(watcher.playersDealtDamageThisGame);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent != null) {
                Set<UUID> toAdd;
                if (playersDealtDamageThisGame.get(event.getSourceId()) == null) {
                    toAdd = new HashSet<>();
                } else {
                    toAdd = playersDealtDamageThisGame.get(event.getSourceId());
                }
                toAdd.add(event.getPlayerId());
                playersDealtDamageThisGame.put(event.getSourceId(), toAdd);
            }
        }
    }

    public Set<UUID> getPlayerDealtDamageThisGame(UUID creatureId) {
        return playersDealtDamageThisGame.get(creatureId);
    }

    @Override
    public TheFallenWatcher copy() {
        return new TheFallenWatcher(this);
    }
}
