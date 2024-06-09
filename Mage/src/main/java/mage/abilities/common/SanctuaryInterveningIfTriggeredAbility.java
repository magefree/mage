
package mage.abilities.common;

import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author TheElk801
 */
public class SanctuaryInterveningIfTriggeredAbility extends ConditionalInterveningIfTriggeredAbility {

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

    public SanctuaryInterveningIfTriggeredAbility(OneShotEffect effect1, OneShotEffect effect2, ObjectColor color1, ObjectColor color2, String text) {
        super(makeTrigger(effect1, effect2, color1, color2), makeOrCondition(color1, color2), text);
    }

    protected SanctuaryInterveningIfTriggeredAbility(final SanctuaryInterveningIfTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SanctuaryInterveningIfTriggeredAbility copy() {
        return new SanctuaryInterveningIfTriggeredAbility(this);
    }
}
