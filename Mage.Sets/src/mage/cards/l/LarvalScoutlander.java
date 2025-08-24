package mage.cards.l;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LarvalScoutlander extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("land or a Lander");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                SubType.LANDER.getPredicate()
        ));
    }

    public LarvalScoutlander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, you may sacrifice a land or a Lander. If you do, search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                        0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS
                ), true), new SacrificeTargetCost(filter)
        )));

        // Station
        this.addAbility(new StationAbility());

        // STATION 7+
        // Flying
        // 3/3
        this.addAbility(new StationLevelAbility(7)
                .withLevelAbility(FlyingAbility.getInstance())
                .withPT(3, 3));
    }

    private LarvalScoutlander(final LarvalScoutlander card) {
        super(card);
    }

    @Override
    public LarvalScoutlander copy() {
        return new LarvalScoutlander(this);
    }
}
