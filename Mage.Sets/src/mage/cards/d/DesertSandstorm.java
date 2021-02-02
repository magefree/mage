
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class DesertSandstorm extends CardImpl {

    public DesertSandstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Desert Sandstorm deals 1 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, new FilterCreaturePermanent()));
    }

    private DesertSandstorm(final DesertSandstorm card) {
        super(card);
    }

    @Override
    public DesertSandstorm copy() {
        return new DesertSandstorm(this);
    }
}
