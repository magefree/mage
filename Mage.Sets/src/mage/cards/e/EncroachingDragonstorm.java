package mage.cards.e;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EncroachingDragonstorm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "a Dragon");

    public EncroachingDragonstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // When this enchantment enters, search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS), true
        )));

        // When a Dragon you control enters, return this enchantment to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter));
    }

    private EncroachingDragonstorm(final EncroachingDragonstorm card) {
        super(card);
    }

    @Override
    public EncroachingDragonstorm copy() {
        return new EncroachingDragonstorm(this);
    }
}
