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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public class MightyEmergence extends CardImpl<MightyEmergence> {
    
    public MightyEmergence(UUID ownerId) {
        super(ownerId, 137, "Mighty Emergence", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "ALA";

        this.color.setGreen(true);

        // Whenever a creature with power 5 or greater enters the battlefield under your control, you may put two +1/+1 counters on it.
        this.addAbility(new MightyEmergenceTriggeredAbility());
    }

    public MightyEmergence(final MightyEmergence card) {
        super(card);
    }

    @Override
    public MightyEmergence copy() {
        return new MightyEmergence(this);
    }
}


class MightyEmergenceTriggeredAbility extends TriggeredAbilityImpl<MightyEmergenceTriggeredAbility> {
    private static final FilterCreatureCard filter = new FilterCreatureCard("creature with power 5 or greater");
    static {
        filter.add(new PowerPredicate(Filter.ComparisonType.GreaterThan, 4));
    }
    
    public MightyEmergenceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(), true);
    }

    public MightyEmergenceTriggeredAbility(MightyEmergenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            UUID targetId = event.getTargetId();
            Permanent permanent = game.getPermanent(targetId);
            if (filter.match(permanent, getSourceId(), getControllerId(), game)) {
                if (getTargets().size() == 0) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(targetId));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature with power 5 or greater enters the battlefield under your control, you may put two +1/+1 counters on it";
    }

    @Override
    public MightyEmergenceTriggeredAbility copy() {
        return new MightyEmergenceTriggeredAbility(this);
    }
}

class AddCountersTargetEffect extends OneShotEffect<AddCountersTargetEffect> {



     public AddCountersTargetEffect() {
        super(Constants.Outcome.Benefit);
     }

    public AddCountersTargetEffect(final AddCountersTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(2), game);
            return true;
        } 
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "put two +1/+1 counters on it";
    }

    @Override
    public AddCountersTargetEffect copy() {
        return new AddCountersTargetEffect(this);
    }

    
}
