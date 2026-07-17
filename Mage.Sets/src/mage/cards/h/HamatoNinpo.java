package mage.cards.h;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author muz
 */
public final class HamatoNinpo extends CardImpl {

    public HamatoNinpo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Hamato Ninpo deals 4 damage to target attacking or blocking creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private HamatoNinpo(final HamatoNinpo card) {
        super(card);
    }

    @Override
    public HamatoNinpo copy() {
        return new HamatoNinpo(this);
    }
}
