package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class SpinerockKnoll extends CardImpl {

    public SpinerockKnoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Hideaway
        this.addAbility(new HideawayAbility(4));
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {R}, {tap}: You may play the exiled card without paying its mana cost if an opponent was dealt 7 or more damage this turn.
        Ability ability = new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new HideawayPlayEffect(), SpinerockKnollCondition.instance,
                "you may play the exiled card without paying its mana cost " +
                        "if an opponent was dealt 7 or more damage this turn"
        ), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability, new SpinerockKnollWatcher());
    }

    private SpinerockKnoll(final SpinerockKnoll card) {
        super(card);
    }

    @Override
    public SpinerockKnoll copy() {
        return new SpinerockKnoll(this);
    }
}

enum SpinerockKnollCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpinerockKnollWatcher watcher = game.getState().getWatcher(SpinerockKnollWatcher.class);
        if (watcher == null) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (watcher.getDamageReceived(opponentId) >= 7) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if an opponent was dealt 7 or more damage this turn";
    }
}

class SpinerockKnollWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfDamageReceivedThisTurn = new HashMap<>();

    SpinerockKnollWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != EventType.DAMAGED_PLAYER) {
            return;
        }
        Integer amount = amountOfDamageReceivedThisTurn.compute(
                event.getPlayerId(), (u, i) -> i == null ? event.getAmount() : Integer.sum(event.getAmount(), i)
        );
    }

    public int getDamageReceived(UUID playerId) {
        return amountOfDamageReceivedThisTurn.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        amountOfDamageReceivedThisTurn.clear();
    }
}
