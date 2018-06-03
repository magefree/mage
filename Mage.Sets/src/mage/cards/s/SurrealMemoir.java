
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 *
 * @author North
 */
public final class SurrealMemoir extends CardImpl {

    public SurrealMemoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // Return an instant card at random from your graveyard to your hand.
        this.getSpellAbility().addEffect(new SurrealMemoirEffect());
        this.addAbility(new ReboundAbility());
    }

    public SurrealMemoir(final SurrealMemoir card) {
        super(card);
    }

    @Override
    public SurrealMemoir copy() {
        return new SurrealMemoir(this);
    }
}

class SurrealMemoirEffect extends OneShotEffect {

    public SurrealMemoirEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return an instant card at random from your graveyard to your hand";
    }

    public SurrealMemoirEffect(final SurrealMemoirEffect effect) {
        super(effect);
    }

    @Override
    public SurrealMemoirEffect copy() {
        return new SurrealMemoirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            FilterCard filter = new FilterCard("instant card");
            filter.add(new CardTypePredicate(CardType.INSTANT));
            Card[] cards = player.getGraveyard().getCards(filter, game).toArray(new Card[0]);
            if (cards.length > 0) {
                Card card = cards[RandomUtil.nextInt(cards.length)];
                card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                game.informPlayers(card.getName() + "returned to the hand of" + player.getLogName());
                return true;
            }
        }
        return false;
    }
}
