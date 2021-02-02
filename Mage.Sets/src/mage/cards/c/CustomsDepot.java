
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class CustomsDepot extends CardImpl {

    public CustomsDepot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever you cast a creature spell, you may pay {1}. If you do, draw a card, then discard a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(
                        new DrawDiscardControllerEffect(),
                        new GenericManaCost(1)
                ),
                StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));
    }

    private CustomsDepot(final CustomsDepot card) {
        super(card);
    }

    @Override
    public CustomsDepot copy() {
        return new CustomsDepot(this);
    }
}
