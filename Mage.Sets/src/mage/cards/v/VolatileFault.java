package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VolatileFault extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("nonbasic land an opponent controls");

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VolatileFault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}, Sacrifice Volatile Fault: Destroy target nonbasic land an opponent controls. That player may search their library for a basic land card, put it onto the battlefield, then shuffle. You create a Treasure token.
        Ability ability = new SimpleActivatedAbility(
                new DestroyTargetEffect(),
                new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new SearchLibraryPutInPlayTargetControllerEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND),
                false, Outcome.PutLandInPlay, "that player"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("you"));
        this.addAbility(ability);
    }

    private VolatileFault(final VolatileFault card) {
        super(card);
    }

    @Override
    public VolatileFault copy() {
        return new VolatileFault(this);
    }
}
