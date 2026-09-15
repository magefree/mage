package mage.cards.c;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetPlayer;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author nandmp
 */
public final class CruelCalculations extends CardImpl {

    public CruelCalculations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Draw X cards, where X is the number of cards that were put into target player's graveyard from their library this turn.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(CruelCalculationsValue.instance));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addWatcher(new CruelCalculationsWatcher());
    }

    private CruelCalculations(final CruelCalculations card) {
        super(card);
    }

    @Override
    public CruelCalculations copy() {
        return new CruelCalculations(this);
    }
}

enum CruelCalculationsValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getState().getWatcher(CruelCalculationsWatcher.class)
                .getCount(sourceAbility.getFirstTarget());
    }

    @Override
    public CruelCalculationsValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of cards that were put into target player's graveyard from their library this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class CruelCalculationsWatcher extends Watcher {

    private final Map<UUID, Integer> counts = new HashMap<>();

    CruelCalculationsWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.LIBRARY && zEvent.getToZone() == Zone.GRAVEYARD) {
            counts.compute(zEvent.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    int getCount(UUID playerId) {
        return counts.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        counts.clear();
    }
}
