package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class TumbleMagnet extends CardImpl {

    public TumbleMagnet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.CHARGE.createInstance(3)));

        Ability ability = new SimpleActivatedAbility(
                new TapTargetEffect(),
                new TapSourceCost()
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private TumbleMagnet(final TumbleMagnet card) {
        super(card);
    }

    @Override
    public TumbleMagnet copy() {
        return new TumbleMagnet(this);
    }

}
