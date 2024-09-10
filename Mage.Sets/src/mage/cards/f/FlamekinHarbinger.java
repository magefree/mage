
package mage.cards.f;

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
 * @author jonubuu
 */
public final class FlamekinHarbinger extends CardImpl {

    private static final FilterCard filter = new FilterCard("Elemental card");

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
    }

    public FlamekinHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Flamekin Harbinger enters the battlefield, you may search your library for an Elemental card,
        // reveal it, then shuffle your library and put that card on top of it.
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(target, true), true));
    }

    private FlamekinHarbinger(final FlamekinHarbinger card) {
        super(card);
    }

    @Override
    public FlamekinHarbinger copy() {
        return new FlamekinHarbinger(this);
    }
}
