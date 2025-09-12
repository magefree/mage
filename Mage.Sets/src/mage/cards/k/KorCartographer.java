package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author North
 */
public final class KorCartographer extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.PLAINS);

    public KorCartographer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Kor Cartographer enters the battlefield, you may search your library for a Plains card, put it onto the battlefield tapped, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true), true
        ));
    }

    private KorCartographer(final KorCartographer card) {
        super(card);
    }

    @Override
    public KorCartographer copy() {
        return new KorCartographer(this);
    }
}
