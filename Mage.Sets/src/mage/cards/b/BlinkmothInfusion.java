package mage.cards.b;

import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class BlinkmothInfusion extends CardImpl {

    public BlinkmothInfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{12}{U}{U}");

        // Affinity for artifacts
        this.addAbility(new AffinityForArtifactsAbility());

        // Untap all artifacts.
        this.getSpellAbility().addEffect(new UntapAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS));
    }

    private BlinkmothInfusion(final BlinkmothInfusion card) {
        super(card);
    }

    @Override
    public BlinkmothInfusion copy() {
        return new BlinkmothInfusion(this);
    }
}
