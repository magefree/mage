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

package mage.sets.magic2012;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BeastToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class GarrukPrimalHunter extends CardImpl<GarrukPrimalHunter> {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("land your control");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }


    public GarrukPrimalHunter (UUID ownerId) {
        super(ownerId, 174, "Garruk, Primal Hunter", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{G}{G}");
        this.expansionSetCode = "M12";
        this.subtype.add("Garruk");
        this.color.setGreen(true);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), ""));
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new BeastToken()), 1));
        this.addAbility(new LoyaltyAbility(new GarrukPrimalHunterEffect(), -3));
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new WurmToken(), new PermanentsOnBattlefieldCount(filter)), -6));
    }

    public GarrukPrimalHunter (final GarrukPrimalHunter card) {
        super(card);
    }

    @Override
    public GarrukPrimalHunter copy() {
        return new GarrukPrimalHunter(this);
    }

}

class GarrukPrimalHunterEffect extends OneShotEffect<GarrukPrimalHunterEffect> {
    GarrukPrimalHunterEffect() {
        super(Constants.Outcome.DrawCard);
        staticText = "Draw cards equal to the greatest power among creatures you control";
    }

    GarrukPrimalHunterEffect(final GarrukPrimalHunterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = 0;
            for (Permanent p : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), game)) {
                if (p.getPower().getValue() > amount)
                    amount = p.getPower().getValue();
            }
            player.drawCards(amount, game);
            return true;
        }
        return false;
    }

    @Override
    public GarrukPrimalHunterEffect copy() {
        return new GarrukPrimalHunterEffect(this);
    }

}

class WurmToken extends Token {

    public WurmToken() {
        super("Wurm", "a 6/6 green Wurm creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.GREEN;
        subtype.add("Wurm");
        power = new MageInt(6);
        toughness = new MageInt(6);
    }

}