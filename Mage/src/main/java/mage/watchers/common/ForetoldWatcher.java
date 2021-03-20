package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class ForetoldWatcher extends Watcher {

    // If foretell was activated or a card was Foretold by the controller this turn, this list stores it.  Cleared at the end of the turn.
    private final Set<UUID> foretellCardsThisTurn = new HashSet<>();
    private final Set<UUID> foretoldCardsThisTurn = new HashSet<>();

    public ForetoldWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAKEN_SPECIAL_ACTION) {
            Card card = game.getCard(event.getSourceId());
            if (card != null
                    && card.getAbilities(game).containsClass(ForetellAbility.class)
                    && controllerId == event.getPlayerId()) {
                foretellCardsThisTurn.add(card.getId());
            }
        }
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.getZone() == Zone.EXILED) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null
                    && controllerId == event.getPlayerId()) {
                UUID exileId = CardUtil.getExileZoneId(spell.getSourceId().toString() + "foretellAbility", game);
                if (exileId != null) {
                    foretoldCardsThisTurn.add(spell.getSourceId());
                }
            }
        }
    }

    public boolean cardUsedForetell(UUID sourceId) {
        return foretellCardsThisTurn.contains(sourceId);
    }

    public boolean cardWasForetold(UUID sourceId) {
        return foretoldCardsThisTurn.contains(sourceId);
    }

    public int countNumberForetellThisTurn() {
        return foretellCardsThisTurn.size();
    }

    public int countNumberForetoldThisTurn() {
        return foretoldCardsThisTurn.size();
    }

    @Override
    public void reset() {
        super.reset();
        foretellCardsThisTurn.clear();
        foretoldCardsThisTurn.clear();
    }
}
