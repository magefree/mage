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
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public class SecondHarvest extends CardImpl {

    public SecondHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}{G}");

        // For each token you control, create a token that's a copy of that permanent.
        this.getSpellAbility().addEffect(new SecondHarvestEffect());
    }

    public SecondHarvest(final SecondHarvest card) {
        super(card);
    }

    @Override
    public SecondHarvest copy() {
        return new SecondHarvest(this);
    }
}

class SecondHarvestEffect extends OneShotEffect {

    public SecondHarvestEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each token you control, create a token that's a copy of that permanent";
    }

    public SecondHarvestEffect(final SecondHarvestEffect effect) {
        super(effect);
    }

    @Override
    public SecondHarvestEffect copy() {
        return new SecondHarvestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterControlledPermanent filter = new FilterControlledPermanent("each token you control");
            filter.add(new TokenPredicate());
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
                if (permanent != null) {
                    PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
