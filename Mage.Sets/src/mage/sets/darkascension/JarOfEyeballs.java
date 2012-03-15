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

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class JarOfEyeballs extends CardImpl<JarOfEyeballs> {

    public JarOfEyeballs(UUID ownerId) {
        super(ownerId, 152, "Jar of Eyeballs", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "DKA";

        // Whenever a creature you control dies, put two eyeball counters on Jar of Eyeballs.
        this.addAbility(new JarOfEyeballsTriggeredAbility());
        // {3}, {tap}, Remove all eyeball counters from Jar of Eyeballs:
        // Look at the top X cards of your library, where X is the number of eyeball counters removed this way.
        // Put one of them into your hand and the rest on the bottom of your library in any order.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new JarOfEyeballsEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new JarOfEyeballsCost());
        this.addAbility(ability);
    }

    public JarOfEyeballs(final JarOfEyeballs card) {
        super(card);
    }

    @Override
    public JarOfEyeballs copy() {
        return new JarOfEyeballs(this);
    }
}

class JarOfEyeballsTriggeredAbility extends TriggeredAbilityImpl<JarOfEyeballsTriggeredAbility> {

    public JarOfEyeballsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.EYEBALL.createInstance(2)));
    }

    public JarOfEyeballsTriggeredAbility(final JarOfEyeballsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JarOfEyeballsTriggeredAbility copy() {
        return new JarOfEyeballsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent.getControllerId().equals(this.getControllerId()) && permanent.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control dies, " + super.getRule();
    }
}

class JarOfEyeballsCost extends CostImpl<JarOfEyeballsCost> {

    private int removedCounters;

    public JarOfEyeballsCost() {
        super();
        this.removedCounters = 0;
        this.text = "Remove all eyeball counters from {this}";
    }

    public JarOfEyeballsCost(JarOfEyeballsCost cost) {
        super(cost);
        this.removedCounters = cost.removedCounters;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent != null) {
            this.removedCounters = permanent.getCounters().getCount(CounterType.EYEBALL);
            if (this.removedCounters > 0) {
                permanent.removeCounters(CounterType.EYEBALL.createInstance(this.removedCounters), game);
            }
        }
        this.paid = true;
        return true;
    }

    @Override
    public JarOfEyeballsCost copy() {
        return new JarOfEyeballsCost(this);
    }

    public int getRemovedCounters() {
        return this.removedCounters;
    }
}

class JarOfEyeballsEffect extends OneShotEffect<JarOfEyeballsEffect> {

    public JarOfEyeballsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top X cards of your library, where X is the number of eyeball counters removed this way. Put one of them into your hand and the rest on the bottom of your library in any order";
    }

    public JarOfEyeballsEffect(final JarOfEyeballsEffect effect) {
        super(effect);
    }

    @Override
    public JarOfEyeballsEffect copy() {
        return new JarOfEyeballsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int countersRemoved = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof JarOfEyeballsCost) {
                countersRemoved = ((JarOfEyeballsCost) cost).getRemovedCounters();
            }
        }

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
        int count = Math.min(player.getLibrary().size(), countersRemoved);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
            }
        }
        player.lookAtCards("Jar of Eyeballs", cards, game);

        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put into your hand"));
        if (player.choose(Outcome.DrawCard, cards, target, game)) {
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.HAND, source.getId(), game, false);
            }
        }

        target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }
}
