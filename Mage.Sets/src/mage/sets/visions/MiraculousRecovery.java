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
package mage.sets.visions;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class MiraculousRecovery extends CardImpl<MiraculousRecovery> {

    public MiraculousRecovery(UUID ownerId) {
        super(ownerId, 113, "Miraculous Recovery", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{W}");
        this.expansionSetCode = "VIS";

        this.color.setWhite(true);

        // Return target creature card from your graveyard to the battlefield. Put a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        Target target = new TargetCardInYourGraveyard(new FilterCreatureCard());
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new MiraculousRecoveryEffect());
    }

    public MiraculousRecovery(final MiraculousRecovery card) {
        super(card);
    }

    @Override
    public MiraculousRecovery copy() {
        return new MiraculousRecovery(this);
    }
}

class MiraculousRecoveryEffect extends OneShotEffect<MiraculousRecoveryEffect> {

    public MiraculousRecoveryEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put a +1/+1 counter on it";
    }

    public MiraculousRecoveryEffect(final MiraculousRecoveryEffect effect) {
        super(effect);
    }

    @Override
    public MiraculousRecoveryEffect copy() {
        return new MiraculousRecoveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // targetPointer can't be used because target moved from graveyard to battlefield
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(), game);
        }
        return false;
    }
}
