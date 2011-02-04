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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfControllerUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class GoldenUrn extends CardImpl<GoldenUrn> {

    public GoldenUrn (UUID ownerId) {
        super(ownerId, 158, "Golden Urn", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "SOM";
        this.addAbility(new BeginningOfControllerUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), true));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoldenUrnEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public GoldenUrn (final GoldenUrn card) {
        super(card);
    }

    @Override
    public GoldenUrn copy() {
        return new GoldenUrn(this);
    }

}

class GoldenUrnEffect extends OneShotEffect<GoldenUrnEffect> {
    public GoldenUrnEffect() {
        super(Constants.Outcome.GainLife);
    }

    public GoldenUrnEffect(final GoldenUrnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getBattlefield().getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (p == null) {
            p = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (p != null && player != null) {
            player.gainLife(p.getCounters().getCount(CounterType.CHARGE), game);
            return true;
        }
        return false;
    }

    @Override
    public GoldenUrnEffect copy() {
        return new GoldenUrnEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "You gain life equal to the number of charge counters on Golden Urn";
    }
}