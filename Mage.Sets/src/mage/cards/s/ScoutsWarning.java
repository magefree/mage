
package mage.cards.s;

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
 *
 * @author LevelX2
 */
public final class ScoutsWarning extends CardImpl {

    public ScoutsWarning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // The next creature card you play this turn can be played as though it had flash.
        this.getSpellAbility().addEffect(new ScoutsWarningAsThoughEffect());
        this.getSpellAbility().addWatcher(new ScoutsWarningWatcher());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ScoutsWarning(final ScoutsWarning card) {
        super(card);
    }

    @Override
    public ScoutsWarning copy() {
        return new ScoutsWarning(this);
    }
}

class ScoutsWarningAsThoughEffect extends AsThoughEffectImpl {

    private ScoutsWarningWatcher watcher;
    private int zoneChangeCounter;

    public ScoutsWarningAsThoughEffect() {
        super(AsThoughEffectType.CAST_AS_INSTANT, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next creature card you play this turn can be played as though it had flash";
    }

    public ScoutsWarningAsThoughEffect(final ScoutsWarningAsThoughEffect effect) {
        super(effect);
        this.watcher = effect.watcher;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public void init(Ability source, Game game) {
        watcher = game.getState().getWatcher(ScoutsWarningWatcher.class, source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (watcher != null && card != null) {
            zoneChangeCounter = card.getZoneChangeCounter(game);
            watcher.addScoutsWarningSpell(source.getSourceId(), zoneChangeCounter);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ScoutsWarningAsThoughEffect copy() {
        return new ScoutsWarningAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (watcher.isScoutsWarningSpellActive(source.getSourceId(), zoneChangeCounter)) {
            Card card = game.getCard(sourceId);
            if (card != null && card.isCreature(game) && source.isControlledBy(affectedControllerId)) {
                return true;
            }
        }
        return false;
    }

}

class ScoutsWarningWatcher extends Watcher {

    private List<String> activeScoutsWarningSpells = new ArrayList<>();

    public ScoutsWarningWatcher() {
        super(WatcherScope.PLAYER);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (!getActiveScoutsWarningSpells().isEmpty() && event.getPlayerId().equals(getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && spell.isCreature(game)) {
                    getActiveScoutsWarningSpells().clear();
                }
            }
        }
    }

    public void addScoutsWarningSpell(UUID sourceId, int zoneChangeCounter) {
        String spellKey = sourceId.toString() + '_' + zoneChangeCounter;
        getActiveScoutsWarningSpells().add(spellKey);
    }

    public boolean isScoutsWarningSpellActive(UUID sourceId, int zoneChangeCounter) {
        String spellKey = sourceId.toString() + '_' + zoneChangeCounter;
        return getActiveScoutsWarningSpells().contains(spellKey);
    }

    @Override
    public void reset() {
        super.reset();
        getActiveScoutsWarningSpells().clear();
    }

    public List<String> getActiveScoutsWarningSpells() {
        return activeScoutsWarningSpells;
    }

    public void setActiveScoutsWarningSpells(List<String> activeScoutsWarningSpells) {
        this.activeScoutsWarningSpells = activeScoutsWarningSpells;
    }
}
