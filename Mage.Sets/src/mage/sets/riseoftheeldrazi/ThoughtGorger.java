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
package mage.sets.riseoftheeldrazi;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.players.Player;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.effects.OneShotEffect;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.cards.CardImpl;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 */
public class ThoughtGorger extends CardImpl<ThoughtGorger> {

    public ThoughtGorger(UUID ownerId) {
        super(ownerId, 129, "Thought Gorger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(TrampleAbility.getInstance());

        // When Thought Gorger enters the battlefield, put a +1/+1 counter on it for each card in your hand. If you do, discard your hand.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new ThoughtGorgerEffectEnters());
        this.addAbility(ability1);

        // When Thought Gorger leaves the battlefield, draw a card for each +1/+1 counter on it.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ThoughtGorgerEffectLeaves(), false);
        this.addAbility(ability2);
    }

    public ThoughtGorger(final ThoughtGorger card) {
        super(card);
    }

    @Override
    public ThoughtGorger copy() {
        return new ThoughtGorger(this);
    }

}

class ThoughtGorgerEffectEnters extends OneShotEffect<ThoughtGorgerEffectEnters> {

    public ThoughtGorgerEffectEnters() {
        super(Outcome.Benefit);
        this.staticText = "put a +1/+1 counter on it for each card in your hand. If you do, discard your hand.";
    }

    public ThoughtGorgerEffectEnters(final ThoughtGorgerEffectEnters effect) {
        super(effect);
    }

    @Override
    public ThoughtGorgerEffectEnters copy() {
        return new ThoughtGorgerEffectEnters(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent thoughtGorger = game.getPermanent(source.getSourceId());
        if (player != null && player.getHand().size() > 0 && thoughtGorger != null ) {
            int cardsInHand = player.getHand().size();
            thoughtGorger.addCounters(CounterType.P1P1.createInstance(cardsInHand), game);
            player.discard(cardsInHand, source, game);
            return true;
        }
        return false;
    }
}

class ThoughtGorgerEffectLeaves extends OneShotEffect<ThoughtGorgerEffectLeaves> {

    public ThoughtGorgerEffectLeaves() {
        super(Outcome.Neutral);
        this.staticText = "draw a card for each +1/+1 counter on it.";
    }

    public ThoughtGorgerEffectLeaves(final ThoughtGorgerEffectLeaves effect) {
        super(effect);
    }

    @Override
    public ThoughtGorgerEffectLeaves copy() {
        return new ThoughtGorgerEffectLeaves(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent thoughtGorgerLastState = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        int numberCounters = thoughtGorgerLastState.getCounters().getCount(CounterType.P1P1);
        if (player != null) {
            player.drawCards(numberCounters, game);
            return true;
        }
        return false;
    }
}

