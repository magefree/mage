package mage.cards.n;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class NaturesClaim extends CardImpl {

    public NaturesClaim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Destroy target artifact or enchantment. Its controller gains 4 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeTargetControllerEffect(4));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
    }

    private NaturesClaim(final NaturesClaim card) {
        super(card);
    }

    @Override
    public NaturesClaim copy() {
        return new NaturesClaim(this);
    }
}
