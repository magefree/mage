
package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
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
public final class ViridianEmissary extends CardImpl {

    public ViridianEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Viridian Emissary dies, you may search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        this.addAbility(new DiesSourceTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true), true));
    }

    private ViridianEmissary(final ViridianEmissary card) {
        super(card);
    }

    @Override
    public ViridianEmissary copy() {
        return new ViridianEmissary(this);
    }

}
