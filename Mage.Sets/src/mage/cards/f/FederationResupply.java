package mage.cards.f;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.BeamMeUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author muz
 */
public final class FederationResupply extends CardImpl {

    private static final FilterCard filter = new FilterCard("Aura, Equipment, or Spacecraft card");

    static {
        filter.add(Predicates.or(
            SubType.AURA.getPredicate(),
            SubType.EQUIPMENT.getPredicate(),
            SubType.SPACECRAFT.getPredicate()
        ));
    }

    public FederationResupply(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Search your library for an Aura, Equipment, or Spacecraft card, reveal it, put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true));

        // Beam me up {3}{W}
        this.addAbility(new BeamMeUpAbility(this, "{3}{W}"));
    }

    private FederationResupply(final FederationResupply card) {
        super(card);
    }

    @Override
    public FederationResupply copy() {
        return new FederationResupply(this);
    }
}
