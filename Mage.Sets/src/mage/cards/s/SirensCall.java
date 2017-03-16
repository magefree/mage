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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.BeforeAttackersAreDeclaredCondition;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author emerald000
 */
public class SirensCall extends CardImpl {

    public SirensCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Cast Siren's Call only during an opponent's turn, before attackers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, null,
                new CompoundCondition(OnOpponentsTurnCondition.instance, BeforeAttackersAreDeclaredCondition.instance),
                "Cast {this} only during an opponent's turn, before attackers are declared"));

        // Creatures the active player controls attack this turn if able.
        this.getSpellAbility().addEffect(new SirensCallMustAttackEffect());

        // At the beginning of the next end step, destroy all non-Wall creatures that player controls that didn't attack this turn. Ignore this effect for each creature the player didn't control continuously since the beginning of the turn.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SirensCallDestroyEffect())));
        this.getSpellAbility().addWatcher(new AttackedThisTurnWatcher());
    }

    public SirensCall(final SirensCall card) {
        super(card);
    }

    @Override
    public SirensCall copy() {
        return new SirensCall(this);
    }
}

class SirensCallMustAttackEffect extends RequirementEffect {

    SirensCallMustAttackEffect() {
        super(Duration.EndOfTurn);
        staticText = "Creatures the active player controls attack this turn if able";
    }

    SirensCallMustAttackEffect(final SirensCallMustAttackEffect effect) {
        super(effect);
    }

    @Override
    public SirensCallMustAttackEffect copy() {
        return new SirensCallMustAttackEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return game.getActivePlayerId().equals(permanent.getControllerId());
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}

class SirensCallDestroyEffect extends OneShotEffect {

    SirensCallDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all non-Wall creatures that player controls that didn't attack this turn. Ignore this effect for each creature the player didn't control continuously since the beginning of the turn";
    }

    SirensCallDestroyEffect(final SirensCallDestroyEffect effect) {
        super(effect);
    }

    @Override
    public SirensCallDestroyEffect copy() {
        return new SirensCallDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
                                
                   // Non Creature Cards are safe.
                if(!permanent.isCreature())
                    continue;  
                
                // Walls are safe.
                if (permanent.getSubtype(game).contains("Wall")) {
                    continue;
                }
                // Creatures that attacked are safe.
                AttackedThisTurnWatcher watcher = (AttackedThisTurnWatcher) game.getState().getWatchers().get("AttackedThisTurn");
                if (watcher != null && watcher.getAttackedThisTurnCreatures().contains(permanent.getId())) {
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
