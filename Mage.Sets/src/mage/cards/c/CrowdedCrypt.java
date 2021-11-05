package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieDecayedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrowdedCrypt extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.CORPSE);

    public CrowdedCrypt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // Whenever a creature you control dies, put a corpse counter on Crowded Crypt.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CORPSE.createInstance()),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));

        // {4}{B}{B}, {T}, Sacrifice Crowded Crypt: Create a 2/2 black Zombie creature token with decayed for each corpse counter on Crowded Crypt.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ZombieDecayedToken(), xValue)
                        .setText("create a 2/2 black Zombie creature token with decayed for each corpse counter on {this}"),
                new ManaCostsImpl<>("{4}{B}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CrowdedCrypt(final CrowdedCrypt card) {
        super(card);
    }

    @Override
    public CrowdedCrypt copy() {
        return new CrowdedCrypt(this);
    }
}
