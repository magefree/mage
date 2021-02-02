
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class Tarfire extends CardImpl {

    public Tarfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{R}");
        this.subtype.add(SubType.GOBLIN);

        // Tarfire deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private Tarfire(final Tarfire card) {
        super(card);
    }

    @Override
    public Tarfire copy() {
        return new Tarfire(this);
    }
}
