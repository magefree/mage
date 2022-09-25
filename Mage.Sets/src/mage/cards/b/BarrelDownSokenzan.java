package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.SweepNumber;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.SweepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BarrelDownSokenzan extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(SweepNumber.MOUNTAIN, 2);

    public BarrelDownSokenzan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.subtype.add(SubType.ARCANE);

        // Sweep - Return any number of Mountains you control to their owner's hand. Barrel Down Sokenzan deals damage to target creature equal to twice the number of Mountains returned this way.
        this.getSpellAbility().addEffect(new SweepEffect(SubType.MOUNTAIN));
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals damage to target creature equal to twice the number of Mountains returned this way")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BarrelDownSokenzan(final BarrelDownSokenzan card) {
        super(card);
    }

    @Override
    public BarrelDownSokenzan copy() {
        return new BarrelDownSokenzan(this);
    }
}
