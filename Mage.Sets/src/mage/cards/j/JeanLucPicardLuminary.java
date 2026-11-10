package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class JeanLucPicardLuminary extends CardImpl {

    private static final FilterControlledPermanent filter
        = new FilterControlledPermanent("another historic permanent you control with mana value 3 or greater");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.SPACECRAFT, "Spacecraft");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(HistoricPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 3));
    }

    public JeanLucPicardLuminary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever another historic permanent you control with mana value 3 or greater enters, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));

        // Seek out -- Whenever you draw your second card each turn, put a +1/+1 counter on up to one other target creature and two charge counters on up to one target Spacecraft.
        Ability ability = new DrawNthCardTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        ).withFlavorWord("Seek out");
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_CREATURE));
        ability.addEffect(new AddCountersTargetEffect(CounterType.CHARGE.createInstance(2))
            .setTargetPointer(new SecondTargetPointer()).setText("and two charge counters on up to one target Spacecraft"));
        ability.addTarget(new TargetPermanent(0, 1, filter2));
        this.addAbility(ability);
    }

    private JeanLucPicardLuminary(final JeanLucPicardLuminary card) {
        super(card);
    }

    @Override
    public JeanLucPicardLuminary copy() {
        return new JeanLucPicardLuminary(this);
    }
}
