package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;
import mage.cards.Cards;
import mage.cards.CardsImpl;

/**
 * @author LevelX2
 */
public final class SereneRemembrance extends CardImpl {

    public SereneRemembrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Shuffle Serene Remembrance and up to three target cards from a single graveyard into their owners' libraries.
        this.getSpellAbility().addEffect(new SereneRemembranceEffect());
        this.getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0, 3, StaticFilters.FILTER_CARD_CARDS));

    }

    private SereneRemembrance(final SereneRemembrance card) {
        super(card);
    }

    @Override
    public SereneRemembrance copy() {
        return new SereneRemembrance(this);
    }
}

class SereneRemembranceEffect extends OneShotEffect {

    public SereneRemembranceEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle {this} and up to three target cards from a single graveyard into their owners' libraries";
    }

    private SereneRemembranceEffect(final SereneRemembranceEffect effect) {
        super(effect);
    }

    @Override
    public SereneRemembranceEffect copy() {
        return new SereneRemembranceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                controller.shuffleCardsToLibrary(card, game, source);
            }
            Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
            if (!cards.isEmpty()) {
                Player owner = game.getPlayer(cards.getCards(game).iterator().next().getOwnerId());
                if (owner != null) {
                    owner.shuffleCardsToLibrary(cards, game, source);
                }
            }
            return true;
        }
        return false;
    }
}
