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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.abilities.TriggeredAbilityImpl;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;


/**
 *
 * @author jeffwadsworth
 */
public class LoneRevenant extends CardImpl<LoneRevenant> {
    
    public LoneRevenant(UUID ownerId) {
        super(ownerId, 64, "Lone Revenant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Spirit");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(HexproofAbility.getInstance());
        
        // Whenever Lone Revenant deals combat damage to a player, if you control no other creatures, look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new LoneRevenantTriggeredAbility());
    }

    public LoneRevenant(final LoneRevenant card) {
        super(card);
    }

    @Override
    public LoneRevenant copy() {
        return new LoneRevenant(this);
    }
}

class LoneRevenantTriggeredAbility extends TriggeredAbilityImpl<LoneRevenantTriggeredAbility> {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    public LoneRevenantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoneRevenantEffect());
    }
    
    public LoneRevenantTriggeredAbility(final LoneRevenantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LoneRevenantTriggeredAbility copy() {
        return new LoneRevenantTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getSourceId().equals(this.sourceId)
        && ((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            int number = game.getBattlefield().countAll(filter, controllerId, game);
                
            if (permanent != null && number != 1) {
                return false;
            }
            if (permanent == null && number != 0) {
                return false;
            }
            return true;
        }
        return false;
    }
}

class LoneRevenantEffect extends OneShotEffect<LoneRevenantEffect> {
    
    public LoneRevenantEffect() {
        super(Constants.Outcome.DrawCard);
        this.staticText = "Whenever Lone Revenant deals combat damage to a player, if you control no other creatures, look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order";
    }

    public LoneRevenantEffect(final LoneRevenantEffect effect) {
        super(effect);
    }

    @Override
    public LoneRevenantEffect copy() {
        return new LoneRevenantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(Constants.Zone.PICK);
        int count = Math.min(player.getLibrary().size(), 4);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Constants.Zone.PICK);
            }
        }
        player.lookAtCards("Lone Revenant", cards, game);

        TargetCard target = new TargetCard(Constants.Zone.PICK, new FilterCard("card to put into your hand"));
        if (player.choose(Constants.Outcome.DrawCard, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
            }
        }

        target = new TargetCard(Constants.Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Constants.Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }
}