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
package mage.sets.commander2014;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public class InfernalOffering extends CardImpl {

    public InfernalOffering(UUID ownerId) {
        super(ownerId, 24, "Infernal Offering", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{B}");
        this.expansionSetCode = "C14";


        // Choose an opponent. You and that player each sacrifice a creature. Each player who sacrificed a creature this way draws two cards.
        this.getSpellAbility().addEffect(new InfernalOfferingSacrificeEffect());
        
        // Choose an opponent. Return a creature card from your graveyard to the battlefield, then that player returns a creature card from his or her graveyard to the battlefield.
        this.getSpellAbility().addEffect(new InfernalOfferingReturnEffect());
    }

    public InfernalOffering(final InfernalOffering card) {
        super(card);
    }

    @Override
    public InfernalOffering copy() {
        return new InfernalOffering(this);
    }
}

class InfernalOfferingSacrificeEffect extends OneShotEffect {
    
    InfernalOfferingSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Choose an opponent. You and that player each sacrifice a creature. Each player who sacrificed a creature this way draws two cards";
    }
    
    InfernalOfferingSacrificeEffect(final InfernalOfferingSacrificeEffect effect) {
        super(effect);
    }
    
    @Override
    public InfernalOfferingSacrificeEffect copy() {
        return new InfernalOfferingSacrificeEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.Sacrifice, source.getControllerId(), source.getSourceId(), game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                //Choose creatures to sacrifice
                Map<UUID, UUID> toSacrifice = new HashMap<>(2);
                for (UUID playerId : player.getInRange()) {
                    if (playerId == player.getId() || playerId == opponent.getId()) {
                        target = new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent(), true);
                        if (target.choose(Outcome.Sacrifice, playerId, source.getControllerId(), game)) {
                            toSacrifice.put(playerId, target.getFirstTarget());
                        }
                    }
                }
                //Sacrifice the chosen creatures
                List<UUID> toDraw = new ArrayList<>(2);
                for (Entry<UUID, UUID> entry : toSacrifice.entrySet()) {
                    Permanent permanent = game.getPermanent(entry.getValue());
                    if (permanent != null) {
                        if (permanent.sacrifice(source.getSourceId(), game)) {
                            toDraw.add(entry.getKey());
                        }
                    }
                }
                //Draw cards if creatures have been sacrificed
                for (UUID playerId : toDraw) {
                    Player playerToDraw = game.getPlayer(playerId);
                    if (playerToDraw != null) {
                        playerToDraw.drawCards(2, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class InfernalOfferingReturnEffect extends OneShotEffect {
    
    InfernalOfferingReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose an opponent. Return a creature card from your graveyard to the battlefield, then that player returns a creature card from his or her graveyard to the battlefield";
    }
    
    InfernalOfferingReturnEffect(final InfernalOfferingReturnEffect effect) {
        super(effect);
    }
    
    @Override
    public InfernalOfferingReturnEffect copy() {
        return new InfernalOfferingReturnEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetOpponent(true);
            target.choose(Outcome.PutCreatureInPlay, source.getControllerId(), source.getSourceId(), game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard"));
            if (target.choose(Outcome.PutCreatureInPlay, player.getId(), source.getSourceId(), game)) {
                Card card = player.getGraveyard().get(target.getFirstTarget(), game);
                if (card != null) {
                    player.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId());
                }
            }
            if (opponent != null) {
                target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card in your graveyard"));
                if (target.choose(Outcome.PutCreatureInPlay, opponent.getId(), source.getSourceId(), game)) {
                    Card card = opponent.getGraveyard().get(target.getFirstTarget(), game);
                    if (card != null) {
                        opponent.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
