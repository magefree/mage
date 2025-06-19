package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Sirocco extends CardImpl {

    public Sirocco(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target player reveals their hand. For each blue instant card revealed this way, that player discards that card unless they pay 4 life.
        this.getSpellAbility().addEffect(new SiroccoEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Sirocco(final Sirocco card) {
        super(card);
    }

    @Override
    public Sirocco copy() {
        return new Sirocco(this);
    }
}

class SiroccoEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("blue instant card");

    static {
        filter.add(CardType.INSTANT.getPredicate());
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    SiroccoEffect() {
        super(Outcome.Benefit);
        staticText = "target player reveals their hand. For each blue instant card revealed this way, " +
                "that player discards that card unless they pay 4 life";
    }

    private SiroccoEffect(final SiroccoEffect effect) {
        super(effect);
    }

    @Override
    public SiroccoEffect copy() {
        return new SiroccoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        player.revealCards(source, player.getHand(), game);
        Set<Card> cards = player.getHand().getCards(filter, game);
        if (cards.isEmpty()) {
            return true;
        }
        for (Card card : cards) {
            Cost cost = new PayLifeCost(4);
            if (!cost.canPay(source, source, player.getId(), game)
                    || !player.chooseUse(
                    outcome, "Pay 4 life or discard " + card.getIdName() + '?',
                    null, "Pay life", "Discard", source, game
            ) || !cost.pay(source, game, source, player.getId(), true)) {
                player.discard(card, false, source, game);
            }
        }
        return true;
    }
}
