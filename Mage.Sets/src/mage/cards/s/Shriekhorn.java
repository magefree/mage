package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Shriekhorn extends CardImpl {

    public Shriekhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.CHARGE.createInstance(3)
                ), "with three charge counters on it"
        ));

        Ability ability = new SimpleActivatedAbility(new MillCardsTargetEffect(2), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Shriekhorn(final Shriekhorn card) {
        super(card);
    }

    @Override
    public Shriekhorn copy() {
        return new Shriekhorn(this);
    }

}
