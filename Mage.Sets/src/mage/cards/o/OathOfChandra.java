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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class OathOfChandra extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public OathOfChandra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.supertype.add("Legendary");

        // When Oath of Chandra enters the battlefield, it deals 3 damage to target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        // At the beginning of each end step, if a planeswalker entered the battlefield under your control this turn, Oath of Chandra deals 2 damage to each opponent.
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
                new DamagePlayersEffect(Outcome.Damage, new StaticValue(2), TargetController.OPPONENT),
                TargetController.ANY, false), OathOfChandraCondition.getInstance(),
                "At the beginning of each end step, if a planeswalker entered the battlefield under your control this turn, {this} deals 2 damage to each opponent."), new OathOfChandraWatcher());
    }

    public OathOfChandra(final OathOfChandra card) {
        super(card);
    }

    @Override
    public OathOfChandra copy() {
        return new OathOfChandra(this);
    }
}

class OathOfChandraCondition implements Condition {

    private static final OathOfChandraCondition fInstance = new OathOfChandraCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        OathOfChandraWatcher watcher = (OathOfChandraWatcher) game.getState().getWatchers().get("OathOfChandraWatcher");
        return watcher != null && watcher.enteredPlaneswalkerForPlayer(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if a planeswalker entered the battlefield under your control this turn";
    }

}

class OathOfChandraWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    public OathOfChandraWatcher() {
        super("OathOfChandraWatcher", WatcherScope.GAME);
    }

    public OathOfChandraWatcher(final OathOfChandraWatcher watcher) {
        super(watcher);
        this.players.addAll(watcher.players);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.BATTLEFIELD
                    && zEvent.getTarget().getCardType().contains(CardType.PLANESWALKER)) {
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
    public OathOfChandraWatcher copy() {
        return new OathOfChandraWatcher(this);
    }

}
