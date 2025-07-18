package mage.cards.f;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ReinforceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FowlStrike extends CardImpl {

    public FowlStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Destroy target creature with flying.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));

        // Reinforce 2--{2}{G}
        this.addAbility(new ReinforceAbility(2, new ManaCostsImpl<>("{2}{G}")));
    }

    private FowlStrike(final FowlStrike card) {
        super(card);
    }

    @Override
    public FowlStrike copy() {
        return new FowlStrike(this);
    }
}
