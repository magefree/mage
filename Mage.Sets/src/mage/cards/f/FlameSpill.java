package mage.cards.f;

import mage.abilities.effects.common.DamageWithExcessEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlameSpill extends CardImpl {

    public FlameSpill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Flame Spill deals 4 damage to target creature. Excess damage is dealt to that creature's controller instead.
        this.getSpellAbility().addEffect(new DamageWithExcessEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FlameSpill(final FlameSpill card) {
        super(card);
    }

    @Override
    public FlameSpill copy() {
        return new FlameSpill(this);
    }
}
