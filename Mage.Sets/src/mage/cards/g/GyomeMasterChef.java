package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GyomeMasterChef extends CardImpl {

    private static final Hint hint = new ValueHint("Nontoken creatures entered this turn", GyomeMasterChefValue.instance);

    public GyomeMasterChef(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, create a number of Food tokens equal to the number of nontoken creatures you had enter the battlefield under your control this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(
                        new FoodToken(), GyomeMasterChefValue.instance
                ).setText("create a number of Food tokens equal to the number of nontoken creatures " +
                        "you had enter the battlefield under your control this turn"),
                TargetController.YOU, false
        ).addHint(hint), new GyomeMasterChefWatcher());

        // {1}, Sacrifice a Food: Target creature gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_FOOD)));
        ability.addEffect(new TapTargetEffect("tap it"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GyomeMasterChef(final GyomeMasterChef card) {
        super(card);
    }

    @Override
    public GyomeMasterChef copy() {
        return new GyomeMasterChef(this);
    }
}

enum GyomeMasterChefValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return GyomeMasterChefWatcher.getValue(sourceAbility.getControllerId(), game);
    }

    @Override
    public GyomeMasterChefValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class GyomeMasterChefWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    GyomeMasterChefWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent == null || permanent instanceof PermanentToken || !permanent.isCreature(game)) {
            return;
        }
        playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    static int getValue(UUID playerId, Game game) {
        GyomeMasterChefWatcher watcher = game.getState().getWatcher(GyomeMasterChefWatcher.class);
        if (watcher == null) {
            return 0;
        }
        return watcher != null ? watcher.playerMap.getOrDefault(playerId, 0) : 0;
    }
}
