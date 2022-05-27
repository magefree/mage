package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DraconicMuralists extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public DraconicMuralists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Draconic Muralists dies, you may search your library for a Dragon card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new DiesSourceTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true, true
        ), true));
    }

    private DraconicMuralists(final DraconicMuralists card) {
        super(card);
    }

    @Override
    public DraconicMuralists copy() {
        return new DraconicMuralists(this);
    }
}
