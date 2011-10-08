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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author North
 */
public class WarrenInstigator extends CardImpl<WarrenInstigator> {

    public WarrenInstigator(UUID ownerId) {
        super(ownerId, 154, "Warren Instigator", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{R}{R}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Goblin");
        this.subtype.add("Berserker");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(new WarrenInstigatorTriggeredAbility());
    }

    public WarrenInstigator(final WarrenInstigator card) {
        super(card);
    }

    @Override
    public WarrenInstigator copy() {
        return new WarrenInstigator(this);
    }
}

class WarrenInstigatorTriggeredAbility extends TriggeredAbilityImpl<WarrenInstigatorTriggeredAbility> {

    public WarrenInstigatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WarrenInstigatorEffect(), true);
    }

    public WarrenInstigatorTriggeredAbility(final WarrenInstigatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WarrenInstigatorTriggeredAbility copy() {
        return new WarrenInstigatorTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getSourceId().equals(this.sourceId)
                && game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to an opponent, you may put a Goblin creature card from your hand onto the battlefield.";
    }
}

class WarrenInstigatorEffect extends OneShotEffect<WarrenInstigatorEffect> {

    public WarrenInstigatorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put a Goblin creature card from your hand onto the battlefield";
    }

    public WarrenInstigatorEffect(final WarrenInstigatorEffect effect) {
        super(effect);
    }

    @Override
    public WarrenInstigatorEffect copy() {
        return new WarrenInstigatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        FilterCreatureCard filter = new FilterCreatureCard("Goblin creature card from your hand");
        filter.getSubtype().add("Goblin");
        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                player.removeFromHand(card, game);
                card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }
}
