
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author TheElk801
 */
public final class NiambiFaithfulHealer extends CardImpl {

    private static final FilterCard filter = new FilterCard("Teferi, Timebender");

    static {
        filter.add(new NamePredicate("Teferi, Timebender"));
    }

    public NiambiFaithfulHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Niambi, Faithful Healer enters the battlefield, you may search your library and/or graveyard for a card named Teferi, Timebender, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter), true));
    }

    private NiambiFaithfulHealer(final NiambiFaithfulHealer card) {
        super(card);
    }

    @Override
    public NiambiFaithfulHealer copy() {
        return new NiambiFaithfulHealer(this);
    }
}
