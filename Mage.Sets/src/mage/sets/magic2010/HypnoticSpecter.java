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
package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class HypnoticSpecter extends CardImpl<HypnoticSpecter> {

    public HypnoticSpecter(UUID ownerId) {
        super(ownerId, 100, "Hypnotic Specter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "M10";
        this.subtype.add("Specter");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Hypnotic Specter deals damage to an opponent, that player discards a card at random.
        this.addAbility(new HypnoticSpecterTriggeredAbility());
    }

    public HypnoticSpecter(final HypnoticSpecter card) {
        super(card);
    }

    @Override
    public HypnoticSpecter copy() {
        return new HypnoticSpecter(this);
    }
}

class HypnoticSpecterTriggeredAbility extends TriggeredAbilityImpl<HypnoticSpecterTriggeredAbility> {

    public HypnoticSpecterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new HypnoticSpecterEffect());
    }

    public HypnoticSpecterTriggeredAbility(final HypnoticSpecterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HypnoticSpecterTriggeredAbility copy() {
        return new HypnoticSpecterTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getSourceId().equals(this.sourceId)
                && game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to an opponent, that player discards a card at random.";
    }
}

class HypnoticSpecterEffect extends OneShotEffect<HypnoticSpecterEffect> {

    public HypnoticSpecterEffect() {
        super(Outcome.Discard);
    }

    public HypnoticSpecterEffect(final HypnoticSpecterEffect effect) {
        super(effect);
    }

    @Override
    public HypnoticSpecterEffect copy() {
        return new HypnoticSpecterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(source));
        if (player != null && !player.getHand().isEmpty()) {
            Card card = player.getHand().getRandom(game);
            if (card != null) {
                player.discard(card, source, game);
                return true;
            }
        }
        return false;
    }
}
