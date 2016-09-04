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
package mage.sets.masterseditionii;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromTopOfLibraryCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class ThoughtLash extends CardImpl {

    public ThoughtLash(UUID ownerId) {
        super(ownerId, 70, "Thought Lash", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        this.expansionSetCode = "ME2";

        // Cumulative upkeep - Exile the top card of your library.
        this.addAbility(new CumulativeUpkeepAbility(new ExileFromTopOfLibraryCost(1)));

        // When a player doesn't pay Thought Lash's cumulative upkeep, that player exiles all cards from his or her library.
        this.addAbility(new ThoughtLashTriggeredAbility());

        // Exile the top card of your library: Prevent the next 1 damage that would be dealt to you this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ThoughtLashPreventionEffect(), new ExileFromTopOfLibraryCost(1)));
    }

    public ThoughtLash(final ThoughtLash card) {
        super(card);
    }

    @Override
    public ThoughtLash copy() {
        return new ThoughtLash(this);
    }
}

class ThoughtLashTriggeredAbility extends TriggeredAbilityImpl {

    ThoughtLashTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ThoughtLashExileLibraryEffect(), false);
    }

    ThoughtLashTriggeredAbility(final ThoughtLashTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThoughtLashTriggeredAbility copy() {
        return new ThoughtLashTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DIDNT_PAY_CUMULATIVE_UPKEEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId() != null && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When a player doesn't pay {this}'s cumulative upkeep, that player exiles all cards from his or her library.";
    }
}

class ThoughtLashExileLibraryEffect extends OneShotEffect {

    ThoughtLashExileLibraryEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player exiles all cards from his or her library";
    }

    ThoughtLashExileLibraryEffect(final ThoughtLashExileLibraryEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtLashExileLibraryEffect copy() {
        return new ThoughtLashExileLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, controller.getLibrary().size()));
            controller.moveCards(cards, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}

class ThoughtLashPreventionEffect extends PreventionEffectImpl {

    ThoughtLashPreventionEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "Prevent the next 1 damage that would be dealt to you this turn";
    }

    ThoughtLashPreventionEffect(final ThoughtLashPreventionEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtLashPreventionEffect copy() {
        return new ThoughtLashPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE,
                source.getControllerId(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            if (damage > 0) {
                event.setAmount(damage - 1);
                this.used = true;
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE,
                        source.getControllerId(), source.getSourceId(), source.getControllerId(), 1));
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getControllerId());
    }
}
