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

package mage.sets.conflux;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

import java.util.UUID;

/**
 *
 * @author loki
 */
public class BloodhallOoze extends CardImpl<BloodhallOoze> {

    public BloodhallOoze(UUID ownerId) {
        super(ownerId, 59, "Bloodhall Ooze", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "CON";
        this.color.setRed(true);
        this.subtype.add("Ooze");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new BloodhallOozeTriggeredAbility1());
        this.addAbility(new BloodhallOozeTriggeredAbility2());
    }

    public BloodhallOoze(final BloodhallOoze card) {
        super(card);
    }

    @Override
    public BloodhallOoze copy() {
        return new BloodhallOoze(this);
    }

}

class BloodhallOozeTriggeredAbility1 extends TriggeredAbilityImpl<BloodhallOozeTriggeredAbility1> {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public BloodhallOozeTriggeredAbility1() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
    }

    public BloodhallOozeTriggeredAbility1(final BloodhallOozeTriggeredAbility1 ability) {
        super(ability);
    }

    @Override
    public BloodhallOozeTriggeredAbility1 copy() {
        return new BloodhallOozeTriggeredAbility1(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().countAll(filter, this.controllerId, game) >= 1;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control a black permanent, you may put a +1/+1 counter on {this}.";
    }
}

class BloodhallOozeTriggeredAbility2 extends TriggeredAbilityImpl<BloodhallOozeTriggeredAbility2> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public BloodhallOozeTriggeredAbility2() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
    }

    public BloodhallOozeTriggeredAbility2(final BloodhallOozeTriggeredAbility2 ability) {
        super(ability);
    }

    @Override
    public BloodhallOozeTriggeredAbility2 copy() {
        return new BloodhallOozeTriggeredAbility2(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().countAll(filter, this.controllerId, game) >= 1;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control a green permanent, you may put a +1/+1 counter on {this}.";
    }
}