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
package mage.sets.morningtide;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class CountrysideCrusher extends CardImpl<CountrysideCrusher> {

    public CountrysideCrusher(UUID ownerId) {
        super(ownerId, 89, "Countryside Crusher", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Giant");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, reveal the top card of your library. If it's a land card, put it into your graveyard and repeat this process.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CountrysideCrusherEffect(), Constants.TargetController.YOU, false));
        // Whenever a land card is put into your graveyard from anywhere, put a +1/+1 counter on Countryside Crusher.
        this.addAbility(new CountrysideCrusherTriggeredAbility());
    }

    public CountrysideCrusher(final CountrysideCrusher card) {
        super(card);
    }

    @Override
    public CountrysideCrusher copy() {
        return new CountrysideCrusher(this);
    }
}

class CountrysideCrusherEffect extends OneShotEffect<CountrysideCrusherEffect> {

    public CountrysideCrusherEffect() {
        super(Constants.Outcome.Discard);
        this.staticText = "reveal the top card of your library. If it's a land card, put it into your graveyard and repeat this process";
    }

    public CountrysideCrusherEffect(final CountrysideCrusherEffect effect) {
        super(effect);
    }

    @Override
    public CountrysideCrusherEffect copy() {
        return new CountrysideCrusherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (player != null && permanent != null) {
            while (player.getLibrary().size() > 0) {
                Card card = player.getLibrary().getFromTop(game);
                player.revealCards(permanent.getName(), new CardsImpl(card), game);
                if (card.getCardType().contains(CardType.LAND)) {
                    card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                } else {
                    break;
                }
            }
            return true;
        }
        return false;
    }
}


class CountrysideCrusherTriggeredAbility extends TriggeredAbilityImpl<CountrysideCrusherTriggeredAbility> {

    public CountrysideCrusherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    public CountrysideCrusherTriggeredAbility(final CountrysideCrusherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CountrysideCrusherTriggeredAbility copy() {
        return new CountrysideCrusherTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && card.getOwnerId().equals(this.getControllerId()) && card.getCardType().contains(CardType.LAND)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land card is put into your graveyard from anywhere, put a +1/+1 counter on {this}";
    }
}
