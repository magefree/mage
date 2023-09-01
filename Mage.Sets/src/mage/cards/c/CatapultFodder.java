package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class CatapultFodder extends TransformingDoubleFacedCard {

    private static final Hint hint = new ValueHint(
            "Creatures you control with higher toughness than power", CatapultFodderValue.instance
    );

    public CatapultFodder(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE}, "{2}{B}",
                "Catapult Captain",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE}, "B"
        );
        this.getLeftHalfCard().setPT(1, 5);
        this.getRightHalfCard().setPT(2, 6);

        // At the beginning of combat on your turn, if you control three or more creatures that each have toughness greater than their power, transform Catapult Fodder.
        this.getLeftHalfCard().addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false),
                CatapultFodderCondition.instance, "At the beginning of combat on your turn, if you control " +
                "three or more creatures that each have toughness greater than their power, transform {this}"
        ));

        // {2}{B}, {T}, Sacrifice another creature: Target opponent loses life equal to the sacrificed creature's toughness.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeTargetEffect(SacrificeCostCreaturesToughness.instance)
                        .setText("Target opponent loses life equal to the sacrificed creature's toughness"),
                new ManaCostsImpl<>("{2}{B}")
        );
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

enum CatapultFodderCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(permanent -> permanent.getToughness().getValue() > permanent.getPower().getValue()).count() >= 3;
    }

    @Override
    public String toString() {
        return "you control three or more creatures that each have toughness greater than their power";
    }
}

enum CatapultFodderValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .filter(permanent -> permanent.getToughness().getValue() > permanent.getPower().getValue())
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public CatapultFodderValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
