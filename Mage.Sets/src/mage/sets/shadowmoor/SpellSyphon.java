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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public class SpellSyphon extends CardImpl {

    public SpellSyphon(UUID ownerId) {
        super(ownerId, 52, "Spell Syphon", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "SHM";


        // Counter target spell unless its controller pays {1} for each blue permanent you control.
        this.getSpellAbility().addEffect(new SpellSyphonEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        
    }

    public SpellSyphon(final SpellSyphon card) {
        super(card);
    }

    @Override
    public SpellSyphon copy() {
        return new SpellSyphon(this);
    }
}

class SpellSyphonEffect extends OneShotEffect {
    
    private static final FilterPermanent filter = new FilterPermanent("blue permanent you control");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public SpellSyphonEffect() {
        super(Outcome.Detriment);
    }

    public SpellSyphonEffect(final SpellSyphonEffect effect) {
        super(effect);
    }

    @Override
    public SpellSyphonEffect copy() {
        return new SpellSyphonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            Player controller = game.getPlayer(source.getControllerId());
            if (player != null && controller != null) {
                int amount = game.getBattlefield().count(filter, source.getSourceId(), controller.getId(), game);
                if (amount == 0) {
                    game.informPlayers("Spell Syphon: no blue permanents in controller's battlefield.");
                } else {
                    GenericManaCost cost = new GenericManaCost(amount);
                    if (!cost.pay(source, game, spell.getControllerId(), spell.getControllerId(), false)) {
                        game.informPlayers("Spell Syphon: cost wasn't payed - countering target spell.");
                        return game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell unless its controller pays {1} for each blue permanent you control";
    }

}
