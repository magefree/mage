package mage.cards.i;

import mage.abilities.Ability;
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

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ImmortalServitude extends CardImpl {

    public ImmortalServitude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W/B}{W/B}{W/B}");

        // Return each creature card with converted mana cost X from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ImmortalServitudeEffect());
    }

    private ImmortalServitude(final ImmortalServitude card) {
        super(card);
    }

    @Override
    public ImmortalServitude copy() {
        return new ImmortalServitude(this);
    }
}

class ImmortalServitudeEffect extends OneShotEffect {

    ImmortalServitudeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return each creature card with mana value X from your graveyard to the battlefield";
    }

    private ImmortalServitudeEffect(final ImmortalServitudeEffect effect) {
        super(effect);
    }

    @Override
    public ImmortalServitudeEffect copy() {
        return new ImmortalServitudeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        }
        int count = source.getManaCostsToPay().getX();
        Set<Card> cards = you.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game);
        cards.removeIf(Objects::isNull);
        cards.removeIf(card -> !card.isCreature(game));
        cards.removeIf(card -> card.getManaValue() != count);
        return you.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
