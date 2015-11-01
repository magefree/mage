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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class SpellShrivel extends CardImpl {

    public SpellShrivel(UUID ownerId) {
        super(ownerId, 66, "Spell Shrivel", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "BFZ";

        // Devoid
        Ability ability = new DevoidAbility(this.color);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Counter target spell unless its controller pays {4}. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new SpellShrivelCounterUnlessPaysEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public SpellShrivel(final SpellShrivel card) {
        super(card);
    }

    @Override
    public SpellShrivel copy() {
        return new SpellShrivel(this);
    }
}

class SpellShrivelCounterUnlessPaysEffect extends OneShotEffect {

    public SpellShrivelCounterUnlessPaysEffect() {
        super(Outcome.Detriment);
    }

    public SpellShrivelCounterUnlessPaysEffect(final SpellShrivelCounterUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public SpellShrivelCounterUnlessPaysEffect copy() {
        return new SpellShrivelCounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (spell != null && (spell instanceof Spell) && sourceObject != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int amount = 4;
                if (amount > 0) {
                    GenericManaCost cost = new GenericManaCost(amount);
                    if (!cost.pay(source, game, spell.getControllerId(), spell.getControllerId(), false)) {
                        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
                        if (stackObject != null && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER, source.getFirstTarget(), source.getSourceId(), stackObject.getControllerId()))) {
                            game.informPlayers(sourceObject.getIdName() + ": cost wasn't payed - countering " + stackObject.getName());
                            game.rememberLKI(source.getFirstTarget(), Zone.STACK, (Spell) stackObject);
                            controller.moveCards((Spell) spell, null, Zone.EXILED, source, game);
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
        return "Counter target spell unless its controller pays {4}. If that spell is countered this way, exile it instead of putting it into its owner's graveyard";
    }

}
