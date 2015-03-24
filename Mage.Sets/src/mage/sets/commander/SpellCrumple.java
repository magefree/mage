/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.commander;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.postresolve.ReturnToLibrarySpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SpellCrumple extends CardImpl {

    public SpellCrumple(UUID ownerId) {
        super(ownerId, 63, "Spell Crumple", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.expansionSetCode = "CMD";

        this.color.setBlue(true);

        // Counter target spell. If that spell is countered this way, put it on the bottom of its owner's library instead of into that player's graveyard. Put Spell Crumple on the bottom of its owner's library.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new SpellCrumpleCounterEffect());    
        this.getSpellAbility().addEffect(new ReturnToLibrarySpellEffect(false));
    }

    public SpellCrumple(final SpellCrumple card) {
        super(card);
    }

    @Override
    public SpellCrumple copy() {
        return new SpellCrumple(this);
    }
}

class SpellCrumpleCounterEffect extends OneShotEffect {

    public SpellCrumpleCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. If that spell is countered this way, put it on the bottom of its owner's library instead of into that player's graveyard";
    }

    public SpellCrumpleCounterEffect(final SpellCrumpleCounterEffect effect) {
        super(effect);
    }

    @Override
    public SpellCrumpleCounterEffect copy() {
        return new SpellCrumpleCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID objectId = source.getFirstTarget();
        UUID sourceId = source.getSourceId();
        // counter code from SpellStack
        StackObject stackObject = game.getStack().getStackObject(objectId);
        MageObject sourceObject = game.getObject(sourceId);
        if (stackObject != null && sourceObject != null) {
            if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER, objectId, sourceId, stackObject.getControllerId()))) {
                if ( stackObject instanceof Spell ) {
                    game.rememberLKI(objectId, Zone.STACK, (Spell)stackObject);
                }                
                // Spell Crumple specific code
                ReplacementEffectImpl effect = new SpellCrumpleReplacementEffect();
                effect.setTargetPointer(new FixedTarget(stackObject.getId()));
                game.addEffect(effect, source);
                // Spell Crumple specific code end
                game.informPlayers(new StringBuilder(stackObject.getName()).append(" is countered by ").append(sourceObject.getLogName()).toString());
                game.getStack().remove(stackObject);
                stackObject.counter(sourceId, game); // tries to move to graveyard                
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERED, objectId, sourceId, stackObject.getControllerId()));
            } else {
                game.informPlayers(new StringBuilder(stackObject.getName()).append(" could not be countered by ").append(sourceObject.getLogName()).toString());
            }
            return true;
        }
        return false;        
        // counter code from SpellStack end
    }
}

class SpellCrumpleReplacementEffect extends ReplacementEffectImpl {

    private PhaseStep phaseStep;
    
    public SpellCrumpleReplacementEffect() {
        super(Duration.OneUse, Outcome.Benefit);
        staticText = "If that spell is countered this way, put it on the bottom of its owner's library instead of into that player's graveyard";
        phaseStep = null;
    }

    public SpellCrumpleReplacementEffect(final SpellCrumpleReplacementEffect effect) {
        super(effect);
        phaseStep = effect.phaseStep;
    }

    @Override
    public SpellCrumpleReplacementEffect copy() {
        return new SpellCrumpleReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (!game.getPhase().getStep().getType().equals(phaseStep)) {
            return true;
        }
        return super.isInactive(source, game);         
    }

    @Override
    public void init(Ability source, Game game) {
        phaseStep = game.getPhase().getStep().getType();
        super.init(source, game); 
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject targetObject = game.getObject(event.getTargetId());
        if (targetObject instanceof Card) {
            Card card = (Card) targetObject;
            if (card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false, event.getAppliedEffects())) {            
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    game.informPlayers(controller.getName() + " has put " + card.getName() + " on the bottom of the library.");
                }
                return true;
            }
        }        
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).getToZone().equals(Zone.GRAVEYARD)) {
            MageObject mageObject = game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.STACK);
            if (mageObject instanceof Spell) {
                return ((Spell)mageObject).getSourceId().equals(event.getTargetId());
            }
        }
        return false;
    }

}
