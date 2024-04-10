package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author Susucr
 */
public final class SlickSequence extends CardImpl {

    public SlickSequence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}");

        // Slick Sequence deals 2 damage to any target. If you've cast another spell this turn, draw a card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(
                new ConditionalOneShotEffect(
                        new DrawCardSourceControllerEffect(1),
                        SlickSequenceCondition.instance
                )
        );
        this.getSpellAbility().addHint(SlickSequenceCondition.hint);
        this.getSpellAbility().addWatcher(new SlickSequenceWatcher());
    }

    private SlickSequence(final SlickSequence card) {
        super(card);
    }

    @Override
    public SlickSequence copy() {
        return new SlickSequence(this);
    }
}


enum SlickSequenceCondition implements Condition {
    instance;
    static final Hint hint = new ConditionHint(instance, "you've cast another spell this turn");

    @Override
    public boolean apply(Game game, Ability source) {
        SlickSequenceWatcher watcher = game.getState().getWatcher(SlickSequenceWatcher.class);
        if (watcher == null) {
            return false;
        }
        // may be null, watcher will handle that.
        Spell sourceSpell = game.getSpell(source.getSourceId());
        return watcher.didPlayerCastAnotherSpellThisTurn(source.getControllerId(), sourceSpell, game);
    }

    @Override
    public String toString() {
        return "you've cast another spell this turn";
    }
}

class SlickSequenceWatcher extends Watcher {

    // Per player, MOR of the spells cast this turn.
    private final Map<UUID, Set<MageObjectReference>> spellsCastThisTurn = new HashMap<>();

    /**
     * Game default watcher
     */
    public SlickSequenceWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            UUID playerId = event.getPlayerId();
            if (playerId != null) {
                MageObjectReference mor = new MageObjectReference(event.getTargetId(), game);
                spellsCastThisTurn.computeIfAbsent(playerId, x -> new HashSet<>()).add(mor);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        spellsCastThisTurn.clear();
    }

    boolean didPlayerCastAnotherSpellThisTurn(UUID playerId, Spell spell, Game game) {
        Set<MageObjectReference> spells = spellsCastThisTurn.getOrDefault(playerId, new HashSet<>());
        if (spell == null) {
            // Not currently a spell, so any spell recorded does count as another.
            return !spells.isEmpty();
        } else {
            // Is currently a spell, so need to exclude that one.
            MageObjectReference spellMOR = new MageObjectReference(spell.getId(), game);
            return spells.stream().anyMatch(mor -> !mor.equals(spellMOR));
        }
    }
}
