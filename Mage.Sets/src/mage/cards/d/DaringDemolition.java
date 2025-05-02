package mage.cards.d;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DaringDemolition extends CardImpl {

    public DaringDemolition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Destroy target creature or Vehicle.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_VEHICLE));
    }

    private DaringDemolition(final DaringDemolition card) {
        super(card);
    }

    @Override
    public DaringDemolition copy() {
        return new DaringDemolition(this);
    }
}
