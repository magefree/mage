package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class HeideggerShinraExecutive extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.SOLDIER, "Soldiers you control"), null
    );
    private static final Hint hint = new ValueHint("Soldiers you control", xValue);

    public HeideggerShinraExecutive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, target creature you control gets +X/+0 until end of turn, where X is the number of Soldiers you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(xValue, StaticValue.get(0)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // At the beginning of your end step, create a number of 1/1 white Soldier creature tokens equal to the number of opponents who control more creatures than you.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new SoldierToken(), HeideggerShinraExecutiveValue.instance)
                        .setText("create a number of 1/1 white Soldier creature tokens equal " +
                                "to the number of opponents who control more creatures than you")
        ).addHint(HeideggerShinraExecutiveValue.getHint()));
    }

    private HeideggerShinraExecutive(final HeideggerShinraExecutive card) {
        super(card);
    }

    @Override
    public HeideggerShinraExecutive copy() {
        return new HeideggerShinraExecutive(this);
    }
}

enum HeideggerShinraExecutiveValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Opponents with more creatures than you", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Map<UUID, Integer> map = game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .map(Controllable::getControllerId)
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum));
        int amount = map.getOrDefault(sourceAbility.getControllerId(), 0);
        return game
                .getOpponents(sourceAbility.getControllerId())
                .stream()
                .mapToInt(uuid -> map.getOrDefault(uuid, 0))
                .filter(x -> x > amount)
                .map(x -> 1)
                .sum();
    }

    @Override
    public HeideggerShinraExecutiveValue copy() {
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
