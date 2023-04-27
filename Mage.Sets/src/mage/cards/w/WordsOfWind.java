package mage.cards.w;

import java.util.List;
import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class WordsOfWind extends CardImpl {

    public WordsOfWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // {1}: The next time you would draw a card this turn, each player returns a permanent they control to its owner's hand instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WordsOfWindEffect(), new ManaCostsImpl<>("{1}")));
    }

    private WordsOfWind(final WordsOfWind card) {
        super(card);
    }

    @Override
    public WordsOfWind copy() {
        return new WordsOfWind(this);
    }
}

class WordsOfWindEffect extends ReplacementEffectImpl {

    public WordsOfWindEffect() {
        super(Duration.EndOfTurn, Outcome.ReturnToHand);
        staticText = "The next time you would draw a card this turn, each player returns a permanent they control to its owner's hand instead";
    }

    public WordsOfWindEffect(final WordsOfWindEffect effect) {
        super(effect);
    }

    @Override
    public WordsOfWindEffect copy() {
        return new WordsOfWindEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        game.informPlayers("Each player returns a permanent they control to its owner's hand instead");
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                TargetControlledPermanent target = new TargetControlledPermanent();
                List<Permanent> liste = game.getBattlefield().getActivePermanents(new FilterControlledPermanent(), playerId, game);
                if (!liste.isEmpty()) {
                    while (!player.choose(Outcome.ReturnToHand, target, source, game)) {
                        if (!player.canRespond()) {
                            return false;
                        }
                    }
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        player.moveCards(permanent, Zone.HAND, source, game);
                    }
                }
            }
        }
        this.used = true;
        discard();
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used) {
            return source.isControlledBy(event.getPlayerId());
        }
        return false;
    }
}
