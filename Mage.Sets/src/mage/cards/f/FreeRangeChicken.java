
package mage.cards.f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public final class FreeRangeChicken extends CardImpl {

    public FreeRangeChicken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.CHICKEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{G}: Roll two six-sided dice. If both results are the same, Free-Range Chicken gets +X/+X until end of turn, where X is that result. If the total of those results is equal to any other total you have rolled this turn for Free-Range Chicken, sacrifice it. (For example, if you roll two 3s, Free-Range Chicken gets +3/+3. If you roll a total of 6 for Free-Range Chicken later that turn, sacrifice it.)
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new FreeRangeChickenEffect(), new ManaCostsImpl("{1}{G}")), new FreeRangeChickenWatcher());
    }

    public FreeRangeChicken(final FreeRangeChicken card) {
        super(card);
    }

    @Override
    public FreeRangeChicken copy() {
        return new FreeRangeChicken(this);
    }
}

class FreeRangeChickenEffect extends OneShotEffect {

    public FreeRangeChickenEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Roll two six-sided dice. If both results are the same, Free-Range Chicken gets +X/+X until end of turn, where X is that result. If the total of those results is equal to any other total you have rolled this turn for Free-Range Chicken, sacrifice it";
    }

    public FreeRangeChickenEffect(final FreeRangeChickenEffect effect) {
        super(effect);
    }

    @Override
    public FreeRangeChickenEffect copy() {
        return new FreeRangeChickenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int firstRoll = controller.rollDice(game, 6);
            int secondRoll = controller.rollDice(game, 6);
            if (firstRoll == secondRoll) {
                game.addEffect(new BoostSourceEffect(firstRoll, firstRoll, Duration.EndOfTurn), source);
            }
            FreeRangeChickenWatcher watcher = game.getState().getWatcher(FreeRangeChickenWatcher.class);
            if (watcher != null) {
                int totalRoll = firstRoll + secondRoll;
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null) {
                    if (watcher.rolledThisTurn(sourcePermanent.getId(), totalRoll)) {
                        sourcePermanent.sacrifice(source.getSourceId(), game);
                    } else {
                        watcher.addRoll(sourcePermanent.getId(), totalRoll);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class FreeRangeChickenWatcher extends Watcher {

    private final Map<UUID, Integer> totalRolls = new HashMap<>();

    public FreeRangeChickenWatcher() {
        super(WatcherScope.GAME);
    }

    public FreeRangeChickenWatcher(final FreeRangeChickenWatcher watcher) {
        super(watcher);
        for (Map.Entry<UUID, Integer> entry : watcher.totalRolls.entrySet()) {
            this.totalRolls.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
    }

    @Override
    public void reset() {
        totalRolls.clear();
    }

    @Override
    public FreeRangeChickenWatcher copy() {
        return new FreeRangeChickenWatcher(this);
    }

    public void addRoll(UUID sourceId, int roll) {
        totalRolls.put(sourceId, roll);
    }

    public boolean rolledThisTurn(UUID sourceId, int roll) {
        if (totalRolls.get(sourceId) != null) {
            return totalRolls.get(sourceId) == roll;
        }
        return false;
    }
}
