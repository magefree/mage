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

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

/**
 *
 * @author jeffwadsworth & emerald000 & L_J
 */
public class TotalWar extends CardImpl {

    public TotalWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // Whenever a player attacks with one or more creatures, destroy all untapped non-Wall creatures that player controls that didn't attack, except for creatures the player hasn't controlled continuously since the beginning of the turn.
        this.addAbility(new TotalWarTriggeredAbility(), new AttackedOrBlockedThisCombatWatcher());
    }

    public TotalWar(final TotalWar card) {
        super(card);
    }

    @Override
    public TotalWar copy() {
        return new TotalWar(this);
    }
}

class TotalWarTriggeredAbility extends TriggeredAbilityImpl {

    public TotalWarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TotalWarDestroyEffect());
    }

    public TotalWarTriggeredAbility(final TotalWarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TotalWarTriggeredAbility copy() {
        return new TotalWarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !game.getCombat().getAttackers().isEmpty();
    }

    @Override
    public String getRule() {
        return "Whenever a player attacks with one or more creatures, " + super.getRule();
    }
}

class TotalWarDestroyEffect extends OneShotEffect {

    TotalWarDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all untapped non-Wall creatures that player controls that didn't attack, except for creatures the player hasn't controlled continuously since the beginning of the turn";
    }

    TotalWarDestroyEffect(final TotalWarDestroyEffect effect) {
        super(effect);
    }

    @Override
    public TotalWarDestroyEffect copy() {
        return new TotalWarDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(activePlayer.getId())) {
                // Noncreature cards are safe.
                if (!permanent.isCreature()) {
                    continue;
                }
                // Tapped cards are safe.
                if (permanent.isTapped()) {
                    continue;
                }
                // Walls are safe.
                if (permanent.hasSubtype(SubType.WALL, game)) {
                    continue;
                }
                // Creatures that attacked are safe.
                AttackedOrBlockedThisCombatWatcher watcher = (AttackedOrBlockedThisCombatWatcher) game.getState().getWatchers().get(AttackedOrBlockedThisCombatWatcher.class.getSimpleName());
                if (watcher != null 
                    && watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(permanent, game))) {
                    continue;
                }
                // Creatures that weren't controlled since the beginning of turn are safe.
                if (!permanent.wasControlledFromStartOfControllerTurn()) {
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
