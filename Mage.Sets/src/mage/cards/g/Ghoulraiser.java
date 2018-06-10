
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 *
 * @author North
 */
public final class Ghoulraiser extends CardImpl {

    public Ghoulraiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Ghoulraiser enters the battlefield, return a Zombie card at random from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GhoulraiserEffect(), false));
    }

    public Ghoulraiser(final Ghoulraiser card) {
        super(card);
    }

    @Override
    public Ghoulraiser copy() {
        return new Ghoulraiser(this);
    }
}

class GhoulraiserEffect extends OneShotEffect {

    public GhoulraiserEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return a Zombie card at random from your graveyard to your hand";
    }

    public GhoulraiserEffect(final GhoulraiserEffect effect) {
        super(effect);
    }

    @Override
    public GhoulraiserEffect copy() {
        return new GhoulraiserEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            FilterCard filter = new FilterCard("Zombie card");
            filter.add(new SubtypePredicate(SubType.ZOMBIE));
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
