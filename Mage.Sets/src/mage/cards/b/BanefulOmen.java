
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BanefulOmen extends CardImpl {

    public BanefulOmen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}{B}");

        // At the beginning of your end step, you may reveal the top card of your library. If you do, each opponent loses life equal to that card's converted mana cost.
        this.addAbility(new BanefulOmenTriggeredAbility());
    }

    private BanefulOmen(final BanefulOmen card) {
        super(card);
    }

    @Override
    public BanefulOmen copy() {
        return new BanefulOmen(this);
    }

    class BanefulOmenTriggeredAbility extends TriggeredAbilityImpl {

        BanefulOmenTriggeredAbility() {
            super(Zone.BATTLEFIELD, new BanefulOmenEffect(), true);
        }

        private BanefulOmenTriggeredAbility(BanefulOmenTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public BanefulOmenTriggeredAbility copy() {
            return new BanefulOmenTriggeredAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return event.getPlayerId().equals(this.controllerId);
        }

        @Override
        public String getRule() {
            return "At the beginning of your end step, you may reveal the top card of your library. If you do, each opponent loses life equal to that card's mana value.";
        }
    }

    static class BanefulOmenEffect extends OneShotEffect {

        BanefulOmenEffect() {
            super(Outcome.Benefit);
        }

        private BanefulOmenEffect(final BanefulOmenEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                return false;
            }
            if (!player.getLibrary().hasCards()) {
                return false;
            }
            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            Cards cards = new CardsImpl(card);
            player.revealCards("Baneful Omen", cards, game);


            int loseLife = card.getManaValue();
            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            for (UUID opponentUuid : opponents) {
                Player opponent = game.getPlayer(opponentUuid);
                if (opponent != null) {
                    opponent.loseLife(loseLife, game, source, false);
                }
            }
            return true;
        }

        @Override
        public BanefulOmenEffect copy() {
            return new BanefulOmenEffect(this);
        }
    }
}
