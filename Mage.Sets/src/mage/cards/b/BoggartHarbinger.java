
package mage.cards.b;

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
 * @author dustinconrad
 */
public final class BoggartHarbinger extends CardImpl {

    private static final FilterCard filter = new FilterCard("Goblin card");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public BoggartHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.GOBLIN, SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Boggart Harbinger enters the battlefield, you may search your library for a Goblin card, reveal it,
        // then shuffle your library and put that card on top of it.
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(target, true),true));
    }

    private BoggartHarbinger(final BoggartHarbinger card) {
        super(card);
    }

    @Override
    public BoggartHarbinger copy() {
        return new BoggartHarbinger(this);
    }
}
