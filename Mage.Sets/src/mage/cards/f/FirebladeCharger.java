package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;
import mage.abilities.common.DiesSourceTriggeredAbility;

/**
 * @author TheElk801
 */
public final class FirebladeCharger extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public FirebladeCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As long as Fireblade Charger is equipped, it has haste.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield),
                EquippedSourceCondition.instance, "as long as {this} is equipped, it has haste"
        )));

        // When Fireblade Charger dies, it deals damage equal to its power to any target.
        Ability ability = new DiesSourceTriggeredAbility(
                new DamageTargetEffect(xValue).setText("it deals damage equal to its power to any target")
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FirebladeCharger(final FirebladeCharger card) {
        super(card);
    }

    @Override
    public FirebladeCharger copy() {
        return new FirebladeCharger(this);
    }
}
