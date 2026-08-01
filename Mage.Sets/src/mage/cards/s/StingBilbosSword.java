package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class StingBilbosSword extends CardImpl {

    private static final DynamicValue xValue =
            new PermanentsTargetOpponentControlsCount(StaticFilters.FILTER_PERMANENT_CREATURES);

    public StingBilbosSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Sting enters, put a hone counter on Sting for each creature target opponent controls. Attach Sting to up to one target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new AddCountersSourceEffect(CounterType.HONE.createInstance(), xValue)
                .setText("put a hone counter on {this} for each creature target opponent controls")
        );
        ability.addTarget(new TargetOpponent());
        ability.addEffect(
            new AttachEffect(Outcome.BoostCreature, "attach {this} to up to one target creature you control")
                .setTargetPointer(new SecondTargetPointer())
        );
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));

    }

    private StingBilbosSword(final StingBilbosSword card) {
        super(card);
    }

    @Override
    public StingBilbosSword copy() {
        return new StingBilbosSword(this);
    }
}
