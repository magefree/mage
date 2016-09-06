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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public class Grindclock extends CardImpl {

    public Grindclock (UUID ownerId) {
        super(ownerId, 163, "Grindclock", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "SOM";
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost()));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrindclockEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public Grindclock (final Grindclock card) {
        super(card);
    }

    @Override
    public Grindclock copy() {
        return new Grindclock(this);
    }

}

class GrindclockEffect extends OneShotEffect {
    public GrindclockEffect() {
        super(Outcome.Detriment);
        staticText = "Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of charge counters on {this}";
    }

    public GrindclockEffect(final GrindclockEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = game.getPermanent(source.getSourceId()).getCounters(game).getCount(CounterType.CHARGE);
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            targetPlayer.moveCards(targetPlayer.getLibrary().getTopCards(game, amount), Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }

    @Override
    public GrindclockEffect copy() {
        return new GrindclockEffect(this);
    }

}