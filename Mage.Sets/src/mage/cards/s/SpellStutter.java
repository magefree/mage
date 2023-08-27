package mage.cards.s;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SpellStutter extends CardImpl {

    private static final DynamicValue faerieCount =
            new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FAERIE));
    private static final DynamicValue xValue = new IntPlusDynamicValue(2, faerieCount);

    private static final Hint hint = new ValueHint("Faeries controlled", faerieCount);

    public SpellStutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {2} plus an additional {1} for each Faerie you control.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(xValue).setText(
                "Counter target spell unless its controller pays {2} "
                        + "plus an additional {1} for each Faerie you control."
        ));
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addHint(hint);
    }

    private SpellStutter(final SpellStutter card) {
        super(card);
    }

    @Override
    public SpellStutter copy() {
        return new SpellStutter(this);
    }
}
