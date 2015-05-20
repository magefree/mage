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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.players.Player;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.filter.common.FilterPermanentCard;

/**
 *
 * @author Mitchel Stein
 * 
 */
public class ChaosWarp extends CardImpl {

    public ChaosWarp(UUID ownerId) {
        super(ownerId, 154, "Chaos Warp", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.expansionSetCode = "VMA";


        // The owner of target permanent shuffles it into his or her library,
        this.getSpellAbility().addEffect(new ChaosWarpShuffleIntoLibraryEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        //then reveals the top card of his or her library. 
        //If it's a permanent card, he or she puts it onto the battlefield.
        this.getSpellAbility().addEffect(new ChaosWarpRevealEffect());
        
    }

    public ChaosWarp(final ChaosWarp card) {
        super(card);
    }

    @Override
    public ChaosWarp copy() {
        return new ChaosWarp(this);
    }
}

class ChaosWarpShuffleIntoLibraryEffect extends OneShotEffect {

    public ChaosWarpShuffleIntoLibraryEffect() {
        super(Outcome.Detriment);
        this.staticText = "The owner of target permanent shuffles it into his or her library";
    }

    public ChaosWarpShuffleIntoLibraryEffect(final ChaosWarpShuffleIntoLibraryEffect effect) {
        super(effect);
    }

    @Override
    public ChaosWarpShuffleIntoLibraryEffect copy() {
        return new ChaosWarpShuffleIntoLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));        
        if (permanent != null) {
            Player owner = game.getPlayer(permanent.getOwnerId());            
            if (owner != null) {
                owner.moveCardToLibraryWithInfo(permanent, source.getSourceId(), game, Zone.BATTLEFIELD, true, true);
                owner.shuffleLibrary(game);
                return true;
            }
        }
        return false;
    }
}

class ChaosWarpRevealEffect extends OneShotEffect {
	   
public ChaosWarpRevealEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "then reveals the top card of his or her library. If it's a permanent card, he or she puts it onto the battlefield.";
    }

    public ChaosWarpRevealEffect(final ChaosWarpRevealEffect effect) {
        super(effect);
    }

    @Override
    public ChaosWarpRevealEffect copy() {
        return new ChaosWarpRevealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.BATTLEFIELD);
        if (permanent == null) {
            return false;
        }
        Player owner = game.getPlayer(permanent.getOwnerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (owner == null || sourceObject == null) {
            return false;
        }

        if (owner.getLibrary().size() > 0) {
            Card card = owner.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                owner.revealCards(sourceObject.getName(), cards, game);
            	if (new FilterPermanentCard().match(card, game)) {
                    owner.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
                }
            }
        }
        return true;
    }
}
