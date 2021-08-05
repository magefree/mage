package mage.cards.m;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonologueTax extends CardImpl {

    public MonologueTax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever an opponent casts their second spell each turn, you create a Treasure token.
        this.addAbility(new MonologueTaxTriggeredAbility());
    }

    private MonologueTax(final MonologueTax card) {
        super(card);
    }

    @Override
    public MonologueTax copy() {
        return new MonologueTax(this);
    }
}

class MonologueTaxTriggeredAbility extends TriggeredAbilityImpl {

    MonologueTaxTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()));
    }

    private MonologueTaxTriggeredAbility(final MonologueTaxTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(getControllerId());
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return player != null
                && watcher != null
                && player.hasOpponent(event.getPlayerId(), game)
                && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2;
    }

    @Override
    public MonologueTaxTriggeredAbility copy() {
        return new MonologueTaxTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts their second spell each turn, you create a Treasure token.";
    }
}
