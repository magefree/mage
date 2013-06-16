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
package mage.sets.darksteel;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.WinGameEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class DarksteelReactor extends CardImpl<DarksteelReactor> {

    public DarksteelReactor(UUID ownerId) {
        super(ownerId, 114, "Darksteel Reactor", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "DST";

        // Darksteel Reactor is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());
        // At the beginning of your upkeep, you may put a charge counter on Darksteel Reactor.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), TargetController.YOU, true));
        // When Darksteel Reactor has twenty or more charge counters on it, you win the game.
        this.addAbility(new DarksteelReactorStateTriggeredAbility());

    }

    public DarksteelReactor(final DarksteelReactor card) {
        super(card);
    }

    @Override
    public DarksteelReactor copy() {
        return new DarksteelReactor(this);
    }
}

class DarksteelReactorStateTriggeredAbility extends StateTriggeredAbility<DarksteelReactorStateTriggeredAbility> {

    public DarksteelReactorStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WinGameEffect());
    }

    public DarksteelReactorStateTriggeredAbility(final DarksteelReactorStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DarksteelReactorStateTriggeredAbility copy() {
        return new DarksteelReactorStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if(permanent.getCounters().getCount(CounterType.CHARGE) >= 20){
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} has twenty or more charge counters on it, you win the game.";
    }

}
