
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class DisruptDecorum extends CardImpl {

    public DisruptDecorum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Goad all creatures you don't control.
        this.getSpellAbility().addEffect(new GoadAllEffect(StaticFilters.FILTER_CREATURES_YOU_DONT_CONTROL));
    }

    private DisruptDecorum(final DisruptDecorum card) {
        super(card);
    }

    @Override
    public DisruptDecorum copy() {
        return new DisruptDecorum(this);
    }
}
