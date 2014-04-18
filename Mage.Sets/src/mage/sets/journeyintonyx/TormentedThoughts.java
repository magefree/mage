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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class TormentedThoughts extends CardImpl<TormentedThoughts> {

    public TormentedThoughts(UUID ownerId) {
        super(ownerId, 86, "Tormented Thoughts", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "JOU";

        this.color.setBlack(true);

        // As an additional cost to cast Tormented Thoughts, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,new FilterControlledCreaturePermanent("a creature"), false)));

        // Target player discards a number of cards equal to the sacrificed creature's power.
        this.getSpellAbility().addEffect(new TormentedThoughtsDiscardEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(true));
    }

    public TormentedThoughts(final TormentedThoughts card) {
        super(card);
    }

    @Override
    public TormentedThoughts copy() {
        return new TormentedThoughts(this);
    }
}

class TormentedThoughtsDiscardEffect extends OneShotEffect<TormentedThoughtsDiscardEffect> {

    public TormentedThoughtsDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player discards a number of cards equal to the sacrificed creature's power";
    }

    public TormentedThoughtsDiscardEffect(final TormentedThoughtsDiscardEffect effect) {
        super(effect);
    }

    @Override
    public TormentedThoughtsDiscardEffect copy() {
        return new TormentedThoughtsDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            int power = 0;
            COSTS: for (Cost cost :source.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    SacrificeTargetCost sacCost = (SacrificeTargetCost) cost;
                    for(Permanent permanent : sacCost.getPermanents()) {
                        power = permanent.getPower().getValue();
                        break COSTS;
                    }
                }
            }
            if (power > 0) {
                targetPlayer.discard(power, source, game);
            }
            return true;
        }
        return false;
    }
}
