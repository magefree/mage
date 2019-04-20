package mage.cards.d;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DivineArrow extends CardImpl {

    public DivineArrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Divine Arrow deals 4 damage to target attacking or blocking creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private DivineArrow(final DivineArrow card) {
        super(card);
    }

    @Override
    public DivineArrow copy() {
        return new DivineArrow(this);
    }
}
