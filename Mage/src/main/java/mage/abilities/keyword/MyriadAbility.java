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
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

public class MyriadAbility extends AttacksTriggeredAbility {

    public MyriadAbility() {
        super(new MyriadEffect(), false,
                "Myriad <i>(Whenever this creature attacks, for each opponent other than the defending player, put a token that's a copy of this creature onto the battlefield tapped and attacking that player or a planeswalker he or she controls. Exile those tokens at the end of combat.)</i>",
                SetTargetPointer.PLAYER
        );
    }

    public MyriadAbility(final MyriadAbility ability) {
        super(ability);
    }

    @Override
    public MyriadAbility copy() {
        return new MyriadAbility(this);
    }

}

class MyriadEffect extends OneShotEffect {

    public MyriadEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each opponent other than the defending player, you may put a token that's a copy of this creature onto the battlefield tapped and attacking that player or a planeswalker he or she controls. Exile the tokens at the end of combat";
    }

    public MyriadEffect(final MyriadEffect effect) {
        super(effect);
    }

    @Override
    public MyriadEffect copy() {
        return new MyriadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceObject != null) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (playerId != defendingPlayerId && controller.hasOpponent(playerId, game)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null && controller.chooseUse(Outcome.PutCreatureInPlay,
                            "Put a copy of " + sourceObject.getIdName() + " onto battlefield attacking " + opponent.getName() + "?", source, game)) {
                        PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect(controller.getId(), null, false, 1, true, true, playerId);
                        effect.setTargetPointer(new FixedTarget(sourceObject, game));
                        effect.apply(game, source);
                        ExileTargetEffect exileEffect = new ExileTargetEffect();
                        exileEffect.setTargetPointer(new FixedTargets(effect.getAddedPermanent(), game));
                        DelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                }

            }
            return true;
        }
        return false;
    }
}
