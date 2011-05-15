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
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class ElspethTirel extends CardImpl<ElspethTirel> {

    public ElspethTirel (UUID ownerId) {
        super(ownerId, 6, "Elspeth Tirel", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Elspeth");
		this.color.setWhite(true);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), ""));

		
        this.addAbility(new LoyaltyAbility(new ElspethTirelFirstEffect(), 2));
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SoldierToken(), 3), -2));
        this.addAbility(new LoyaltyAbility(new ElspethTirelThirdEffect(), -5));
    }

    public ElspethTirel (final ElspethTirel card) {
        super(card);
    }

    @Override
    public ElspethTirel copy() {
        return new ElspethTirel(this);
    }
}

class ElspethTirelFirstEffect extends OneShotEffect<ElspethTirelFirstEffect> {
    public ElspethTirelFirstEffect() {
        super(Constants.Outcome.GainLife);
    }

    public ElspethTirelFirstEffect(final ElspethTirelFirstEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = game.getBattlefield().countAll(new FilterCreaturePermanent(), source.getControllerId());
        Player player = game.getPlayer(source.getControllerId());
		if (player != null) {
			player.gainLife(amount, game);
		}
		return true;
    }

    @Override
    public ElspethTirelFirstEffect copy() {
        return new ElspethTirelFirstEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "You gain 1 life for each creature you control";
    }
}

class ElspethTirelThirdEffect extends OneShotEffect<ElspethTirelThirdEffect> {
    public ElspethTirelThirdEffect() {
        super(Constants.Outcome.DestroyPermanent);
    }

    public ElspethTirelThirdEffect(final ElspethTirelThirdEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm: game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
			if (!perm.getId().equals(source.getSourceId()) && !(perm instanceof PermanentToken) && ! (perm.getCardType().contains(CardType.LAND)))
				perm.destroy(source.getId(), game, false);
		}
        return true;
    }

    @Override
    public ElspethTirelThirdEffect copy() {
        return new ElspethTirelThirdEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "Destroy all other permanents except for lands and tokens";
    }
}