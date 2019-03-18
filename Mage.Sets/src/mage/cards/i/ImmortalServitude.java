
package mage.cards.i;

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
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class ImmortalServitude extends CardImpl {

    public ImmortalServitude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W/B}{W/B}{W/B}");

        // Return each creature card with converted mana cost X from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ImmortalServitudeEffect());
    }

    public ImmortalServitude(final ImmortalServitude card) {
        super(card);
    }

    @Override
    public ImmortalServitude copy() {
        return new ImmortalServitude(this);
    }
}

class ImmortalServitudeEffect extends OneShotEffect {

    public ImmortalServitudeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return each creature card with converted mana cost X from your graveyard to the battlefield";
    }

    public ImmortalServitudeEffect(final ImmortalServitudeEffect effect) {
        super(effect);
    }

    @Override
    public ImmortalServitudeEffect copy() {
        return new ImmortalServitudeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        int count = source.getManaCostsToPay().getX();
        Set<Card> cards = you.getGraveyard().getCards(new FilterCreatureCard(), game);
        for (Card card : cards) {
            if (card != null && card.getConvertedManaCost() == count) {
                card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
