package mage.cards.n;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NissasTriumph extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic Forest cards");
    private static final FilterPermanent filter2 = new FilterControlledPlaneswalkerPermanent(SubType.NISSA);

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.FOREST.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter2);

    public NissasTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}");

        // Search your land for up to two basic Forest cards. If you control a Nissa planeswalker, instead search your library for up to three land cards. reveal those cards, put them in your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                        0, 3, StaticFilters.FILTER_CARD_LAND
                ), true),
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                        0, 2, filter
                ), true),
                new PermanentsOnTheBattlefieldCondition(filter2),
                "Search your library for up to two basic Forest cards. If you control a Nissa planeswalker, " +
                        "instead search your library for up to three land cards. " +
                        "Reveal those cards, put them into your hand, then shuffle."
        ));
    }

    private NissasTriumph(final NissasTriumph card) {
        super(card);
    }

    @Override
    public NissasTriumph copy() {
        return new NissasTriumph(this);
    }
}
