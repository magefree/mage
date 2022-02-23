
package mage.cards.g;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class GleefulSabotage extends CardImpl {

    public GleefulSabotage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        // Conspire
        this.addAbility(new ConspireAbility(ConspireAbility.ConspireTargets.ONE));
    }

    private GleefulSabotage(final GleefulSabotage card) {
        super(card);
    }

    @Override
    public GleefulSabotage copy() {
        return new GleefulSabotage(this);
    }
}
