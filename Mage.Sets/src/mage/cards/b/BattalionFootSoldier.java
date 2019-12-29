package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattalionFootSoldier extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Battalion Foot Soldier");

    static {
        filter.add(new NamePredicate("Battalion Foot Soldier"));
    }

    public BattalionFootSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Battalion Foot Soldier enters the battlefield, you may search your library for any number of cards named Battalion Foot Soldier, reveal them, put them into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), true, true
        ), true));
    }

    private BattalionFootSoldier(final BattalionFootSoldier card) {
        super(card);
    }

    @Override
    public BattalionFootSoldier copy() {
        return new BattalionFootSoldier(this);
    }
}
