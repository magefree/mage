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
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HasCounterCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author jeffwadsworth
 */
public class QuestForTheGoblinLord extends CardImpl<QuestForTheGoblinLord> {

    private static final String rule = "As long as Quest for the Goblin Lord has five or more quest counters on it, creatures you control get +2/+0.";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent goblinFilter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        goblinFilter.add(new SubtypePredicate("Goblin"));
    }

    public QuestForTheGoblinLord(UUID ownerId) {
        super(ownerId, 86, "Quest for the Goblin Lord", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{R}");
        this.expansionSetCode = "WWK";

        this.color.setRed(true);

        // Whenever a Goblin enters the battlefield under your control, you may put a quest counter on Quest for the Goblin Lord.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), goblinFilter, true));

        // As long as Quest for the Goblin Lord has five or more quest counters on it, creatures you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(new BoostAllEffect(2, 0, Constants.Duration.WhileOnBattlefield, filter, false), new HasCounterCondition(CounterType.QUEST, 5, Integer.MAX_VALUE), rule)));
    }

    public QuestForTheGoblinLord(final QuestForTheGoblinLord card) {
        super(card);
    }

    @Override
    public QuestForTheGoblinLord copy() {
        return new QuestForTheGoblinLord(this);
    }
}
