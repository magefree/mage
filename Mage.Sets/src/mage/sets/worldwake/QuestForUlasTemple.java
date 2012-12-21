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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class QuestForUlasTemple extends CardImpl<QuestForUlasTemple> {

    public QuestForUlasTemple(UUID ownerId) {
        super(ownerId, 35, "Quest for Ula's Temple", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.expansionSetCode = "WWK";

        this.color.setBlue(true);

        // At the beginning of your upkeep, you may look at the top card of your library. If it's a creature card, you may reveal it and put a quest counter on Quest for Ula's Temple.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Constants.Zone.BATTLEFIELD, new QuestForUlasTempleEffect(), TargetController.YOU, true));

        // At the beginning of each end step, if there are three or more quest counters on Quest for Ula's Temple, you may put a Kraken, Leviathan, Octopus, or Serpent creature card from your hand onto the battlefield.
        this.addAbility(new QuestForUlasTempleTriggeredAbility());
    }

    public QuestForUlasTemple(final QuestForUlasTemple card) {
        super(card);
    }

    @Override
    public QuestForUlasTemple copy() {
        return new QuestForUlasTemple(this);
    }
}

class QuestForUlasTempleEffect extends OneShotEffect<QuestForUlasTempleEffect> {

    public QuestForUlasTempleEffect() {
        super(Constants.Outcome.Benefit);
        this.staticText = "you may look at the top card of your library. If it's a creature card, you may reveal it and put a quest counter on {this}";
    }

    public QuestForUlasTempleEffect(final QuestForUlasTempleEffect effect) {
        super(effect);
    }

    @Override
    public QuestForUlasTempleEffect copy() {
        return new QuestForUlasTempleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() > 0) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            player.lookAtCards("This card", cards, game);
            if (card.getCardType().contains(CardType.CREATURE)) {
                if (player.chooseUse(Constants.Outcome.DrawCard, "Do you wish to reveal the creature card at the top of the library?", game)) {
                    player.revealCards("Quest for Ula's Temple", cards, game);
                    Permanent questForUlasTemple = game.getPermanent(source.getSourceId());
                    if (questForUlasTemple != null) {
                        questForUlasTemple.addCounters(CounterType.QUEST.createInstance(), game);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class QuestForUlasTempleTriggeredAbility extends TriggeredAbilityImpl<QuestForUlasTempleTriggeredAbility> {

    public QuestForUlasTempleTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new QuestForUlasTempleEffect2(), true);
    }

    public QuestForUlasTempleTriggeredAbility(final QuestForUlasTempleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QuestForUlasTempleTriggeredAbility copy() {
        return new QuestForUlasTempleTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent quest = game.getPermanent(super.getSourceId());
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE
                && quest != null
                && quest.getCounters().getCount(CounterType.QUEST) >= 3) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each end step, if there are three or more quest counters on {this}, you may put a Kraken, Leviathan, Octopus, or Serpent creature card from your hand onto the battlefield.";
    }
}

class QuestForUlasTempleEffect2 extends OneShotEffect<QuestForUlasTempleEffect2> {

    private static final String query = "Do you want to put a Kraken, Leviathan, Octopus, or Serpent creature card from your hand onto the battlefield?";
    private static final FilterCreatureCard filter = new FilterCreatureCard("Kraken, Leviathan, Octopus, or Serpent creature card from your hand");

    static {
        filter.add(Predicates.or(
                new SubtypePredicate("Kraken"),
                new SubtypePredicate("Leviathan"),
                new SubtypePredicate("Octopus"),
                new SubtypePredicate("Serpent")));
    }

    QuestForUlasTempleEffect2() {
        super(Constants.Outcome.PutCreatureInPlay);
    }

    QuestForUlasTempleEffect2(final QuestForUlasTempleEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Constants.Outcome.PutCreatureInPlay, query, game)) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(Constants.Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                card.putOntoBattlefield(game, Constants.Zone.HAND, source.getId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }

    @Override
    public QuestForUlasTempleEffect2 copy() {
        return new QuestForUlasTempleEffect2(this);
    }
}
