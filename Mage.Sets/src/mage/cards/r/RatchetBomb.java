package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
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
 * @author Loki
 */
public final class RatchetBomb extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent(
            "each nonland permanent with mana value equal to the number of charge counters on {this}"
    );
    static {
        filter.add(new ManaValueEqualToCountersSourceCountPredicate(CounterType.CHARGE));
    }

    public RatchetBomb (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        
        // {T}: Put a charge counter on Ratchet Bomb.
        this.addAbility(new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost()));
        
        // {T}, Sacrifice Ratchet Bomb: Destroy each nonland permanent with a converted mana cost equal to the number of charge counters on Ratchet Bomb.
        Ability ability = new SimpleActivatedAbility(new DestroyAllEffect(filter), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private RatchetBomb(final RatchetBomb card) {
        super(card);
    }

    @Override
    public RatchetBomb copy() {
        return new RatchetBomb(this);
    }

}
