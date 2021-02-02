package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StreetRiot extends CardImpl {

    public StreetRiot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        // As long as it's your turn, creatures you control get +1/+0 and have trample.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostControlledEffect(
                                1, 0, Duration.WhileOnBattlefield
                        ), MyTurnCondition.instance,
                        "As long as it's your turn, "
                                + "creatures you control get +1/+0"
                ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CONTROLLED_CREATURES
                ), MyTurnCondition.instance, "and have trample"
        ));
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private StreetRiot(final StreetRiot card) {
        super(card);
    }

    @Override
    public StreetRiot copy() {
        return new StreetRiot(this);
    }
}
