

package mage.cards.s;

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
 * @author BetaSteward_at_googlemail.com
 */
public final class SylvanRanger extends CardImpl {

    public SylvanRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.RANGER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Sylvan Ranger enters the battlefield, you may search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true), true));
    }

    private SylvanRanger(final SylvanRanger card) {
        super(card);
    }

    @Override
    public SylvanRanger copy() {
        return new SylvanRanger(this);
    }

}
