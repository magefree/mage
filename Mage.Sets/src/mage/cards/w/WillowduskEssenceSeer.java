package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WillowduskEssenceSeer extends CardImpl {

    public WillowduskEssenceSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}, {T}: Choose another target creature. Put a number of +1/+1 counters on it equal to the amount of life you gained this turn or the amount of life you lost this turn, whichever is greater. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance(0), WillowduskEssenceSeerValue.instance
        ).setText("choose another target creature. Put a number of +1/+1 counters on it " +
                "equal to the amount of life you gained this turn or the amount of " +
                "life you lost this turn, whichever is greater"), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        ability.addHint(ControllerGainedLifeCount.getHint());
        ability.addHint(WillowduskEssenceSeerHint.instance);
        this.addAbility(ability, new PlayerGainedLifeWatcher());
    }

    private WillowduskEssenceSeer(final WillowduskEssenceSeer card) {
        super(card);
    }

    @Override
    public WillowduskEssenceSeer copy() {
        return new WillowduskEssenceSeer(this);
    }
}

enum WillowduskEssenceSeerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PlayerLostLifeWatcher watcher1 = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        int lifeLost = watcher1 != null ? watcher1.getLifeLost(sourceAbility.getControllerId()) : 0;
        PlayerGainedLifeWatcher watcher2 = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        int lifeGained = watcher2 != null ? watcher2.getLifeGained(sourceAbility.getControllerId()) : 0;
        return Math.max(lifeLost, lifeGained);
    }

    @Override
    public WillowduskEssenceSeerValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

enum WillowduskEssenceSeerHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        return "Life lost this turn: " + (watcher != null ? watcher.getLifeLost(ability.getControllerId()) : 0);
    }

    @Override
    public WillowduskEssenceSeerHint copy() {
        return instance;
    }
}
