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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class EnigmaSphinx extends CardImpl<EnigmaSphinx> {

    public EnigmaSphinx(UUID ownerId) {
        super(ownerId, 106, "Enigma Sphinx", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{U}{B}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Sphinx");

        this.color.setWhite(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Enigma Sphinx is put into your graveyard from the battlefield, put it into your library third from the top.
        this.addAbility(new PutIntoGraveFromBattlefieldTriggeredAbility(new EnigmaSphinxEffect()));

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    public EnigmaSphinx(final EnigmaSphinx card) {
        super(card);
    }

    @Override
    public EnigmaSphinx copy() {
        return new EnigmaSphinx(this);
    }
}

class EnigmaSphinxEffect extends OneShotEffect<EnigmaSphinxEffect> {

    public EnigmaSphinxEffect() {
        super(Outcome.ReturnToHand);
        staticText = "put it into your library third from the top";
    }

    public EnigmaSphinxEffect(final EnigmaSphinxEffect effect) {
        super(effect);
    }

    @Override
    public EnigmaSphinxEffect copy() {
        return new EnigmaSphinxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            Player owner = game.getPlayer(card.getOwnerId());
            if (card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true)) {
                // Move Sphinx to third position
                Library lib = game.getPlayer(source.getControllerId()).getLibrary();
                if (lib != null) {
                    Card card1 = lib.removeFromTop(game);
                    if (card1.getId().equals(source.getSourceId())) {
                        Card card2 = lib.removeFromTop(game);
                        Card card3 = lib.removeFromTop(game);
                        if (card1 != null) {
                            lib.putOnTop(card1, game);
                        }
                        if (card3 != null) {
                            lib.putOnTop(card3, game);
                        }
                        if (card2 != null) {
                            lib.putOnTop(card2, game);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}