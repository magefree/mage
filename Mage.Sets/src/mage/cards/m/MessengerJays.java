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
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CouncilsDilemmaVoteEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author JRHerlehy
 */
public class MessengerJays extends CardImpl {

    public MessengerJays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        
        this.subtype.add("Bird");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Council's dilemma &mdash; When Messenger Jays enters the battlefield, starting with you, each player votes for feather or quill. Put a +1/+1 counter on Messenger Jays for each feather vote and draw a card for each quill vote. For each card drawn this way, discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MessengerJaysDilemmaEffect(), false, "<i>Council's dilemma</i> — "));
    }

    public MessengerJays(final MessengerJays card) {
        super(card);
    }

    @Override
    public MessengerJays copy() {
        return new MessengerJays(this);
    }
}

class MessengerJaysDilemmaEffect extends CouncilsDilemmaVoteEffect {

    public MessengerJaysDilemmaEffect() {
        super(Outcome.Benefit);
        this.staticText = "starting with you, each player votes for feather or quill. Put a +1/+1 counter on {this} for each feather vote and draw a card for each quill vote. For each card drawn this way, discard a card.";
    }

    public MessengerJaysDilemmaEffect(final MessengerJaysDilemmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        //If no controller, exit out here and do not vote.
        if (controller == null) return false;

        this.vote("feather", "quill", controller, game, source);

        Permanent permanent = game.getPermanent(source.getSourceId());

        //Feathers Votes
        //If feathers received zero votes or the permanent is no longer on the battlefield, do not attempt to put P1P1 counter on it.
        if (voteOneCount > 0 && permanent != null)
            permanent.addCounters(CounterType.P1P1.createInstance(voteOneCount), game);

        //Quill Votes
        //Only let the controller loot the appropriate amount of cards if it was voted for.
        if (voteTwoCount > 0) {
            Effect lootCardsEffect = new DrawDiscardControllerEffect(voteTwoCount, voteTwoCount);
            lootCardsEffect.apply(game, source);
        }

        return true;
    }

    @Override
    public MessengerJaysDilemmaEffect copy() {
        return new MessengerJaysDilemmaEffect(this);
    }
}
