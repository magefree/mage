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
package mage.abilities.common;

import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public class SanctuaryTriggeredAbility extends ConditionalTriggeredAbility {

    private static Condition makeOrCondition(ObjectColor color1, ObjectColor color2) {
        FilterPermanent filter = new FilterPermanent();
        filter.add(Predicates.or(
                new ColorPredicate(color1),
                new ColorPredicate(color2)
        ));
        return new PermanentsOnTheBattlefieldCondition(filter);
    }

    private static Condition makeAndCondition(ObjectColor color1, ObjectColor color2) {
        FilterPermanent filter1 = new FilterPermanent();
        filter1.add(new ColorPredicate(color1));
        Condition condition1 = new PermanentsOnTheBattlefieldCondition(filter1);
        FilterPermanent filter2 = new FilterPermanent();
        filter2.add(new ColorPredicate(color2));
        Condition condition2 = new PermanentsOnTheBattlefieldCondition(filter2);
        return new CompoundCondition(condition1, condition2);
    }

    private static TriggeredAbility makeTrigger(OneShotEffect effect1, OneShotEffect effect2, ObjectColor color1, ObjectColor color2) {
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(
                new ConditionalOneShotEffect(effect1, new InvertCondition(makeAndCondition(color1, color2))), TargetController.YOU, false
        );
        ability.addEffect(new ConditionalOneShotEffect(effect2, makeAndCondition(color1, color2)));
        return ability;
    }

    public SanctuaryTriggeredAbility(OneShotEffect effect1, OneShotEffect effect2, ObjectColor color1, ObjectColor color2, String text) {
        super(makeTrigger(effect1, effect2, color1, color2), makeOrCondition(color1, color2), text);
    }
}
