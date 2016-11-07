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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class RighteousFury extends CardImpl {

    public RighteousFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");

        // Destroy all tapped creatures. You gain 2 life for each creature destroyed this way.
        this.getSpellAbility().addEffect(new RighteousFuryEffect());
    }

    public RighteousFury(final RighteousFury card) {
        super(card);
    }

    @Override
    public RighteousFury copy() {
        return new RighteousFury(this);
    }
}

class RighteousFuryEffect extends OneShotEffect {

    public RighteousFuryEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all tapped creatures. You gain 2 life for each creature destroyed this way";
    }

    public RighteousFuryEffect(final RighteousFuryEffect effect) {
        super(effect);
    }

    @Override
    public RighteousFuryEffect copy() {
        return new RighteousFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int destroyedCreature = 0;
            FilterCreaturePermanent filter = new FilterCreaturePermanent("all tapped creatures");
            filter.add(new TappedPredicate());
            for(Permanent creature: game.getState().getBattlefield().getActivePermanents(filter, controller.getId(), game)) {
                if (creature.destroy(source.getSourceId(), game, false)) {
                    destroyedCreature++;
                }
            }
            if (destroyedCreature > 0) {
                new GainLifeEffect(destroyedCreature * 2).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
