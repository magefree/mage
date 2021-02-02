
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author LoneFox
 */
public final class BurningFields extends CardImpl {

    public BurningFields(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Burning Fields deals 5 damage to target opponent.
        this.getSpellAbility().addTarget(new TargetOpponentOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
    }

    private BurningFields(final BurningFields card) {
        super(card);
    }

    @Override
    public BurningFields copy() {
        return new BurningFields(this);
    }
}
