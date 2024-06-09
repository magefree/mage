package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PugnaciousHammerskull extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DINOSAUR);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public PugnaciousHammerskull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Pugnacious Hammerskull attacks while you don't control another Dinosaur, put a stun counter on it.
        this.addAbility(new ConditionalTriggeredAbility(
                new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.STUN.createInstance())), condition,
                "Whenever {this} attacks while you don't control another Dinosaur, put a stun counter on it."
        ));
    }

    private PugnaciousHammerskull(final PugnaciousHammerskull card) {
        super(card);
    }

    @Override
    public PugnaciousHammerskull copy() {
        return new PugnaciousHammerskull(this);
    }
}
