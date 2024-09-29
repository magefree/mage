package mage.cards.d;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesertsDue extends CardImpl {

    private static final DynamicValue desertCount = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.DESERT));
    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new SignInversionDynamicValue(desertCount), StaticValue.get(-2)
    );
    private static final Hint hint = new ValueHint("Deserts you control", desertCount);

    public DesertsDue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets -2/-2 until end of turn. It gets an additional -1/-1 until end of turn for each Desert you control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue)
                .setText("target creature gets -2/-2 until end of turn. " +
                        "It gets an additional -1/-1 until end of turn for each Desert you control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DesertsDue(final DesertsDue card) {
        super(card);
    }

    @Override
    public DesertsDue copy() {
        return new DesertsDue(this);
    }
}
