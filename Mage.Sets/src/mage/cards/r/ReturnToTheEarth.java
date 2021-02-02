
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class ReturnToTheEarth extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, enchantment, or creature with flying");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.and(
                    CardType.CREATURE.getPredicate(),
                    new AbilityPredicate(FlyingAbility.class))
              ));
    }

    public ReturnToTheEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        // Destroy target artifact, enchantment, or creature with flying.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    private ReturnToTheEarth(final ReturnToTheEarth card) {
        super(card);
    }

    @Override
    public ReturnToTheEarth copy() {
        return new ReturnToTheEarth(this);
    }
}
