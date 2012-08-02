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
package mage.sets.torment;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public class CircularLogic extends CardImpl<CircularLogic> {

    public CircularLogic(UUID ownerId) {
        super(ownerId, 33, "Circular Logic", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "TOR";

        this.color.setBlue(true);

        // Counter target spell unless its controller pays {1} for each card in your graveyard.
        this.getSpellAbility().addEffect(new CircularLogicCounterUnlessPaysEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // Madness {U}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{U}")));
    }

    public CircularLogic(final CircularLogic card) {
        super(card);
    }

    @Override
    public CircularLogic copy() {
        return new CircularLogic(this);
    }
}

class CircularLogicCounterUnlessPaysEffect extends OneShotEffect<CircularLogicCounterUnlessPaysEffect> {

    public CircularLogicCounterUnlessPaysEffect() {
        super(Constants.Outcome.Detriment);
    }

    public CircularLogicCounterUnlessPaysEffect(final CircularLogicCounterUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public CircularLogicCounterUnlessPaysEffect copy() {
        return new CircularLogicCounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            Player controller = game.getPlayer(source.getControllerId());
            if (player != null && controller != null) {
                int amount = controller.getGraveyard().size();
                if (amount == 0) {
                    game.informPlayers("Circular Logic: no cards in controller's graveyard.");
                } else {
                    GenericManaCost cost = new GenericManaCost(amount);
                    if (!cost.pay(source, game, spell.getControllerId(), spell.getControllerId(), false)) {
                        game.informPlayers("Circular Logic: cost wasn't payed - countering target spell.");
                        return game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell unless its controller pays {1} for each card in your graveyard";
    }

}
