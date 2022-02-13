package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeveredSuspicion extends CardImpl {

    public FeveredSuspicion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}{R}");

        // Each opponent exiles cards from the top of their library until they exile a nonland card. You may cast any number of spells from among those nonland cards without paying their mana costs.
        this.getSpellAbility().addEffect(new FeveredSuspicionEffect());

        // Rebound
        this.addAbility(new ReboundAbility());
    }

    private FeveredSuspicion(final FeveredSuspicion card) {
        super(card);
    }

    @Override
    public FeveredSuspicion copy() {
        return new FeveredSuspicion(this);
    }
}

class FeveredSuspicionEffect extends OneShotEffect {

    FeveredSuspicionEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent exiles cards from the top of their library until they exile a nonland card. " +
                "You may cast any number of spells from among those nonland cards without paying their mana costs";
    }

    private FeveredSuspicionEffect(final FeveredSuspicionEffect effect) {
        super(effect);
    }

    @Override
    public FeveredSuspicionEffect copy() {
        return new FeveredSuspicionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Cards nonlands = new CardsImpl();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            for (Card card : opponent.getLibrary().getCards(game)) {
                cards.add(card);
                if (!card.isLand(game)) {
                    nonlands.add(card);
                    break;
                }
            }
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        nonlands.retainZone(Zone.EXILED, game);
        CardUtil.castMultipleWithAttributeForFree(
                controller, source, game, nonlands,
                StaticFilters.FILTER_CARD
        );
        return true;
    }
}
