package mage.cards.g;

import mage.MageInt;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreaterTanuki extends CardImpl {

    public GreaterTanuki(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Channel — {2}{G}, Discard Greater Tanuki: Search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new ChannelAbility("{2}{G}", new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        )));
    }

    private GreaterTanuki(final GreaterTanuki card) {
        super(card);
    }

    @Override
    public GreaterTanuki copy() {
        return new GreaterTanuki(this);
    }
}
