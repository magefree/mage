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
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CouncilsDilemmaVoteEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author JRHerlehy
 */
public class LieutenantsOfTheGuard extends CardImpl {

    public LieutenantsOfTheGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Council's dilemma</i> &mdash; When Lieutenants of the Guard enters the battlefield, starting with you, each player votes for strength or numbers. Put a +1/+1 counter on Lieutenants of the Guard for each strength vote and put a 1/1 white Soldier creature token onto the battlefield for each numbers vote.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LieutenantsOfTheGuardDilemmaEffect(), false, "<i>Council's dilemma</i> — "));
    }

    public LieutenantsOfTheGuard(final LieutenantsOfTheGuard card) {
        super(card);
    }

    @Override
    public LieutenantsOfTheGuard copy() {
        return new LieutenantsOfTheGuard(this);
    }
}

class LieutenantsOfTheGuardDilemmaEffect extends CouncilsDilemmaVoteEffect {
    public LieutenantsOfTheGuardDilemmaEffect() {
        super(Outcome.Benefit);
        this.staticText = "starting with you, each player votes for strength or numbers. Put a +1/+1 counter on {this} for each strength vote and put a 1/1 white Soldier creature token onto the battlefield for each numbers vote.";
    }

    public LieutenantsOfTheGuardDilemmaEffect(final LieutenantsOfTheGuardDilemmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        //If no controller, exit out here and do not vote.
        if (controller == null) return false;

        this.vote("strength", "numbers", controller, game, source);

        Permanent permanent = game.getPermanent(source.getSourceId());

        //Strength Votes
        //If strength received zero votes or the permanent is no longer on the battlefield, do not attempt to put P1P1 counters on it.
        if (voteOneCount > 0 && permanent != null)
            permanent.addCounters(CounterType.P1P1.createInstance(voteOneCount), game);

        //Numbers Votes
        if (voteTwoCount > 0) {
            Effect tokenEffect = new CreateTokenEffect(new SoldierToken(), voteTwoCount);
            tokenEffect.apply(game, source);
        }

        return true;
    }

    @Override
    public LieutenantsOfTheGuardDilemmaEffect copy() {
        return new LieutenantsOfTheGuardDilemmaEffect(this);
    }
}
