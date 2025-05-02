package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CacheGrab extends CardImpl {

    public CacheGrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Mill four cards. You may put a permanent card from among the cards milled this way into your hand. If you control a Squirrel or returned a Squirrel card to your hand this way, create a Food token.
        this.getSpellAbility().addEffect(new CacheGrabEffect());
    }

    private CacheGrab(final CacheGrab card) {
        super(card);
    }

    @Override
    public CacheGrab copy() {
        return new CacheGrab(this);
    }
}

class CacheGrabEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SQUIRREL);

    CacheGrabEffect() {
        super(Outcome.Benefit);
        staticText = "mill four cards. You may put a permanent card from among the cards milled this way into your " +
                "hand. If you control a Squirrel or returned a Squirrel card to your hand this way, create a Food token";
    }

    private CacheGrabEffect(final CacheGrabEffect effect) {
        super(effect);
    }

    @Override
    public CacheGrabEffect copy() {
        return new CacheGrabEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(4, source, game);
        TargetCard target = new TargetCard(
                0, 1, Zone.ALL, StaticFilters.FILTER_CARD_PERMANENT
        );
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        player.moveCards(card, Zone.HAND, source, game);
        if ((card != null && card.hasSubtype(SubType.SQUIRREL, game))
                || game.getBattlefield().contains(filter, source.getControllerId(), source, game, 1)) {
            new FoodToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
