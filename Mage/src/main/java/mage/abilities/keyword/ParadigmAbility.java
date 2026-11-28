package mage.abilities.keyword;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public class ParadigmAbility extends SimpleStaticAbility {

    public ParadigmAbility() {
        super(Zone.STACK, new ParadigmCastFromHandReplacementEffect());
        this.addWatcher(new ParadigmWatcher());
    }

    protected ParadigmAbility(final ParadigmAbility ability) {
        super(ability);
    }

    @Override
    public ParadigmAbility copy() {
        return new ParadigmAbility(this);
    }

    @Override
    public String getRule() {
        return "Paradigm <i> (Then exile this spell. After you first resolve a spell with this name, " +
                "you may cast a copy of it from exile without paying its mana cost " +
                "at the beginning of each of your first main phases.)</i>";
    }
}

class ParadigmCastFromHandReplacementEffect extends ReplacementEffectImpl {

    ParadigmCastFromHandReplacementEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit);
    }

    private ParadigmCastFromHandReplacementEffect(final ParadigmCastFromHandReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return ((ZoneChangeEvent) event).getFromZone() == Zone.STACK
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && event.getSourceId() != null
                && event.getSourceId().equals(source.getSourceId())
                && game.getStack().getSpell(event.getTargetId()) != null;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell sourceSpell = game.getStack().getSpell(source.getSourceId());
        if (sourceSpell == null) {
            return false;
        }
        Card sourceCard = sourceSpell.getMainCard();
        if (sourceCard == null) {
            return false;
        }
        Player player = game.getPlayer(sourceCard.getOwnerId());
        if (player == null) {
            return false;
        }
        player.moveCardsToExile(sourceCard, source, game, true, null, "Paradigm");
        if (!ParadigmWatcher.checkSpellName(sourceSpell, source, game)) {
            game.addDelayedTriggeredAbility(new ParadigmDelayedTriggeredAbility(sourceCard), source);
        }
        return true;
    }

    @Override
    public ParadigmCastFromHandReplacementEffect copy() {
        return new ParadigmCastFromHandReplacementEffect(this);
    }

}

class ParadigmDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ParadigmDelayedTriggeredAbility(Card card) {
        super(new ParadigmCastEffect(card), Duration.EndOfGame, false, true);
    }

    private ParadigmDelayedTriggeredAbility(final ParadigmDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ParadigmDelayedTriggeredAbility copy() {
        return new ParadigmDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(getControllerId());
    }

    @Override
    public String getRule() {
        return "At the beginning of your first main phase, you may cast a copy of this spell without paying its mana cost.";
    }
}

class ParadigmCastEffect extends OneShotEffect {

    private final Card card;

    ParadigmCastEffect(Card card) {
        super(Outcome.Benefit);
        this.card = card.copy();
    }

    private ParadigmCastEffect(final ParadigmCastEffect effect) {
        super(effect);
        this.card = effect.card.copy();
    }

    @Override
    public ParadigmCastEffect copy() {
        return new ParadigmCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card copyCard = game.copyCard(card, source, player.getId());
        SpellAbility ability = copyCard.getSpellAbility();
        return player.cast(ability, game, true, new ApprovingObject(source, game));
    }
}

class ParadigmWatcher extends Watcher {

    private final Map<UUID, Set<String>> spellNameMap = new HashMap<>();

    ParadigmWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case SPELL_RESOLVED:
                Spell spell = game.getSpell(event.getTargetId());
                if (spell != null) {
                    spellNameMap
                            .computeIfAbsent(event.getPlayerId(), x -> new HashSet<>())
                            .add(spell.getName());
                }
                return;
            case BEGINNING_PHASE_PRE:
                if (game.getTurnNum() == 1) {
                    spellNameMap.clear();
                }
        }
    }

    static boolean checkSpellName(Spell spell, Ability source, Game game) {
        return game
                .getState()
                .getWatcher(ParadigmWatcher.class)
                .spellNameMap
                .getOrDefault(source.getControllerId(), Collections.emptySet())
                .contains(spell.getName());
    }
}
