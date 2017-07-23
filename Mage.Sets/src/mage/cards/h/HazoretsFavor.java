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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class HazoretsFavor extends CardImpl {

    public HazoretsFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of combat on your turn, you may have target creature you control get +2/+0 and gain haste until end of turn.
        // If you do, sacrifice it at the beginning of the next end step.
        Effect effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        effect.setText("you may have target creature you control get +2/+0");
        Ability ability = new BeginningOfCombatTriggeredAbility(effect, TargetController.YOU, true);
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText(" and gain haste until end of turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new HazoretsFavorSacrificeEffect());
        this.addAbility(ability);
    }

    public HazoretsFavor(final HazoretsFavor card) {
        super(card);
    }

    @Override
    public HazoretsFavor copy() {
        return new HazoretsFavor(this);
    }

    private static class HazoretsFavorSacrificeEffect extends OneShotEffect {

        public HazoretsFavorSacrificeEffect() {
            super(Outcome.Sacrifice);
            this.staticText = "If you do, sacrifice it at the beginning of the next end step";
        }

        public HazoretsFavorSacrificeEffect(final HazoretsFavorSacrificeEffect effect) {
            super(effect);
        }

        @Override
        public HazoretsFavorSacrificeEffect copy() {
            return new HazoretsFavorSacrificeEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (creature != null) {
                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("Sacrifice boosted " + creature.getName(), source.getControllerId());
                sacrificeEffect.setTargetPointer(new FixedTarget(creature, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
    }
}
