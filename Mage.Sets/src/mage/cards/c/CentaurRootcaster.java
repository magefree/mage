
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
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
 * @author fireshoes
 */
public final class CentaurRootcaster extends CardImpl {

    public CentaurRootcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Centaur Rootcaster deals combat damage to a player, you may search your library for a basic land card and put that card onto the battlefield tapped. If you do, shuffle your library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true),
                true));
    }

    private CentaurRootcaster(final CentaurRootcaster card) {
        super(card);
    }

    @Override
    public CentaurRootcaster copy() {
        return new CentaurRootcaster(this);
    }
}
