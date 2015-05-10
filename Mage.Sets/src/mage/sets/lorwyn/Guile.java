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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class Guile extends CardImpl {

    public Guile(UUID ownerId) {
        super(ownerId, 69, "Guile", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{U}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Elemental");
        this.subtype.add("Incarnation");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Guile can't be blocked except by three or more creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByOneEffect(3)));
        
        // If a spell or ability you control would counter a spell, instead exile that spell and you may play that card without paying its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GuileReplacementEffect()));
        
        // When Guile is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    public Guile(final Guile card) {
        super(card);
    }

    @Override
    public Guile copy() {
        return new Guile(this);
    }
}

class GuileReplacementEffect extends ReplacementEffectImpl {

    GuileReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a spell or ability you control would counter a spell, instead exile that spell and you may play that card without paying its mana cost";
    }

    GuileReplacementEffect(final GuileReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GuileReplacementEffect copy() {
        return new GuileReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        Player guileController = game.getPlayer(source.getControllerId());
        if (spell != null && guileController != null) {
            Card spellCard = spell.getCard();
            guileController.moveCardToExileWithInfo(spellCard, null, "", source.getSourceId(), game, Zone.STACK, true);
            if (guileController.chooseUse(Outcome.PlayForFree, "Cast that card for free?", game)) {
                guileController.cast(spellCard.getSpellAbility(), game, true);
            }
            return true;
        }
        return false;
    }
    
    @Override    
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COUNTER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell counteredSpell = game.getStack().getSpell(event.getTargetId());
        StackObject counteringObject = game.getStack().getStackObject(event.getSourceId());
        return counteredSpell != null 
                && counteringObject != null
                && counteringObject.getControllerId().equals(source.getControllerId());
    }
}
