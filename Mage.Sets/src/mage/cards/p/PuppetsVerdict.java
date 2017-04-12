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
package mage.cards.p;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class PuppetsVerdict extends CardImpl {

    public PuppetsVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{R}");

        // Flip a coin. If you win the flip, destroy all creatures with power 2 or less. If you lose the flip, destroy all creatures with power 3 or greater.
        this.getSpellAbility().addEffect(new PuppetsVerdictEffect());
    }

    public PuppetsVerdict(final PuppetsVerdict card) {
        super(card);
    }

    @Override
    public PuppetsVerdict copy() {
        return new PuppetsVerdict(this);
    }
}

class PuppetsVerdictEffect extends OneShotEffect {

    public PuppetsVerdictEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, destroy all creatures with power 2 or less. If you lose the flip, destroy all creatures with power 3 or greater";
    }

    public PuppetsVerdictEffect(PuppetsVerdictEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.flipCoin(game)) {
                
                FilterCreaturePermanent filterPower2OrLess = new FilterCreaturePermanent("all creatures power 2 or less");
                filterPower2OrLess.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
                for (Permanent permanent: game.getBattlefield().getAllActivePermanents(filterPower2OrLess, game)) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
                return true;
            } else {
                FilterCreaturePermanent filterPower3OrGreater = new FilterCreaturePermanent("all creatures power 3 or greater");
                filterPower3OrGreater.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
                for (Permanent permanent: game.getBattlefield().getAllActivePermanents(filterPower3OrGreater, game)) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
                return true;
                }
            }
        return false;
    }

    @Override
    public PuppetsVerdictEffect copy() {
        return new PuppetsVerdictEffect(this);
    }
}
