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
package mage.cards.j;

import java.util.ArrayList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class JumboImp extends CardImpl {

    public JumboImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        //  Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Jumbo Imp enters the battlefield, roll a six-sided die. Jumbo Imp enters the battlefield with a number of +1/+1 counters on it equal to the result.
        this.addAbility(new EntersBattlefieldAbility(new JumboImpEffect(new Counter("P1P1"))));

        // At the beginning of your upkeep, roll a six-sided die and put a number of +1/+1 counters on Jumbo Imp equal to the result. 
        Ability ability2 = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new JumboImpAddCountersEffect(), TargetController.YOU, false);
        this.addAbility(ability2);

        // At the beginning of your end step, roll a six-sided die and remove a number of +1/+1 counters from Jumbo Imp equal to the result.
        Ability ability3 = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new JumboImpRemoveCountersEffect(), TargetController.YOU, null, false);
        this.addAbility(ability3);
    }

    public JumboImp(final JumboImp card) {
        super(card);
    }

    @Override
    public JumboImp copy() {
        return new JumboImp(this);
    }
}

class JumboImpEffect extends EntersBattlefieldWithXCountersEffect {

    public JumboImpEffect(Counter counter) {
        super(counter);
    }

    public JumboImpEffect(EntersBattlefieldWithXCountersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            ArrayList<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appldiedEffects"); // the basic event is the EntersBattlefieldEvent, so use already applied replacement effects from that event
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game, appliedEffects);
            return super.apply(game, source);
        }
        return false;
    }

    @Override
    public EntersBattlefieldWithXCountersEffect copy() {
        return new JumboImpEffect(this);
    }

}

class JumboImpAddCountersEffect extends OneShotEffect {

    public JumboImpAddCountersEffect() {
        super(Outcome.Benefit);
        this.staticText = "roll a six-sided die and put a number of +1/+1 counters on {this} equal to the result";
    }

    public JumboImpAddCountersEffect(final JumboImpAddCountersEffect effect) {
        super(effect);
    }

    @Override
    public JumboImpAddCountersEffect copy() {
        return new JumboImpAddCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
            return true;
        }
        return false;
    }
}

class JumboImpRemoveCountersEffect extends OneShotEffect {

    public JumboImpRemoveCountersEffect() {
        super(Outcome.Detriment);
        this.staticText = "roll a six-sided die and remove a number of +1/+1 counters on {this} equal to the result";
    }

    public JumboImpRemoveCountersEffect(final JumboImpRemoveCountersEffect effect) {
        super(effect);
    }

    @Override
    public JumboImpRemoveCountersEffect copy() {
        return new JumboImpRemoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            permanent.removeCounters(CounterType.P1P1.createInstance(amount), game);
            return true;
        }
        return false;
    }
}
