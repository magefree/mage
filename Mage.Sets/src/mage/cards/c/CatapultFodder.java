package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessGreaterThanPowerPredicate;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class CatapultFodder extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(
            "you control three or more creatures that each have toughness greater than their power"
    );

    static {
        filter.add(ToughnessGreaterThanPowerPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ValueHint(
            "Creatures you control with toughness greater than their power", new PermanentsOnBattlefieldCount(filter)
    );

    public CatapultFodder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE}, "{2}{B}",
                "Catapult Captain",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE}, "B");

        // Catapult Fodder
        this.getLeftHalfCard().setPT(1, 5);

        // At the beginning of combat on your turn, if you control three or more creatures that each have toughness greater than their power, transform Catapult Fodder.
        this.getLeftHalfCard().addAbility(new BeginningOfCombatTriggeredAbility(new TransformSourceEffect()).withInterveningIf(condition).addHint(hint));

        // Catapult Captain
        this.getRightHalfCard().setPT(2, 6);

        // {2}{B}, {T}, Sacrifice another creature: Target opponent loses life equal to the sacrificed creature's toughness.
        Ability ability = new SimpleActivatedAbility(new LoseLifeTargetEffect(SacrificeCostCreaturesToughness.instance)
                .setText("Target opponent loses life equal to the sacrificed creature's toughness"), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addTarget(new TargetOpponent());
        this.getRightHalfCard().addAbility(ability);
    }

    private CatapultFodder(final CatapultFodder card) {
        super(card);
    }

    @Override
    public CatapultFodder copy() {
        return new CatapultFodder(this);
    }
}
