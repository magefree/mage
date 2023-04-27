package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.costs.costadjusters.LegendaryCreatureCostAdjuster;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoseijuWhoEndures extends CardImpl {

    private static final FilterPermanent filterDestroy
            = new FilterPermanent("artifact, enchantment, or nonbasic land an opponent controls");
    private static final FilterCard filterSearch = new FilterLandCard("land card with a basic land type");

    static {
        filterDestroy.add(TargetController.OPPONENT.getControllerPredicate());
        filterDestroy.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.and(
                        Predicates.not(SuperType.BASIC.getPredicate()),
                        CardType.LAND.getPredicate()
                )
        ));
        filterSearch.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    public BoseijuWhoEndures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // Channel — {1}{G}, Discard Boseiju, Who Endures: Destroy target artifact, enchantment, or nonbasic land an opponent controls.
        // That player may search their library for a land card with a basic land type, put it onto the battlefield, then shuffle.
        // This ability costs {1} less to activate for each legendary creature you control.
        Ability ability = new ChannelAbility("{1}{G}", new DestroyTargetEffect());
        ability.addEffect(new SearchLibraryPutInPlayTargetControllerEffect(
                new TargetCardInLibrary(filterSearch), false, Outcome.PutLandInPlay, "that player"
        ));
        ability.addEffect(new InfoEffect(
                "This ability costs {1} less to activate for each legendary creature you control"
        ));
        ability.addTarget(new TargetPermanent(filterDestroy));
        ability.setCostAdjuster(LegendaryCreatureCostAdjuster.instance);
        this.addAbility(ability.addHint(LegendaryCreatureCostAdjuster.getHint()));
    }

    private BoseijuWhoEndures(final BoseijuWhoEndures card) {
        super(card);
    }

    @Override
    public BoseijuWhoEndures copy() {
        return new BoseijuWhoEndures(this);
    }
}
