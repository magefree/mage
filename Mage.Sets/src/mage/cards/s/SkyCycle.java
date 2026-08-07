package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SkyCycle extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
        new FilterControlledPermanent(SubType.VEHICLE, "twice the number of Vehicles you control"), 2
    );

    private static final Hint hint = new ValueHint(
        "Vehicles you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.VEHICLE))
    );

    public SkyCycle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this Vehicle enters, it deals X damage to up to one target creature, where X is twice the number of Vehicles you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(xValue));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addHint(hint);
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private SkyCycle(final SkyCycle card) {
        super(card);
    }

    @Override
    public SkyCycle copy() {
        return new SkyCycle(this);
    }
}
