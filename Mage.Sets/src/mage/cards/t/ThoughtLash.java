
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromTopOfLibraryCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class ThoughtLash extends CardImpl {

    public ThoughtLash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // Cumulative upkeep - Exile the top card of your library.
        this.addAbility(new CumulativeUpkeepAbility(new ExileFromTopOfLibraryCost(1)));

        // When a player doesn't pay Thought Lash's cumulative upkeep, that player exiles all cards from their library.
        this.addAbility(new ThoughtLashTriggeredAbility());

        // Exile the top card of your library: Prevent the next 1 damage that would be dealt to you this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToControllerEffect(Duration.EndOfTurn, 1), new ExileFromTopOfLibraryCost(1)));
    }

    private ThoughtLash(final ThoughtLash card) {
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

    private ThoughtLashTriggeredAbility(final ThoughtLashTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThoughtLashTriggeredAbility copy() {
        return new ThoughtLashTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIDNT_PAY_CUMULATIVE_UPKEEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId() != null && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When a player doesn't pay {this}'s cumulative upkeep, that player exiles all cards from their library.";
    }
}

class ThoughtLashExileLibraryEffect extends OneShotEffect {

    ThoughtLashExileLibraryEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player exiles all cards from their library";
    }

    private ThoughtLashExileLibraryEffect(final ThoughtLashExileLibraryEffect effect) {
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
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, controller.getLibrary().size()));
            controller.moveCards(cards, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}
