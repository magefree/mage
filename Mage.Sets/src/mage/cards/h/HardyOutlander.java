package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.AttacksOpponentWithMostLifeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HardyOutlander extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public HardyOutlander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever this creature attacks a player, if no opponent has more life than that player, another target creature you control gets +X/+X until end of turn, where X is this creature's power."
        Ability ability = new AttacksOpponentWithMostLifeTriggeredAbility(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn
        ).setText("another target creature you control gets +X/+X until end of turn, where X is this creature's power"), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private HardyOutlander(final HardyOutlander card) {
        super(card);
    }

    @Override
    public HardyOutlander copy() {
        return new HardyOutlander(this);
    }
}
