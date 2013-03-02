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

import java.util.List;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */

public abstract class SearchTargetGraveyardHandLibraryForCardNameAndExileEffect extends OneShotEffect<SearchTargetGraveyardHandLibraryForCardNameAndExileEffect> {

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

    public boolean applySearchAndExile(Game game, Ability source, String cardName, UUID targetPlayerId) {
        Player player = game.getPlayer(source.getControllerId());
        if (cardName != null && player != null) {
            Player targetPlayer = game.getPlayer(targetPlayerId);
            if (targetPlayer != null) {
                FilterCard filter = new FilterCard("card named " + cardName);
                filter.add(new NamePredicate(cardName));

                // cards in Graveyard
                int cardsCount = (cardName.isEmpty() ? 0 :targetPlayer.getGraveyard().count(filter, game));
                if (cardsCount > 0) {
                    filter.setMessage("card named " + cardName + " in the graveyard of " + targetPlayer.getName());
                    TargetCardInGraveyard target = new TargetCardInGraveyard((graveyardExileOptional ? 0 :cardsCount), cardsCount, filter);
                    if (player.choose(Outcome.Exile, targetPlayer.getGraveyard(), target, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card targetCard = targetPlayer.getGraveyard().get(targetId, game);
                            if (targetCard != null) {
                                targetPlayer.getGraveyard().remove(targetCard);
                                targetCard.moveToZone(Zone.EXILED, source.getId(), game, false);
                            }
                        }
                    }
                }

                // cards in Hand
                cardsCount = (cardName.isEmpty() ? 0 :targetPlayer.getHand().count(filter, game));
                if (cardsCount > 0) {
                    filter.setMessage("card named " + cardName + " in the hand of " + targetPlayer.getName());
                    TargetCardInHand target = new TargetCardInHand(0, cardsCount, filter);
                    if (player.choose(Outcome.Exile, targetPlayer.getHand(), target, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card targetCard = targetPlayer.getHand().get(targetId, game);
                            if (targetCard != null) {
                                targetPlayer.getHand().remove(targetCard);
                                targetCard.moveToZone(Zone.EXILED, source.getId(), game, false);
                            }
                        }
                    }
                } else {
                    if (targetPlayer.getHand().size() > 0) {
                        player.lookAtCards(targetPlayer.getName() + " hand", targetPlayer.getHand(), game);
                    }
                }

                // cards in Library
                Cards cardsInLibrary = new CardsImpl(Zone.LIBRARY);
                cardsInLibrary.addAll(targetPlayer.getLibrary().getCards(game));
                cardsCount = (cardName.isEmpty() ? 0 : cardsInLibrary.count(filter, game));
                if (cardsCount > 0) {
                    filter.setMessage("card named " + cardName + " in the library of " + targetPlayer.getName());
                    TargetCardInLibrary target = new TargetCardInLibrary(0, cardsCount, filter);
                    if (player.choose(Outcome.Exile, cardsInLibrary, target, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card targetCard = targetPlayer.getLibrary().remove(targetId, game);
                            if (targetCard != null) {
                                targetCard.moveToZone(Zone.EXILED, source.getId(), game, false);
                            }
                        }
                    }
                } else {
                    player.lookAtCards(targetPlayer.getName() + " library", cardsInLibrary, game);
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
