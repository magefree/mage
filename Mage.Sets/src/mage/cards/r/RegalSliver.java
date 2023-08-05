package mage.cards.r;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RegalSliver extends CardImpl {

    public RegalSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sliver creatures you control have "When this creature enters the battlefield, Slivers you control get +1/+1 until end of turn if you're the monarch. Otherwise, you become the monarch."
        TriggeredAbility trigger =
            new EntersBattlefieldTriggeredAbility(
                new ConditionalOneShotEffect(
                    new AddContinuousEffectToGame(
                        new BoostControlledEffect(1, 1, Duration.EndOfTurn,
                            StaticFilters.FILTER_PERMANENT_SLIVERS
                        )),
                    new BecomesMonarchSourceEffect(),
                    MonarchIsSourceControllerCondition.instance,
                    "Slivers you control get +1/+1 until end of turn if you're the monarch. Otherwise, you become the monarch."
                )
            );

        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            trigger, Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENT_ALL_SLIVERS
        ).setText("Sliver creatures you control have \"When this creature enters the battlefield, "
            + "Slivers you control get +1/+1 until end of turn if you're the monarch. Otherwise, you become the monarch.\"")));
    }

    private RegalSliver(final RegalSliver card) {
        super(card);
    }

    @Override
    public RegalSliver copy() {
        return new RegalSliver(this);
    }
}
