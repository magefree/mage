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
package mage.sets.darkascension;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class LostInTheWoods extends CardImpl<LostInTheWoods> {

    public LostInTheWoods(UUID ownerId) {
        super(ownerId, 123, "Lost in the Woods", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");
        this.expansionSetCode = "DKA";

        this.color.setGreen(true);

        // Whenever a creature attacks you or a planeswalker you control, reveal the top card of your library. If it's a Forest card, remove that creature from combat. Then put the revealed card on the bottom of your library.
        this.addAbility(new LostInTheWoodsTriggeredAbility());
    }

    public LostInTheWoods(final LostInTheWoods card) {
        super(card);
    }

    @Override
    public LostInTheWoods copy() {
        return new LostInTheWoods(this);
    }
}

class LostInTheWoodsEffect extends OneShotEffect<LostInTheWoodsEffect> {

    public LostInTheWoodsEffect() {
        super(Outcome.PreventDamage);
    }
    
    public LostInTheWoodsEffect(final LostInTheWoodsEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.getLibrary().size() > 0) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            player.revealCards("Lost in the Woods", cards, game);

            if (card != null) {
                if (card.getSubtype().contains("Forest")) {
                    Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
                    if (permanent != null) {
                        permanent.removeFromCombat(game);
                    }
                }
                card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public LostInTheWoodsEffect copy() {
        return new LostInTheWoodsEffect(this);
    }

}

class LostInTheWoodsTriggeredAbility extends TriggeredAbilityImpl<LostInTheWoodsTriggeredAbility> {

    public LostInTheWoodsTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new LostInTheWoodsEffect(), false);
    }

    public LostInTheWoodsTriggeredAbility(final LostInTheWoodsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LostInTheWoodsTriggeredAbility copy() {
        return new LostInTheWoodsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            if (event.getTargetId().equals(controllerId)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId()));
                return true;
            }
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getCardType().contains(CardType.PLANESWALKER) && permanent.getControllerId().equals(controllerId)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks you or a planeswalker you control, reveal the top card of your library. If it's a Forest card, remove that creature from combat. Then put the revealed card on the bottom of your library.";
    }
}
