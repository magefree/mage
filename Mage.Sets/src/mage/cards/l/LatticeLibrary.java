package mage.cards.l;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FractalToken;
import mage.game.stack.Spell;
import mage.watchers.common.FirstXSpellCastThisTurnWatcher;

/**
 *
 * @author muz
 */
public final class LatticeLibrary extends CardImpl {

    public LatticeLibrary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{G}{G}");

        // This enchantment enters with X study counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.STUDY.createInstance())));

        // When this enchantment enters and whenever you cast your first spell with {X} in its mana cost each turn,
        // create a 0/0 green and blue Fractal creature token. Put a number of +1/+1 counters on it equal to the
        // number of study counters on this enchantment.
        this.addAbility(new LatticeLibraryTriggeredAbility(), new FirstXSpellCastThisTurnWatcher());
    }

    private LatticeLibrary(final LatticeLibrary card) {
        super(card);
    }

    @Override
    public LatticeLibrary copy() {
        return new LatticeLibrary(this);
    }
}

class LatticeLibraryTriggeredAbility extends TriggeredAbilityImpl {

    LatticeLibraryTriggeredAbility() {
        super(
            Zone.BATTLEFIELD,
            FractalToken.getEffect(
                new CountersSourceCount(CounterType.STUDY),
                ". Put a number of +1/+1 counters on it equal to the number of study counters on {this}"
            ),
            false
        );
        setTriggerPhrase("When {this} enters and whenever you cast your first spell with {X} in its mana cost each turn, ");
    }

    private LatticeLibraryTriggeredAbility(final LatticeLibraryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return event.getTargetId().equals(getSourceId());
        }

        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        // Watcher fires before triggers; it records first X spell via putIfAbsent.
        // Matching the current spell's ID ensures we only trigger on the first one.
        FirstXSpellCastThisTurnWatcher watcher = game.getState().getWatcher(FirstXSpellCastThisTurnWatcher.class);
        return watcher != null && spell.getId().equals(watcher.getFirstXSpellId(getControllerId()));
    }

    @Override
    public LatticeLibraryTriggeredAbility copy() {
        return new LatticeLibraryTriggeredAbility(this);
    }
}
