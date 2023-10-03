package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Loki
 */
public final class PilgrimsEye extends CardImpl {

    public PilgrimsEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.THOPTER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());

        // When Pilgrim's Eye enters the battlefield, you may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true), true));
    }

    private PilgrimsEye(final PilgrimsEye card) {
        super(card);
    }

    @Override
    public PilgrimsEye copy() {
        return new PilgrimsEye(this);
    }

}
