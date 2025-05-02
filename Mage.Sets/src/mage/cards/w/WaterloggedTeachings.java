package mage.cards.w;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WaterloggedTeachings extends ModalDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("an instant card or a card with flash");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                new AbilityPredicate(FlashAbility.class)
        ));
    }

    public WaterloggedTeachings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{3}{U/B}",
                "Inundated Archive", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Watterlogged Teaching
        // Instant

        // Search your library for an instant card or a card with flash, reveal it, put it into your hand, then shuffle.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        );

        // 2.
        // Inundated Archive
        // Land

        // Inundated Archive enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U} or {B}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private WaterloggedTeachings(final WaterloggedTeachings card) {
        super(card);
    }

    @Override
    public WaterloggedTeachings copy() {
        return new WaterloggedTeachings(this);
    }
}
