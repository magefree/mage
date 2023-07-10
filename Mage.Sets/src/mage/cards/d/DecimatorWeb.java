

package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki, nantuko
 */
public final class DecimatorWeb extends CardImpl {

    public DecimatorWeb (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AddCountersTargetEffect(CounterType.POISON.createInstance()));
        ability.addEffect(new MillCardsTargetEffect(6));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public DecimatorWeb (final DecimatorWeb card) {
        super(card);
    }

    @Override
    public DecimatorWeb copy() {
        return new DecimatorWeb(this);
    }

}
