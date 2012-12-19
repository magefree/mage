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
package mage.sets.returntoravnica;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.cards.CardImpl;
import mage.target.TargetSpell;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class Syncopate extends CardImpl<Syncopate> {

    public Syncopate(UUID ownerId) {
        super(ownerId, 54, "Syncopate", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{X}{U}");
        this.expansionSetCode = "RTR";

        this.color.setBlue(true);

        // Counter target spell unless its controller pays {X}. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new SyncopateCounterUnlessPaysEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public Syncopate(final Syncopate card) {
        super(card);
    }

    @Override
    public Syncopate copy() {
        return new Syncopate(this);
    }
}

class SyncopateCounterUnlessPaysEffect extends OneShotEffect<SyncopateCounterUnlessPaysEffect> {

    public SyncopateCounterUnlessPaysEffect() {
        super(Constants.Outcome.Detriment);
    }

    public SyncopateCounterUnlessPaysEffect(final SyncopateCounterUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public SyncopateCounterUnlessPaysEffect copy() {
        return new SyncopateCounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            Player controller = game.getPlayer(source.getControllerId());
            if (player != null && controller != null) {
                
                int amount = source.getManaCostsToPay().getX();
                if (amount > 0) {
                    GenericManaCost cost = new GenericManaCost(amount);
                    if (!cost.pay(source, game, spell.getControllerId(), spell.getControllerId(), false)) {
                        
                        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
                        if (stackObject != null && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER, source.getFirstTarget(), source.getSourceId(), stackObject.getControllerId()))) {
                            game.informPlayers("Syncopate: cost wasn't payed - countering " + stackObject.getName());
                            if (stackObject instanceof Spell) {
                                game.rememberLKI(source.getFirstTarget(), Constants.Zone.STACK, (Spell) stackObject);
                            }
                            game.getStack().remove(stackObject);
                            MageObject card = game.getObject(stackObject.getSourceId());
                            if (card instanceof Card) {
                                ((Card) card).moveToZone(Constants.Zone.EXILED, source.getSourceId(), game, false);
                            }
                            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERED, source.getFirstTarget(), source.getSourceId(), stackObject.getControllerId()));
                            return true;
                        }
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell unless its controller pays {X}. If that spell is countered this way, exile it instead of putting it into its owner's graveyard";
    }

}