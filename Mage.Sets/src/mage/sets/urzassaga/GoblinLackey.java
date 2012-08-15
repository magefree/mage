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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jonubuu
 */
public class GoblinLackey extends CardImpl<GoblinLackey> {

    public GoblinLackey(UUID ownerId) {
        super(ownerId, 190, "Goblin Lackey", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "USG";
        this.subtype.add("Goblin");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Goblin Lackey deals damage to a player, you may put a Goblin permanent card from your hand onto the battlefield.
        this.addAbility(new GoblinLackeyTriggeredAbility());
    }

    public GoblinLackey(final GoblinLackey card) {
        super(card);
    }

    @Override
    public GoblinLackey copy() {
        return new GoblinLackey(this);
    }
}

class GoblinLackeyTriggeredAbility extends TriggeredAbilityImpl<GoblinLackeyTriggeredAbility> {

    public GoblinLackeyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GoblinLackeyEffect(), true);
    }

    public GoblinLackeyTriggeredAbility(final GoblinLackeyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoblinLackeyTriggeredAbility copy() {
        return new GoblinLackeyTriggeredAbility(this);
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

class GoblinLackeyEffect extends OneShotEffect<GoblinLackeyEffect> {

    public GoblinLackeyEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put a Goblin creature card from your hand onto the battlefield";
    }

    public GoblinLackeyEffect(final GoblinLackeyEffect effect) {
        super(effect);
    }

    @Override
    public GoblinLackeyEffect copy() {
        return new GoblinLackeyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        FilterCreatureCard filter = new FilterCreatureCard("Goblin creature card from your hand");
        filter.add(new SubtypePredicate("Goblin"));
        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }
}
