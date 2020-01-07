package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlacialRevelation extends CardImpl {

    public GlacialRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal the top six cards of your library. You may put any number of snow permanent cards from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new GlacialRevelationEffect());
    }

    private GlacialRevelation(final GlacialRevelation card) {
        super(card);
    }

    @Override
    public GlacialRevelation copy() {
        return new GlacialRevelation(this);
    }
}

class GlacialRevelationEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPermanentCard("snow permanent cards");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    GlacialRevelationEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal the top six cards of your library. You may put any number of snow permanent cards " +
                "from among them into your hand. Put the rest into your graveyard.";
    }

    private GlacialRevelationEffect(final GlacialRevelationEffect effect) {
        super(effect);
    }

    @Override
    public GlacialRevelationEffect copy() {
        return new GlacialRevelationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        player.revealCards(source, cards, game);
        TargetCard targetCard = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, filter);
        targetCard.setNotTarget(true);
        if (player.choose(outcome, cards, targetCard, game)) {
            Cards toHand = new CardsImpl(targetCard.getTargets());
            cards.removeAll(targetCard.getTargets());
            player.moveCards(toHand, Zone.HAND, source, game);
        }
        return player.moveCards(cards, Zone.GRAVEYARD, source, game);
    }
}