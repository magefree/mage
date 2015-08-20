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
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public class BrokenAmbitions extends CardImpl {

    public BrokenAmbitions(UUID ownerId) {
        super(ownerId, 54, "Broken Ambitions", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{X}{U}");
        this.expansionSetCode = "LRW";

        // Counter target spell unless its controller pays {X}. Clash with an opponent. If you win, that spell's controller puts the top four cards of his or her library into his or her graveyard.
        this.getSpellAbility().addEffect(new BrokenAmbitionsEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public BrokenAmbitions(final BrokenAmbitions card) {
        super(card);
    }

    @Override
    public BrokenAmbitions copy() {
        return new BrokenAmbitions(this);
    }
}

class BrokenAmbitionsEffect extends OneShotEffect {
    
    protected Cost cost;
    protected DynamicValue genericMana;

    public BrokenAmbitionsEffect(Cost cost) {
        super(Outcome.Benefit);
        this.cost = cost;
        this.staticText = "Counter target spell unless its controller pays {X}. Clash with an opponent. If you win, that spell's controller puts the top four cards of his or her library into his or her graveyard";
    }
    
    public BrokenAmbitionsEffect(DynamicValue genericMana) {
        super(Outcome.Detriment);
        this.genericMana = genericMana;
    }

    public BrokenAmbitionsEffect(final BrokenAmbitionsEffect effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        }
        if (effect.genericMana != null) {
            this.genericMana = effect.genericMana.copy();
        }
    }

    @Override
    public BrokenAmbitionsEffect copy() {
        return new BrokenAmbitionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(spell.getControllerId());
        if (controller != null) {
            Cost costToPay;
            if (cost != null) {
                    costToPay = cost.copy();
                } else  {
                    costToPay = new GenericManaCost(genericMana.calculate(game, source, this));
                }
                String message;
                if (costToPay instanceof ManaCost) {
                    message = "Would you like to pay " + costToPay.getText() + " to prevent counter effect?";
                } else {
                    message = costToPay.getText() + " to prevent counter effect?";
                }
                costToPay.clearPaid();
                if (!(controller.chooseUse(Outcome.Benefit, message, source, game) && costToPay.pay(source, game, spell.getSourceId(), spell.getControllerId(), false))) {
                    game.getStack().counter(spell.getId(), source.getSourceId(), game);
                }
                if (ClashEffect.getInstance().apply(game, source)) {
                    controller.moveCards(controller.getLibrary().getTopCards(game, 4), Zone.LIBRARY, Zone.GRAVEYARD, source, game);
                }
            return true;
        }
        return false;
    }
}