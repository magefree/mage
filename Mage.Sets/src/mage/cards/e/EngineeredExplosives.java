package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValueEqualToCountersSourceCountPredicate;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class EngineeredExplosives extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent(
            "each nonland permanent with mana value equal to the number of charge counters on {this}"
    );
    static {
        filter.add(new ManaValueEqualToCountersSourceCountPredicate(CounterType.CHARGE));
    }

    public EngineeredExplosives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{X}");

        // Sunburst
        this.addAbility(new SunburstAbility(this));

        // {2}, Sacrifice Engineered Explosives: Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Engineered Explosives.
        Ability ability = new SimpleActivatedAbility(new DestroyAllEffect(filter), new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private EngineeredExplosives(final EngineeredExplosives card) {
        super(card);
    }

    @Override
    public EngineeredExplosives copy() {
        return new EngineeredExplosives(this);
    }
}
