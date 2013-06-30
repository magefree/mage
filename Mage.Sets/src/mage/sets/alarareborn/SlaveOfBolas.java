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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class SlaveOfBolas extends CardImpl<SlaveOfBolas> {

    public SlaveOfBolas(UUID ownerId) {
        super(ownerId, 136, "Slave of Bolas", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{U/R}{B}");
        this.expansionSetCode = "ARB";

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);

        // Gain control of target creature. Untap that creature. It gains haste until end of turn. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new SlaveOfBolasEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public SlaveOfBolas(final SlaveOfBolas card) {
        super(card);
    }

    @Override
    public SlaveOfBolas copy() {
        return new SlaveOfBolas(this);
    }
}

class SlaveOfBolasEffect extends OneShotEffect<SlaveOfBolasEffect> {

    public SlaveOfBolasEffect() {
        super(Outcome.Sacrifice);
        staticText = "Sacrifice it at the beginning of the next end step";
    }

    public SlaveOfBolasEffect(final SlaveOfBolasEffect effect) {
        super(effect);
    }

    @Override
    public SlaveOfBolasEffect copy() {
        return new SlaveOfBolasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice this");
        sacrificeEffect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
        DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(sacrificeEffect);
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
    }
}
