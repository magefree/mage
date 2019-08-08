package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.EldraziToken;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IdolOfOblivion extends CardImpl {

    public IdolOfOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Draw a card. Activate this ability only if you created a token this turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1),
                new TapSourceCost(), IdolOfOblivionCondition.instance
        ));

        // {8}, {T}, Sacrifice Idol of Oblivion: Create 10/10 colorless Eldrazi creature token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new EldraziToken()), new GenericManaCost(8)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability, new IdolOfOblivionWatcher());
    }

    private IdolOfOblivion(final IdolOfOblivion card) {
        super(card);
    }

    @Override
    public IdolOfOblivion copy() {
        return new IdolOfOblivion(this);
    }
}

enum IdolOfOblivionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        IdolOfOblivionWatcher watcher = game.getState().getWatcher(IdolOfOblivionWatcher.class);
        return watcher != null && watcher.tokenEntered(source.getControllerId());
    }

    @Override
    public String toString() {
        return "if you created a token this turn";
    }
}

class IdolOfOblivionWatcher extends Watcher {

    private final Set<UUID> playerIds = new HashSet<>();

    IdolOfOblivionWatcher() {
        super(WatcherScope.GAME);
    }

    private IdolOfOblivionWatcher(final IdolOfOblivionWatcher watcher) {
        super(watcher);
        playerIds.addAll(watcher.playerIds);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent instanceof PermanentToken) {
            playerIds.add(permanent.getControllerId());
        }
    }

    @Override
    public void reset() {
        playerIds.clear();
    }

    @Override
    public IdolOfOblivionWatcher copy() {
        return new IdolOfOblivionWatcher(this);
    }

    boolean tokenEntered(UUID playerId) {
        return playerIds.contains(playerId);
    }
}
