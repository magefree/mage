package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeistflameReservoir extends CardImpl {

    public GeistflameReservoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        // Whenever you cast an instant or sorcery spell, put a charge counter on Geistflame Reservoir.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // {1}{R}, {T}, Remove any number of charge counters from Geistflame Reservoir: It deals that much damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(GetXValue.instance)
                .setText("it deals that much damage to any target"), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(
                CounterType.CHARGE, "Remove any number of charge counters from {this}"
        ));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {1}{R}, {T}: Exile the top card of your library. You may play that card this turn.
        ability = new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1)
                        .setText("exile the top card of your library. You may play that card this turn"),
                new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private GeistflameReservoir(final GeistflameReservoir card) {
        super(card);
    }

    @Override
    public GeistflameReservoir copy() {
        return new GeistflameReservoir(this);
    }
}
