package mage.cards.d;

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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
        staticText = "each player can't cast more than one noncreature spell each turn";
    }

    private DeafeningSilenceEffect(final DeafeningSilenceEffect effect) {
        super(effect);
    }

    @Override
    public DeafeningSilenceEffect copy() {
        return new DeafeningSilenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getTargetId());
        if (card == null || card.isCreature()) {
            return false;
        }
        DeafeningSilenceWatcher watcher = game.getState().getWatcher(DeafeningSilenceWatcher.class);
        return watcher != null && watcher.castSpell(event.getPlayerId());
    }
}

class DeafeningSilenceWatcher extends Watcher {

    private final Set<UUID> castSpell = new HashSet<>();

    DeafeningSilenceWatcher() {
        super(WatcherScope.GAME);
    }

    private DeafeningSilenceWatcher(final DeafeningSilenceWatcher watcher) {
        super(watcher);
        this.castSpell.addAll(watcher.castSpell);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || spell.isCreature()) {
            return;
        }
        castSpell.add(event.getPlayerId());
    }

    @Override
    public void reset() {
        super.reset();
        castSpell.clear();
    }

    boolean castSpell(UUID playerId) {
        return castSpell.contains(playerId);
    }
}
