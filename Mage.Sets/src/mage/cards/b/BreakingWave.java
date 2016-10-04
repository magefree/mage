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
package mage.sets.invasion;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LoneFox

 */
public class BreakingWave extends CardImpl {

    public BreakingWave(UUID ownerId) {
        super(ownerId, 48, "Breaking Wave", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");
        this.expansionSetCode = "INV";

        Effect effect = new BreakingWaveEffect();
        // You may cast Breaking Wave as though it had flash if you pay {2} more to cast it.
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl("{2}"));
        ability.addEffect(effect);
        this.addAbility(ability);
        // Simultaneously untap all tapped creatures and tap all untapped creatures.
        this.getSpellAbility().addEffect(effect);
    }

    public BreakingWave(final BreakingWave card) {
        super(card);
    }

    @Override
    public BreakingWave copy() {
        return new BreakingWave(this);
    }
}

class BreakingWaveEffect extends OneShotEffect {

    public BreakingWaveEffect() {
        super(Outcome.Neutral);
        staticText = "Simultaneously untap all tapped creatures and tap all untapped creatures.";
    }

    public BreakingWaveEffect(BreakingWaveEffect copy) {
        super(copy);
    }

    @Override
    public BreakingWaveEffect copy() {
        return new BreakingWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> creatures = game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(),
            source.getControllerId(), source.getSourceId(), game);
        for(Permanent creature: creatures) {
            if(creature.isTapped()) {
                creature.untap(game);
            }
            else {
                creature.tap(game);
            }
        }
        return true;
    }
}
