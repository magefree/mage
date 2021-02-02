
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterOpponentsCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class BlazingVolley extends CardImpl {

    public BlazingVolley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Blazing Volley deals 1 damage to each creature your opponents control.
        getSpellAbility().addEffect(new DamageAllEffect(1, new FilterOpponentsCreaturePermanent("creature your opponents control")));
    }

    private BlazingVolley(final BlazingVolley card) {
        super(card);
    }

    @Override
    public BlazingVolley copy() {
        return new BlazingVolley(this);
    }
}
