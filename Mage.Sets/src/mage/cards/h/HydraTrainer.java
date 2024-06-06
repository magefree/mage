package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class HydraTrainer extends CardImpl {

    public HydraTrainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may exert Hydra Trainer as it attacks. When you do, target creature gets +X/+X until end of turn, where X is the number of counters on permanents you control.
        BecomesExertSourceTriggeredAbility trigger = new BecomesExertSourceTriggeredAbility(
                new BoostTargetEffect(HydraTrainerValue.instance, HydraTrainerValue.instance)
                        .setText("target creature gets +X/+X until end of turn, where X is the number of counters on permanents you control")
        );
        trigger.addTarget(new TargetCreaturePermanent());
        this.addAbility(new ExertAbility(trigger).addHint(HydraTrainerValue.getHint()));

        // {2}{G}: Adapt 2.
        this.addAbility(new AdaptAbility(2, "{2}{G}"));
    }

    private HydraTrainer(final HydraTrainer card) {
        super(card);
    }

    @Override
    public HydraTrainer copy() {
        return new HydraTrainer(this);
    }
}

enum HydraTrainerValue implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Number of counters on permanents you control", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield()
                .getAllActivePermanents(sourceAbility.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .map(perm -> perm.getCounters(game))
                .flatMap(counters -> counters.values().stream())
                .mapToInt(Counter::getCount)
                .sum();
    }

    @Override
    public HydraTrainerValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of counters on permanents you control";
    }
}
