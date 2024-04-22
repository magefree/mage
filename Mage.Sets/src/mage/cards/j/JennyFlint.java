package mage.cards.j;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JennyFlint extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Clue or Food");

    static {
        filter.add(Predicates.or(
                SubType.CLUE.getPredicate(),
                SubType.FOOD.getPredicate()
        ));
    }

    public JennyFlint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Madame Vastra
        this.addAbility(new PartnerWithAbility("Madame Vastra"));

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Training
        this.addAbility(new TrainingAbility());

        // Whenever you sacrifice a Clue or Food, put a +1/+1 counter on another target creature you control.
        TriggeredAbility trigger = new SacrificePermanentTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                filter
        );
        trigger.addTarget(new TargetControlledCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(trigger);
    }

    private JennyFlint(final JennyFlint card) {
        super(card);
    }

    @Override
    public JennyFlint copy() {
        return new JennyFlint(this);
    }
}
