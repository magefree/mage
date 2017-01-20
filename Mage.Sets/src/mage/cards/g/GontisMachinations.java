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
package mage.cards.g;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class GontisMachinations extends CardImpl {

    public GontisMachinations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Whenever you lose life for the first time each turn, you get {E}.
        this.addAbility(new GontisMachinationsTriggeredAbility(), new GontisMachinationsFirstLostLifeThisTurnWatcher());

        // Pay {E}{E}, Sacrifice Gonti's Machinations: Each opponent loses 3 life. You gain life equal to the life lost this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GontisMachinationsEffect(), new PayEnergyCost(2));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    public GontisMachinations(final GontisMachinations card) {
        super(card);
    }

    @Override
    public GontisMachinations copy() {
        return new GontisMachinations(this);
    }
}

class GontisMachinationsTriggeredAbility extends TriggeredAbilityImpl {

    public GontisMachinationsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GetEnergyCountersControllerEffect(1), false);
    }

    public GontisMachinationsTriggeredAbility(final GontisMachinationsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            GontisMachinationsFirstLostLifeThisTurnWatcher watcher
                    = (GontisMachinationsFirstLostLifeThisTurnWatcher) game.getState().getWatchers().get(GontisMachinationsFirstLostLifeThisTurnWatcher.class.getName());
            if (watcher != null && watcher.timesLostLifeThisTurn(event.getTargetId()) < 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GontisMachinationsTriggeredAbility copy() {
        return new GontisMachinationsTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you lose life for the first time each turn, " + super.getRule();
    }
}

class GontisMachinationsFirstLostLifeThisTurnWatcher extends Watcher {

    private final Map<UUID, Integer> playersLostLife = new HashMap<>();

    public GontisMachinationsFirstLostLifeThisTurnWatcher() {
        super(GontisMachinationsFirstLostLifeThisTurnWatcher.class.getName(), WatcherScope.GAME);
    }

    public GontisMachinationsFirstLostLifeThisTurnWatcher(final GontisMachinationsFirstLostLifeThisTurnWatcher watcher) {
        super(watcher);
        this.playersLostLife.putAll(watcher.playersLostLife);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case LOST_LIFE:
                int timesLifeLost = 0;
                if (playersLostLife.containsKey(event.getTargetId())) {
                    timesLifeLost = playersLostLife.get(event.getTargetId());
                }
                playersLostLife.put(event.getTargetId(), timesLifeLost);
        }
    }

    @Override
    public GontisMachinationsFirstLostLifeThisTurnWatcher copy() {
        return new GontisMachinationsFirstLostLifeThisTurnWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        playersLostLife.clear();
    }

    public int timesLostLifeThisTurn(UUID playerId) {
        if (playersLostLife.containsKey(playerId)) {
            return playersLostLife.get(playerId);
        }
        return 0;
    }
}

class GontisMachinationsEffect extends OneShotEffect {

    public GontisMachinationsEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 3 life. You gain life equal to the life lost this way";
    }

    public GontisMachinationsEffect(final GontisMachinationsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            damage += game.getPlayer(opponentId).damage(3, source.getSourceId(), game, false, true);
        }
        game.getPlayer(source.getControllerId()).gainLife(damage, game);
        return true;
    }

    @Override
    public GontisMachinationsEffect copy() {
        return new GontisMachinationsEffect(this);
    }

}
