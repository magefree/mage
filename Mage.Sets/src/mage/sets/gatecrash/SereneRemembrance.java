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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class SereneRemembrance extends CardImpl<SereneRemembrance> {

    public SereneRemembrance (UUID ownerId) {
        super(ownerId, 132, "Serene Remembrance", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{G}");
        this.expansionSetCode = "GTC";

        this.color.setGreen(true);

        // Shuffle Serene Remembrance and up to three target cards from a single graveyard into their owners' libraries.
        this.getSpellAbility().addEffect(new SereneRemembranceEffect());
        this.getSpellAbility().addTarget(new SereneRemembranceTargetCardsInGraveyard(0,3,new FilterCard()));
        
    }

    public SereneRemembrance(final SereneRemembrance card) {
        super(card);
    }

    @Override
    public SereneRemembrance  copy() {
        return new SereneRemembrance(this);
    }
}

class SereneRemembranceEffect extends OneShotEffect<SereneRemembranceEffect> {
    
    public SereneRemembranceEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle Serene Remembrance and up to three target cards from a single graveyard into their owners' libraries";
    }
    
    public SereneRemembranceEffect(final SereneRemembranceEffect effect) {
        super(effect);
    }
    
    @Override
    public SereneRemembranceEffect copy() {
        return new SereneRemembranceEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Player graveyardPlayer = null;
        for (UUID cardInGraveyard : targetPointer.getTargets(game, source)) {
            Card card = game.getCard(cardInGraveyard);
            if (card != null) {
                for (Player player : game.getPlayers().values()) {
                    if (player.getGraveyard().contains(card.getId())) {
                        graveyardPlayer = player;
                        player.getGraveyard().remove(card);
                        result |= card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, true);
                    }
                }
            }            
        }
        Card card = game.getCard(source.getSourceId());
        result |= card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
        Player player = game.getPlayer(card.getOwnerId());
        if (player != null){
            player.shuffleLibrary(game);
        }
        if (graveyardPlayer != null && !graveyardPlayer.equals(player)) {
            graveyardPlayer.shuffleLibrary(game);
        }
        return result;
    }
}

class SereneRemembranceTargetCardsInGraveyard extends TargetCard<SereneRemembranceTargetCardsInGraveyard> {

    public SereneRemembranceTargetCardsInGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, Constants.Zone.GRAVEYARD, filter);
        this.targetName = "up to three target cards from a single graveyard";
    }

    public SereneRemembranceTargetCardsInGraveyard(final SereneRemembranceTargetCardsInGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID firstTarget = this.getFirstTarget();
        if (firstTarget != null) {
            Card card = game.getCard(firstTarget);
            Card targetCard = game.getCard(id);
            if (card == null || targetCard == null
                    || !card.getOwnerId().equals(targetCard.getOwnerId())) {
                return false;
            }
        }
        return super.canTarget(id, source, game);
    }


    @Override
    public SereneRemembranceTargetCardsInGraveyard copy() {
        return new SereneRemembranceTargetCardsInGraveyard(this);
    }
}
