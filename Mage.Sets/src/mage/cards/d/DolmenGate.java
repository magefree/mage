
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreatureInPlay;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author LevelX2
 */
public final class DolmenGate extends CardImpl {

    private static final FilterControlledCreatureInPlay filter = new FilterControlledCreatureInPlay("attacking creatures you control");

    static {
        filter.getCreatureFilter().add(AttackingPredicate.instance);
    }

    public DolmenGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Prevent all combat damage that would be dealt to attacking creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, filter, true)));
    }

    public DolmenGate(final DolmenGate card) {
        super(card);
    }

    @Override
    public DolmenGate copy() {
        return new DolmenGate(this);
    }
}
