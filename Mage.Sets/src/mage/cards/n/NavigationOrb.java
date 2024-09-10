package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NavigationOrb extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land cards and/or Gate cards");

    static {
        filter.add(Predicates.or(Predicates.and(
                CardType.LAND.getPredicate(),
                SuperType.BASIC.getPredicate()
        ), SubType.GATE.getPredicate()));
    }

    public NavigationOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}, {T}, Sacrifice Navigation Orb: Search your library for up to two basic land cards and/or Gate cards, reveal those cards, put one onto the battlefield tapped and the other into your hand, then shuffle.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect(
                new TargetCardInLibrary(0, 2, filter)), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private NavigationOrb(final NavigationOrb card) {
        super(card);
    }

    @Override
    public NavigationOrb copy() {
        return new NavigationOrb(this);
    }
}
