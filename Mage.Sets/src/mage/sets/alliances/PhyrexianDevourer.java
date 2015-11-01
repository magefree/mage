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
package mage.sets.alliances;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PhyrexianDevourer extends CardImpl {

    public PhyrexianDevourer(UUID ownerId) {
        super(ownerId, 167, "Phyrexian Devourer", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.expansionSetCode = "ALL";
        this.subtype.add("Construct");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Phyrexian Devourer's power is 7 or greater, sacrifice it.
        this.addAbility(new PhyrexianDevourerStateTriggeredAbility());

        // Exile the top card of your library: Put X +1/+1 counters on Phyrexian Devourer, where X is the exiled card's converted mana cost.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhyrexianDevourerEffect(), new ExileTopCardLibraryCost()));

    }

    public PhyrexianDevourer(final PhyrexianDevourer card) {
        super(card);
    }

    @Override
    public PhyrexianDevourer copy() {
        return new PhyrexianDevourer(this);
    }
}

class PhyrexianDevourerStateTriggeredAbility extends StateTriggeredAbility {

    public PhyrexianDevourerStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public PhyrexianDevourerStateTriggeredAbility(final PhyrexianDevourerStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PhyrexianDevourerStateTriggeredAbility copy() {
        return new PhyrexianDevourerStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getPower().getValue() >= 7;
    }

    @Override
    public String getRule() {
        return "When {this}'s power is 7 or greater, sacrifice it.";
    }

}

class PhyrexianDevourerEffect extends OneShotEffect {

    public PhyrexianDevourerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put X +1/+1 counters on {this}, where X is the exiled card's converted mana cost";
    }

    public PhyrexianDevourerEffect(final PhyrexianDevourerEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianDevourerEffect copy() {
        return new PhyrexianDevourerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = null;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof ExileTopCardLibraryCost) {
                    card = ((ExileTopCardLibraryCost) cost).getCard();
                }
            }
            if (card != null) {
                int amount = card.getManaCost().convertedManaCost();
                if (amount > 0) {
                    return new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount)).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}

class ExileTopCardLibraryCost extends CostImpl {

    Card card;

    public ExileTopCardLibraryCost() {
        this.text = "Exile the top card of your library";
    }

    public ExileTopCardLibraryCost(final ExileTopCardLibraryCost cost) {
        super(cost);
        this.card = cost.getCard();
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                paid = controller.moveCards(card, null, Zone.EXILED, ability, game);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            return controller.getLibrary().size() > 0;
        }
        return false;
    }

    @Override
    public ExileTopCardLibraryCost copy() {
        return new ExileTopCardLibraryCost(this);
    }

    public Card getCard() {
        return card;
    }

}
