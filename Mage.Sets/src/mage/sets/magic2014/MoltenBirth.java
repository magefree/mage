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
package mage.sets.magic2014;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class MoltenBirth extends CardImpl<MoltenBirth> {

    public MoltenBirth(UUID ownerId) {
        super(ownerId, 147, "Molten Birth", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");
        this.expansionSetCode = "M14";

        this.color.setRed(true);

        // Put two 1/1 red Elemental creature tokens onto the battlefield. Then flip a coin. If you win the flip, return Molten Birth to its owner's hand.
        this.getSpellAbility().addEffect(new MoltenBirthEffect());
        
    }

    public MoltenBirth(final MoltenBirth card) {
        super(card);
    }

    @Override
    public MoltenBirth copy() {
        return new MoltenBirth(this);
    }
}

class MoltenBirthEffect extends OneShotEffect<MoltenBirthEffect> {

    public MoltenBirthEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put two 1/1 red Elemental creature tokens onto the battlefield. Then flip a coin. If you win the flip, return {this} to its owner's hand";
    }

    public MoltenBirthEffect(final MoltenBirthEffect effect) {
        super(effect);
    }

    @Override
    public MoltenBirthEffect copy() {
        return new MoltenBirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Card molten = game.getCard(source.getSourceId());
        if (you != null) {
            ElementalToken token = new ElementalToken();
            token.putOntoBattlefield(2, game, source.getId(), source.getControllerId());
            if (you.flipCoin(game)) {
                molten.moveToZone(Zone.HAND, source.getId(), game, true);
                game.informPlayers(new StringBuilder(you.getName()).append(" won the flip.  Molten Birth is returned to ").append(you.getName()).append(" hand.").toString());
            }
        }
        return false;
    }
}

class ElementalToken extends Token {

    public ElementalToken() {
        super("Elemental", "1/1 red Elemental creature");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.RED;
        subtype.add("Elemental");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}