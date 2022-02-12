package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PacksongPup extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.WEREWOLF.getPredicate(),
                SubType.WOLF.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control another Wolf or Werewolf");
    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public PacksongPup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you control another Wolf or Werewolf, put a +1/+1 counter on Packsong Pup.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        TargetController.YOU, false
                ), condition, "At the beginning of combat on your turn," +
                " if you control another Wolf or Werewolf, put a +1/+1 counter on {this}"
        ).addHint(hint));

        // When Packsong Pup dies, you gain life equal to its power.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(xValue).setText("you gain life equal to its power")));
    }

    private PacksongPup(final PacksongPup card) {
        super(card);
    }

    @Override
    public PacksongPup copy() {
        return new PacksongPup(this);
    }
}
