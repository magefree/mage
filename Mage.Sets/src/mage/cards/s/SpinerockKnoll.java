
package mage.cards.s;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.ComparisonType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

/**
 * @author emerald000
 */
public final class SpinerockKnoll extends CardImpl {

    public SpinerockKnoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Hideaway
        this.addAbility(new HideawayAbility());

        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {R}, {tap}: You may play the exiled card without paying its mana cost if an opponent was dealt 7 or more damage this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new HideawayPlayEffect(),
                new ColoredManaCost(ColoredManaSymbol.R),
                new SpinerockKnollCondition());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new SpinerockKnollWatcher());
    }

    public SpinerockKnoll(final SpinerockKnoll card) {
        super(card);
    }

    @Override
    public SpinerockKnoll copy() {
        return new SpinerockKnoll(this);
    }
}

class SpinerockKnollCondition extends IntCompareCondition {

    SpinerockKnollCondition() {
        super(ComparisonType.MORE_THAN, 6);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        int maxDamageReceived = 0;
        SpinerockKnollWatcher watcher = game.getState().getWatcher(SpinerockKnollWatcher.class, source.getSourceId());
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                int damageReceived = watcher.getDamageReceived(opponentId);
                if (damageReceived > maxDamageReceived) {
                    maxDamageReceived = damageReceived;
                }
            }
        }
        return maxDamageReceived;
    }

    @Override
    public String toString() {
        return "if an opponent was dealt 7 or more damage this turn";
    }
}

class SpinerockKnollWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfDamageReceivedThisTurn = new HashMap<>(1);

    SpinerockKnollWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                Integer amount = amountOfDamageReceivedThisTurn.getOrDefault(playerId, 0);
                amount += event.getAmount();
                amountOfDamageReceivedThisTurn.put(playerId, amount);
            }
        }
    }

    public int getDamageReceived(UUID playerId) {
        return amountOfDamageReceivedThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        amountOfDamageReceivedThisTurn.clear();
    }
}
