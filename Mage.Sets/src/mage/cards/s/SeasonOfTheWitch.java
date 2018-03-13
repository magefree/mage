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
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author L_J
 */
public class SeasonOfTheWitch extends CardImpl {

    public SeasonOfTheWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");

        // At the beginning of your upkeep, sacrifice Season of the Witch unless you pay 2 life.
        Cost cost = new PayLifeCost(2);
        cost.setText("2 life");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(cost), TargetController.YOU, false));

        // At the beginning of the end step, destroy all untapped creatures that didn't attack this turn, except for creatures that couldn't attack.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new SeasonOfTheWitchEffect(), TargetController.ANY, false);
        ability.addWatcher(new AttackedThisTurnWatcher());
        ability.addWatcher(new CouldAttackThisTurnWatcher());
        this.addAbility(ability);
    }

    public SeasonOfTheWitch(final SeasonOfTheWitch card) {
        super(card);
    }

    @Override
    public SeasonOfTheWitch copy() {
        return new SeasonOfTheWitch(this);
    }
}

class SeasonOfTheWitchEffect extends OneShotEffect {

    SeasonOfTheWitchEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all untapped creatures that didn't attack this turn, except for creatures that couldn't attack";
    }

    SeasonOfTheWitchEffect(final SeasonOfTheWitchEffect effect) {
        super(effect);
    }

    @Override
    public SeasonOfTheWitchEffect copy() {
        return new SeasonOfTheWitchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game)) {
                // Noncreature cards are safe.
                if (!permanent.isCreature()) {
                    continue;
                }
                // Tapped cards are safe.
                if (permanent.isTapped()) {
                    continue;
                }
                // Creatures that attacked are safe.
                AttackedThisTurnWatcher watcher = (AttackedThisTurnWatcher) game.getState().getWatchers().get(AttackedThisTurnWatcher.class.getSimpleName());
                if (watcher != null
                        && watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(permanent, game))) {
                    continue;
                }
                // Creatures that couldn't attack are safe.
                CouldAttackThisTurnWatcher watcher2 = (CouldAttackThisTurnWatcher) game.getState().getWatchers().get(CouldAttackThisTurnWatcher.class.getSimpleName());
                if (watcher2 != null
                        && !watcher2.getCouldAttackThisTurnCreatures().contains(new MageObjectReference(permanent, game))) {
                    continue;
                }
                // Destroy the rest.
                permanent.destroy(source.getSourceId(), game, false);
            }
            return true;
        }
        return false;
    }
}

class CouldAttackThisTurnWatcher extends Watcher {

    public final Set<MageObjectReference> couldAttackThisTurnCreatures = new HashSet<>();

    public CouldAttackThisTurnWatcher() {
        super(CouldAttackThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CouldAttackThisTurnWatcher(final CouldAttackThisTurnWatcher watcher) {
        super(watcher);
        this.couldAttackThisTurnCreatures.addAll(watcher.couldAttackThisTurnCreatures);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKERS_STEP_PRE) {
            Player activePlayer = game.getPlayer(game.getActivePlayerId());
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(activePlayer.getId())) {
                if (permanent.isCreature()) {
                    for (UUID defender : game.getCombat().getDefenders()) {
                        if (defender != activePlayer.getId()) {
                            if (permanent.canAttack(defender, game)) {
                                // exclude Propaganda style effects
                                if (!game.getContinuousEffects().checkIfThereArePayCostToAttackBlockEffects(
                                        GameEvent.getEvent(GameEvent.EventType.DECLARE_ATTACKER,
                                                defender, permanent.getId(), permanent.getControllerId()), game)) {
                                    this.couldAttackThisTurnCreatures.add(new MageObjectReference(permanent.getId(), game));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Set<MageObjectReference> getCouldAttackThisTurnCreatures() {
        return this.couldAttackThisTurnCreatures;
    }

    @Override
    public CouldAttackThisTurnWatcher copy() {
        return new CouldAttackThisTurnWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        this.couldAttackThisTurnCreatures.clear();
    }
}
