package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StrengthBobblehead extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.BOBBLEHEAD, "Bobbleheads you control"), null
    );
    private static final Hint hint = new ValueHint("Bobbleheads you control", xValue);

    public StrengthBobblehead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.BOBBLEHEAD);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {3}, {T}: Put X +1/+1 counters on target creature, where X is the number of Bobbleheads you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), xValue)
                        .setText("Put X +1/+1 counters on target creature, where X is the number of Bobbleheads you control."),
                new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addHint(hint);
        this.addAbility(ability);
    }

    private StrengthBobblehead(final StrengthBobblehead card) {
        super(card);
    }

    @Override
    public StrengthBobblehead copy() {
        return new StrengthBobblehead(this);
    }
}
