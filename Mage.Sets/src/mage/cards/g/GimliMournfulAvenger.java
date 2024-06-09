package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.watchers.common.AbilityResolvedWatcher;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GimliMournfulAvenger extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Creatures that died under your control this turn", GimliMournfulAvengerValue.instance
    );

    public GimliMournfulAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Gimli, Mournful Avenger has indestructible as long as two or more creatures died under your control this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                GimliMournfulAvengerCondition.instance, "{this} has indestructible as long as " +
                "two or more creatures died under your control this turn"
        )).addHint(hint));

        // Whenever another creature you control dies, put a +1/+1 counter on Gimli. When this ability resolves for the third time this turn, Gimli fights up to one target creature you don't control.
        Ability ability = new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        );
        ability.addEffect(new GimliMournfulAvengerEffect());
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private GimliMournfulAvenger(final GimliMournfulAvenger card) {
        super(card);
    }

    @Override
    public GimliMournfulAvenger copy() {
        return new GimliMournfulAvenger(this);
    }
}

enum GimliMournfulAvengerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(CreaturesDiedWatcher.class)
                .getAmountOfCreaturesDiedThisTurnByOwner(source.getControllerId()) >= 2;
    }
}

enum GimliMournfulAvengerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getState()
                .getWatcher(CreaturesDiedWatcher.class)
                .getAmountOfCreaturesDiedThisTurnByOwner(sourceAbility.getControllerId());
    }

    @Override
    public GimliMournfulAvengerValue copy() {
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

class GimliMournfulAvengerEffect extends OneShotEffect {

    GimliMournfulAvengerEffect() {
        super(Outcome.Benefit);
        staticText = "when this ability resolves for the third time this turn, " +
                "{this} fights up to one target creature you don't control.";
    }

    private GimliMournfulAvengerEffect(final GimliMournfulAvengerEffect effect) {
        super(effect);
    }

    @Override
    public GimliMournfulAvengerEffect copy() {
        return new GimliMournfulAvengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (AbilityResolvedWatcher.getResolutionCount(game, source) != 3) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new FightTargetSourceEffect(), false);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
