package mage.cards.l;

import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeylineOfAbundance extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("you tap a creature");

    public LeylineOfAbundance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // If Leyline of Abundance is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // Whenever you tap a creature for mana, add an additional {G}.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                (ManaEffect) new BasicManaEffect(Mana.GreenMana(1))
                        .setText("add an additional {G}"),
                filter, SetTargetPointer.NONE
        ));

        // {6}{G}{G}: Put a +1/+1 counter on each creature you control.
        this.addAbility(new SimpleActivatedAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(),
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ), new ManaCostsImpl<>("{6}{G}{G}")));
    }

    private LeylineOfAbundance(final LeylineOfAbundance card) {
        super(card);
    }

    @Override
    public LeylineOfAbundance copy() {
        return new LeylineOfAbundance(this);
    }
}
