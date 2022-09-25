package mage.cards.t;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheirNameIsDeath extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonartifact creatures");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public TheirNameIsDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}{B}");

        // Destroy all nonartifact creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private TheirNameIsDeath(final TheirNameIsDeath card) {
        super(card);
    }

    @Override
    public TheirNameIsDeath copy() {
        return new TheirNameIsDeath(this);
    }
}
