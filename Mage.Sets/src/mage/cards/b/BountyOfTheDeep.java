package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BountyOfTheDeep extends CardImpl {

    public BountyOfTheDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // If you have no land cards in your hand, seek a land card and a nonland card. Otherwise, seek two nonland cards.
        this.getSpellAbility().addEffect(new BountyOfTheDeepEffect());
    }

    private BountyOfTheDeep(final BountyOfTheDeep card) {
        super(card);
    }

    @Override
    public BountyOfTheDeep copy() {
        return new BountyOfTheDeep(this);
    }
}

class BountyOfTheDeepEffect extends OneShotEffect {

    BountyOfTheDeepEffect() {
        super(Outcome.Benefit);
        staticText = "if you have no land cards in your hand, " +
                "seek a land card and a nonland card. Otherwise, seek two nonland cards";
    }

    private BountyOfTheDeepEffect(final BountyOfTheDeepEffect effect) {
        super(effect);
    }

    @Override
    public BountyOfTheDeepEffect copy() {
        return new BountyOfTheDeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.getHand().count(StaticFilters.FILTER_CARD_LAND, game) < 1) {
            player.seekCard(StaticFilters.FILTER_CARD_LAND, source, game);
        } else {
            player.seekCard(StaticFilters.FILTER_CARD_NON_LAND, source, game);
        }
        player.seekCard(StaticFilters.FILTER_CARD_NON_LAND, source, game);
        return true;
    }
}
