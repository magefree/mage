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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.PhaseStep;
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
 * @author North
 */
public class Hinder extends CardImpl {

    public Hinder(UUID ownerId) {
        super(ownerId, 65, "Hinder", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.expansionSetCode = "CHK";

        // Counter target spell. If that spell is countered this way, put that card on the top or bottom of its owner's library instead of into that player's graveyard.
        this.getSpellAbility().addEffect(new HinderEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Hinder(final Hinder card) {
        super(card);
    }

    @Override
    public Hinder copy() {
        return new Hinder(this);
    }
}

class HinderEffect extends OneShotEffect {

    public HinderEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. If that spell is countered this way, put that card on the top or bottom of its owner's library instead of into that player's graveyard";
    }

    public HinderEffect(final HinderEffect effect) {
        super(effect);
    }

    @Override
    public HinderEffect copy() {
        return new HinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID objectId = source.getFirstTarget();
        UUID sourceId = source.getSourceId();
        // counter code from Spellstack
        StackObject stackObject = game.getStack().getStackObject(objectId);
        MageObject sourceObject = game.getObject(sourceId);
        if (stackObject != null && sourceObject != null) {
            if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER, objectId, sourceId, stackObject.getControllerId()))) {
                if ( stackObject instanceof Spell ) {
                    game.rememberLKI(objectId, Zone.STACK, (Spell)stackObject);
                }                
                // Hinder specific code
                ReplacementEffectImpl effect = new HinderReplacementEffect();
                effect.setTargetPointer(new FixedTarget(stackObject.getId()));
                game.addEffect(effect, source);
                // Hinder specific code end
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
        // counter code from Spellstack end
    }
}

class HinderReplacementEffect extends ReplacementEffectImpl {

    private PhaseStep phaseStep;
    
    public HinderReplacementEffect() {
        super(Duration.OneUse, Outcome.Benefit);
        staticText = "If that spell is countered this way, put that card on the top or bottom of its owner's library instead of into that player's graveyard";
        phaseStep = null;
    }

    public HinderReplacementEffect(final HinderReplacementEffect effect) {
        super(effect);
        phaseStep = effect.phaseStep;
    }

    @Override
    public HinderReplacementEffect copy() {
        return new HinderReplacementEffect(this);
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
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {                
                boolean top = player.chooseUse(Outcome.Neutral, "Put " + card.getName() + " on top of the library? Otherwise it will be put on the bottom.", source, game);
                if (card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, top, event.getAppliedEffects())) {
                    game.informPlayers(player.getLogName() + " has put " + card.getName() + " on " + (top ? "top" : "the bottom") + " of the library.");
                }
                return true;
            }
        }        
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent)event).getToZone().equals(Zone.GRAVEYARD)) {
            MageObject mageObject = game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.STACK);
            if (mageObject instanceof Spell) {
                return ((Spell)mageObject).getSourceId().equals(event.getTargetId());
            }
        }
        return false;
    }

}
