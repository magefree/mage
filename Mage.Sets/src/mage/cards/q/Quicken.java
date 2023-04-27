
package mage.cards.q;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 * @author LevelX2
 */
public final class Quicken extends CardImpl {

    public Quicken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // The next sorcery card you cast this turn can be cast as though it had flash.
        this.getSpellAbility().addEffect(new QuickenAsThoughEffect());
        this.getSpellAbility().addWatcher(new QuickenWatcher());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Quicken(final Quicken card) {
        super(card);
    }

    @Override
    public Quicken copy() {
        return new Quicken(this);
    }
}

class QuickenAsThoughEffect extends AsThoughEffectImpl {

    private QuickenWatcher quickenWatcher;
    private int zoneChangeCounter;

    public QuickenAsThoughEffect() {
        super(AsThoughEffectType.CAST_AS_INSTANT, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next sorcery spell you cast this turn can be cast as though it had flash";
    }

    public QuickenAsThoughEffect(final QuickenAsThoughEffect effect) {
        super(effect);
        this.quickenWatcher = effect.quickenWatcher;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public void init(Ability source, Game game) {
        quickenWatcher = game.getState().getWatcher(QuickenWatcher.class);
        Card card = game.getCard(source.getSourceId());
        if (quickenWatcher != null && card != null) {
            zoneChangeCounter = card.getZoneChangeCounter(game);
            quickenWatcher.addQuickenSpell(source.getControllerId(), source.getSourceId(), zoneChangeCounter);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public QuickenAsThoughEffect copy() {
        return new QuickenAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (quickenWatcher.isQuickenSpellActive(affectedControllerId, source.getSourceId(), zoneChangeCounter)) {
            Card card = game.getCard(sourceId);
            if (card != null && card.isSorcery(game) && source.isControlledBy(affectedControllerId)) {
                return true;
            }
        }
        return false;
    }

}

class QuickenWatcher extends Watcher {

    private List<String> activeQuickenSpells = new ArrayList<>();

    public QuickenWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (!activeQuickenSpells.isEmpty() && event.getPlayerId().equals(getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && spell.isSorcery(game)) {
                    activeQuickenSpells.clear();
                }
            }
        }
    }

    public void addQuickenSpell(UUID playerId, UUID sourceId, int zoneChangeCounter) {
        String spellKey = playerId.toString() + sourceId.toString() + '_' + zoneChangeCounter;
        activeQuickenSpells.add(spellKey);
    }

    public boolean isQuickenSpellActive(UUID playerId, UUID sourceId, int zoneChangeCounter) {
        String spellKey = playerId.toString() + sourceId.toString() + '_' + zoneChangeCounter;
        return activeQuickenSpells.contains(spellKey);
    }

    @Override
    public void reset() {
        super.reset();
        activeQuickenSpells.clear();
    }

}
