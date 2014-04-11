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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class SageOfHours extends CardImpl<SageOfHours> {

    public SageOfHours(UUID ownerId) {
        super(ownerId, 50, "Sage of Hours", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Heroic - Whenever you cast a spell that targets Sage of Hours, put a +1/+1 counter on it.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
        // Remove all +1/+1 counters from Sage of Hours: For each five counters removed this way, take an extra turn after this one.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SageOfHoursEffect(), new SageOfHoursCost()));
        
    }

    public SageOfHours(final SageOfHours card) {
        super(card);
    }

    @Override
    public SageOfHours copy() {
        return new SageOfHours(this);
    }
}

class SageOfHoursCost extends CostImpl<SageOfHoursCost> {

    private int removedCounters;

    public SageOfHoursCost() {
        super();
        this.removedCounters = 0;
        this.text = "Remove all +1/+1 counters from {this}";
    }

    public SageOfHoursCost(SageOfHoursCost cost) {
        super(cost);
        this.removedCounters = cost.removedCounters;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent != null) {
            this.removedCounters = permanent.getCounters().getCount(CounterType.P1P1);
            if (this.removedCounters > 0) {
                permanent.removeCounters(CounterType.P1P1.createInstance(this.removedCounters), game);
            }
        }
        this.paid = true;
        return true;
    }

    @Override
    public SageOfHoursCost copy() {
        return new SageOfHoursCost(this);
    }

    public int getRemovedCounters() {
        return this.removedCounters;
    }
}

class SageOfHoursEffect extends OneShotEffect<SageOfHoursEffect> {

    public SageOfHoursEffect() {
        super(Outcome.GainLife);
        this.staticText = "For each five counters removed this way, take an extra turn after this one";
    }

    public SageOfHoursEffect(final SageOfHoursEffect effect) {
        super(effect);
    }

    @Override
    public SageOfHoursEffect copy() {
        return new SageOfHoursEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int countersRemoved = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof SageOfHoursCost) {
                    countersRemoved = ((SageOfHoursCost) cost).getRemovedCounters();
                }
            }
            int turns = countersRemoved % 5;
            for (int i = 0; i < turns; i++) {
                game.getState().getTurnMods().add(new TurnMod(player.getId(), false));
            }
            game.informPlayers(new StringBuilder(player.getName()).append(" takes ")
                    .append(CardUtil.numberToText(turns, "an"))
                    .append(turns > 1 ? " extra turns ":" extra turn ")
                    .append("after this one").toString());
            return true;
        }
        return false;
    }
}
