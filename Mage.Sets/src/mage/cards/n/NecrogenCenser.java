

package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class NecrogenCenser extends CardImpl {

    public NecrogenCenser (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.CHARGE.createInstance(2)));
        Ability ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(2), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private NecrogenCenser(final NecrogenCenser card) {
        super(card);
    }

    @Override
    public NecrogenCenser copy() {
        return new NecrogenCenser(this);
    }

}
