package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.MoveCountersFromSourceToTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueConditionHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class DiamondCity extends CardImpl {

    public DiamondCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Diamond City enters the battlefield with a shield counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SHIELD.createInstance(1)),
                "with a shield counter on it. <i>(If it would be dealt damage " +
                        "or destroyed, remove a shield counter from it instead.)</i>"
        ));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Move a shield counter from Diamond City onto target creature. Activate only if two or more creatures entered the battlefield under your control this turn.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new MoveCountersFromSourceToTargetEffect(CounterType.SHIELD),
                new TapSourceCost(), DiamondCityCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addHint(DiamondCityCreaturesThatEnteredThisTurnCount.getHint());
        this.addAbility(ability, new PermanentsEnteredBattlefieldWatcher());
    }

    private DiamondCity(final DiamondCity card) {
        super(card);
    }

    @Override
    public DiamondCity copy() {
        return new DiamondCity(this);
    }
}

enum DiamondCityCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return DiamondCityCreaturesThatEnteredThisTurnCount.instance.calculate(game, source, null) >= 2;
    }

    @Override
    public String toString() {
        return "two or more creatures entered the battlefield under your control this turn";
    }
}

enum DiamondCityCreaturesThatEnteredThisTurnCount implements DynamicValue {
    instance;

    private static final Hint hint = new ValueConditionHint(instance, DiamondCityCondition.instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        if (watcher != null) {
            List<Permanent> list = watcher.getThisTurnEnteringPermanents(sourceAbility.getControllerId());
            return (int) list.stream().filter(MageObject::isCreature).count();
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "creatures that attacked this turn";
    }

    public static Hint getHint() {
        return hint;
    }
}
