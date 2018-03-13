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
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public class ArcboundFiend extends CardImpl {

    public ArcboundFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Fear
        this.addAbility(FearAbility.getInstance());

        // At the beginning of your upkeep, you may move a +1/+1 counter from target creature onto Arcbound Fiend.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new MoveCounterFromTargetToSourceEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Modular 3
        this.addAbility(new ModularAbility(this, 3));
    }

    public ArcboundFiend(final ArcboundFiend card) {
        super(card);
    }

    @Override
    public ArcboundFiend copy() {
        return new ArcboundFiend(this);
    }
}

class MoveCounterFromTargetToSourceEffect extends OneShotEffect {

    public MoveCounterFromTargetToSourceEffect() {
        super(Outcome.Detriment);
        this.staticText = "move a +1/+1 counter from target creature onto {this}";
    }

    public MoveCounterFromTargetToSourceEffect(final MoveCounterFromTargetToSourceEffect effect) {
        super(effect);
    }

    @Override
    public MoveCounterFromTargetToSourceEffect copy() {
        return new MoveCounterFromTargetToSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Permanent fromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (fromPermanent != null && fromPermanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
                fromPermanent.removeCounters(CounterType.P1P1.createInstance(), game);
                sourceObject.addCounters(CounterType.P1P1.createInstance(), source, game);
                game.informPlayers("Moved a +1/+1 counter from " + fromPermanent.getLogName() + " to " + sourceObject.getLogName());
            }
            return true;
        }
        return false;
    }
}
