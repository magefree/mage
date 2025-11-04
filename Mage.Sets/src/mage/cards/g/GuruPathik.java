package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuruPathik extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Lesson, Saga, or Shrine card");
    private static final FilterSpell filter2 = new FilterSpell("a Lesson, Saga, or Shrine spell");

    static {
        filter.add(Predicates.or(
                SubType.LESSON.getPredicate(),
                SubType.SAGA.getPredicate(),
                SubType.SHRINE.getPredicate()
        ));
        filter2.add(Predicates.or(
                SubType.LESSON.getPredicate(),
                SubType.SAGA.getPredicate(),
                SubType.SHRINE.getPredicate()
        ));
    }

    public GuruPathik(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/U}{G/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Guru Pathik enters, look at the top five cards of your library. You may reveal a Lesson, Saga, or Shrine card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));

        // Whenever you cast a Lesson, Saga, or Shrine spell, put a +1/+1 counter on another target creature you control.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), filter2, false
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private GuruPathik(final GuruPathik card) {
        super(card);
    }

    @Override
    public GuruPathik copy() {
        return new GuruPathik(this);
    }
}
