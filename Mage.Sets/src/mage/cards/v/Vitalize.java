
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author Quercitron
 */
public final class Vitalize extends CardImpl {

    public Vitalize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Untap all creatures you control.
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, "Untap all creatures you control"));
    }

    private Vitalize(final Vitalize card) {
        super(card);
    }

    @Override
    public Vitalize copy() {
        return new Vitalize(this);
    }
}
