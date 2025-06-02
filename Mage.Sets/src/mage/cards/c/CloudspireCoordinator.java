package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PilotSaddleCrewToken;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloudspireCoordinator extends CardImpl {

    public CloudspireCoordinator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When this creature enters, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // {T}: Create X 1/1 colorless Pilot creature tokens, where X is the number of Mounts and/or Vehicles that entered the battlefield under your control this turn. The tokens have "This token saddles Mounts and crews Vehicles as though its power were 2 greater."
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(
                new PilotSaddleCrewToken(), CloudspireCoordinatorValue.instance
        ).setText("create X 1/1 colorless Pilot creature tokens, where X is " +
                "the number of Mounts and/or Vehicles that entered the battlefield " +
                "under your control this turn. The tokens have \"This token saddles Mounts " +
                "and crews Vehicles as though its power were 2 greater.\""),
                new TapSourceCost()
        ).addHint(CloudspireCoordinatorValue.getHint()), new CloudspireCoordinatorWatcher());
    }

    private CloudspireCoordinator(final CloudspireCoordinator card) {
        super(card);
    }

    @Override
    public CloudspireCoordinator copy() {
        return new CloudspireCoordinator(this);
    }
}

enum CloudspireCoordinatorValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint(
            "Mounts and/or Vehicles that entered under your control this turn", instance
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CloudspireCoordinatorWatcher.getValue(sourceAbility, game);
    }

    @Override
    public CloudspireCoordinatorValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class CloudspireCoordinatorWatcher extends Watcher {

    private final Map<UUID, Integer> map = new HashMap<>();

    CloudspireCoordinatorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null
                && (permanent.hasSubtype(SubType.MOUNT, game)
                || permanent.hasSubtype(SubType.VEHICLE, game))) {
            map.compute(permanent.getControllerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static int getValue(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(CloudspireCoordinatorWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), 0);
    }
}
