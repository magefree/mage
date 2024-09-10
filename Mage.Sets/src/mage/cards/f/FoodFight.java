package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FoodFight extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new NamePredicate("Food Fight"));
    }

    private static final DynamicValue countFoodFight = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Food Fight you control", countFoodFight);
    private static final DynamicValue xValue = new IntPlusDynamicValue(1, countFoodFight);


    public FoodFight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Artifacts you control have "{2}, Sacrifice this artifact: It deals damage to any target equal to 1 plus the number of permanents named Food Fight you control."
        Ability givenAbility = new SimpleActivatedAbility(
                new DamageTargetEffect(xValue)
                        .setText("It deals damage to any target equal to 1 plus the number of permanents named Food Fight you control"),
                new GenericManaCost(2)
        );
        givenAbility.addCost(new SacrificeSourceCost());
        givenAbility.addTarget(new TargetAnyTarget());
        givenAbility.addHint(hint);

        this.addAbility(new SimpleStaticAbility(
                new GainAbilityAllEffect(
                        givenAbility,
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS
                ).setText("artifacts you control have \"{2}, Sacrifice this artifact: It deals damage to any "
                        + "target equal to 1 plus the number of permanents named Food Fight you control.\"")
        ));
    }

    private FoodFight(final FoodFight card) {
        super(card);
    }

    @Override
    public FoodFight copy() {
        return new FoodFight(this);
    }
}
