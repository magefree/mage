package mage.cards.s;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Soulquake extends CardImpl {

    public Soulquake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}{B}{B}");

        // Return all creatures on the battlefield and all creature cards in graveyards to their owners' hands.
        this.getSpellAbility().addEffect(new SoulquakeEffect());
    }

    private Soulquake(final Soulquake card) {
        super(card);
    }

    @Override
    public Soulquake copy() {
        return new Soulquake(this);
    }
}

class SoulquakeEffect extends OneShotEffect {

    private static final FilterCreatureCard filter2 = new FilterCreatureCard("creature");

    public SoulquakeEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return all creatures on the battlefield and all creature cards in graveyards to their owners' hands";
    }

    public SoulquakeEffect(final SoulquakeEffect effect) {
        super(effect);
    }

    @Override
    public SoulquakeEffect copy() {
        return new SoulquakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToHand = new LinkedHashSet<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
            cardsToHand.add((Card) permanent);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (Card card : player.getGraveyard().getCards(filter2, game)) {
                    cardsToHand.add(card);
                }
            }
        }
        return controller.moveCards(cardsToHand, Zone.HAND, source, game);
    }
}
