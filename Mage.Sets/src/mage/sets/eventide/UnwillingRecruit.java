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
package mage.sets.eventide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class UnwillingRecruit extends CardImpl<UnwillingRecruit> {

    public UnwillingRecruit(UUID ownerId) {
        super(ownerId, 64, "Unwilling Recruit", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");
        this.expansionSetCode = "EVE";

        this.color.setRed(true);

        // Gain control of target creature until end of turn. Untap that creature. It gets +X/+0 and gains haste until end of turn.
        this.getSpellAbility().addEffect(new UnwillingRecruitEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));

    }

    public UnwillingRecruit(final UnwillingRecruit card) {
        super(card);
    }

    @Override
    public UnwillingRecruit copy() {
        return new UnwillingRecruit(this);
    }
}

class UnwillingRecruitEffect extends OneShotEffect<UnwillingRecruitEffect> {

    UnwillingRecruitEffect() {
        super(Outcome.Benefit);
        staticText = "Gain control of target creature until end of turn. Untap that creature. It gets +X/+0 and gains haste until end of turn";
    }

    UnwillingRecruitEffect(UnwillingRecruitEffect effect) {
        super(effect);
    }

    @Override
    public UnwillingRecruitEffect copy() {
        return new UnwillingRecruitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            source.getEffects().get(0).setTargetPointer(new FixedTarget(targetCreature.getId()));
            game.addEffect(new GainControlTargetEffect(Duration.EndOfTurn), source);
            targetCreature.untap(game);
            game.addEffect(new BoostTargetEffect(source.getManaCostsToPay().getX(), 0, Duration.EndOfTurn), source);
            game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}