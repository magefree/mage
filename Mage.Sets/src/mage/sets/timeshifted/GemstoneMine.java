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
package mage.sets.timeshifted;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class GemstoneMine extends CardImpl<GemstoneMine> {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.not(new AnotherPredicate()));
    }

    public GemstoneMine(UUID ownerId) {
        super(ownerId, 119, "Gemstone Mine", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "TSB";

        // Gemstone Mine enters the battlefield with three mining counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.MINING.createInstance(3))));

        // {tap}, Remove a mining counter from Gemstone Mine: Add one mana of any color to your mana pool. If there are no mining counters on Gemstone Mine, sacrifice it.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new RemoveCountersSourceCost(CounterType.MINING.createInstance(1)));
        this.addAbility(ability);
        this.addAbility(new GemstoneMineTriggeredAbility());
    }

    public GemstoneMine(final GemstoneMine card) {
        super(card);
    }

    @Override
    public GemstoneMine copy() {
        return new GemstoneMine(this);
    }
}

class GemstoneMineTriggeredAbility extends StateTriggeredAbility<GemstoneMineTriggeredAbility> {

    private static final String staticText = "If there are no mining counters on {this}, sacrifice it.";

    public GemstoneMineTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public GemstoneMineTriggeredAbility(GemstoneMineTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER_REMOVED) {
            Permanent gemstoneMine = game.getPermanent(this.getSourceId());
            if (gemstoneMine != null) {
                if (!gemstoneMine.getCounters().containsKey(CounterType.MINING)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GemstoneMineTriggeredAbility copy() {
        return new GemstoneMineTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}
