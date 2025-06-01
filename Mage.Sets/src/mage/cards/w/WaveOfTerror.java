package mage.cards.w;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.triggers.BeginningOfDrawTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValueEqualToCountersSourceCountPredicate;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class WaveOfTerror extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(
            "each creature with mana value equal to the number of age counters on {this}"
    );
    static {
        filter.add(new ManaValueEqualToCountersSourceCountPredicate(CounterType.AGE));
    }

    public WaveOfTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // At the beginning of your draw step, destroy each creature with converted mana cost equal to the number of age counters on Wave of Terror. They can't be regenerated.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new DestroyAllEffect(filter, true), false));
    }

    private WaveOfTerror(final WaveOfTerror card) {
        super(card);
    }

    @Override
    public WaveOfTerror copy() {
        return new WaveOfTerror(this);
    }
}
