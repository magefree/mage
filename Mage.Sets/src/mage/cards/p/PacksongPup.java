package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
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

    private static final FilterPermanent filter = new FilterControlledPermanent("you control another Wolf or Werewolf");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.WEREWOLF.getPredicate(),
                SubType.WOLF.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition);

    public PacksongPup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you control another Wolf or Werewolf, put a +1/+1 counter on Packsong Pup.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).withInterveningIf(condition).addHint(hint));

        // When Packsong Pup dies, you gain life equal to its power.
        this.addAbility(new DiesSourceTriggeredAbility(
                new GainLifeEffect(SourcePermanentPowerValue.NOT_NEGATIVE).setText("you gain life equal to its power")
        ));
    }

    private PacksongPup(final PacksongPup card) {
        super(card);
    }

    @Override
    public PacksongPup copy() {
        return new PacksongPup(this);
    }
}
