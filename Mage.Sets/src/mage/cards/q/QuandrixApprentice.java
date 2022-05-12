package mage.cards.q;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuandrixApprentice extends CardImpl {

    public QuandrixApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, look at the top three cards of your library. You may reveal a land card from among them and put that card into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new MagecraftAbility(new LookLibraryAndPickControllerEffect(
                3, 1, StaticFilters.FILTER_CARD_LAND_A, PutCards.HAND, PutCards.BOTTOM_ANY)));
    }

    private QuandrixApprentice(final QuandrixApprentice card) {
        super(card);
    }

    @Override
    public QuandrixApprentice copy() {
        return new QuandrixApprentice(this);
    }
}
