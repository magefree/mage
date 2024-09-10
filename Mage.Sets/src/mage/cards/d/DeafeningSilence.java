package mage.cards.d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public final class DeafeningSilence extends CardImpl {

    public DeafeningSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // Each player can't cast more than one noncreature spell each turn.
        this.addAbility(new SimpleStaticAbility(new DeafeningSilenceEffect()), new DeafeningSilenceWatcher());
    }

    private DeafeningSilence(final DeafeningSilence card) {
        super(card);
    }

    @Override
    public DeafeningSilence copy() {
        return new DeafeningSilence(this);
    }
}

class DeafeningSilenceEffect extends ContinuousRuleModifyingEffectImpl {

    DeafeningSilenceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each player can't cast more than one noncreature spell each turn";
    }

    private DeafeningSilenceEffect(final DeafeningSilenceEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "Each player can't cast more than one noncreature spell each turn";
    }

    @Override
    public DeafeningSilenceEffect copy() {
        return new DeafeningSilenceEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card == null
                || card.isCreature(game)) {
            return false;
        }
        DeafeningSilenceWatcher watcher = game.getState().getWatcher(DeafeningSilenceWatcher.class);
        return watcher != null
                && watcher.spellsCastByPlayerThisTurnNonCreature(event.getPlayerId()) > 0;
    }
}

class DeafeningSilenceWatcher extends Watcher {

    private final Map<UUID, Integer> spellsCastByPlayerThisTurnNonCreature = new HashMap<>();

    DeafeningSilenceWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null
                || spell.isCreature(game)) {
            return;
        }
        UUID playerId = event.getPlayerId();
        if (playerId != null) {
            spellsCastByPlayerThisTurnNonCreature.putIfAbsent(playerId, 0);
            spellsCastByPlayerThisTurnNonCreature.compute(playerId, (k, v) -> v + 1);
        }
    }

    @Override
    public void reset() {
        super.reset();
        spellsCastByPlayerThisTurnNonCreature.clear();
    }

    public int spellsCastByPlayerThisTurnNonCreature(UUID playerId) {
        return spellsCastByPlayerThisTurnNonCreature.getOrDefault(playerId, 0);
    }
}
