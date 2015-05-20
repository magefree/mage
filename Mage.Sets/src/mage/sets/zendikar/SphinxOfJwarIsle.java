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
package mage.sets.zendikar;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class SphinxOfJwarIsle extends CardImpl {

    public SphinxOfJwarIsle(UUID ownerId) {
        super(ownerId, 68, "Sphinx of Jwar Isle", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Sphinx");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
        // TODO: this should be a static ability
        this.addAbility(new SphinxOfJwarIsleLookAbility());

    }

    public SphinxOfJwarIsle(final SphinxOfJwarIsle card) {
        super(card);
    }

    @Override
    public SphinxOfJwarIsle copy() {
        return new SphinxOfJwarIsle(this);
    }
}

class SphinxOfJwarIsleLookAbility extends ActivatedAbilityImpl {

    public SphinxOfJwarIsleLookAbility() {
        super(Zone.BATTLEFIELD, new SphinxOfJwarIsleEffect(), new GenericManaCost(0));
        this.usesStack = false;
    }

    public SphinxOfJwarIsleLookAbility(SphinxOfJwarIsleLookAbility ability) {
        super(ability);
    }

    @Override
    public SphinxOfJwarIsleLookAbility copy() {
        return new SphinxOfJwarIsleLookAbility(this);
    }

}

class SphinxOfJwarIsleEffect extends OneShotEffect {

    public SphinxOfJwarIsleEffect() {
        super(Outcome.Neutral);        
        this.staticText = "You may look at the top card of your library";
    }

    public SphinxOfJwarIsleEffect(final SphinxOfJwarIsleEffect effect) {
        super(effect);
    }

    @Override
    public SphinxOfJwarIsleEffect copy() {
        return new SphinxOfJwarIsleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Card card = player.getLibrary().getFromTop(game);
        if (card != null) {
            Cards cards = new CardsImpl(Zone.PICK);
            cards.add(card);
            player.lookAtCards("Sphinx of Jwar Isle", cards, game);
        } else {
            return false;
        }

        return true;
    }
}
