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
package mage.sets.shardsofalara;

import java.util.UUID;

import mage.constants.*;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class LichsMirror extends CardImpl<LichsMirror> {

    public LichsMirror(UUID ownerId) {
        super(ownerId, 210, "Lich's Mirror", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "ALA";

        // If you would lose the game, instead shuffle your hand, your graveyard, and all permanents you own into your library, then draw seven cards and your life total becomes 20.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LichsMirrorEffect()));
    }

    public LichsMirror(final LichsMirror card) {
        super(card);
    }

    @Override
    public LichsMirror copy() {
        return new LichsMirror(this);
    }
}

class LichsMirrorEffect extends ReplacementEffectImpl<LichsMirrorEffect> {

    public LichsMirrorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would lose the game, instead shuffle your hand, your graveyard, and all permanents you own into your library, then draw seven cards and your life total becomes 20";
    }

    public LichsMirrorEffect(final LichsMirrorEffect effect) {
        super(effect);
    }

    @Override
    public LichsMirrorEffect copy() {
        return new LichsMirrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            FilterControlledPermanent filter = new FilterControlledPermanent();
            filter.add(new OwnerIdPredicate(player.getId()));
            for (UUID uuid : player.getHand().copy()) {
                Card card = game.getCard(uuid);
                if (card != null) {
                    card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
                }
            }
        
            for (UUID uuid : player.getGraveyard().copy()) {
                Card card = game.getCard(uuid);
                if (card != null) {
                    card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
                }
            }
            
            for(Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)){
                permanent.moveToZone(Zone.LIBRARY, source.getId(), game, true);
            }
            player.shuffleLibrary(game);
            
            player.drawCards(7, game);
            
            player.setLife(20, game);
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.LOSES && event.getPlayerId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }

}