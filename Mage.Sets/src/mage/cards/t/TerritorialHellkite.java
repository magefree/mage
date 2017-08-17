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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttacksIfAbleTargetPlayerSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public class TerritorialHellkite extends CardImpl {

    public TerritorialHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add("Dragon");
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of combat on your turn, choose an opponent at random that Territorial Hellkite didn't attack during your last combat. Territorial Hellkite attacks that player this combat if able. If you can't choose an opponent this way, tap Territorial Hellkite.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AttackIfAbleTargetRandoOpponentSourceEffect(), TargetController.YOU, false), new AttackedLastCombatWatcher());
    }

    public TerritorialHellkite(final TerritorialHellkite card) {
        super(card);
    }

    @Override
    public TerritorialHellkite copy() {
        return new TerritorialHellkite(this);
    }
}

class AttackedLastCombatWatcher extends Watcher {

    public final Map<UUID, UUID> attackedLastCombatPlayers = new HashMap<>();

    public AttackedLastCombatWatcher() {
        super(AttackedLastCombatWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public AttackedLastCombatWatcher(final AttackedLastCombatWatcher watcher) {
        super(watcher);
        for (Entry<UUID, UUID> entry : watcher.attackedLastCombatPlayers.entrySet()) {
            attackedLastCombatPlayers.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        //TODO: this will have problems if the creature is stolen and then given back before the original controller's next combat
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE) {
            if (!attackedLastCombatPlayers.keySet().isEmpty()) {
                Iterator<Map.Entry<UUID, UUID>> attackers = attackedLastCombatPlayers.entrySet().iterator();
                while (attackers.hasNext()) {
                    Map.Entry<UUID, UUID> attacker = attackers.next();
                    if (game.getPermanent(attacker.getKey()).getControllerId().equals(game.getActivePlayerId())) {
                        attackers.remove();
                    }
                }
            }
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            attackedLastCombatPlayers.put(event.getSourceId(), game.getCombat().getDefenderId(event.getSourceId()));
        }
    }

    public Map<UUID, UUID> getAttackedLastCombatPlayers() {
        return this.attackedLastCombatPlayers;
    }

    @Override
    public AttackedLastCombatWatcher copy() {
        return new AttackedLastCombatWatcher(this);
    }

}

class AttackIfAbleTargetRandoOpponentSourceEffect extends OneShotEffect {

    public AttackIfAbleTargetRandoOpponentSourceEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose an opponent at random that {this} didn't attack during your last combat. {this} attacks that player this combat if able. If you can't choose an opponent this way, tap {this}";
    }

    public AttackIfAbleTargetRandoOpponentSourceEffect(final AttackIfAbleTargetRandoOpponentSourceEffect effect) {
        super(effect);
    }

    @Override
    public AttackIfAbleTargetRandoOpponentSourceEffect copy() {
        return new AttackIfAbleTargetRandoOpponentSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<UUID> opponents = new ArrayList<>();
            AttackedLastCombatWatcher watcher = (AttackedLastCombatWatcher) game.getState().getWatchers().get(AttackedLastCombatWatcher.class.getSimpleName());
            if (watcher != null) {
                boolean ignoreMe;
                for (UUID opp : game.getOpponents(controller.getId())) {
                    ignoreMe = false;
                    if (watcher.getAttackedLastCombatPlayers().getOrDefault(source.getSourceId(), source.getControllerId()).equals(opp)) {
                        ignoreMe = true;
                    }
                    if (!ignoreMe) {
                        opponents.add(opp);
                    }
                }
            } else {
                opponents.addAll(game.getOpponents(controller.getId()));
            }
            if (!opponents.isEmpty()) {
                Player opponent = game.getPlayer(opponents.get(RandomUtil.nextInt(opponents.size())));
                if (opponent != null) {
                    ContinuousEffect effect = new AttacksIfAbleTargetPlayerSourceEffect();
                    effect.setTargetPointer(new FixedTarget(opponent.getId()));
                    game.addEffect(effect, source);
                    return true;
                }
            } else {
                game.getPermanent(source.getSourceId()).tap(game);
            }
        }
        return false;
    }
}
