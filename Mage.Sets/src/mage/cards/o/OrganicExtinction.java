package mage.cards.o;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.ImproviseAbility;
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
public final class OrganicExtinction extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonartifact creatures");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public OrganicExtinction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{8}{W}{W}");

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Destroy all nonartifact creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private OrganicExtinction(final OrganicExtinction card) {
        super(card);
    }

    @Override
    public OrganicExtinction copy() {
        return new OrganicExtinction(this);
    }
}
