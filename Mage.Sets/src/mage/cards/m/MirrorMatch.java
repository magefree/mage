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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author LevelX2
 */
public class MirrorMatch extends CardImpl {

    public MirrorMatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}{U}");

        // Cast Mirror Match only during the declare blockers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(PhaseStep.DECLARE_BLOCKERS));

        // For each creature attacking you or a planeswalker you control, create a token that's a copy of that creature blocking that creature. Exile those tokens at end of combat.
        this.getSpellAbility().addEffect(new MirrorMatchEffect());

    }

    public MirrorMatch(final MirrorMatch card) {
        super(card);
    }

    @Override
    public MirrorMatch copy() {
        return new MirrorMatch(this);
    }
}

class MirrorMatchEffect extends OneShotEffect {

    public MirrorMatchEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each creature attacking you or a planeswalker you control, create a token that's a copy of that creature blocking that creature. Exile those tokens at end of combat";
    }

    public MirrorMatchEffect(final MirrorMatchEffect effect) {
        super(effect);
    }

    @Override
    public MirrorMatchEffect copy() {
        return new MirrorMatchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID attackerId : game.getCombat().getAttackers()) {
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null
                        && source.getControllerId().equals(game.getCombat().getDefendingPlayerId(attackerId, game))) {
                    PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect(source.getControllerId(), null, false);
                    effect.setTargetPointer(new FixedTarget(attacker, game));
                    effect.apply(game, source);
                    CombatGroup group = game.getCombat().findGroup(attacker.getId());
                    boolean isCreature = false;
                    if (group != null) {
                        for (Permanent addedToken : effect.getAddedPermanent()) {
                            if (addedToken.isCreature()) {
                                group.addBlockerToGroup(addedToken.getId(), attackerId, game);
                                isCreature = true;
                            }
                        }
                        ExileTargetEffect exileEffect = new ExileTargetEffect("Exile those tokens at end of combat");
                        exileEffect.setTargetPointer(new FixedTargets(effect.getAddedPermanent(), game));
                        game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect), source);
                        if (isCreature) {
                            group.pickBlockerOrder(attacker.getControllerId(), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
