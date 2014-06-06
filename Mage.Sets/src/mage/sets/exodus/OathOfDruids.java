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
package mage.sets.exodus;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.other.PlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public class OathOfDruids extends CardImpl {

    private static final FilterPlayer filter = new FilterPlayer();
    static{
        filter.add(new OathOfDruidsPredicate());
    }
    
   public OathOfDruids(UUID ownerId) {
        super(ownerId, 115, "Oath of Druids", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "EXO";

        this.color.setGreen(true);

        // At the beginning of each player's upkeep, that player chooses target player who controls more creatures than he or she does and is his or her opponent. The first player may reveal cards from the top of his or her library until he or she reveals a creature card. If he or she does, that player puts that card onto the battlefield and all other cards revealed this way into his or her graveyard.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new OathOfDruidsEffect(), TargetController.ANY, true);
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        this.addAbility(ability);
    }
   
   
    @Override
    public void adjustTargets(Ability ability, Game game) {
    if (ability instanceof BeginningOfUpkeepTriggeredAbility) {
            
            Player activePlayer = game.getPlayer(game.getActivePlayerId());
            if (activePlayer != null) {
                ability.setControllerId(activePlayer.getId());
                ability.getTargets().clear();
                TargetPlayer target = new TargetPlayer(1, 1, false, filter);
                target.setRequired(true);
                ability.getTargets().add(target);
            }
        }
    }

    public OathOfDruids(final OathOfDruids card) {
        super(card);
    }

    @Override
    public OathOfDruids copy() {
        return new OathOfDruids(this);
    }
}

class OathOfDruidsPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

    public OathOfDruidsPredicate() {
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        //Get active input.playerId because adjust target is used after canTarget function
        UUID activePlayer = game.getActivePlayerId();
        if (targetPlayer == null || activePlayer == null) {
            return false;
        }
        if(targetPlayer.getId().equals(activePlayer))
        {
            return false;
        }
        int countTarget = game.getBattlefield().count(new FilterControlledCreaturePermanent(), null, targetPlayer.getId(), game);
        int countController = game.getBattlefield().count(new FilterControlledCreaturePermanent(), null, activePlayer, game);
        
        return countTarget > countController;
    }

    @Override
    public String toString() {
        return "player who controls more creatures than he or she does";
    }
}

class OathOfDruidsEffect extends OneShotEffect {

    public OathOfDruidsEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "that player chooses target player who controls more creatures than he or she does and is his or her opponent. The first player may reveal cards from the top of his or her library until he or she reveals a creature card. If he or she does, that player puts that card onto the battlefield and all other cards revealed this way into his or her graveyard";
    }

    public OathOfDruidsEffect(OathOfDruidsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards revealed = new CardsImpl();
        Card creatureCard = null;
        Cards nonCreatureCards = new CardsImpl();
        //The first player may reveal cards from the top of his or her library
        while (creatureCard == null && player.getLibrary().size() > 0) {
            Card card = player.getLibrary().removeFromTop(game);
            revealed.add(card);
            // until he or she reveals a creature card. 
            if (card.getCardType().contains(CardType.CREATURE)){
                creatureCard = card;
            }
            else{
                nonCreatureCards.add(card);
            }
        }
        player.revealCards("Oath of Druids", revealed, game);
        
        //If he or she does, that player puts that card onto the battlefield
        if (creatureCard != null) {
            creatureCard.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), player.getId());
        }
        // and all other cards revealed this way into his or her graveyard
        for(UUID uuid : nonCreatureCards){
            Card card = game.getCard(uuid);
            if(card != null){
                card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
            }
        }
        return true;
    }

    @Override
    public OathOfDruidsEffect copy() {
        return new OathOfDruidsEffect(this);
    }
}