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
package mage.abilities.effects.common.search;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public abstract class SearchTargetGraveyardHandLibraryForCardNameAndExileEffect extends OneShotEffect {

    protected String searchWhatText;
    protected String searchForText;

    /* Slaughter Games
     * 10/1/2012: You can leave any cards with that name in the zone they are in. You don’t have to exile them.
     *
     * Sowing Salt
     * 2/1/2005: The copies must be found if they are in publicly viewable zones. Finding copies while searching private zones is optional.
     */
    protected Boolean graveyardExileOptional;

    public SearchTargetGraveyardHandLibraryForCardNameAndExileEffect(Boolean graveyardExileOptional, String searchWhatText, String searchForText) {
        super(Outcome.Exile);
        this.searchWhatText = searchWhatText;
        this.searchForText = searchForText;
        this.graveyardExileOptional = graveyardExileOptional;
    }

    public SearchTargetGraveyardHandLibraryForCardNameAndExileEffect(final SearchTargetGraveyardHandLibraryForCardNameAndExileEffect effect) {
        super(effect);
        this.searchWhatText = effect.searchWhatText;
        this.searchForText = effect.searchForText;
        this.graveyardExileOptional = effect.graveyardExileOptional;
    }

    /**
     *
     * @param game
     * @param source
     * @param cardName name of the card to exile
     * @param targetPlayerId id of the target player to exile card name from his
     * or her zones
     * @return
     */
    public boolean applySearchAndExile(Game game, Ability source, String cardName, UUID targetPlayerId) {
        Player controller = game.getPlayer(source.getControllerId());
        if (cardName != null && controller != null) {
            Player targetPlayer = game.getPlayer(targetPlayerId);
            if (targetPlayer != null) {
                FilterCard filter = new FilterCard("card named " + cardName);
                filter.add(new NamePredicate(cardName));

                // cards in Graveyard
                int cardsCount = (cardName.isEmpty() ? 0 : targetPlayer.getGraveyard().count(filter, game));
                if (cardsCount > 0) {
                    filter.setMessage("card named " + cardName + " in the graveyard of " + targetPlayer.getLogName());
                    TargetCard target = new TargetCard((graveyardExileOptional ? 0 : cardsCount), cardsCount, Zone.GRAVEYARD, filter);
                    if (controller.choose(Outcome.Exile, targetPlayer.getGraveyard(), target, game)) {
                        controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
                    }
                }

                // cards in Hand
                cardsCount = (cardName.isEmpty() ? 0 : targetPlayer.getHand().count(filter, game));
                filter.setMessage("card named " + cardName + " in the hand of " + targetPlayer.getLogName());
                TargetCard target = new TargetCard(0, cardsCount, Zone.HAND, filter);
                if (controller.choose(Outcome.Exile, targetPlayer.getHand(), target, game)) {
                    controller.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
                }

                // cards in Library
                Cards cardsInLibrary = new CardsImpl(Zone.LIBRARY);
                cardsInLibrary.addAll(targetPlayer.getLibrary().getCards(game));
                cardsCount = (cardName.isEmpty() ? 0 : cardsInLibrary.count(filter, game));
                filter.setMessage("card named " + cardName + " in the library of " + targetPlayer.getLogName());
                TargetCardInLibrary targetLib = new TargetCardInLibrary(0, cardsCount, filter);
                if (controller.choose(Outcome.Exile, cardsInLibrary, targetLib, game)) {
                    controller.moveCards(new CardsImpl(targetLib.getTargets()), Zone.EXILED, source, game);
                }
                targetPlayer.shuffleLibrary(game);
            }

            return true;
        }

        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Search ").append(this.searchWhatText);
        sb.append(" graveyard, hand, and library for ");
        sb.append(this.searchForText);
        sb.append(" and exile them. Then that player shuffles his or her library");
        return sb.toString();
    }
}
