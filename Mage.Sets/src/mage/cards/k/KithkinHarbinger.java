
package mage.cards.k;

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
 * @author LoneFox
 */
public final class KithkinHarbinger extends CardImpl {

    static final FilterCard filter = new FilterCard("Kithkin card");

    static {
        filter.add(SubType.KITHKIN.getPredicate());
    }

    public KithkinHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Kithkin Harbinger enters the battlefield, you may search your library for a Kithkin card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true), true));
    }

    private KithkinHarbinger(final KithkinHarbinger card) {
        super(card);
    }

    @Override
    public KithkinHarbinger copy() {
        return new KithkinHarbinger(this);
    }
}
