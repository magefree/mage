
package mage.cards.r;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
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
public final class RelicCrush extends CardImpl {

    public RelicCrush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{G}");

        // Destroy target artifact or enchantment and up to one other target artifact or enchantment.
        Effect effect = new DestroyTargetEffect(false, true);
        effect.setText("Destroy target artifact or enchantment and up to one other target artifact or enchantment");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT, false));
    }

    private RelicCrush(final RelicCrush card) {
        super(card);
    }

    @Override
    public RelicCrush copy() {
        return new RelicCrush(this);
    }
}
