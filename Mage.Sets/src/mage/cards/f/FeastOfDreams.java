
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FeastOfDreams extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("enchanted creature or enchantment creature");

    static {
        filter.add(Predicates.or(
                EnchantedPredicate.instance,
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public FeastOfDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");


        // Destroy target enchanted creature or enchantment creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private FeastOfDreams(final FeastOfDreams card) {
        super(card);
    }

    @Override
    public FeastOfDreams copy() {
        return new FeastOfDreams(this);
    }
}
