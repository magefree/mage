
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class GiantHarbinger extends CardImpl {
    
    static final FilterCard filter = new FilterCard("Giant card");
    static {
        filter.add(SubType.GIANT.getPredicate());
    }

    public GiantHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Giant Harbinger enters the battlefield, you may search your library for a Giant card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true), true));
    }

    private GiantHarbinger(final GiantHarbinger card) {
        super(card);
    }

    @Override
    public GiantHarbinger copy() {
        return new GiantHarbinger(this);
    }
}
