package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class SaguWildling extends OmenCard {

    public SaguWildling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{4}{G}",
                "Roost Seek",
                new CardType[]{CardType.SORCERY}, "{G}");

        // Sagu Wildling
        this.getLeftHalfCard().setPT(3, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When this creature enters, you gain 3 life.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // Roost Seek
        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        this.getRightHalfCard().getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(target, true));

        finalizeCard();
    }

    private SaguWildling(final SaguWildling card) {
        super(card);
    }

    @Override
    public SaguWildling copy() {
        return new SaguWildling(this);
    }
}
