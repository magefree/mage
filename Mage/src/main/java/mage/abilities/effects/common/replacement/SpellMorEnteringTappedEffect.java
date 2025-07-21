package mage.abilities.effects.common.replacement;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 * Used to affect a spell on the stack.
 * The permanent it may become enters tapped.
 * <p>
 * Short-lived replacement effect, auto-cleanup if the mor is no longer a spell.
 *
 * @author Susucr
 */
public class SpellMorEnteringTappedEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    public SpellMorEnteringTappedEffect(MageObjectReference mor) {
        super(Duration.OneUse, Outcome.Tap);
        this.staticText = "That permanent enters the battlefield tapped";
        this.mor = mor;
    }

    private SpellMorEnteringTappedEffect(final SpellMorEnteringTappedEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public SpellMorEnteringTappedEffect copy() {
        return new SpellMorEnteringTappedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getSpell(event.getSourceId());
        Spell morSpell = mor.getSpell(game);
        if (morSpell == null) {
            // cleanup if something other than resolving happens to the spell.
            discard();
            return false;
        }
        return spell != null
                && morSpell.getSourceId() == spell.getSourceId()
                && morSpell.getZoneChangeCounter(game) == spell.getZoneChangeCounter(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            permanent.setTapped(true);
        }
        return false;
    }
}
