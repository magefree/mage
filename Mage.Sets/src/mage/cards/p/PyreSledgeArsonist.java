package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PyreSledgeArsonist extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Permanents you've sacrificed this turn", PyreSledgeArsonistValue.instance
    );

    public PyreSledgeArsonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}, {T}: Pyre-Sledge Arsonist deals X damage to any target, where X is the number of permanents you sacrificed this turn.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(PyreSledgeArsonistValue.instance), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.addHint(hint));
    }

    private PyreSledgeArsonist(final PyreSledgeArsonist card) {
        super(card);
    }

    @Override
    public PyreSledgeArsonist copy() {
        return new PyreSledgeArsonist(this);
    }
}

enum PyreSledgeArsonistValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return PyreSledgeArsonistWatcher.getAmount(sourceAbility.getControllerId(), game);
    }

    @Override
    public PyreSledgeArsonistValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of permanents you've sacrificed this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class PyreSledgeArsonistWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    PyreSledgeArsonistWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT) {
            playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    static int getAmount(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(PyreSledgeArsonistWatcher.class)
                .playerMap
                .getOrDefault(playerId, 0);
    }
}
