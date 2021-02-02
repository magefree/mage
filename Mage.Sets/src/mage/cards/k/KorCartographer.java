
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterBySubtypeCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class KorCartographer extends CardImpl {

    public KorCartographer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Kor Cartographer enters the battlefield, you may search your library for a Plains card, put it onto the battlefield tapped, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterBySubtypeCard(SubType.PLAINS)), true), true));
    }

    private KorCartographer(final KorCartographer card) {
        super(card);
    }

    @Override
    public KorCartographer copy() {
        return new KorCartographer(this);
    }
}
