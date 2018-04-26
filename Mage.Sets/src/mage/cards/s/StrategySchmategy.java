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

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class StrategySchmategy extends CardImpl {

    public StrategySchmategy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Roll a six-sided die. Strategy, Schmategy has the indicated effect. 1 - Do nothing. 2 - Destroy all artifacts. 3 - Destroy all lands. 4 - Strategy, Schmategy deals 3 damage to each creature and each player. 5 - Each player discards their hand and draws seven cards. 6 - Repeat this process two more times.
        this.getSpellAbility().addEffect(new StrategySchmategyffect());
    }

    public StrategySchmategy(final StrategySchmategy card) {
        super(card);
    }

    @Override
    public StrategySchmategy copy() {
        return new StrategySchmategy(this);
    }
}

class StrategySchmategyffect extends OneShotEffect {

    public StrategySchmategyffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Roll a six-sided die. {this} has the indicated effect. 1 - Do nothing. 2 - Destroy all artifacts. 3 - Destroy all lands. 4 - {this} deals 3 damage to each creature and each player. 5 - Each player discards their hand and draws seven cards. 6 - Repeat this process two more times";
    }

    public StrategySchmategyffect(final StrategySchmategyffect effect) {
        super(effect);
    }

    @Override
    public StrategySchmategyffect copy() {
        return new StrategySchmategyffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int numTimesToDo = 1;
        if (controller != null) {
            // 1 - Do nothing.
            // 2 - Destroy all artifacts.
            // 3 - Destroy all lands.
            // 4 - {this} deals 3 damage to each creature and each player.
            // 5 - Each player discards their hand and draws seven cards.
            // 6 - Repeat this process two more times
            while (numTimesToDo > 0) {
                int amount = controller.rollDice(game, 6);
                numTimesToDo--;
                if (amount == 2) {
                    List<Permanent> artifactPermanents = game.getBattlefield().getActivePermanents(new FilterArtifactPermanent(), controller.getId(), game);
                    for (Permanent permanent : artifactPermanents) {
                        permanent.destroy(permanent.getId(), game, false);
                    }
                } else if (amount == 3) {
                    List<Permanent> landPermanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LANDS, controller.getId(), game);
                    for (Permanent permanent : landPermanents) {
                        permanent.destroy(permanent.getId(), game, false);
                    }
                } else if (amount == 4) {
                    new DamageEverythingEffect(3, new FilterCreaturePermanent()).apply(game, source);
                } else if (amount == 5) {
                    new DiscardHandAllEffect().apply(game, source);
                    new DrawCardAllEffect(7).apply(game, source);
                } else if (amount == 6) {
                    numTimesToDo += 2;
                }
            }
        }
        return false;
    }
}
