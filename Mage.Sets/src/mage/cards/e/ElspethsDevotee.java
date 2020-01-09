package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElspethsDevotee extends CardImpl {

    private static final FilterCard filter = new FilterCard("Elspeth, Undaunted Hero");

    static {
        filter.add(new NamePredicate("Elspeth, Undaunted Hero"));
    }

    public ElspethsDevotee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Elspeth's Devotee enters the battlefield, you may search your library and/or graveyard for a card named Elspeth, Undaunted Hero, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        ));
    }

    private ElspethsDevotee(final ElspethsDevotee card) {
        super(card);
    }

    @Override
    public ElspethsDevotee copy() {
        return new ElspethsDevotee(this);
    }
}
