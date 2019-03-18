
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
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
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl("{2}"));
        ability.addEffect(effect);
        this.addAbility(ability);
        // Each player returns all creature cards from their graveyard to the battlefield.
        this.getSpellAbility().addEffect(effect);
    }

    public TwilightsCall(final TwilightsCall card) {
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
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game), Zone.BATTLEFIELD, source, game);
            }
        }
        return true;
    }
}
