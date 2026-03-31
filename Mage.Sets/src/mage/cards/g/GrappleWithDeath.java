package mage.cards.g;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrappleWithDeath extends CardImpl {

    public GrappleWithDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{G}");

        // Destroy target artifact or creature. You gain 1 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new GainLifeEffect(1));
    }

    private GrappleWithDeath(final GrappleWithDeath card) {
        super(card);
    }

    @Override
    public GrappleWithDeath copy() {
        return new GrappleWithDeath(this);
    }
}
