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
package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.ZombieToken;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author fireshoes
 */
public class OathOfLiliana extends CardImpl {

    public OathOfLiliana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        addSuperType(SuperType.LEGENDARY);

        // When Oath of Liliana enters the battlefield, each opponent sacrifices a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeOpponentsEffect(new FilterControlledCreaturePermanent("a creature")), false));

        // At the beginning of each end step, if a planeswalker entered the battlefield under your control this turn, create a 2/2 black Zombie creature token.
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new ZombieToken()),
                TargetController.ANY, false), OathOfLilianaCondition.instance,
                "At the beginning of each end step, if a planeswalker entered the battlefield under your control this turn, "
                        + "create a 2/2 black Zombie creature token."), new OathOfLilianaWatcher());
    }

    public OathOfLiliana(final OathOfLiliana card) {
        super(card);
    }

    @Override
    public OathOfLiliana copy() {
        return new OathOfLiliana(this);
    }
}

enum OathOfLilianaCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        OathOfLilianaWatcher watcher = (OathOfLilianaWatcher) game.getState().getWatchers().get(OathOfLilianaWatcher.class.getSimpleName());
        return watcher != null && watcher.enteredPlaneswalkerForPlayer(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if a planeswalker entered the battlefield under your control this turn";
    }

}

class OathOfLilianaWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public OathOfLilianaWatcher() {
        super(OathOfLilianaWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public OathOfLilianaWatcher(final OathOfLilianaWatcher watcher) {
        super(watcher);
        this.players.addAll(watcher.players);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD
                    && zEvent.getTarget().isPlaneswalker()) {
                players.add(zEvent.getTarget().getControllerId());
            }
        }
    }

    @Override
    public void reset() {
        players.clear();
    }

    public boolean enteredPlaneswalkerForPlayer(UUID playerId) {
        return players.contains(playerId);
    }

    @Override
    public OathOfLilianaWatcher copy() {
        return new OathOfLilianaWatcher(this);
    }

}
