package mage.abilities.common;

import mage.ObjectColor;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author TheElk801
 */
public class SanctuaryTriggeredAbility extends BeginningOfUpkeepTriggeredAbility {

    private static Condition makeOrCondition(ObjectColor color1, ObjectColor color2) {
        FilterPermanent filter = new FilterPermanent("you control a " + color1.getDescription() + " or " + color2.getDescription() + " permanent");
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

    public SanctuaryTriggeredAbility(OneShotEffect effect1, OneShotEffect effect2, ObjectColor color1, ObjectColor color2, String text) {
        super(new ConditionalOneShotEffect(effect2, effect1, makeAndCondition(color1, color2), text));
        this.withInterveningIf(makeOrCondition(color1, color2));
    }

    protected SanctuaryTriggeredAbility(final SanctuaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SanctuaryTriggeredAbility copy() {
        return new SanctuaryTriggeredAbility(this);
    }
}
