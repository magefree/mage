
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.PopulateEffect;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevleX2
 */
public final class DruidsDeliverance extends CardImpl {

    public DruidsDeliverance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Prevent all combat damage that would be dealt to you this turn. Populate.
        // (Create a token that's a copy of a creature token you control.)
        this.getSpellAbility().addEffect(new PreventDamageToControllerEffect(Duration.EndOfTurn, true, false, Integer.MAX_VALUE));
        this.getSpellAbility().addEffect(new PopulateEffect());
    }

    private DruidsDeliverance(final DruidsDeliverance card) {
        super(card);
    }

    @Override
    public DruidsDeliverance copy() {
        return new DruidsDeliverance(this);
    }
}
