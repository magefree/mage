
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class BorderlandRanger extends CardImpl {

    public BorderlandRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN, SubType.SCOUT, SubType.RANGER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Borderland Ranger enters the battlefield, you may search your library for a basic land card, reveal it, and put it into your hand. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true), true));
    }

    private BorderlandRanger(final BorderlandRanger card) {
        super(card);
    }

    @Override
    public BorderlandRanger copy() {
        return new BorderlandRanger(this);
    }
}
