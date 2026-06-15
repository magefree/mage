package mage.cards.i;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class InspiredFire extends CardImpl {

    public InspiredFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // This spell costs {1} less to cast for each card you've drawn this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
            new SpellCostReductionSourceEffect(CardsDrawnThisTurnDynamicValue.instance)
                .setText("this spell costs {1} less to cast for each card you've drawn this turn")
        ).addHint(CardsDrawnThisTurnDynamicValue.getHint()));

        // Inspired Fire deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private InspiredFire(final InspiredFire card) {
        super(card);
    }

    @Override
    public InspiredFire copy() {
        return new InspiredFire(this);
    }
}
