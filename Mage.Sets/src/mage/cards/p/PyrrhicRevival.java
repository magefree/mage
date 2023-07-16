
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 *
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

    public PyrrhicRevivalEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Each player returns each creature card from their graveyard to the battlefield with an additional -1/-1 counter on it";
    }

    public PyrrhicRevivalEffect(final PyrrhicRevivalEffect effect) {
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
        Set<Card> toBattlefield = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (Card card : player.getGraveyard().getCards(game)) {
                    if (card != null && card.isCreature(game)) {
                        toBattlefield.add(card);
                    }
                }
            }
        }
        Effect returnEffect =
            new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(
                CounterType.M1M1.createInstance(),
                true,
                true);
        returnEffect.setTargetPointer(new FixedTargets(toBattlefield, game));
        returnEffect.apply(game, source);

        return true;
    }
}
