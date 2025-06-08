package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.replacement.CreaturesAreExiledOnDeathReplacementEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.WardAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.VrenRatToken;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class VrenTheRelentless extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Creatures exiled under opponents' control this turn", VrenTheRelentlessCount.instance
    );

    public VrenTheRelentless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT, SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Ward {2}
        this.addAbility(new WardAbility(new GenericManaCost(2), false));

        // If a creature an opponent controls would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(
                new CreaturesAreExiledOnDeathReplacementEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE)
        ));

        // At the beginning of each end step, create X 1/1 black Rat creature tokens with "This creature gets +1/+1 for each
        // other Rat you control," where X is the number of creatures your opponents controlled that were exiled this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new CreateTokenEffect(new VrenRatToken(), VrenTheRelentlessCount.instance), false
        ).addHint(hint), new VrenTheRelentlessWatcher());
    }

    private VrenTheRelentless(final VrenTheRelentless card) {
        super(card);
    }

    @Override
    public VrenTheRelentless copy() {
        return new VrenTheRelentless(this);
    }
}

enum VrenTheRelentlessCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        VrenTheRelentlessWatcher watcher = game.getState().getWatcher(VrenTheRelentlessWatcher.class);
        return watcher == null ? 0 : watcher.getCount(sourceAbility.getControllerId(), game);
    }

    @Override
    public VrenTheRelentlessCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of creatures that were exiled under your opponents' control this turn";
    }
}

class VrenTheRelentlessWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    VrenTheRelentlessWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.EXILED
                && zEvent.getFromZone() == Zone.BATTLEFIELD
                && zEvent.getTarget().isCreature(game)) {
            playerMap.compute(zEvent.getTarget().getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    int getCount(UUID playerId, Game game) {
        return game
                .getOpponents(playerId)
                .stream()
                .mapToInt(uuid -> playerMap.getOrDefault(uuid, 0))
                .sum();
    }
}
