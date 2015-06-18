/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.search;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */

public class SearchLibraryPutInPlayTargetPlayerEffect extends SearchEffect {

    protected boolean tapped;
    protected boolean forceShuffle;

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target) {
        this(target, false, true, Outcome.PutCardInPlay);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped) {
        this(target, tapped, true, Outcome.PutCardInPlay);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped, boolean forceShuffle) {
        this(target, tapped, forceShuffle, Outcome.PutCardInPlay);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped, Outcome outcome) {
        this(target, tapped, true, outcome);
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(TargetCardInLibrary target, boolean tapped, boolean forceShuffle, Outcome outcome) {
        super(target, outcome);
        this.tapped = tapped;
        this.forceShuffle = forceShuffle;
        setText();
    }

    public SearchLibraryPutInPlayTargetPlayerEffect(final SearchLibraryPutInPlayTargetPlayerEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.forceShuffle = effect.forceShuffle;
    }

    @Override
    public SearchLibraryPutInPlayTargetPlayerEffect copy() {
        return new SearchLibraryPutInPlayTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));        
        if (player != null) {
            if (player.searchLibrary(target, game)) {
                if (target.getTargets().size() > 0) {
                    for (UUID cardId: target.getTargets()) {
                        Card card = player.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            player.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId(), tapped);
                        }
                    }
                }
                player.shuffleLibrary(game);
                return true;
            }
            
            if (forceShuffle) {
                player.shuffleLibrary(game);
            }
        }
        
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("target player searches his or her library for ");
        if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
            if ( target.getMaxNumberOfTargets() == Integer.MAX_VALUE ) {
                sb.append("any number of ").append(" ");
            }
            else {
                sb.append("up to ").append(target.getMaxNumberOfTargets()).append(" ");
            }
            sb.append(target.getTargetName()).append(" and put them onto the battlefield");
        }
        else {
            sb.append("a ").append(target.getTargetName()).append(" and put it onto the battlefield");
        }
        if (tapped) {
            sb.append(" tapped");
        }
        if (forceShuffle) {
            sb.append(". Then that player shuffles his or her library");
        }
        else {
            sb.append(". If that player does, he or she shuffles his or her library");
        }
        staticText = sb.toString();
    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }

}
