package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 *
 */
public final class TwilightsCall extends CardImpl {

    public TwilightsCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        Effect effect = new TwilightsCallEffect();
        // You may cast Twilight's Call as though it had flash if you pay {2} more to cast it.
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl<>("{2}"));
        ability.addEffect(effect);
        this.addAbility(ability);
        // Each player returns all creature cards from their graveyard to the battlefield.
        this.getSpellAbility().addEffect(effect);
    }

    private TwilightsCall(final TwilightsCall card) {
        super(card);
    }

    @Override
    public TwilightsCall copy() {
        return new TwilightsCall(this);
    }
}

class TwilightsCallEffect extends OneShotEffect {

    public TwilightsCallEffect() {
        super(Outcome.Neutral);
        staticText = "Each player returns all creature cards from their graveyard to the battlefield";
    }

    public TwilightsCallEffect(TwilightsCallEffect copy) {
        super(copy);
    }

    @Override
    public TwilightsCallEffect copy() {
        return new TwilightsCallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> toBattlefield = new HashSet<>();
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    toBattlefield.addAll(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
                }
            }

            // must happen simultaneously Rule 101.4
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, true, null);
            return true;
        }
        return false;
    }
}
