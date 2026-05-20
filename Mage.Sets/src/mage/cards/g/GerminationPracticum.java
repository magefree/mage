package mage.cards.g;

import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ParadigmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GerminationPracticum extends CardImpl {

    public GerminationPracticum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        this.subtype.add(SubType.LESSON);

        // Put two +1/+1 counters on each creature you control.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));

        // Paradigm
        this.addAbility(new ParadigmAbility());
    }

    private GerminationPracticum(final GerminationPracticum card) {
        super(card);
    }

    @Override
    public GerminationPracticum copy() {
        return new GerminationPracticum(this);
    }
}
