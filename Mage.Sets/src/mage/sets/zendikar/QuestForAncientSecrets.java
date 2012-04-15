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
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class QuestForAncientSecrets extends CardImpl<QuestForAncientSecrets> {

    public QuestForAncientSecrets(UUID ownerId) {
        super(ownerId, 59, "Quest for Ancient Secrets", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.expansionSetCode = "ZEN";

        this.color.setBlue(true);

        // Whenever a card is put into your graveyard from anywhere, you may put a quest counter on Quest for Ancient Secrets.
        this.addAbility(new QuestForAncientSecretsTriggeredAbility());
        // Remove five quest counters from Quest for Ancient Secrets and sacrifice it: Target player shuffles his or her graveyard into his or her library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new QuestForAncientSecretsEffect(),
                new RemoveCountersSourceCost(CounterType.QUEST.createInstance(5)));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public QuestForAncientSecrets(final QuestForAncientSecrets card) {
        super(card);
    }

    @Override
    public QuestForAncientSecrets copy() {
        return new QuestForAncientSecrets(this);
    }
}

class QuestForAncientSecretsTriggeredAbility extends TriggeredAbilityImpl<QuestForAncientSecretsTriggeredAbility> {

    public QuestForAncientSecretsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true);
    }

    public QuestForAncientSecretsTriggeredAbility(final QuestForAncientSecretsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QuestForAncientSecretsTriggeredAbility copy() {
        return new QuestForAncientSecretsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getOwnerId().equals(this.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a card is put into your graveyard from anywhere, you may put a quest counter on {this}";
    }
}

class QuestForAncientSecretsEffect extends OneShotEffect<QuestForAncientSecretsEffect> {

    public QuestForAncientSecretsEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles his or her graveyard into his or her library";
    }

    public QuestForAncientSecretsEffect(final QuestForAncientSecretsEffect effect) {
        super(effect);
    }

    @Override
    public QuestForAncientSecretsEffect copy() {
        return new QuestForAncientSecretsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.getLibrary().addAll(player.getGraveyard().getCards(game), game);
            player.getGraveyard().clear();
            player.getLibrary().shuffle();
            return true;
        }
        return false;
    }
}
