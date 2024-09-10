package mage.cards.s;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShowerOfArrows extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifact, enchantment, or creature with flying");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        new AbilityPredicate(FlyingAbility.class)
                )
        ));
    }

    public ShowerOfArrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Destroy target artifact, enchantment, or creature with flying. Scry 1.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ShowerOfArrows(final ShowerOfArrows card) {
        super(card);
    }

    @Override
    public ShowerOfArrows copy() {
        return new ShowerOfArrows(this);
    }
}
