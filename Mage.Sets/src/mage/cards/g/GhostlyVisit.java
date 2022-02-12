package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class GhostlyVisit extends CardImpl {

    public GhostlyVisit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Destroy target nonblack creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private GhostlyVisit(final GhostlyVisit card) {
        super(card);
    }

    @Override
    public GhostlyVisit copy() {
        return new GhostlyVisit(this);
    }
}
