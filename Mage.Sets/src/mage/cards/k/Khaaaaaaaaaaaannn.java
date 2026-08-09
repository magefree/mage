package mage.cards.k;

import java.util.UUID;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class Khaaaaaaaaaaaannn extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(GetXValue.instance, 2);

    public Khaaaaaaaaaaaannn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // Khaaaaaaaaaaaannn! deals twice X damage to target creature. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue).setText("{this} deals twice X damage to target creature"));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Khaaaaaaaaaaaannn(final Khaaaaaaaaaaaannn card) {
        super(card);
    }

    @Override
    public Khaaaaaaaaaaaannn copy() {
        return new Khaaaaaaaaaaaannn(this);
    }
}
