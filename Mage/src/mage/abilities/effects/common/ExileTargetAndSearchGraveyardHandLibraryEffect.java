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

package mage.abilities.effects.common;

import java.util.UUID;
import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */

public class ExileTargetAndSearchGraveyardHandLibraryEffect extends OneShotEffect<ExileTargetAndSearchGraveyardHandLibraryEffect> {

    private String exileZone = null;
    private UUID exileId = null;

    public ExileTargetAndSearchGraveyardHandLibraryEffect() {
        super(Constants.Outcome.Exile);
    }

    public ExileTargetAndSearchGraveyardHandLibraryEffect(UUID exileId, String exileZone) {
        this();
        this.exileId = exileId;
        this.exileZone = exileZone;
    }
    
    public ExileTargetAndSearchGraveyardHandLibraryEffect(final ExileTargetAndSearchGraveyardHandLibraryEffect effect) {
        super(effect);
        this.exileZone = effect.exileZone;
        this.exileId = effect.exileId;
    }

    @Override
    public ExileTargetAndSearchGraveyardHandLibraryEffect copy() {
        return new ExileTargetAndSearchGraveyardHandLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        String sourceName = null;
        if (source instanceof SpellAbility) {
            Card sourceCard = game.getCard(source.getSourceId());
            if (sourceCard != null) {
                sourceName = sourceCard.getName();
            }
        } else {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                sourceName = sourcePermanent.getName();
            }
        }
       
        Card chosenCard = null;
        Player you = game.getPlayer(source.getControllerId());
        Player searchPlayer = null;
        Target target = source.getTargets().get(0);
        if (target != null && target instanceof TargetPermanent) {
            Permanent permanentToExile = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanentToExile != null) {
                chosenCard = permanentToExile;
                searchPlayer = game.getPlayer(permanentToExile.getOwnerId());
                result = permanentToExile.moveToExile(exileId, exileZone, source.getSourceId(), game);
            }
        } 
        
        if (searchPlayer != null && you != null && chosenCard != null) {
            game.informPlayers("Searched for cards with the name: " + chosenCard.getName());
            //cards in Library
            Cards cardsInLibrary = new CardsImpl(Constants.Zone.LIBRARY);
            cardsInLibrary.addAll(searchPlayer.getLibrary().getCards(game));
            you.lookAtCards(sourceName + " search of Library", cardsInLibrary, game);

            // cards in Graveyard
            Cards cardsInGraveyard = new CardsImpl(Constants.Zone.GRAVEYARD);
            cardsInGraveyard.addAll(searchPlayer.getGraveyard());

            // cards in Hand
            Cards cardsInHand = new CardsImpl(Constants.Zone.HAND);
            cardsInHand.addAll(searchPlayer.getHand());
            you.lookAtCards(sourceName + " search of Hand", cardsInHand, game);

            // exile same named cards from zones
            for (Card checkCard : cardsInLibrary.getCards(game)) {
                if (checkCard.getName().equals(chosenCard.getName())) {
                    checkCard.moveToExile(id, "Library", id, game);
                }
            }
            for (Card checkCard : cardsInGraveyard.getCards(game)) {
                if (checkCard.getName().equals(chosenCard.getName())) {
                    checkCard.moveToExile(id, "Graveyard", id, game);
                }
            }
            for (Card checkCard : cardsInHand.getCards(game)) {
                if (checkCard.getName().equals(chosenCard.getName())) {
                    checkCard.moveToExile(id, "Hand", id, game);
                }
            }

            searchPlayer.shuffleLibrary(game);            
        }
        return result;
    }
    
    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        sb.append("Exile target ").append(target.getTargetName()).append(". ");
        sb.append("Search its controller's graveyard, hand, and library for all cards with the same name as that ");

        if (target instanceof TargetCreaturePermanent) {
            sb.append("creature");
        } else if (target instanceof TargetCreaturePermanent) {
            sb.append("land");
        } else if (target instanceof TargetArtifactPermanent) {
            sb.append("artifact");
        } else {
            sb.append(target.getTargetName());
        }
        sb.append(" and exile them. Then that player shuffles his or her library");
        return sb.toString();
    }    
}
