
package mage.cards.m;

import java.util.UUID;
import mage.MageObject;
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
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public final class ManaMaze extends CardImpl {

    public ManaMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Players can't cast spells that share a color with the spell most recently cast this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ManaMazeEffect()), new LastSpellCastWatcher());
    }

    private ManaMaze(final ManaMaze card) {
        super(card);
    }

    @Override
    public ManaMaze copy() {
        return new ManaMaze(this);
    }
}

class ManaMazeEffect extends ContinuousRuleModifyingEffectImpl {

    ManaMazeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't cast spells that share a color with the spell most recently cast this turn";
    }

    ManaMazeEffect(final ManaMazeEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null) {
            LastSpellCastWatcher watcher = game.getState().getWatcher(LastSpellCastWatcher.class);
            if (watcher != null && watcher.getLastSpellCast() != null) {
                return !card.getColor(game).intersection(watcher.getLastSpellCast().getColor(game)).isColorless();
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ManaMazeEffect copy() {
        return new ManaMazeEffect(this);
    }
}

class LastSpellCastWatcher extends Watcher {

    private Spell lastSpellCast = null;

    public LastSpellCastWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell == null) {
                MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.STACK);
                if (mageObject instanceof Spell) {
                    spell = (Spell) mageObject;
                }
            }
            if (spell != null) {
                lastSpellCast = spell;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        lastSpellCast = null;
    }

    public Spell getLastSpellCast() {
        return lastSpellCast;
    }
}
