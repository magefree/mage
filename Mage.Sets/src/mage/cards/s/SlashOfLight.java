package mage.cards.s;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlashOfLight extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURES),
            new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.EQUIPMENT))
    );
    private static final Hint hint = new ValueHint("Creatures you control plus Equipment you control", xValue);

    public SlashOfLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Slash of Light deals damage equal to the number of creatures you control plus the number of Equipment you control to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals damage equal to the number of creatures you control " +
                        "plus the number of Equipment you control to target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SlashOfLight(final SlashOfLight card) {
        super(card);
    }

    @Override
    public SlashOfLight copy() {
        return new SlashOfLight(this);
    }
}
