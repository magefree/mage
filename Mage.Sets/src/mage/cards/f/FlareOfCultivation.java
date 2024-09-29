package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.search.SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlareOfCultivation extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nontoken green creature");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public FlareOfCultivation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{G}");

        // You may sacrifice a nontoken green creature rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(filter)).setRuleAtTheTop(true));

        // Search your library for up to two basic land cards, reveal those cards, put one onto the battlefield tapped and the other into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutOneOntoBattlefieldTappedRestInHandEffect(
                new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS)
        ));
    }

    private FlareOfCultivation(final FlareOfCultivation card) {
        super(card);
    }

    @Override
    public FlareOfCultivation copy() {
        return new FlareOfCultivation(this);
    }
}
