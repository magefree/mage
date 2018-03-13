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

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.*;

/**
 *
 * @author LevelX2
 */
public class TymnaTheWeaver extends CardImpl {

    public TymnaTheWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your postcombat main phase, you may pay X life, where X is the number of opponents that were dealt combat damage this turn. If you do, draw X cards.
        this.addAbility(new BeginningOfPostCombatMainTriggeredAbility(new TymnaTheWeaverEffect(), TargetController.YOU, true), new TymnaTheWeaverWatcher());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    public TymnaTheWeaver(final TymnaTheWeaver card) {
        super(card);
    }

    @Override
    public TymnaTheWeaver copy() {
        return new TymnaTheWeaver(this);
    }
}

class TymnaTheWeaverEffect extends OneShotEffect {

    public TymnaTheWeaverEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you may pay X life, where X is the number of opponents that were dealt combat damage this turn. If you do, draw X cards";
    }

    public TymnaTheWeaverEffect(final TymnaTheWeaverEffect effect) {
        super(effect);
    }

    @Override
    public TymnaTheWeaverEffect copy() {
        return new TymnaTheWeaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TymnaTheWeaverWatcher watcher = (TymnaTheWeaverWatcher) game.getState().getWatchers().get(TymnaTheWeaverWatcher.class.getSimpleName());
            if (watcher != null) {
                int cardsToDraw = watcher.opponentsThatGotCombatDamage(source.getControllerId(), game);
                Cost cost = new PayLifeCost(cardsToDraw);
                if (cost.canPay(source, source.getSourceId(), source.getControllerId(), game)
                        && cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                    controller.drawCards(cardsToDraw, game);
                }
                return true;
            }
        }
        return false;
    }
}

class TymnaTheWeaverWatcher extends Watcher {

    // private final Set<UUID> players = new HashSet<>();
    private final Map<UUID, Set<UUID>> players = new HashMap<>();

    public TymnaTheWeaverWatcher() {
        super(TymnaTheWeaverWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public TymnaTheWeaverWatcher(final TymnaTheWeaverWatcher watcher) {
        super(watcher);
        for (UUID playerId : watcher.players.keySet()) {
            Set<UUID> opponents = new HashSet<>();
            opponents.addAll(watcher.players.get(playerId));
            players.put(playerId, opponents);
        }
    }

    @Override
    public TymnaTheWeaverWatcher copy() {
        return new TymnaTheWeaverWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent dEvent = (DamagedPlayerEvent) event;
            if (dEvent.isCombatDamage()) {
                if (players.containsKey(event.getTargetId())) { // opponenets can die before number of opponents are checked
                    players.get(event.getTargetId()).addAll(game.getOpponents(event.getTargetId()));
                } else {
                    players.put(event.getTargetId(), game.getOpponents(event.getTargetId()));
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    public int opponentsThatGotCombatDamage(UUID playerId, Game game) {
        int numberOfOpponents = 0;
        for (Set<UUID> opponents : players.values()) {
            if (opponents.contains(playerId)) {
                numberOfOpponents++;
            }
        }
        return numberOfOpponents;
    }

}
