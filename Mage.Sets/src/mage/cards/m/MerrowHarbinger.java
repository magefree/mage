
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LoneFox

 */
public final class MerrowHarbinger extends CardImpl {

    static final FilterCard filter = new FilterCard("Merfolk card");

    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK   ));
    }

    public MerrowHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // When Merrow Harbinger enters the battlefield, you may search your library for a Merfolk card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true, true), true));
    }

    public MerrowHarbinger(final MerrowHarbinger card) {
        super(card);
    }

    @Override
    public MerrowHarbinger copy() {
        return new MerrowHarbinger(this);
    }
}
