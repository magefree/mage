package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalCostModificationEffect;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PaladinClass extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_ATTACKING_CREATURES), StaticValue.get(-1)
    );

    public PaladinClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Spells your opponents cast during your turn cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(new ConditionalCostModificationEffect(
                new SpellsCostIncreasingAllEffect(
                        1, StaticFilters.FILTER_CARD, TargetController.OPPONENT
                ), MyTurnCondition.instance, "spells your opponents cast during your turn cost {1} more to cast"
        )));

        // {2}{W}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{2}{W}"));

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield), 2
        )));

        // {4}{W}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{4}{W}"));

        // Whenever you attack, until end of turn, target attacking creature gets +1/+1 for each other attacking creature and gains double strike.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn, true)
                        .setText("until end of turn, target attacking creature " +
                                "gets +1/+1 for each other attacking creature"),
                1
        );
        ability.addEffect(new GainAbilityTargetEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains double strike"));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 3)));
    }

    private PaladinClass(final PaladinClass card) {
        super(card);
    }

    @Override
    public PaladinClass copy() {
        return new PaladinClass(this);
    }
}
