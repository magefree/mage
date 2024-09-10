
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class SeedguideAsh extends CardImpl {

    private static final FilterCard filter = new FilterCard("Forest cards");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public SeedguideAsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        // When Seedguide Ash dies, you may search your library for up to three Forest cards and put them onto the battlefield tapped. If you do, shuffle your library.
        this.addAbility(new DiesSourceTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 3, filter), true), true));
    }

    private SeedguideAsh(final SeedguideAsh card) {
        super(card);
    }

    @Override
    public SeedguideAsh copy() {
        return new SeedguideAsh(this);
    }
}
