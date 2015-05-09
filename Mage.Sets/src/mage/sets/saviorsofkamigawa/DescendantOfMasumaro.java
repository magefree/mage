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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class DescendantOfMasumaro extends CardImpl {

    public DescendantOfMasumaro(UUID ownerId) {
        super(ownerId, 126, "Descendant of Masumaro", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "SOK";
        this.subtype.add("Human");
        this.subtype.add("Monk");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, put a +1/+1 counter on Descendant of Masumaro for each card in your hand, then remove a +1/+1 counter from Descendant of Masumaro for each card in target opponent's hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DescendantOfMasumaroEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public DescendantOfMasumaro(final DescendantOfMasumaro card) {
        super(card);
    }

    @Override
    public DescendantOfMasumaro copy() {
        return new DescendantOfMasumaro(this);
    }
}

class DescendantOfMasumaroEffect extends OneShotEffect {

    public DescendantOfMasumaroEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a +1/+1 counter on {this} for each card in your hand, then remove a +1/+1 counter from {this} for each card in target opponent's hand";
    }

    public DescendantOfMasumaroEffect(final DescendantOfMasumaroEffect effect) {
        super(effect);
    }

    @Override
    public DescendantOfMasumaroEffect copy() {
        return new DescendantOfMasumaroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (controller.getHand().size() > 0) {
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(controller.getHand().size()), true).apply(game, source);
            }
            Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetOpponent != null && targetOpponent.getHand().size() > 0) {
                sourcePermanent.getCounters().removeCounter(CounterType.P1P1, targetOpponent.getHand().size());
                game.informPlayers(controller.getLogName() + " removes " +  targetOpponent.getHand().size() + " +1/+1 counters from " + sourcePermanent.getLogName());
            }
            return true;
        }
        return false;
    }
}
