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
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author LevelX2
 */
public class TearsOfRage extends CardImpl {

    public TearsOfRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");

        // Cast Tears of Rage only during the declare attackers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(PhaseStep.DECLARE_ATTACKERS));

        // Attacking creatures you control get +X/+0 until end of turn, where X is the number of attacking creatures. Sacrifice those creatures at the beginning of the next end step.
        BoostControlledEffect effect = new BoostControlledEffect(new AttackingCreatureCount("the number of attacking creatures"), new StaticValue(0),
                Duration.EndOfTurn, new FilterAttackingCreature("Attacking creatures"), false);
        effect.setLockedIn(true);
        getSpellAbility().addEffect(effect);
        getSpellAbility().addEffect(new TearsOfRageEffect());
    }

    public TearsOfRage(final TearsOfRage card) {
        super(card);
    }

    @Override
    public TearsOfRage copy() {
        return new TearsOfRage(this);
    }
}

class TearsOfRageEffect extends OneShotEffect {

    public TearsOfRageEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Sacrifice those creatures at the beginning of the next end step";
    }

    public TearsOfRageEffect(final TearsOfRageEffect effect) {
        super(effect);
    }

    @Override
    public TearsOfRageEffect copy() {
        return new TearsOfRageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Effect effect = new SacrificeTargetEffect("Sacrifice those creatures at the beginning of the next end step", source.getControllerId());
            effect.setTargetPointer(new FixedTargets(game.getBattlefield().getAllActivePermanents(new FilterAttackingCreature(), controller.getId(), game), game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
            return true;
        }
        return false;
    }

}
