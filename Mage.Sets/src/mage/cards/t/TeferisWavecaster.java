package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.FlashAbility;
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
public final class TeferisWavecaster extends CardImpl {

    private static final FilterCard filter = new FilterCard("Teferi, Timeless Voyager");

    static {
        filter.add(new NamePredicate("Teferi, Timeless Voyager"));
    }

    public TeferisWavecaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Teferi's Wavecaster enters the battlefield, you may search your library and/or graveyard for a card named Teferi, Timeless Voyager, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter), true));
    }

    private TeferisWavecaster(final TeferisWavecaster card) {
        super(card);
    }

    @Override
    public TeferisWavecaster copy() {
        return new TeferisWavecaster(this);
    }
}
