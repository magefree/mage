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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class TemporalCascade extends CardImpl {

    public TemporalCascade(UUID ownerId) {
        super(ownerId, 52, "Temporal Cascade", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");
        this.expansionSetCode = "MRD";

        // Choose one - Each player shuffles his or her hand and graveyard into his or her library;
        this.getSpellAbility().addEffect(new TemporalCascadeShuffleEffect());
        
        // or each player draws seven cards.
        Mode mode = new Mode();
        mode.getEffects().add(new TemporalCascadeDrawEffect());
        this.getSpellAbility().getModes().addMode(mode);
        
        // Entwine {2}
        this.addAbility(new EntwineAbility("{2}"));
    }

    public TemporalCascade(final TemporalCascade card) {
        super(card);
    }

    @Override
    public TemporalCascade copy() {
        return new TemporalCascade(this);
    }
}

class TemporalCascadeShuffleEffect extends OneShotEffect {

    public TemporalCascadeShuffleEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles his or her hand and graveyard into his or her library";
    }

    public TemporalCascadeShuffleEffect(final TemporalCascadeShuffleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        for (UUID playerId: sourcePlayer.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (Card card: player.getHand().getCards(game)) {
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
                for (Card card: player.getGraveyard().getCards(game)) {
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
                player.shuffleLibrary(game);
                
            }
        }
        return true;
    }

    @Override
    public TemporalCascadeShuffleEffect copy() {
        return new TemporalCascadeShuffleEffect(this);
    }
}

class TemporalCascadeDrawEffect extends OneShotEffect {

    public TemporalCascadeDrawEffect() {
        super(Outcome.Neutral);
        staticText = "Each player draws seven cards";
    }

    public TemporalCascadeDrawEffect(final TemporalCascadeDrawEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        game.getState().handleSimultaneousEvent(game); // needed here so state based triggered effects 
        for (UUID playerId: sourcePlayer.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(7, game);
            }            
        }
        return true;
    }

    @Override
    public TemporalCascadeDrawEffect copy() {
        return new TemporalCascadeDrawEffect(this);
    }
}