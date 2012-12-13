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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.ReturnToHandSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class MagosiTheWaterveil extends CardImpl<MagosiTheWaterveil> {

    public MagosiTheWaterveil(UUID ownerId) {
        super(ownerId, 218, "Magosi, the Waterveil", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ZEN";

        // Magosi, the Waterveil enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // {tap}: Add {U} to your mana pool.
        this.addAbility(new BlueManaAbility());
        
        // {U}, {tap}: Put an eon counter on Magosi, the Waterveil. Skip your next turn.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new MagosiTheWaterveilEffect(), new ManaCostsImpl("{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
        // {tap}, Remove an eon counter from Magosi, the Waterveil and return it to its owner's hand: Take an extra turn after this one.
        Ability ability2 = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AddExtraTurnControllerEffect(), new TapSourceCost());
        ability2.addCost(new RemoveCountersSourceCost(CounterType.EON.createInstance()));
        ability2.addCost(new ReturnToHandSourceCost());
        this.addAbility(ability2);
        
    }

    public MagosiTheWaterveil(final MagosiTheWaterveil card) {
        super(card);
    }

    @Override
    public MagosiTheWaterveil copy() {
        return new MagosiTheWaterveil(this);
    }
}

class MagosiTheWaterveilEffect extends OneShotEffect<MagosiTheWaterveilEffect> {

    public MagosiTheWaterveilEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "Put an eon counter on Magosi, the Waterveil. Skip your next turn";
    }

    public MagosiTheWaterveilEffect(final MagosiTheWaterveilEffect effect) {
        super(effect);
    }

    @Override
    public MagosiTheWaterveilEffect copy() {
        return new MagosiTheWaterveilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent magosi = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        
        if (magosi != null) {
            magosi.addCounters(CounterType.EON.createInstance(), game);
        }
        if (player != null) {
            game.getState().getTurnMods().add(new TurnMod(player.getId(), true));
        }
        return true;
    }

}