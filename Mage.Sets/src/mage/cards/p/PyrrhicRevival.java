package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jeffwadsworth, Susucr
 */
public final class PyrrhicRevival extends CardImpl {

    public PyrrhicRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W/B}{W/B}{W/B}");

        // Each player returns each creature card from their graveyard to the battlefield with an additional -1/-1 counter on it.
        this.getSpellAbility().addEffect(new PyrrhicRevivalEffect());

    }

    private PyrrhicRevival(final PyrrhicRevival card) {
        super(card);
    }

    @Override
    public PyrrhicRevival copy() {
        return new PyrrhicRevival(this);
    }
}

class PyrrhicRevivalEffect extends OneShotEffect {

    PyrrhicRevivalEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Each player returns each creature card from their graveyard to the battlefield with an additional -1/-1 counter on it";
    }

    private PyrrhicRevivalEffect(final PyrrhicRevivalEffect effect) {
        super(effect);
    }

    @Override
    public PyrrhicRevivalEffect copy() {
        return new PyrrhicRevivalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> toBattlefield =
            game.getState().getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .flatMap(p -> p.getGraveyard().getCards(game).stream())
                .filter(c -> c != null && c.isCreature(game))
                .collect(Collectors.toSet());
        Counters counters = new Counters();
        counters.addCounter(CounterType.M1M1.createInstance());
        for (Card card : toBattlefield) {
            game.setEnterWithCounters(card.getId(), counters.copy());
        }
        controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, true, null);
        return true;
    }
}
