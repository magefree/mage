
package mage.cards.r;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class RelicCrush extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent("other target artifact or enchantment");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public RelicCrush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{G}");

        // Destroy target artifact or enchantment and up to one other target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect().setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT).setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter).setTargetTag(2));
    }

    private RelicCrush(final RelicCrush card) {
        super(card);
    }

    @Override
    public RelicCrush copy() {
        return new RelicCrush(this);
    }
}
