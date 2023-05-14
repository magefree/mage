
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class TreefolkHarbinger extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Treefolk or Forest card");
    static {
       filter.add(Predicates.or(SubType.TREEFOLK.getPredicate(), SubType.FOREST.getPredicate()));
    }
    
    public TreefolkHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Treefolk Harbinger enters the battlefield, you may search your library for a Treefolk or Forest card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true), true));
    }

    private TreefolkHarbinger(final TreefolkHarbinger card) {
        super(card);
    }

    @Override
    public TreefolkHarbinger copy() {
        return new TreefolkHarbinger(this);
    }
}
