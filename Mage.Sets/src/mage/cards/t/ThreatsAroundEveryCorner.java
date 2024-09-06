package mage.cards.t;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThreatsAroundEveryCorner extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a face-down permanent you control");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public ThreatsAroundEveryCorner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // When Threats Around Every Corner enters, manifest dread.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ManifestDreadEffect()));

        // Whenever a face-down permanent you control enters, search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ), filter));
    }

    private ThreatsAroundEveryCorner(final ThreatsAroundEveryCorner card) {
        super(card);
    }

    @Override
    public ThreatsAroundEveryCorner copy() {
        return new ThreatsAroundEveryCorner(this);
    }
}
