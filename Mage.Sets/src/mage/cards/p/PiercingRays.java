package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PiercingRays extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("untapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public PiercingRays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Exile target tapped creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Forecast—{2}{W}, Reveal Piercing Rays from your hand: Tap target untapped creature.
        Ability ability = new ForecastAbility(new TapTargetEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private PiercingRays(final PiercingRays card) {
        super(card);
    }

    @Override
    public PiercingRays copy() {
        return new PiercingRays(this);
    }
}
