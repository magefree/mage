package mage.cards.e;

import mage.abilities.effects.common.ExileTargetEffect;
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
public final class Exorcise extends CardImpl {

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

    public Exorcise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Exile target artifact, enchantment, or creature with power 4 or greater.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private Exorcise(final Exorcise card) {
        super(card);
    }

    @Override
    public Exorcise copy() {
        return new Exorcise(this);
    }
}
