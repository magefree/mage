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
package mage.sets.planeshift;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class PlaneswalkersMirth extends CardImpl {

    public PlaneswalkersMirth(UUID ownerId) {
        super(ownerId, 12, "Planeswalker's Mirth", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "PLS";

        // {3}{W}: Target opponent reveals a card at random from his or her hand. You gain life equal to that card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PlaneswalkersMirthEffect(), new ManaCostsImpl("{3}{W}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public PlaneswalkersMirth(final PlaneswalkersMirth card) {
        super(card);
    }

    @Override
    public PlaneswalkersMirth copy() {
        return new PlaneswalkersMirth(this);
    }
}

class PlaneswalkersMirthEffect extends OneShotEffect {

    public PlaneswalkersMirthEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals a card at random from his or her hand. You gain life equal to that card's converted mana cost";
    }

    public PlaneswalkersMirthEffect(final PlaneswalkersMirthEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        Player player = game.getPlayer(source.getControllerId());
        if (opponent != null && player!= null && opponent.getHand().size() > 0) {
            Cards revealed = new CardsImpl();
            Card card = opponent.getHand().getRandom(game);
            if (card != null) {
                revealed.add(card);
                opponent.revealCards("Planeswalker's Mirth", revealed, game);
                player.gainLife(card.getManaCost().convertedManaCost(), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public PlaneswalkersMirthEffect copy() {
        return new PlaneswalkersMirthEffect(this);
    }

}