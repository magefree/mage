package mage.cards.m;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakeYourMove extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifact, enchantment, or creature with power 4 or greater");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        new PowerPredicate(ComparisonType.MORE_THAN, 3)
                )
        ));
    }

    public MakeYourMove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target artifact, enchantment, or creature with power 4 or greater.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private MakeYourMove(final MakeYourMove card) {
        super(card);
    }

    @Override
    public MakeYourMove copy() {
        return new MakeYourMove(this);
    }
}
