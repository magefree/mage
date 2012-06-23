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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.common.BecomesTappedCreatureControlledTriggeredAbility;
import mage.abilities.common.BeginningOfUntapTriggeredAbility;
import mage.abilities.condition.common.HasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class QuestForRenewal extends CardImpl<QuestForRenewal> {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control");

    public QuestForRenewal(UUID ownerId) {
        super(ownerId, 110, "Quest for Renewal", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "WWK";

        this.color.setGreen(true);

        // Whenever a creature you control becomes tapped, you may put a quest counter on Quest for Renewal.
        this.addAbility(new BecomesTappedCreatureControlledTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));
        
        // As long as there are four or more quest counters on Quest for Renewal, untap all creatures you control during each other player's untap step.
        ConditionalOneShotEffect effect = new ConditionalOneShotEffect(new UntapAllControllerEffect(filter, ""), new HasCounterCondition(CounterType.QUEST, 4), "as long as there are four or more quest counters on <this>, untap all creatures you control");
        this.addAbility(new BeginningOfUntapTriggeredAbility(Constants.Zone.BATTLEFIELD, effect, Constants.TargetController.OPPONENT, false));
        
    }

    public QuestForRenewal(final QuestForRenewal card) {
        super(card);
    }

    @Override
    public QuestForRenewal copy() {
        return new QuestForRenewal(this);
    }
}
