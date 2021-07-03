package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.FractalToken;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeometricNexus extends CardImpl {

    public GeometricNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever a player casts an instant or sorcery spell, put a number of charge counters on Geometric Nexus equal to that spell's mana value.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new AddCountersSourceEffect(
                        CounterType.CHARGE.createInstance(0),
                        GeometricNexusMVValue.instance, false
                ).setText("put a number of charge counters on {this} equal to that spell's mana value"),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // {6}, {T}, Remove all charge counters from Geometric Nexus: Create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it, where X is the number of charge counters removed this way.
        Ability ability = new SimpleActivatedAbility(FractalToken.getEffect(
                GeometricNexusRemovedCounterValue.instance, "Put X +1/+1 counters on it, " +
                        "where X is the number of charge counters removed this way"
        ), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveAllCountersSourceCost(CounterType.CHARGE));
        this.addAbility(ability);
    }

    private GeometricNexus(final GeometricNexus card) {
        super(card);
    }

    @Override
    public GeometricNexus copy() {
        return new GeometricNexus(this);
    }
}

enum GeometricNexusMVValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Spell spell = (Spell) effect.getValue("spellCast");
        return spell != null ? spell.getManaValue() : 0;
    }

    @Override
    public GeometricNexusMVValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

enum GeometricNexusRemovedCounterValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int countersRemoved = 0;
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof RemoveAllCountersSourceCost) {
                countersRemoved = ((RemoveAllCountersSourceCost) cost).getRemovedCounters();
            }
        }
        return countersRemoved;
    }

    @Override
    public GeometricNexusRemovedCounterValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
