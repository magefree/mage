package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SokkaBoldBoomeranger extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an artifact or Lesson spell");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                SubType.LESSON.getPredicate()
        ));
    }

    public SokkaBoldBoomeranger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Sokka enters, discard up to two cards, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardAndDrawThatManyEffect(2)));

        // Whenever you cast an artifact or Lesson spell, put a +1/+1 counter on Sokka.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        ));
    }

    private SokkaBoldBoomeranger(final SokkaBoldBoomeranger card) {
        super(card);
    }

    @Override
    public SokkaBoldBoomeranger copy() {
        return new SokkaBoldBoomeranger(this);
    }
}
