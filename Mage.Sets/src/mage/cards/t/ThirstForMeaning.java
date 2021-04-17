package mage.cards.t;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThirstForMeaning extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard();

    public ThirstForMeaning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Draw three cards. Then discard two cards unless you discard an enchantment card.
        DiscardCardCost cost = new DiscardCardCost(filter);
        cost.setText("discard an enchantment card instead of discarding two cards");
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                null, new DiscardControllerEffect(2), cost
        ).setText("Then discard two cards unless you discard an enchantment card"));
    }

    private ThirstForMeaning(final ThirstForMeaning card) {
        super(card);
    }

    @Override
    public ThirstForMeaning copy() {
        return new ThirstForMeaning(this);
    }
}
