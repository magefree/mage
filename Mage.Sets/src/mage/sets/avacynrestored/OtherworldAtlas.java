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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public class OtherworldAtlas extends CardImpl<OtherworldAtlas> {

    public OtherworldAtlas(UUID ownerId) {
        super(ownerId, 219, "Otherworld Atlas", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "AVR";

        // {tap}: Put a charge counter on Otherworld Atlas.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost()));

        // {tap}: Each player draws a card for each charge counter on Otherworld Atlas.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new OtherworldAtlasDrawEffect(), new TapSourceCost()));
    }

    public OtherworldAtlas(final OtherworldAtlas card) {
        super(card);
    }

    @Override
    public OtherworldAtlas copy() {
        return new OtherworldAtlas(this);
    }
}

class OtherworldAtlasDrawEffect extends OneShotEffect<OtherworldAtlasDrawEffect> {

    public OtherworldAtlasDrawEffect() {
        super(Constants.Outcome.DrawCard);
        staticText = "Each player draws a card for each charge counter on {this}";
    }

    public OtherworldAtlasDrawEffect(final OtherworldAtlasDrawEffect effect) {
        super(effect);
    }

    @Override
    public OtherworldAtlasDrawEffect copy() {
        return new OtherworldAtlasDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            int amount = permanent.getCounters().getCount(CounterType.CHARGE);
            if (amount > 0) {
                for (UUID playerId : sourcePlayer.getInRange()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.drawCards(amount, game);
                    }
                }
            }
        }
        return true;
    }

}
