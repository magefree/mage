
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author TheElk801
 */
public final class DivineRetribution extends CardImpl {

    public DivineRetribution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Divine Retribution deals damage to target attacking creature equal to the number of attacking creatures.
        this.getSpellAbility().addEffect(
                new DamageTargetEffect(new PermanentsOnBattlefieldCount(new FilterAttackingCreature()))
                        .setText("{this} deals damage to target attacking creature equal to the number of attacking creatures.")
        );
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private DivineRetribution(final DivineRetribution card) {
        super(card);
    }

    @Override
    public DivineRetribution copy() {
        return new DivineRetribution(this);
    }
}
